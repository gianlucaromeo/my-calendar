package mycalendar.controller;

import java.time.LocalDate;

import org.joda.time.DateTime;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import mycalendar.config.DurationSettings;

public class EventDurationChangeController implements ChangeListener<String> {

	private DateTimePickerController dtPickerStart;
	private DateTimePickerController dtPickerEnd;
	private DurationSettings settings;
	
	public EventDurationChangeController(DateTimePickerController dtPickerStart, DateTimePickerController dtPickerEnd) {
		this.dtPickerStart = dtPickerStart;
		this.dtPickerEnd = dtPickerEnd;
		settings = DurationSettings.getInstance();
	}
	
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {		

		if (newValue == null)
			return;
		
		if (newValue.equals(settings.getEVENT_DURATION_CUSTOM().get())) {
			dtPickerEnd.setDisable(false);
			return;
		}
		
		LocalDate input = dtPickerStart.getSelectedDate();
		int hoursStart = dtPickerStart.getHours();
		int minutesStart = dtPickerStart.getMinutes();
		DateTime start = new DateTime(input.getYear(), input.getMonthValue(), input.getDayOfMonth(), hoursStart, minutesStart);
		DateTime end;
		
		if (newValue.equals(settings.getEVENT_DURATION_1_HOUR().get())) {
			end = new DateTime(start.plusHours(1));
		} else if (newValue.equals(settings.getEVENT_DURATION_2_HOURS().get())) {
			end = new DateTime(start.plusHours(2));
		} else if (newValue.equals(settings.getEVENT_DURATION_5_HOURS().get())) {
			end = new DateTime(start.plusHours(5));
		} else if (newValue.equals(settings.getEVENT_DURATION_ALL_DAY().get())) {
			end = new DateTime(start.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59));
		} else if (newValue.equals(settings.getEVENT_DURATION_24_HOURS().get())) {
			end = new DateTime(start.plusHours(24));
		} else if (newValue.equals(settings.getEVENT_DURATION_ONE_WEEK().get())) {
			end = new DateTime(start.plusWeeks(1));
		} else {
			end = new DateTime(start.plusMinutes(10)); // Should never get here
			return;
		}
		
		dtPickerEnd.setDisable(true);
		dtPickerEnd.setValues(end, end.getHourOfDay(), end.getMinuteOfHour());
		
	}
	
}
 