package mycalendar.util;

import java.util.regex.Pattern;

import mycalendar.model.User;
import mycalendar.net.Protocol;

/**
 * @author Gianluca
 */
public class RegistrationValidator {
	
	/*
	*
	* ************************ USERNAME ************************
	* 1. User-name must contain at least 4 characters;
	* 2. User-name must contain maximum 20 characters;
	* 3. User-name must contain at only letters and numbers;
	* 4. User-name cannot start with numbers.
	*
	* ************************ PASSWORD ************************
	* 1. Password must contain at least 8 characters;
	* 2. Password must contain at least one upper-case character;
	* 3. Password must contain at least one number;
	* 4. Password must contain at least one special character: !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ ;
	* 5. Password cannot contain spaces.
	* 
	*/
	
	public static final Integer PASSWORD_MIN_LENGTH = 8;
	public static final Integer USERNAME_MIN_LENGTH = 4;
	public static final Integer USERNAME_MAX_LENGTH = 20;
		
	private static final String SPACES_RGX                   = ".*[\\s]+.*";
	private static final String SPECIAL_CHARS_RGX            = ".*[\\p{Punct}]+.*";
	private static final String NUMBERS_RGX                  = ".*[\\d]+.*";
	private static final String UPPERCASE_RGX                = ".*[A-Z]+.*";
	private static final String STARTS_WITH_CHARS_RGX        = "[a-zA-Z]+.*";
	private static final String ONLY_LETTERS_AND_NUMBERS_RGX = "[a-zA-Z0-9]*";
	
	private static final Pattern spacesPattern 				  = Pattern.compile(SPACES_RGX);
	private static final Pattern specialCharsPattern 		  = Pattern.compile(SPECIAL_CHARS_RGX);
	private static final Pattern numbersPattern 			  = Pattern.compile(NUMBERS_RGX);
	private static final Pattern uppercasePattern 			  = Pattern.compile(UPPERCASE_RGX);
	private static final Pattern startsWithCharsPattern       = Pattern.compile(STARTS_WITH_CHARS_RGX);
	private static final Pattern onlyLettersAndNumbersPattern = Pattern.compile(ONLY_LETTERS_AND_NUMBERS_RGX);
	
	
	/**
	 *
	 * Return a String from the {@link Protocol} class.
	 * 
	 * <p>If the credentials the user used during the registration process
	 * are valid, it returns Protocol.OK, which is "OK".
	 * Otherwise, this method will return a String corresponding to
	 * the requirement which has not been satisfied.</p>
	 * 
	 * @param user the user who wants to register.
	 * @author Gianluca Romeo
	 * 
	 */
	public static synchronized String validateUser(User user) {
		
		if (user == null || user.getUsername() == null || user.getPassword() == null)
			return Protocol.ERROR;
		
		String usernameStatus = checkUsername(user.getUsername());
		if (usernameStatus != Protocol.OK)
			return usernameStatus;
	
		String passwordStatus = checkPassword(user.getPassword());
		if (passwordStatus != Protocol.OK)
			return passwordStatus;
		
		return Protocol.OK;
		
	}
	
	
	private static synchronized String checkUsername(String username) {
		
		// 1. User-name must contain at least 4 characters
		boolean minLength = checkMinLength(username, USERNAME_MIN_LENGTH);
		if (!minLength)
			return Protocol.USERNAME_MIN_LENGTH_ERROR;
		
		// 2. User-name must contain maximum 20 characters
		boolean maxLength = checkMaxLength(username, USERNAME_MAX_LENGTH);
		if (!maxLength)
			return Protocol.USERNAME_MAX_LENGTH_ERROR;
		
		// 3. User-name must contain at only letters and numbers
		boolean onlyLettersAndNumbers = checkOnlyLettersAndNumbers(username);
		if (!onlyLettersAndNumbers)
			return Protocol.USERNAME_CHARACTERS_ERROR;
		 
		// 4. User-name can't start with numbers
		boolean startsWithCharacter = checkFirstCharacter(username);
		if (!startsWithCharacter)
			return Protocol.USERNAME_FIRST_CHARACTER_ERROR;
		
		return Protocol.OK;
	}
	
	private static synchronized String checkPassword(String password) {
		
		// 1. Password must contain at least 8 characters
		boolean minLength = checkMinLength(password, PASSWORD_MIN_LENGTH);
		if (!minLength)
			return Protocol.PASSWORD_MIN_LENGTH_ERROR;
		
		// 2. Password must contain at least one upper-case character
		boolean uppercaseChar = checkUppercaseCharacter(password);
		if (!uppercaseChar)
			return Protocol.PASSWORD_UPPERCASE_ERROR;
		
		// 3. Password must contain at least one number
		boolean oneNumber = checkNumber(password);
		if (!oneNumber)
			return Protocol.PASSWORD_NUMBER_ERROR;
		
		// 4. Password must contain at least one special character: 
		//    !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
		boolean specialCharacter = checkSpecialCharacter(password);
		if (!specialCharacter)
			return Protocol.PASSWORD_SPECIAL_CHARACTER_ERROR;
		
		// 5. Password cannot contain spaces
		boolean containsSpaces = checkSpaces(password);
		if (containsSpaces)
			return Protocol.PASSWORD_SPACE_ERROR;
		
		return Protocol.OK;
		
	}
	
	private static boolean checkMinLength(String str, Integer LENGTH) {
		return str.length() >= LENGTH;
	}
	
	private static boolean checkMaxLength(String str, Integer LENGTH) {
		return str.length() <= LENGTH;
	}
	
	private static boolean checkSpaces(String str) {
		return spacesPattern.matcher(str).matches();
	}

	private static boolean checkSpecialCharacter(String str) {
		return specialCharsPattern.matcher(str).matches();
	}

	private static boolean checkNumber(String str) {
		return numbersPattern.matcher(str).matches();
	}
	
	private static boolean checkUppercaseCharacter(String str) {
		return uppercasePattern.matcher(str).matches();
	}

	private static boolean checkFirstCharacter(String username) {
		return startsWithCharsPattern.matcher(username).matches();
	}

	private static boolean checkOnlyLettersAndNumbers(String username) {
		return onlyLettersAndNumbersPattern.matcher(username).matches();
	}
	
}
