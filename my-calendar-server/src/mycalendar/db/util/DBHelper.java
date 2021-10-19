package mycalendar.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mycalendar.config.Settings;

public class DBHelper {

	public synchronized static Connection getConnection() {
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(Settings.DATABASE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
		
	}

	public synchronized static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static void closePreparedStatement(PreparedStatement p) {
		if (p != null) {
			try {
				p.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static boolean checkConnection(Connection con) {

		try {
			if (con == null || con.isClosed())
				return false;
		} catch (SQLException e) {
			System.err.println("[DBHelper] Connection error:");
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
