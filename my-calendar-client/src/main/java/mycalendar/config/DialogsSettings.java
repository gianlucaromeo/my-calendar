package mycalendar.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import mycalendar.i18n.I18NHelper;

public class DialogsSettings {

	@Getter private StringProperty _GENERIC_ERROR_TITLE = new SimpleStringProperty();
    @Getter	private StringProperty _GENERIC_ERROR_CONTENT  = new SimpleStringProperty();
	
	@Getter private StringProperty _EVENT_ADDED_TITLE = new SimpleStringProperty();
    @Getter	private StringProperty _EVENT_ADDED_CONTENT  = new SimpleStringProperty();
    
    @Getter private StringProperty _EVENT_EV_DATE_ERROR_TITLE = new SimpleStringProperty();
    @Getter	private StringProperty _EVENT_EV_DATE_ERROR_CONTENT  = new SimpleStringProperty();
    
    @Getter private StringProperty _EVENT_EDITED_TITLE = new SimpleStringProperty();
    @Getter	private StringProperty _EVENT_EDITED_CONTENT  = new SimpleStringProperty();
    
    @Getter private StringProperty _EVENT_DELETED_OK_TITLE = new SimpleStringProperty();
    @Getter	private StringProperty _EVENT_DELETED_OK_CONTENT = new SimpleStringProperty();
    
    @Getter private StringProperty _EVENT_DELETED_NO_TITLE = new SimpleStringProperty();
    @Getter	private StringProperty _EVENT_DELETED_NO_CONTENT = new SimpleStringProperty();
	
    @Getter private StringProperty _USER_ALREADY_LOGGED_IN_TITLE = new SimpleStringProperty();
    @Getter	private StringProperty _USER_ALREADY_LOGGED_IN_CONTENT  = new SimpleStringProperty();
    
    @Getter private StringProperty _USER_AUTHENTICATION_ERROR_TITLE = new SimpleStringProperty();
    @Getter	private StringProperty _USER_AUTHENTICATION_ERROR_CONTENT  = new SimpleStringProperty();
    
	private DialogsSettings() {
		
		I18NHelper i18n = I18NHelper.getInstance();
		
		_GENERIC_ERROR_TITLE.bind(i18n.createBinding("DLG_GENERIC_ERROR_TITLE"));
		_GENERIC_ERROR_CONTENT.bind(i18n.createBinding("DLG_GENERIC_ERROR_CONTENT"));

		_EVENT_EV_DATE_ERROR_TITLE.bind(i18n.createBinding("DLG_EVENT_EV_DATE_ERROR_TITLE"));
		_EVENT_EV_DATE_ERROR_CONTENT.bind(i18n.createBinding("DLG_EVENT_EV_DATE_ERROR_CONTENT"));
		
		_EVENT_ADDED_TITLE.bind(i18n.createBinding("DLG_EVENT_ADDED_TITLE"));
		_EVENT_ADDED_CONTENT.bind(i18n.createBinding("DLG_EVENT_ADDED_CONTENT"));
		
		_EVENT_EDITED_TITLE.bind(i18n.createBinding("DLG_EVENT_EDITED_TITLE"));
		_EVENT_EDITED_CONTENT.bind(i18n.createBinding("DLG_EVENT_EDITED_CONTENT"));
		
		_USER_ALREADY_LOGGED_IN_TITLE.bind(i18n.createBinding("DLG_USER_ALREADY_LOGGED_IN_TITLE"));
		_USER_ALREADY_LOGGED_IN_CONTENT.bind(i18n.createBinding("DLG_USER_ALREADY_LOGGED_IN_CONTENT"));
		
		_USER_AUTHENTICATION_ERROR_TITLE.bind(i18n.createBinding("DLG_USER_AUTHENTICATION_ERROR_TITLE"));
		_USER_AUTHENTICATION_ERROR_CONTENT.bind(i18n.createBinding("DLG_USER_AUTHENTICATION_ERROR_CONTENT"));

		_EVENT_DELETED_OK_TITLE.bind(i18n.createBinding("DLG_EVENT_DELETED_OK_TITLE"));
		_EVENT_DELETED_OK_CONTENT.bind(i18n.createBinding("DLG_EVENT_DELETED_OK_CONTENT"));
		
		_EVENT_DELETED_NO_TITLE.bind(i18n.createBinding("DLG_EVENT_DELETED_NO_TITLE"));
		_EVENT_DELETED_NO_CONTENT.bind(i18n.createBinding("DLG_EVENT_DELETED_NO_CONTENT"));
	
		
	}
	
	private static DialogsSettings instance = null;
	
	public static DialogsSettings getInstance() {
		if (instance == null)
			instance = new DialogsSettings();
		return instance;
	}
	
}
