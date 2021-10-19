package mycalendar.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import mycalendar.i18n.I18NHelper;

public class NotificationsSettings {

	@Getter private StringProperty _WELCOME_HEADER = new SimpleStringProperty();
	@Getter private StringProperty _WELCOME_TITLE = new SimpleStringProperty();
    @Getter	private StringProperty _WELCOME_EVENTS_CONTENT  = new SimpleStringProperty();
    @Getter	private StringProperty _WELCOME_NO_EVENTS_CONTENT  = new SimpleStringProperty();
    
    @Getter private StringProperty _EV_STARTED_CONTENT = new SimpleStringProperty();
    @Getter	private StringProperty _EV_STARTED_TITLE  = new SimpleStringProperty();
	
    private NotificationsSettings() {
		
		I18NHelper i18n = I18NHelper.getInstance();
		
		_WELCOME_HEADER.bind(i18n.createBinding("NOTIF_WELCOME_HEADER"));
		_WELCOME_TITLE.bind(i18n.createBinding("NOTIF_WELCOME_TITLE"));
		_WELCOME_EVENTS_CONTENT.bind(i18n.createBinding("NOTIF_WELCOME_EVENTS_CONTENT"));
		_WELCOME_NO_EVENTS_CONTENT.bind(i18n.createBinding("NOTIF_WELCOME_NO_EVENTS_CONTENT"));
	
		_EV_STARTED_TITLE.bind(i18n.createBinding("NOTIF_EV_STARTED_TITLE"));
		_EV_STARTED_CONTENT.bind(i18n.createBinding("NOTIF_EV_STARTED_CONTENT"));
		
	}
	
	private static NotificationsSettings instance = null;
	
	public static NotificationsSettings getInstance() {
		if (instance == null)
			instance = new NotificationsSettings();
		return instance;
	}
	
}
