package mycalendar.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import mycalendar.util.IconsHandler;
import mycalendar.view.control.ToggleThemeButton;

public class ThemeHandler implements EventHandler<Event> {
	
	private ToggleThemeButton button;
	
	public ThemeHandler(ToggleThemeButton button) {
		this.button = button;
		this.button.setGraphic(IconsHandler.THEME());
	}
	
	@Override
	public void handle(Event event) {
		ScenesController.getInstance().toggleTheme();
	}
	
}
