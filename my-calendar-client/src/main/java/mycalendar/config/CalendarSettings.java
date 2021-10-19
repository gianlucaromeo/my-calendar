package mycalendar.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import mycalendar.i18n.I18NHelper;

public class CalendarSettings {
	
	@Getter private StringProperty _JANUARY = new SimpleStringProperty();
	@Getter private StringProperty _FEBRUARY = new SimpleStringProperty();
	@Getter private StringProperty _MARCH = new SimpleStringProperty();
	@Getter private StringProperty _APRIL = new SimpleStringProperty();
	@Getter private StringProperty _MAY = new SimpleStringProperty();
	@Getter private StringProperty _JUNE = new SimpleStringProperty();
	@Getter private StringProperty _JULY = new SimpleStringProperty();
	@Getter private StringProperty _AUGUST = new SimpleStringProperty();
	@Getter private StringProperty _SEPTEMBER = new SimpleStringProperty();
	@Getter private StringProperty _OCTOBER = new SimpleStringProperty();
	@Getter private StringProperty _NOVEMBER = new SimpleStringProperty();
	@Getter private StringProperty _DECEMBER = new SimpleStringProperty();

	@Getter private StringProperty _SUNDAY = new SimpleStringProperty();
	@Getter private StringProperty _MONDAY = new SimpleStringProperty();
	@Getter private StringProperty _TUESDAY = new SimpleStringProperty();
	@Getter private StringProperty _WEDNESDAY = new SimpleStringProperty();
	@Getter private StringProperty _THURSDAY = new SimpleStringProperty();
	@Getter private StringProperty _FRIDAY = new SimpleStringProperty();
	@Getter private StringProperty _SATURDAY = new SimpleStringProperty();
	
	public CalendarSettings() {

		I18NHelper i18n = I18NHelper.getInstance();
		
		_JANUARY.bind(i18n.createBinding("JANUARY"));
		_FEBRUARY.bind(i18n.createBinding("FEBRUARY"));
		_MARCH.bind(i18n.createBinding("MARCH"));
		_APRIL.bind(i18n.createBinding("APRIL"));
		_MAY.bind(i18n.createBinding("MAY"));
		_JUNE.bind(i18n.createBinding("JUNE"));
		_JULY.bind(i18n.createBinding("JULY"));
		_AUGUST.bind(i18n.createBinding("AUGUST"));
		_SEPTEMBER.bind(i18n.createBinding("SEPTEMBER"));
		_OCTOBER.bind(i18n.createBinding("OCTOBER"));
		_NOVEMBER.bind(i18n.createBinding("NOVEMBER"));
		_DECEMBER.bind(i18n.createBinding("DECEMBER"));
		
		_SUNDAY.bind(i18n.createBinding("SUNDAY"));
		_MONDAY.bind(i18n.createBinding("MONDAY"));
		_TUESDAY.bind(i18n.createBinding("TUESDAY"));
		_WEDNESDAY.bind(i18n.createBinding("WEDNESDAY"));
		_THURSDAY.bind(i18n.createBinding("THURSDAY"));
		_FRIDAY.bind(i18n.createBinding("FRIDAY"));
		_SATURDAY.bind(i18n.createBinding("SATURDAY"));
		
	}

	@Getter private StringProperty[] months = new StringProperty[] { 
			_JANUARY,
			_FEBRUARY,
			_MARCH,
			_APRIL,
			_MAY,
			_JUNE,
			_JULY,
			_AUGUST,
			_SEPTEMBER,
			_OCTOBER,
			_NOVEMBER,
			_DECEMBER
	};
	
	@Getter private StringProperty[] days = new StringProperty[] {
		_SUNDAY,
		_MONDAY,
		_TUESDAY,
		_WEDNESDAY,
		_THURSDAY,
		_FRIDAY,
		_SATURDAY
	};
	
	/**
	 * To create a combo box of available years.
	 * */
	public static final int YEARS_GAP = 5;
	
}
