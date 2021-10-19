package mycalendar.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import mycalendar.i18n.I18NHelper;


public class PasswordSettings {

	@Getter private StringProperty PASSWORD_INFORMATIONS = new SimpleStringProperty();
	
	@Getter private StringProperty PASSW_MIN_LENGTH_ERROR = new SimpleStringProperty();
	@Getter private StringProperty PASSW_UPPERCASE_ERROR = new SimpleStringProperty();
	@Getter private StringProperty PASSW_NUMBER_ERROR = new SimpleStringProperty();
	@Getter private StringProperty PASSW_SPECIAL_CHARACTER_ERROR = new SimpleStringProperty();
	@Getter private StringProperty PASSW_SPACE_ERROR = new SimpleStringProperty();

	private PasswordSettings() {

		I18NHelper i18n = I18NHelper.getInstance();
		
		PASSWORD_INFORMATIONS.bind(i18n.createBinding("PASSWORD_INFORMATIONS"));
		
		PASSW_MIN_LENGTH_ERROR.bind(i18n.createBinding("PASSW_MIN_LENGTH_ERROR"));
		PASSW_UPPERCASE_ERROR.bind(i18n.createBinding("PASSW_UPPERCASE_ERROR"));
		PASSW_NUMBER_ERROR.bind(i18n.createBinding("PASSW_NUMBER_ERROR"));
		PASSW_SPECIAL_CHARACTER_ERROR.bind(i18n.createBinding("PASSW_SPECIAL_CHARACTER_ERROR"));
		PASSW_SPACE_ERROR.bind(i18n.createBinding("PASSW_SPACE_ERROR"));
		
	}
	
	@Override
	public String toString() {

		StringBuilder b = new StringBuilder();
	
		b.append(PASSW_MIN_LENGTH_ERROR.get())
		 .append("\n")
		 .append(PASSW_UPPERCASE_ERROR.get())
		 .append("\n")
		 .append(PASSW_NUMBER_ERROR.get())
		 .append("\n")
		 .append(PASSW_SPECIAL_CHARACTER_ERROR.get())
		 .append("\n")
		 .append(PASSW_SPACE_ERROR.get());
		
		return b.toString();
	
	}
	
	public static PasswordSettings getInstance() {
		if (instance == null)
			instance = new PasswordSettings();
		return instance;
	}
	
	private static PasswordSettings instance = null;
	
}
