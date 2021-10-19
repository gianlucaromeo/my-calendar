package mycalendar.controller;

import javafx.animation.RotateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class CalendarIconController implements EventHandler<Event> {
	
	private Label label;
	
	public CalendarIconController(Label label) {
		this.label = label;
	}
	
	@Override
	public void handle(Event event) {
		RotateTransition ft = new RotateTransition(Duration.millis(620), label.getGraphic());
		ft.setCycleCount(1);
		ft.setFromAngle(0.0);
		ft.setToAngle(360.0);
		ft.setAutoReverse(false);
		ft.play();
	}
	
}
