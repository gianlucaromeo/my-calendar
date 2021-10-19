package mycalendar.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import mycalendar.i18n.I18NHelper;
import mycalendar.net.Protocol;

public class RegistrationFormSettings {

	@Getter	private final int REGISTRATION_TEXT_FIELD_PREF_WIDTH = 340;
	@Getter private final int REGISTRATION_PASSWORD_FIELD_PREF_WIDTH = 300; // REGISTRATION_TEXT_FIELD_PREF_WIDTH - 40
	
	@Getter private StringProperty REGISTRATION_ERROR_TITLE = new SimpleStringProperty();
	
	@Getter private StringProperty REGISTRATION_OK_TITLE = new SimpleStringProperty();
	@Getter private StringProperty REGISTRATION_OK_CONTENT = new SimpleStringProperty();
	
	@Getter private StringProperty USERNAME_MIN_LENGTH_ERROR = new SimpleStringProperty();
	@Getter private StringProperty USERNAME_MAX_LENGTH_ERROR = new SimpleStringProperty();
	@Getter private StringProperty USERNAME_CHARACTERS_ERROR = new SimpleStringProperty();
	@Getter private StringProperty USERNAME_FIRST_CHARACTER_ERROR = new SimpleStringProperty();
	@Getter private StringProperty PASSWORDS_MATCH_ERROR = new SimpleStringProperty();
	@Getter private StringProperty GENERIC_ERROR = new SimpleStringProperty();
	
	private static RegistrationFormSettings instance = null;
	
	public static RegistrationFormSettings getInstance() {
		if (instance == null)
			instance = new RegistrationFormSettings();
		return instance;
	}
	
	private RegistrationFormSettings() {
		
		I18NHelper i18n = I18NHelper.getInstance();
		
		REGISTRATION_ERROR_TITLE.bind(i18n.createBinding("REGISTRATION_ERROR_TITLE"));

		REGISTRATION_OK_TITLE.bind(i18n.createBinding("REGISTRATION_OK_TITLE"));
		REGISTRATION_OK_CONTENT.bind(i18n.createBinding("REGISTRATION_OK_CONTENT"));
		
		USERNAME_MIN_LENGTH_ERROR.bind(i18n.createBinding("REGISTRATION_USR_MIN_LENGTH_ERROR"));
		USERNAME_MAX_LENGTH_ERROR.bind(i18n.createBinding("REGISTRATION_USR_MAX_LENGTH_ERROR"));
		USERNAME_CHARACTERS_ERROR.bind(i18n.createBinding("REGISTRATION_USR_CHARACTERS_ERROR")); // Only letters and numbers
		USERNAME_FIRST_CHARACTER_ERROR.bind(i18n.createBinding("REGISTRATION_USR_FIRST_CHAR_ERROR"));
		PASSWORDS_MATCH_ERROR.bind(i18n.createBinding("REGISTRATION_PASSWORD_MATCH_ERROR"));
		GENERIC_ERROR.bind(i18n.createBinding("REGISTRATION_GENERIC_ERROR"));
		
	}

	public String getError(String res) {

		if (res.equals(Protocol.USERNAME_MIN_LENGTH_ERROR))
			return USERNAME_MIN_LENGTH_ERROR.get();
		
		if (res.equals(Protocol.USERNAME_MAX_LENGTH_ERROR))
			return USERNAME_MAX_LENGTH_ERROR.get();

		if (res.equals(Protocol.USERNAME_CHARACTERS_ERROR))
			return USERNAME_CHARACTERS_ERROR.get();
		
		if (res.equals(Protocol.USERNAME_FIRST_CHARACTER_ERROR))
			return USERNAME_FIRST_CHARACTER_ERROR.get();
		
		PasswordSettings ps = PasswordSettings.getInstance();
		
		if (res.equals(Protocol.PASSWORD_MIN_LENGTH_ERROR))
			return ps.getPASSW_MIN_LENGTH_ERROR().get();
		
		if (res.equals(Protocol.PASSWORD_UPPERCASE_ERROR))
			return ps.getPASSW_UPPERCASE_ERROR().get();
		
		if (res.equals(Protocol.PASSWORD_NUMBER_ERROR))
			return ps.getPASSW_NUMBER_ERROR().get();
		
		if (res.equals(Protocol.PASSWORD_SPECIAL_CHARACTER_ERROR))
			return ps.getPASSW_SPECIAL_CHARACTER_ERROR().get();
		
		if (res.equals(Protocol.PASSWORD_SPACE_ERROR))
			return ps.getPASSW_SPACE_ERROR().get();
		
		return GENERIC_ERROR.get();
		
	}
	
	
}
