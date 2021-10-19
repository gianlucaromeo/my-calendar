package mycalendar.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mycalendar.i18n.I18NHelper;

public class GeneralSettings {
	
	/* Application */
	public static final String APP_TITLE = "MyCalendar";
	public static final String APP_ICON_PATH = "/application/images/logo.png";
	
	/* Themes */
	public static final String LIGHT_THEME_RES_PATH = "/application/css/light-theme.css";
	public static final String DARK_THEME_RES_PATH = "/application/css/dark-theme.css";
	
	/* Initial Page */
	public static final boolean INIT_PAGE_RESIZABLE = false;
	
	/* MyCalendar Page */
	public static final boolean MYCAL_PAGE_RESIZABLE = true;
	
	/* Upcoming events */
	public static final int MAX_UPCOMING_EVENTS = 3;
	
	/* Calendar Event Dialog */
	public static final String CAL_EVENT_DEFAULT_H_START = "10";
	public static final String CAL_EVENT_DEFAULT_H_END = "11";
	public static final String CAL_EVENT_DEFAULT_M_START = "00";
	public static final String CAL_EVENT_DEFAULT_M_END = "00";
	
	public StringProperty DEFAULT_EVENT_TYPE_TITLE = new SimpleStringProperty();
	
	private GeneralSettings() {
		I18NHelper i18n = I18NHelper.getInstance();
		DEFAULT_EVENT_TYPE_TITLE.bind(i18n.createBinding("EVENT_TYPE_DEFAULT_TITLE"));
	}
	
	private static GeneralSettings instance = null;
	public static GeneralSettings getInstance() {
		if (instance == null)
			instance = new GeneralSettings();
		return instance;
	}
	
}
