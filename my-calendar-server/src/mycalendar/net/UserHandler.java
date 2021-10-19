package mycalendar.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import mycalendar.db.DBEventsHandler;
import mycalendar.db.DBEventsTypesHandler;
import mycalendar.db.DBUsersHandler;
import mycalendar.model.CalendarEvent;
import mycalendar.model.User;
import mycalendar.util.RegistrationValidator;

public class UserHandler implements Runnable {

	private Socket socket = null;
	private User user = null;
	private Integer userId = null;
	private List<CalendarEvent> userEvents = null;
	
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	
	
	public UserHandler(Socket socket) throws IOException {
		this.socket = socket;
		System.out.println("[UserHandler] New connection from: " + socket.getInetAddress());
		out = new ObjectOutputStream(socket.getOutputStream());
	}
	
	
	@Override
	public void run() {
		
		System.out.println("[UserHandler] Connected with a Client. Waiting for a request...");
		
		try {
			
			in = new ObjectInputStream(socket.getInputStream());

			String request = (String) in.readObject();
			
			if (request.equals(Protocol.USER_LOGIN_REQUEST)) {
				
				System.out.println("[UserHandler] [LOGIN REQUEST] Login request from: " + socket.getInetAddress());
				if (!handleLoginRequest()) {
					System.err.println("[UserHandler] [LOGIN REQUEST] [ERROR] Login failed. Requested from " + socket.getInetAddress());
					sendMessage(Protocol.USER_AUTHENTICATION_ERROR);
					closeStreams();
					return;
				}
				
				// Here: User logged successfully
				System.out.println("[UserHandler] [LOGIN REQUEST] User " + user.getUsername() + " logged. Address: " + socket.getInetAddress());
				sendMessage(Protocol.OK);
				sendMessage(this.user);
				
			} else if (request.equals(Protocol.USER_REGISTRATION_REQUEST)) {
				
				System.out.println("[UserHandler] [REGISTRATION REQUEST] Registration request from: " + socket.getInetAddress());
				String registrationStatus = handleRegistrationRequest();
				sendMessage(registrationStatus);
				closeStreams();
				return;
				
			} else {
				
				// Here: No LOGIN, no REGITRATION --> Error
				System.err.println("[UserHandler] [UNKNOWN REQUEST] [ERROR] Unknown request from: " + socket.getInetAddress());
				
				sendMessage(Protocol.ERROR);
				closeStreams();
				return;
				
			}
			
			if (userId == null) {
				System.err.println("[UserHandler] [ERROR] User's id is null. From: " + socket.getInetAddress());
				sendMessage(Protocol.ERROR);
				closeStreams();
				return;
			}
			
			// Here: User is online
			System.out.println("[UserHandler] [OK] User " + user.getUsername() + " logged in!");
			
			// Send User's data
			userEvents = DBEventsHandler.getInstance().getUserEvents(this.user);
			sendMessage(userEvents);
			System.out.println("[UserHandler] Sent data to: " + user.getUsername() + ". Total of events: " + userEvents.size());
			
			while(!Thread.currentThread().isInterrupted()) {
				
				System.out.println("[UserHandler] [WAITING] Waiting for a request from user '" + user.getUsername() + "'.");
				request = (String) in.readObject();
				
				String res = null;
				
				if (request.equals(Protocol.ADD_EVENT_REQUEST)) {
					System.out.println("[UserHandler] [ADD EVENT REQUEST from " + user.getUsername() + "] Processing request...");
					res = handleAddEventRequest();
					sendMessage(res);
					if (res.equals(Protocol.OK))
						sendMessage(userEvents);
					System.out.println("[UserHandler] [ADD EVENT REQUEST from " + user.getUsername() + "] Request status: " + res);
				}
				
				if (request.equals(Protocol.EDIT_EVENT_REQUEST)) {
					System.out.println("[UserHandler] [EDIT EVENT REQUEST from " + user.getUsername() + "] Processing request...");
					res = handleEditEventRequest();
					sendMessage(res);
					if (res.equals(Protocol.OK))
						sendMessage(userEvents);
					System.out.println("[UserHandler] [EDIT EVENT REQUEST from " + user.getUsername() + "] Request status: " + res);
				}
				
				if (request.equals(Protocol.DELETE_EVENT_REQUEST)) {
					System.out.println("[UserHandler] [DELETE EVENT REQUEST from " + user.getUsername() + "] Processing request...");
					res = handDeleteEventRequest();
					sendMessage(res);
					if (res.equals(Protocol.OK))
						sendMessage(userEvents);
					System.out.println("[UserHandler] [DELETE EVENT REQUEST from " + user.getUsername() + "] Request status: " + res);
				}
				
				
				
			}
			
			
		} catch (Exception e) {
			
			OnlineUsersHandler.removeUser(userId);
			
			System.err.println("[UserHandler] [ERROR] Internal error. Possible error: User disconnected or already logged in.");
			
			try {
				closeStreams();
			} catch (IOException e1) {
				System.err.println("[UserHandler] [ERROR] Internal error while closing the streams.");
			}
			return;
		}
		
	} // end run


