package mycalendar.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class CalendarEvent implements Serializable {
	
	private static final long serialVersionUID = -5418558089520301400L;
	
	@Getter 
	@Setter 
	private String title;
	
	@Getter 
	@Setter 
	private String description = null;
	
	@Getter 
	@Setter 
	private String startDate;
	
	@Getter 
	@Setter 
	private String endDate;

	@Getter
	@Setter 
	private CalendarEventType type;
	
	@Getter
	@Setter
	private String locality = null;
	
	public CalendarEvent(String title, String description, String startDate, String endDate, CalendarEventType type, String locality) {
		this.setTitle(title);
		this.setDescription(description);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setType(type);
		this.setLocality(locality);
	}	
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (this.getClass() != obj.getClass())
			return false;
		
		CalendarEvent c = (CalendarEvent) obj;
		
		return c.title.equals(title) && c.startDate.equals(startDate) && c.endDate.equals(endDate) && c.type.equals(type);
		
	}
	
} // class CalendarEvent
