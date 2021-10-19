package mycalendar.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import mycalendar.model.CalendarEvent;
import mycalendar.model.User;

public class Client {

	private Socket socket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	
	@Getter
	private User user = null;
	
	@Getter
	private List<CalendarEvent> userEvents = null;
	
	public String login(@NonNull String username, @NonNull String password) throws ClassNotFoundException, IOException {
		
		sendMessage(Protocol.USER_LOGIN_REQUEST);
		sendMessage(username);
		sendMessage(password);
		
		String res = (String) in.readObject();
		if (res.equals(Protocol.OK)) {
			this.user = (User) in.readObject();
		}
		
		return res;
		
	}
	
	public String register(User newUser) throws ClassNotFoundException, IOException {
		
		sendMessage(Protocol.USER_REGISTRATION_REQUEST);
		sendMessage(newUser);
		
		String res = (String) in.readObject();
		return res;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public String addEvent(CalendarEvent newEvent) throws ClassNotFoundException, IOException {
		
		if (user == null)
			return Protocol.ERROR;
		
		sendMessage(Protocol.ADD_EVENT_REQUEST);
		sendMessage(newEvent);
		
		String res = (String) in.readObject();
		if (res.equals(Protocol.OK)) {
			userEvents = (List<CalendarEvent>) in.readObject();
			orderUserEvents();
		}
		
		return res;
		
	}
	
	@SuppressWarnings("unchecked")
	public String editEvent(CalendarEvent oldEvent, CalendarEvent newEvent) throws ClassNotFoundException, IOException {
		
		if (user == null)
			return Protocol.ERROR;
		
		sendMessage(Protocol.EDIT_EVENT_REQUEST);
		sendMessage(oldEvent);
		sendMessage(newEvent);
		
		String res = (String) in.readObject();
		if (res.equals(Protocol.OK)) {
			userEvents = (List<CalendarEvent>) in.readObject();
			orderUserEvents();
		}
		return res;
		
	}
	
	@SuppressWarnings("unchecked")
	public String deleteEvent(CalendarEvent event) throws ClassNotFoundException, IOException {
		
		if (user == null)
			return Protocol.ERROR;
		
		sendMessage(Protocol.DELETE_EVENT_REQUEST);
		sendMessage(event);
		
		String res = (String) in.readObject();
		if (res.equals(Protocol.OK)) {
			userEvents = (List<CalendarEvent>) in.readObject();
			orderUserEvents();
		}
		return res;
		
	}
	
	private void orderUserEvents() {
		userEvents.sort(new Comparator<CalendarEvent>() {
			@Override
			public int compare(CalendarEvent o1, CalendarEvent o2) {
				return o1.getStartDate().compareTo(o2.getStartDate());
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void initCalendarEvents() {
		
		if (user == null) 
			return;
		
		this.userEvents = new ArrayList<CalendarEvent>();
		
		try {
			
		    userEvents = (List<CalendarEvent>) in.readObject();
		    orderUserEvents();
			//System.out.println("[CLIENT] " + userEvents.size() + " events received from server.");
			
		} catch (Exception e) {
			
			System.err.println("[CLIENT] Error in initCalendarEvents():");
			e.printStackTrace();
			
		}
		
	}

	public void sendMessage(Object message) {
		
		try {
			
			if (socket == null || out == null) {
				System.err.println("[CLIENT] Error in sendMessage(): socket or out is/are null.");
				closeStreams();
				return;
			}

			out.writeObject(message);
			out.flush();

		} catch (IOException e) {

			System.err.println("[CLIENT] Error in sendMessage():");
			e.printStackTrace();

		}

	} // sendMessage

	public void reset() {

		closeStreams();
		instance = null;
		user = null;

	}

	private void closeStreams() {

		try {

			if (in != null)
				in.close();

			if (out != null)
				out.close();

			if (socket != null)
				socket.close();

		} catch (IOException e) {

			System.err.println("[CLIENT] Error in closeStreams():");
			e.printStackTrace();

		}

		in = null;
		out = null;
		socket = null;

	}

	private static Client instance = null;

	public static Client getInstance() {
		if (instance == null)
			instance = new Client();
		return instance;
	}

	private Client() {

		try {
			socket = new Socket("localhost", 8000);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream()); // TODO: Check if it can be instantiated here.
		} catch (Exception e) {
			
		}

	}

	
	

} // class Client