	/*
	 * Reads a User object from the ObjectOutputStream of the Client.
	 * If the Client did not send a User object, or the connection failed,
	 * this will throw an exception and close the streams.
	 * It the Client sent a User object but some required fields are null,
	 * it will return false.
	 * Otherwise, it returns true.
	 * */
	private boolean readUser() {

		try {

			this.user = (User) in.readObject();
			if (user == null || user.getUsername() == null || user.getPassword() == null)
				return false;
			
		} catch (Exception e) {

			System.err.println("[UserHandler] [ERROR] in handleLoginRequest():");
			e.printStackTrace();
			try {
				closeStreams();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;

		}

		return true;
		
	} // end readUser


	private boolean handleLoginRequest() {

		try {
			
			String username = (String) in.readObject();
			String password = (String) in.readObject();
			
			if (DBUsersHandler.getInstance().checkUserCredentials(username, password)) {
				
				userId = DBUsersHandler.getInstance().getUserId(username);
				user = DBUsersHandler.getInstance().getUserByID(userId);	

				// Check if user is already logged in.			
				if (!OnlineUsersHandler.insertUser(userId, user)) {
					userId = null;
					user = null;
					sendMessage(Protocol.USER_ALREADY_LOGGED_IN_ERROR);
					closeStreams();
					return false;
				}
				
				return true;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	/**
	 * Reads a User from the Client and, if the data are valid,
	 * checks if the User can be registered.
	 *
	 * Returns a String from the Protocol class,
	 * after the RegistrationValidator class checks user's fields.
	 * If some fields are not valid, it sends a String with informations
	 * about the requirement the User is not satisfying.
	 * Otherwise, this will return Protocol.OK.
	 * 
	 */
	private String handleRegistrationRequest() {
		
		// Check data
		if (!readUser())
			return Protocol.ERROR;
		
		// Check requirements
		String status = RegistrationValidator.validateUser(user);
		if (!status.equals(Protocol.OK))
			return status;

		// Check database has been updated
		if (!DBUsersHandler.getInstance().registerUser(user))
			return Protocol.ERROR;
		
		return Protocol.OK;
			
	} // handleRegistrationRequest
	
	
	private String handleAddEventRequest() {
		
		String res = null;
		
		try {
			
			CalendarEvent newEvent = (CalendarEvent) in.readObject();
			res = DBEventsHandler.getInstance().insertEvent(newEvent, user) ? Protocol.OK : Protocol.ERROR;
			if (res.equals(Protocol.OK))
				userEvents = DBEventsHandler.getInstance().getUserEvents(this.user);
			
		} catch (Exception e) {
			System.err.println("[UserHandler] [ERROR] Error in handleAddEventRequest():");
			e.printStackTrace();
			return Protocol.ERROR;
		}
		
		
		return res;
		
	}
	
	private String handleEditEventRequest() {

		String res = null;
		
		try {
			
			CalendarEvent oldEvent = (CalendarEvent) in.readObject();
			CalendarEvent editedEvent = (CalendarEvent) in.readObject();
			res = DBEventsHandler.getInstance().editEvent(oldEvent, editedEvent, user) ? Protocol.OK : Protocol.ERROR;
			if (res.equals(Protocol.OK))
				userEvents = DBEventsHandler.getInstance().getUserEvents(this.user);
			
		} catch (Exception e) {
			System.err.println("[UserHandler] [ERROR] Error in handleEditEventRequest():");
			e.printStackTrace();
			return Protocol.ERROR;
		}
		
		
		return res;
		
	}
	
	private String handDeleteEventRequest() {

		String res = null;
		
		try {
			
			CalendarEvent event = (CalendarEvent) in.readObject();
			Integer eventTypeId = DBEventsHandler.getInstance().deleteEvent(event, user);
			if (eventTypeId != null) {
				res = DBEventsTypesHandler.getInstance().deleteEventType(eventTypeId) ? Protocol.OK : Protocol.ERROR;
			}
			if (res.equals(Protocol.OK))
				userEvents = DBEventsHandler.getInstance().getUserEvents(this.user);
			
		} catch (Exception e) {
			System.err.println("[UserHandler] [ERROR] Error in handleDeleteEventRequest():");
			e.printStackTrace();
			return Protocol.ERROR;
		}
		
		
		return res;
		
	}

	
	public void sendMessage(Object message) {
		
		if (out == null)
			return;
		
		try {
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			
		}
		
	} // sendMessage
	
	public void closeStreams() throws IOException {
			
		if (in != null)
			in.close();
		in = null;
		
		if (out != null)
			out.close();
		out = null;
		
		if (socket != null)
			socket.close();
		socket = null;
			
	}

	
} // class UserHandler
