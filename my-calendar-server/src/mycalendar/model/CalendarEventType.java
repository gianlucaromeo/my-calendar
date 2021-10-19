package mycalendar.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class CalendarEventType implements Serializable {

	private static final long serialVersionUID = -8179554171869691643L;

	@Getter
	@Setter
	private String title;
	
	@Getter
	@Setter
	private String color = null;
	
	
	public CalendarEventType(String title, String color) {
		this.title = title;
		this.color = color;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (this.getClass() != obj.getClass())
			return false;
		
		CalendarEventType c = (CalendarEventType) obj;
		
		return c.title.equals(title) && c.color.equals(color);
		
	}
	
} // class CalendarEventType
