package mycalendar.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import mycalendar.i18n.I18NHelper;

public class InputDialogSettings {
	
	@Getter private StringProperty _CLOSE_EVENT_DIALOG_TITLE = new SimpleStringProperty();
	@Getter private StringProperty _CLOSE_EVENT_DIALOG_CONTENT = new SimpleStringProperty();
	@Getter private StringProperty _CLOSE_EVENT_DIALOG_RED_BUTTON = new SimpleStringProperty();
	@Getter private StringProperty _CLOSE_EVENT_DIALOG_GREEN_BUTTON = new SimpleStringProperty();
	
	@Getter private StringProperty _DELETE_EVENT_DIALOG_TITLE = new SimpleStringProperty();
	@Getter private StringProperty _DELETE_EVENT_DIALOG_CONTENT = new SimpleStringProperty();
	@Getter private StringProperty _DELETE_EVENT_DIALOG_RED_BUTTON = new SimpleStringProperty();
	@Getter private StringProperty _DELETE_EVENT_DIALOG_GREEN_BUTTON = new SimpleStringProperty();
	
	@Getter private StringProperty _LEAVE_APP_TITLE = new SimpleStringProperty();
	@Getter private StringProperty _LEAVE_APP_RED_BUTTON = new SimpleStringProperty();
	@Getter private StringProperty _LEAVE_APP_GREEN_BUTTON = new SimpleStringProperty();
	
	private InputDialogSettings() {
		
		I18NHelper i18n = I18NHelper.getInstance();
		
		_CLOSE_EVENT_DIALOG_TITLE.bind(i18n.createBinding("CLOSE_EVENT_DIALOG_TITLE"));
		_CLOSE_EVENT_DIALOG_CONTENT.bind(i18n.createBinding("CLOSE_EVENT_DIALOG_CONTENT"));
		_CLOSE_EVENT_DIALOG_RED_BUTTON.bind(i18n.createBinding("CLOSE_EVENT_DIALOG_RED_BUTTON"));
		_CLOSE_EVENT_DIALOG_GREEN_BUTTON.bind(i18n.createBinding("CLOSE_EVENT_DIALOG_GREEN_BUTTON"));
		
		_DELETE_EVENT_DIALOG_TITLE.bind(i18n.createBinding("DELETE_EVENT_DIALOG_TITLE"));
		_DELETE_EVENT_DIALOG_CONTENT.bind(i18n.createBinding("DELETE_EVENT_DIALOG_CONTENT"));
		_DELETE_EVENT_DIALOG_RED_BUTTON.bind(i18n.createBinding("DELETE_EVENT_DIALOG_RED_BUTTON"));
		_DELETE_EVENT_DIALOG_GREEN_BUTTON.bind(i18n.createBinding("DELETE_EVENT_DIALOG_GREEN_BUTTON"));
		
		_LEAVE_APP_TITLE.bind(i18n.createBinding("LEAVE_APP_DIALOG_TITLE"));
		_LEAVE_APP_RED_BUTTON.bind(i18n.createBinding("LEAVE_APP_DIALOG_RED_BUTTON"));
		_LEAVE_APP_GREEN_BUTTON.bind(i18n.createBinding("LEAVE_APP_DIALOG_GREEN_BUTTON"));
		
	}
	
	private static InputDialogSettings instance = null;
	
	public static InputDialogSettings getInstance() {
		if (instance == null)
			instance = new InputDialogSettings();
		return instance;
	}

}
