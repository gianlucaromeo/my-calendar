package mycalendar.util;

import java.util.List;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import mycalendar.controller.UpcomingEventsCardController;
import mycalendar.model.CalendarEvent;
import mycalendar.service.UpcomingEventService;
import mycalendar.service.UpcomingEventsScheduledService;

public class UpcomingEventsUtil implements EventHandler<WorkerStateEvent> {

	@SuppressWarnings("unchecked")
	@Override
	public void handle(WorkerStateEvent event) {
		List<CalendarEvent> upcomingEvents = (List<CalendarEvent>) event.getSource().getValue();
		UpcomingEventsCardController.getInstance().update(upcomingEvents);
	}
	
	private static UpcomingEventsUtil instance = null;
	private static UpcomingEventsScheduledService scheduledService;
	private static UpcomingEventService service;
	
	public static UpcomingEventsUtil getInstance() {
		if (instance == null)
			instance = new UpcomingEventsUtil();
		return instance;
	}
	
	private UpcomingEventsUtil() {
		
		scheduledService = new UpcomingEventsScheduledService();
		service = new UpcomingEventService();
		
		initScheduledService();
		initService();
		
		service.start();
		scheduledService.start();
		
	}
	
	public void startService() {
		service.restart();
	}

	private void initService() {
		service.setOnSucceeded(this);
	}

	private void initScheduledService() {
		scheduledService.setOnSucceeded(this);
	}

	public void onLogout() {
		service.cancel();
		scheduledService.cancel();
		service = null;
		scheduledService = null;
		instance = null;
	}
	
	
}
