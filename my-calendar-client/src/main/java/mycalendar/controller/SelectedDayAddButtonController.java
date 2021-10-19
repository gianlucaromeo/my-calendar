package mycalendar.controller;

import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mycalendar.util.IconsHandler;

public class SelectedDayAddButtonController implements ChangeListener<Boolean>, EventHandler<ActionEvent> {
	
	private Button button;
	
	public SelectedDayAddButtonController(Button button) {
		this.button = button;
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		
		Text icon;
		
		if (newValue) {
			
			icon = IconsHandler.BIG_PLUS_CIRCLE();
			RotateTransition ft = new RotateTransition(Duration.millis(350), button);
			ft.setCycleCount(1);
			ft.setFromAngle(0.0);
			ft.setToAngle(90.0);
			ft.setAutoReverse(false);
			ft.play();
			
		} else {
			icon = IconsHandler.SMALL_PLUS_CIRCLE();
		}
		
		button.setGraphic(icon);
		
	}

	@Override
	public void handle(ActionEvent event) {
		SelectedDayCardController.getInstance().onAddEventButtonAction();
	}
	
}
