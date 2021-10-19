package mycalendar.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import mycalendar.db.util.DBHelper;
import mycalendar.model.CalendarEventType;
import mycalendar.model.User;

public class DBEventsTypesHandler {

	private Connection con = null;

	public synchronized boolean eventTypeExists(CalendarEventType eventType, User user) {
		
		con = null;
		PreparedStatement p = null;
		
		if (!DBUsersHandler.getInstance().checkUserCredentials(user.getUsername(), user.getPassword()))
			return false;
		
		boolean result = false;
			
		try {
			
			Integer userId = DBUsersHandler.getInstance().getUserId(user.getUsername());
			if (userId == null)
				return false;
			
			con = DBHelper.getConnection();
			if (!DBHelper.checkConnection(con))
				return false;

			String query = "SELECT * FROM Events_types WHERE Title=? AND User_id=?;";
			p = con.prepareStatement(query);
			p.setString(1, eventType.getTitle());
			p.setInt(2, userId);

			result = p.executeQuery().next();
			
		} catch (SQLException e) {
			
			System.err.println("[DBEventsType] Error in eventTypeExists()");
			e.printStackTrace();
			return false;
			
		} finally {

			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}

		return result;

	}


	public synchronized boolean insertEventType(CalendarEventType eventType, User user) {
		
		con = null;
		PreparedStatement p = null;
		
		try {

			Integer userId = DBUsersHandler.getInstance().getUserId(user.getUsername());
			if (userId == null) {
				System.err.println("[DBEventsTypesHandler] User not found.");
				return false; 
			}
			
			con = DBHelper.getConnection();
			if (!DBHelper.checkConnection(con))
				return false;

			String query = "INSERT INTO Events_types VALUES(?, ?, ?, ?);";
			p = con.prepareStatement(query);

			p.setNull(1, Types.INTEGER);
			p.setString(2, eventType.getTitle());
			p.setString(3, eventType.getColor());
			p.setInt(4, userId);

			p.executeUpdate();

		} catch (SQLException e) {
			
			System.err.println("[DBEventsTypesHandler] Error in insertEventType()");
			e.printStackTrace();
			return false;
			
		} finally {
			
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}

		return true;
	}
	
	public boolean deleteEventType(Integer eventTypeId) {

		con = null;
		PreparedStatement p = null;
		ResultSet rs = null;
			
		try {
			
			con = DBHelper.getConnection();
			if (!DBHelper.checkConnection(con))
				return false;

			String query = "DELETE FROM Events_types WHERE Id=?;";
			p = con.prepareStatement(query);
			p.setInt(1, eventTypeId);

			p.executeUpdate();
			
		} catch (SQLException e) {
			
			System.out.println("[DBEventsType] Error in deleteEventType()");
			e.printStackTrace();
			return false;
			
		} finally {

			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			DBHelper.closeResultSet(rs);
			
		}

		return true;
		
	}

	public synchronized Integer getEventTypeId(CalendarEventType eventType, User user) {
		
		con = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		
		Integer eventTypeId = null;
		
		try {
			
			Integer userId = DBUsersHandler.getInstance().getUserId(user.getUsername());
			if (userId == null)
				return null;
			
			con = DBHelper.getConnection();
			if (!DBHelper.checkConnection(con))
				return null;
			
			String query = "SELECT * FROM Events_types WHERE Title=? AND User_id=?;";
			p = con.prepareStatement(query);
			p.setString(1, eventType.getTitle());
			p.setInt(2, userId);
			
			rs = p.executeQuery();
			eventTypeId = null;
			while (rs.next()) // ----> Last one
				eventTypeId = rs.getInt("Id");
			
		} catch (SQLException e) {
			
			System.err.println("[DBEventsTypesHandler] Error in getEventTypeId()");
			e.printStackTrace();
			return null;
			
		} finally {
			
			DBHelper.closeResultSet(rs);
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}
		
		return eventTypeId;
		
	}
	
	public CalendarEventType getEventType(Integer eventTypeId) {
		
		CalendarEventType eventType = null;
		
		con = DBHelper.getConnection();
		if (!DBHelper.checkConnection(con))
			return null;

		String query = "SELECT * FROM Events_types WHERE Id=?;";
		PreparedStatement p = null;
		ResultSet rs = null;
		
		try {
			
			p = con.prepareStatement(query);
			p.setInt(1, eventTypeId);
			
			rs = p.executeQuery();
			
			if (rs.next()) {
				String title = rs.getString("Title");
				String color = rs.getString("Color");
				eventType = new CalendarEventType(title, color);
			}
			
		} catch (SQLException e) {
			
			System.err.println("[DBEventsTypesHandler] Error in getEventType()");
			e.printStackTrace();
			return null;
			
		} finally {
			
			DBHelper.closeResultSet(rs);
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}
		
		return eventType;
		
	}

	private static DBEventsTypesHandler instance = null;

	public static DBEventsTypesHandler getInstance() {
		if (instance == null)
			instance = new DBEventsTypesHandler();
		return instance;
	}

	private DBEventsTypesHandler() {
		
	}


} // class DBEventsTypesHandler
