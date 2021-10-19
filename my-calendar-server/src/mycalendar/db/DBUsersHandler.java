package mycalendar.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.security.crypto.bcrypt.BCrypt;

import mycalendar.db.util.DBHelper;
import mycalendar.model.User;

public class DBUsersHandler {

	private int SALT = 12;
	private Connection con = null;

	
	public synchronized boolean registerUser(User user) {
		
		con = null;
		PreparedStatement p = null;
		
		try {
			
			if (usernameExists(user.getUsername())) {
				System.err.println("[DBUsersHandler] in registerUser(): Username " + user.getUsername() + " already exists.");
				return false;	
			}
			
			con = DBHelper.getConnection();
			if (!DBHelper.checkConnection(con)) {
				System.err.println("[DBUsersHandler] in registerUser(): Connection error.");
				return false;	
			}
			
			String query = "INSERT INTO Users VALUES(?, ?, ?, ?, ?, ?);";
			p = con.prepareStatement(query);
			p.setNull(1, Types.INTEGER);
			p.setString(2, user.getFirstName());
			p.setString(3, user.getLastName());
			p.setString(4, user.getUsername());
			p.setString(5, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(SALT)));
			p.setString(6, user.getEmail());
			
			p.executeUpdate();
		
		} catch (SQLException e) {
			
			System.err.println("[DBUsersHandler] in registerUser(): SQLException:");
			e.printStackTrace();;
			return false;
			
		} finally {
			
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}

		System.out.println("[DBUsersHandler] in registerUser(): User " + user.getUsername() +  " registered.");
		return true;

	} // end registerUser


	private synchronized boolean usernameExists(String username) {
		
		con = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		
		boolean result = false;
		
		try {
			
			con = DBHelper.getConnection();
			
			if (!DBHelper.checkConnection(con))
				return false;
			
			String query = "SELECT * FROM Users WHERE Username=?;";
			p = con.prepareStatement(query);
			p.setString(1, username);

			rs = p.executeQuery();
			result = rs.next();
			
		} catch (SQLException e) {
			
			System.err.println("[DBUsersHandler] Error in usernameExists():");
			e.printStackTrace();
			return false;
			
		} finally {
			
			DBHelper.closeResultSet(rs);
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}

		return result;

	} // end usernameExists
	
	public synchronized User getUserByID(Integer ID) {
		
		con = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		
		try {
			
			con = DBHelper.getConnection();
			
			if (!DBHelper.checkConnection(con))
				return null;
			
			String query = "SELECT * FROM Users WHERE Id=?;";
			p = con.prepareStatement(query);
			p.setInt(1, ID);

			rs = p.executeQuery();
			if (rs.next()) {
				String fName = rs.getString("FirstName");
				String lName = rs.getString("LastName");
				String username = rs.getString("Username");
				String password = rs.getString("Password");
				String email = rs.getString("Email");
				User user = new User(fName, lName, username, password, email);
				return user;
			}
			
		} catch (SQLException e) {
			
			System.err.println("[DBUsersHandler] Error in getUserByID():");
			e.printStackTrace();
			return null;
			
		} finally {
			
			DBHelper.closeResultSet(rs);
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}

		return null;

	} // end getUserByID

	
	public synchronized boolean checkUserCredentials(String username, String password) {

		con = null;
		PreparedStatement p = null;
		ResultSet rs = null;
		
		boolean result = false;
		
		try {
			
			con = DBHelper.getConnection();
			
			if (!DBHelper.checkConnection(con))
				return false;
			
			String query = "SELECT * FROM Users WHERE Username=?;";
			p = con.prepareStatement(query);
			p.setString(1, username);
			rs = p.executeQuery();

			if (rs.next()) {
				String hashPassword = rs.getString("Password");
				result = BCrypt.checkpw(password, hashPassword);
			}
			
		} catch (SQLException e) {
			
			System.err.println("[DBUsersHandler] Error in checkUserCredentials():");
			e.printStackTrace();
			return false;
			
		} finally {
			
			DBHelper.closeResultSet(rs);
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}

		return result;

	} // end checkUserCredentials
	
	public synchronized Integer getUserId(String username) {
		
		con = null;
		PreparedStatement p = null;
		ResultSet res = null;
		
		try {
			
			con = DBHelper.getConnection();
			
			if (!DBHelper.checkConnection(con))
				return null;
			
			String query = "SELECT * FROM Users WHERE Username=?;";
			p = con.prepareStatement(query);
			p.setString(1, username);
			res = p.executeQuery();
			
			if (res.next())
				return res.getInt("Id");
			
			
		} catch (SQLException e) {
			
			System.err.println("[DBUsersHandler] Error in getUserId():");
			e.printStackTrace();
			return null;
			
		} finally {
			
			DBHelper.closeResultSet(res);
			DBHelper.closePreparedStatement(p);
			DBHelper.closeConnection(con);
			
		}
		
		return null;
		
	} // end getUserId

	
	private static DBUsersHandler instance = null;

	public static DBUsersHandler getInstance() {
		if (instance == null)
			instance = new DBUsersHandler();
		return instance;
	}

	private DBUsersHandler() {
	}
	
} // class DBUsersHandler