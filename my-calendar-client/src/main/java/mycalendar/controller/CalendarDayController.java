package mycalendar.controller;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.Getter;
import mycalendar.model.CalendarDay;
import mycalendar.model.CalendarEvent;

public class CalendarDayController extends StackPane {

	@FXML private Label dayLabel;
	@FXML private BorderPane mainContainer;
	@FXML private Label titleLabel;
	@FXML private Label moreEventsLabel;
	@FXML private Circle circle;
	@FXML private Button supportButton;

	@Getter private CalendarDay calendarDay = null; // Model

	/**
	 * Sets the number of the day of the month and shows,
	 * if exists, the first event of this day whit the
	 * color the User assigned to it.
	 * If more than one event has been planned for this day,
	 * it adds a label with the informations about how many
	 * events there will be.
	 * */
	private void initFields() {
		
		dayLabel.setText(calendarDay.getDateTime().toString("dd"));

		List<CalendarEvent> events = calendarDay.getEvents();
		
		if (events != null && events.size() > 0) {

			String firstEventTitle = events.get(0).getTitle();
			String firstEventColor = events.get(0).getType().getColor();
			titleLabel.setText(firstEventTitle);

			if (firstEventColor != null)
				circle.fillProperty().set(Paint.valueOf(firstEventColor));

			int totalEvents = events.size();
			
			if (totalEvents > 1)
				moreEventsLabel.setText("+ " + (totalEvents - 1) + " ");
			else
				moreEventsLabel.setText("");

		} else {
			
			circle.setVisible(false);
			titleLabel.setText("");
			moreEventsLabel.setText("");
			
		}

	}
	
	@FXML
	void onSupportButtonActionPerformed(ActionEvent event) {
		SelectedDayCardController.getInstance().onSelectedDayChanged(this);
	}

	/**
	 * A Calendar Day is a "SupportCalendarDay" if it
	 * does not belong to the current month the user selected,
	 * but is shown in the grid.
	 * */
	public void isSupportCalendarDay(boolean status) {
		supportButton.setDisable(status);
		supportButton.setVisible(true);
		supportButton.getStyleClass().add("calendar-day-support-button");
		mainContainer.getStyleClass().clear();
		mainContainer.getStyleClass().add("support-calendar-day");
	}
	
	private void setStyle() {
		this.getStyleClass().add("calendar-day-base");
		mainContainer.getStyleClass().add("calendar-day");
	}
	
	public CalendarDayController(CalendarDay calendarDay) {

		this.calendarDay = calendarDay;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/control/CalendarDay.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {

			loader.load();
			initFields();
			setStyle();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
