package mycalendar.model;

import java.util.List;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class CalendarDay {

	@Getter
	@Setter
	private DateTime dateTime;
	
	@Getter
	@Setter
	private List<CalendarEvent> events;
	
	public CalendarDay(@NonNull DateTime dateTime, List<CalendarEvent> events) {
		this.dateTime = dateTime;
		this.events = events;
	}
	
	
}
