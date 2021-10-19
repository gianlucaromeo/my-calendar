package mycalendar.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import mycalendar.i18n.I18NHelper;

public class DurationSettings {

	@Getter private StringProperty[] eventDurations;
	
	@Getter private StringProperty EVENT_DURATION_CUSTOM =  new SimpleStringProperty();
	@Getter private StringProperty EVENT_DURATION_1_HOUR = new SimpleStringProperty();
	@Getter private StringProperty EVENT_DURATION_2_HOURS = new SimpleStringProperty();
	@Getter private StringProperty EVENT_DURATION_5_HOURS = new SimpleStringProperty();
	@Getter private StringProperty EVENT_DURATION_ALL_DAY = new SimpleStringProperty();
	@Getter private StringProperty EVENT_DURATION_24_HOURS = new SimpleStringProperty();
	@Getter private StringProperty EVENT_DURATION_ONE_WEEK = new SimpleStringProperty();
	
	private DurationSettings() {
		
		I18NHelper i18n = I18NHelper.getInstance();
		
		EVENT_DURATION_CUSTOM.bind(i18n.createBinding("DURATION_CUSTOM"));
		EVENT_DURATION_1_HOUR.bind(i18n.createBinding("DURATION_1_HOUR"));
		EVENT_DURATION_2_HOURS.bind(i18n.createBinding("DURATION_2_HOURS"));
		EVENT_DURATION_5_HOURS.bind(i18n.createBinding("DURATION_5_HOURS"));
		EVENT_DURATION_ALL_DAY.bind(i18n.createBinding("DURATION_ALL_DAY"));
		EVENT_DURATION_24_HOURS.bind(i18n.createBinding("DURATION_24_HOURS"));
		EVENT_DURATION_ONE_WEEK.bind(i18n.createBinding("DURATION_ONE_WEEK"));
		
		eventDurations = new StringProperty[] {
				EVENT_DURATION_CUSTOM,
				EVENT_DURATION_1_HOUR,
				EVENT_DURATION_2_HOURS,
				EVENT_DURATION_5_HOURS,
				EVENT_DURATION_ALL_DAY,
				EVENT_DURATION_24_HOURS,
				EVENT_DURATION_ONE_WEEK
			};
		
	}
	
	private static DurationSettings instance = null;
	public static DurationSettings getInstance() {
		if (instance == null)
			instance = new DurationSettings();
		return instance;
	}
	
}
