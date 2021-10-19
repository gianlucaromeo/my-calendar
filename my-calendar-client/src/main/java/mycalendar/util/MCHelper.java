package mycalendar.util;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import mycalendar.model.CalendarEvent;
import mycalendar.net.Client;

public class MCHelper {

	public static List<String> getTodaysEventsTitles() {
        List<CalendarEvent> userEvents = Client.getInstance().getUserEvents(); // All
        List<String> todaysEvents = new ArrayList<String>();
        for (CalendarEvent ev : userEvents)
            if (ev.getStartDate().substring(0, 10).equals(DateTime.now().toString("YYYY-MM-dd")))
                todaysEvents.add(ev.getTitle());
        return todaysEvents;
    }
	
}
