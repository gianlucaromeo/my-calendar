package mycalendar.view.control;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import lombok.NonNull;
import mycalendar.config.Sizes;
import mycalendar.controller.CalendarController;

public class MonthButton extends Button {
	
	@SuppressWarnings("unused")
	private int month; // 1 to 12

	public MonthButton(@NonNull StringProperty text, int month) {
		
		this.month = month;
		textProperty().bind(text);
		
		int width = Sizes.MONTH_BUTTON_WIDTH;
		int height = Sizes.MONTH_BUTTON_HEIGHT;
		this.setPrefWidth(width);
		this.setMinWidth(width);
		this.setMaxWidth(width);
		this.setPrefHeight(height);
		this.setMinHeight(height);
		this.setMaxHeight(height);
		
		this.setCursor(Cursor.HAND);
		
		this.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CalendarController.getInstance().onMonthChanghed(month);
			}
		});
		
		this.getStyleClass().add("month-button");
		
	}

	public void onSelected() {
		this.getStyleClass().add("month-button-selected");
	}
	
	public void onDeselected() {
		this.getStyleClass().remove("month-button-selected");
	}
	
}
