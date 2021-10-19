package mycalendar.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import mycalendar.db.util.DBHelper;
import mycalendar.model.CalendarEvent;
import mycalendar.model.CalendarEventType;
import mycalendar.model.User;

public class DBEventsHandler {

	private Connection con = null;

	public synchronized boolean insertEvent(CalendarEvent event, User user) {

		con = null;
		PreparedStatement p = null;
		
		try {
			
			Integer userId = DBUsersHandler.getInstance().getUserId(user.getUsername());
			if (userId == null) {
				System.err.println("[DBEventsHandler] Error in insertEvent(): userId is null");
				return false;
			}
			
			Integer eventTypeId = null;
			if (DBEventsTypesHandler.getInstance().insertEventType(event.getType(), user))
				eventTypeId = DBEventsTypesHandler.getInstance().getEventTypeId(event.getType(), user);
			
			if (eventTypeId == null) {
				return false;
			}
			
			con = DBHelper.getConnection();
			if (!DBHelper.checkConnection(con)) {
				System.out.println("[DBEventsHandler] Connection error");
				return false;
			}

			String query = "INSERT INTO Events VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
			p = con.prepareStatement(query);
			p.setNull(1, Types.INTEGER);
			p.setString(2, event.getTitle());
			p.setString(3, event.getDescription());
			p.setString(4, event.getStartDate());
			p.setString(5, event.getEndDate());
			p.setInt(6, eventTypeId);
			p.setString(7, event.getLocality());
			p.setInt(8, userId);

			p.executeUpdate();
			
		} catch (SQLException e) {
			
			System.err.println("[DBEventsHandler] Error in insertEvent()");
			e.printStackTrace();
			return false;
			
		} finally {
			
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}

		return true;

	} // end insertEvent
	
	
	public boolean editEvent(CalendarEvent oldEvent, CalendarEvent editedEvent, User user) {
		
		con = null;
		PreparedStatement p = null;
		
		try {
			
			Integer userId = DBUsersHandler.getInstance().getUserId(user.getUsername());			
			Integer eventTypeId = getEventTypeId(oldEvent, userId);
			Integer eventId = getEventId(oldEvent, eventTypeId, userId);

			con = DBHelper.getConnection();
			if (!DBHelper.checkConnection(con)) {
				System.out.println("[DBEventsHandler] Connection error");
				return false;
			}

			String query = "UPDATE Events SET Title=?, Description=?, Date_start=?, Date_end=?, Event_type=?, Locality=? WHERE User=? AND Id=?;";
			p = con.prepareStatement(query);
			p.setString(1, editedEvent.getTitle());
			p.setString(2, editedEvent.getDescription());
			p.setString(3, editedEvent.getStartDate());
			p.setString(4, editedEvent.getEndDate());
			p.setInt(5, eventTypeId);
			p.setString(6, editedEvent.getLocality());
			p.setInt(7, userId);
			p.setInt(8, eventId);

			p.executeUpdate();
			
		} catch (SQLException e) {
			
			System.err.println("[DBEventsHandler] Error in editEvent()");
			e.printStackTrace();
			return false;
			
		} finally {
			
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}

		return true;
	}
	
	public Integer deleteEvent(CalendarEvent event, User user) {
		
		con = null;
		PreparedStatement p = null;
		
		Integer userId = null;	
		Integer eventTypeId = null;
		Integer eventId = null;
		
		try {
			
			userId = DBUsersHandler.getInstance().getUserId(user.getUsername());			
			eventTypeId = getEventTypeId(event, userId);
			eventId = getEventId(event, eventTypeId, userId);

			con = DBHelper.getConnection();
			if (!DBHelper.checkConnection(con)) {
				System.out.println("[DBEventsHandler] Connection error");
				return null;
			}

			String query = "DELETE FROM Events WHERE Id=?;";
			p = con.prepareStatement(query);
			p.setInt(1, eventId);

			p.executeUpdate();
			
		} catch (SQLException e) {
			
			System.err.println("[DBEventsHandler] Error in deleteEvent()");
			e.printStackTrace();
			return null;
			
		} finally {
			
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}

		System.out.println("EventId " + eventId + ". EventTypeId " + eventTypeId);
		return eventTypeId;
	}

