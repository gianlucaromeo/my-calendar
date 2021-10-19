package mycalendar.controller;

import java.util.List;

import io.github.palexdev.materialfx.controls.MFXNotification;
import io.github.palexdev.materialfx.controls.SimpleMFXNotificationPane;
import io.github.palexdev.materialfx.notifications.NotificationPos;
import io.github.palexdev.materialfx.notifications.NotificationsManager;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mycalendar.config.NotificationsSettings;
import mycalendar.model.CalendarEvent;
import mycalendar.net.Client;
import mycalendar.util.IconsHandler;
import mycalendar.util.MCHelper;

public class NotificationsController { 

	private NotificationsSettings settings = NotificationsSettings.getInstance();
	
	private static NotificationsController instance = null;
	
	public static NotificationsController getInstance() {
		if (instance == null)
			instance = new NotificationsController();
		return instance;
	}

	private NotificationsController() {}
	
	public void showWelcomeNotification() {
		
		List<String> todaysEvents = MCHelper.getTodaysEventsTitles();
		
		String header = settings.get_WELCOME_HEADER().get() + " " + Client.getInstance().getUser().getFirstName();
		String title = settings.get_WELCOME_TITLE().get();
		
		String content;
		if (todaysEvents.size() == 0)
			content = settings.get_WELCOME_NO_EVENTS_CONTENT().get();
		else {
			content = settings.get_WELCOME_EVENTS_CONTENT().get();
			for (String ev : todaysEvents) {
				content += "\n	- " + ev;
			}
		}
		
		NotificationPos pos = NotificationPos.BOTTOM_RIGHT;
		Text icon = IconsHandler.NOTIF_USER();
		SimpleMFXNotificationPane pane = new SimpleMFXNotificationPane(icon, header, title, content);
		MFXNotification notification = new MFXNotification(pane, true, true);
		notification.setHideAfterDuration(Duration.seconds(2));
		pane.setCloseHandler(colseEvent -> notification.hideNotification());
		
		if (ScenesController.getCurrentTheme() == ScenesController.DARK_THEME)
			setDarkMode(pane);
		
		NotificationsManager.send(pos, notification);
		
	}
	
	public void showEventStartedNotification(CalendarEvent event) {
		
		String header = event.getTitle();
		String title = settings.get_EV_STARTED_TITLE().get();
		String content = settings.get_EV_STARTED_CONTENT().get() + " " + event.getEndDate();
		
		NotificationPos pos = NotificationPos.BOTTOM_RIGHT;
		Text icon = IconsHandler.NOTIF_EVENT();
		SimpleMFXNotificationPane pane = new SimpleMFXNotificationPane(icon, header, title, content);
		MFXNotification notification = new MFXNotification(pane, true, true);
		notification.setHideAfterDuration(Duration.seconds(3));
		pane.setCloseHandler(colseEvent -> notification.hideNotification());
		
		if (ScenesController.getCurrentTheme() == ScenesController.DARK_THEME)
			setDarkMode(pane);
		
		NotificationsManager.send(pos, notification);
		
	}

	private void setDarkMode(SimpleMFXNotificationPane pane) {
		pane.getHeaderLabel().setStyle("-fx-text-fill: #313131");
		pane.getTitleLabel().setStyle("-fx-text-fill: #313131");
		pane.getContentLabel().setStyle("-fx-text-fill: #313131");
	}
	
}
