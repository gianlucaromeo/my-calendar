package mycalendar.net;

public class Protocol {
	
	public static final String OK = "ok";
	public static final String ERROR = "error";

	/* USER LOGIN/REGISTRATION REQUEST */
	public static final String USER_LOGIN_REQUEST = "login request";
	public static final String USER_REGISTRATION_REQUEST = "registration request";
	
	/* USER CHANGE CREDENTIALS REQUEST */
	//public static final String CHANGE_USERNAME_REQUEST = "change username request";
	//public static final String CHANGE_PASSWORD_REQUEST = "change password request";
	
	/* USER ADD/DELETE/EDIT EVENTS REQUEST */
	public static final String ADD_EVENT_REQUEST = "add event request";
	public static final String DELETE_EVENT_REQUEST = "delete event request";
	public static final String EDIT_EVENT_REQUEST = "edit event request";
	
	/* USER ERROR MESSAGES */
	public static final String USER_AUTHENTICATION_ERROR = "authentication error";
	public static final String USER_EXISTS_ERROR = "user exists error";
	public static final String USER_ALREADY_LOGGED_IN_ERROR = "user already logged in error";
	public static final String EVENT_EDIT_ERROR = "event edit error";
	
	/* USERNAME */
	public static final String USERNAME_NOT_VALID_ERROR = "username not valid"; // Generic
	public static final String USERNAME_MIN_LENGTH_ERROR = "username min length error";
	public static final String USERNAME_MAX_LENGTH_ERROR = "username max length error";
	public static final String USERNAME_CHARACTERS_ERROR = "username only letters and numbers error";
	public static final String USERNAME_FIRST_CHARACTER_ERROR = "username first character error";
	
	/* PASSWORD */
	public static final String PASSWORD_NOT_VALID_ERROR = "password not valid"; // Generic
	public static final String PASSWORD_MIN_LENGTH_ERROR = "password min length error";
	public static final String PASSWORD_UPPERCASE_ERROR = "password uppercase error";
	public static final String PASSWORD_NUMBER_ERROR = "password number error";
	public static final String PASSWORD_SPECIAL_CHARACTER_ERROR = "password special characters";
	public static final String PASSWORD_SPACE_ERROR = "password spaces error";

}