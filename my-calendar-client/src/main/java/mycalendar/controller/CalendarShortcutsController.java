package mycalendar.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CalendarShortcutsController implements EventHandler<KeyEvent> {

	@Override
	public void handle(KeyEvent k) {
		
		if (k.isShortcutDown()) {
			
			/* Show Add Event Dialog */
			if (k.getCode() == KeyCode.PLUS) {
				CalendarPageController.getInstance().onAddEvent();
			}
			
			/* Change Theme */
			else if (k.getCode() == KeyCode.T) {
				ScenesController.getInstance().toggleTheme();
			}
			
			/* Logout */
			else if (k.getCode() == KeyCode.X) {
				// ScenesController.getInstance().onLogout();
				// TODO: Continue after fixing 'show alert before closing' bug!
			}
			
			/* Toggle Settings Control */
			else if (k.getCode() == KeyCode.S) {
				CalendarPageController.getInstance().showSettingsControl();
			}
			
		}
		
	} // handle

	
}