	private Integer getEventId(CalendarEvent event, Integer eventTypeId, Integer userId) {
		
		Integer eventId = null;
		
		con = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		
		con = DBHelper.getConnection();
		if (!DBHelper.checkConnection(con))
			return null;
		
		try {

			String query = "SELECT Id FROM Events WHERE Title=? AND Date_start=? AND Date_end=? AND Event_type=? AND User=?;";
			
			p = con.prepareStatement(query);
			p.setString(1, event.getTitle());
			p.setString(2, event.getStartDate());
			p.setString(3, event.getEndDate());
			p.setInt(4, eventTypeId);
			p.setInt(5, userId);
			rs = p.executeQuery();
			
			if (rs.next())
				eventId = rs.getInt("Id");
			
				
		} catch (SQLException e) {
			
			System.err.println("[DBEventsHandler] Error in getEventId():");
			e.printStackTrace();
			return null;
			
		} finally {
			
			DBHelper.closeResultSet(rs);
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}
		
		return eventId;
	}


	private Integer getEventTypeId(CalendarEvent event, Integer userId) {

		Integer eventTypeId = null;
		
		con = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		
		con = DBHelper.getConnection();
		if (!DBHelper.checkConnection(con))
			return null;
		
		String query = "SELECT Event_type FROM Events WHERE Title=? AND Date_start=? AND Date_end=? AND User=?;";
		
		try {
			
			p = con.prepareStatement(query);
			p.setString(1, event.getTitle());
			p.setString(2, event.getStartDate());
			p.setString(3, event.getEndDate());
			p.setInt(4, userId);
			rs = p.executeQuery();
			
			if (rs.next())
				eventTypeId = rs.getInt("Event_type");
			
				
		} catch (SQLException e) {
			
			System.err.println("[DBEventsHandler] Error in getUserEvents()");
			return null;
			
		} finally {
			
			DBHelper.closeResultSet(rs);
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}
		
		return eventTypeId;
		
	}


	@Deprecated
	public synchronized boolean eventExists(CalendarEvent event, User user) {

		con = null;
		ResultSet rs = null;
		PreparedStatement p = null;
		
		boolean result = false;
		
		try {
			
			con = DBHelper.getConnection();
			if (!DBHelper.checkConnection(con))
				return false;
			
			String query = "SELECT * FROM Events WHERE Title=? AND Date_start=? AND Date_end=? AND User=?;";
			p = con.prepareStatement(query);
			p.setString(1, event.getTitle());
			p.setString(2, event.getStartDate());
			p.setString(3, event.getEndDate());
			p.setString(4, user.getUsername());

			rs = p.executeQuery();
			result = rs.next();
			
		} catch (SQLException e) {
			
			System.err.println("[DBEventsHandler] Error in eventExists():");
			e.printStackTrace();
			return false;
			
		} finally {
			
			DBHelper.closeResultSet(rs);
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}

		return result;

	} // end eventExists
	
	public synchronized List<CalendarEvent> getUserEvents(User user) {
		
		List<CalendarEvent> userEvents = new ArrayList<CalendarEvent>();
		
		con = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		
		Integer userId = DBUsersHandler.getInstance().getUserId(user.getUsername());
		if (userId == null)
			return null;
		
		con = DBHelper.getConnection();
		if (!DBHelper.checkConnection(con))
			return null;
		
		String query = "SELECT * FROM Events WHERE User=?;";
		
		try {
			
			p = con.prepareStatement(query);
			p.setInt(1, userId);
			rs = p.executeQuery();
			
			while (rs.next()) {
				
				String title = rs.getString("Title");
				String description = rs.getString("Description");
				String dateStart = rs.getString("Date_start");
				String dateEnd = rs.getString("Date_end");
				
				Integer eventTypeId = rs.getInt("Event_type");
				CalendarEventType eventType = DBEventsTypesHandler.getInstance().getEventType(eventTypeId);
				
				String locality = rs.getString("Locality");
				
				CalendarEvent event = new CalendarEvent(title, description, dateStart, dateEnd, eventType, locality);
				
				userEvents.add(event);
				
			}
			
				
		} catch (SQLException e) {
			
			System.err.println("[DBEventsHandler] Error in getUserEvents()");
			e.printStackTrace();
			return null;
			
		} finally {
			
			DBHelper.closeResultSet(rs);
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}
		
		return userEvents;
		
	}
 
	
	private static DBEventsHandler instance = null;

	public static DBEventsHandler getInstance() {
		if (instance == null)
			instance = new DBEventsHandler();
		return instance;
	}

	private DBEventsHandler() {
	}

} // class DBEventsHandler
