package mycalendar.model;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;
import mycalendar.config.CalendarSettings;

public class MyCalendarModel {	
	
	@Getter @Setter private int prevSelectedMonth;
	@Getter @Setter private int selectedMonth;
	@Getter @Setter private int selectedYear;

	@Getter @Setter private int minYear;
	@Getter @Setter private int maxYear;
	
	@Getter @Setter private DateTime currentDT;
	
	/**
	 * Number of rows of the Calendar's Grid.
	 **/
	public static final int ROWS = 7;    
    
	/**
	 * Number of columns of the Calendar's Grid.
	 * */
	public static final int COLUMNS = 7;  
	
	private static MyCalendarModel instance = null;
	
	public static MyCalendarModel getInstance() {
		if (instance == null)
			instance = new MyCalendarModel();
		return instance;
	}
	
	private MyCalendarModel() {
		initMonths();
		initYears();
		initDateTime();
	}
	
	private void initMonths() {
		selectedMonth = DateTime.now().getMonthOfYear(); 
		prevSelectedMonth = selectedMonth;
	}
	
	private void initYears() {
		selectedYear = DateTime.now().getYear();
		minYear = selectedYear - CalendarSettings.YEARS_GAP;
		maxYear = selectedYear + CalendarSettings.YEARS_GAP;
	}
	
	private void initDateTime() {
		currentDT = new DateTime(selectedYear, selectedMonth, 1, 0, 0);
	}

	/**
	 * Update current and previous month,
	 * based on the month the user selected.
	 */
	public void updateSelectedMonth(int newMonth) {
		setPrevSelectedMonth(selectedMonth);
        setSelectedMonth(newMonth);
	}
	
}
