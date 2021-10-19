package mycalendar.util;

import mycalendar.controller.InitialPageDialogsHandler;
import mycalendar.net.Protocol;

public class LoginErrorsHandler {
	
	private static InitialPageDialogsHandler dialogsHandler = InitialPageDialogsHandler.getInstance();
	
	public void handleError (String error) {
		
		if (error.equals(Protocol.USER_ALREADY_LOGGED_IN_ERROR))
			dialogsHandler.SHOW_USER_ALREADY_LOGGED_IN_ERROR();
		else if (error.equals(Protocol.USER_AUTHENTICATION_ERROR))
			dialogsHandler.SHOW_USER_AUTHENTICATION_ERROR();
		
	}
	
	private static LoginErrorsHandler instance = null;
	
	public static LoginErrorsHandler getInstance() {
		if (instance == null)
			instance = new LoginErrorsHandler();
		return instance;
	}
	
	private LoginErrorsHandler() {
		
	}
	
	
	
}
