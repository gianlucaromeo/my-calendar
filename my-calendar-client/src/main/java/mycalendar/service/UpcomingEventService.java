package mycalendar.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import mycalendar.config.GeneralSettings;
import mycalendar.model.CalendarEvent;
import mycalendar.net.Client;

public class UpcomingEventService extends Service<List<CalendarEvent>> {

	private List<CalendarEvent> upcomingEvents;
	
	@Override
	protected Task<List<CalendarEvent>> createTask() {

		return new Task<List<CalendarEvent>>() {
			
			@Override
			protected List<CalendarEvent> call() throws Exception {
				
				if (Client.getInstance().getUser() == null)
					return null;
				
				//System.out.println("Service starts now");
				upcomingEvents = new ArrayList<CalendarEvent>();

				List<CalendarEvent> userEvents = Client.getInstance().getUserEvents();
				String now = DateTime.now().toString("YYYY-MM-dd HH:mm");
				int count = 0;
				
				for (CalendarEvent ev : userEvents) {
					
					if (ev.getStartDate().compareTo(now) > 0) {
						upcomingEvents.add(ev);
						++count;
						if (count == GeneralSettings.MAX_UPCOMING_EVENTS)
							return upcomingEvents;
					}
					
				}
				
				return upcomingEvents;
				
			}
		};
	}
	
}
