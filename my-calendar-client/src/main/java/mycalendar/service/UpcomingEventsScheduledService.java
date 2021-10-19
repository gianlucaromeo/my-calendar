package mycalendar.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;
import mycalendar.config.GeneralSettings;
import mycalendar.controller.NotificationsController;
import mycalendar.model.CalendarEvent;
import mycalendar.net.Client;

public class UpcomingEventsScheduledService extends ScheduledService<List<CalendarEvent>> {
	
	public UpcomingEventsScheduledService() {
		setPeriod(Duration.seconds(60));
		setDelay(Duration.seconds(getSecsDelay()));
	}
	
	private int getSecsDelay() {
		int secsNow = DateTime.now().getSecondOfMinute();
		return 60 - secsNow;
	}

	private List<CalendarEvent> upcomingEvents = new ArrayList<CalendarEvent>();
	
	@Override
	protected Task<List<CalendarEvent>> createTask() {
		
		return new Task<List<CalendarEvent>>() {
			
			@Override
			protected List<CalendarEvent> call() throws Exception {
				
				if (Client.getInstance().getUser() == null)
					return null;
				//System.out.println("Scheduled service starts Now");
				upcomingEvents.clear();
				
				List<CalendarEvent> userEvents = Client.getInstance().getUserEvents();
				String now = DateTime.now().toString("YYYY-MM-dd HH:mm");
				int count = 0;

				for (CalendarEvent ev : userEvents) {
					
					String evStartDate = ev.getStartDate();
					
					if (ev.getStartDate().compareTo(now) > 0) {
						upcomingEvents.add(ev);
						++count;
						if (count == GeneralSettings.MAX_UPCOMING_EVENTS)
							return upcomingEvents;
					} else if (evStartDate.equals(now)) {
						NotificationsController.getInstance().showEventStartedNotification(ev);
					}
					
				}
				
				return upcomingEvents;
				
			}
		};
	}

	
}
