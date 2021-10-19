package mycalendar.net;

import java.util.HashMap;

import mycalendar.model.User;

public class OnlineUsersHandler {

	private static HashMap<Integer, User> users = new HashMap<Integer, User>();
	
	public synchronized static boolean insertUser(Integer id, User user) {
		if (users.containsKey(id))
			return false;
		users.put(id, user);
		return true;
	}
	
	public synchronized static void removeUser(Integer id) {
		System.out.println("[OnlineUsersHandler] Removing " + users.remove(id).getUsername());
	}
	
} // class OnlineUsersHandler
