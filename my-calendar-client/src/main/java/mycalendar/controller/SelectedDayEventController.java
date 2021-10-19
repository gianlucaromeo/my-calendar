package mycalendar.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import mycalendar.model.CalendarEvent;
import mycalendar.util.IconsHandler;

public class SelectedDayEventController extends VBox {

	@FXML private Label timeStartLabel;
	@FXML private Label eventTitleLabel;
	@FXML private Label minus2;
	@FXML private Label minus1;
	@FXML private Button editEventButton;
	@FXML private Button deleteEventButton;
	@FXML private Circle circle;
	
	private CalendarEvent calendarEvent = null;
	
	@FXML
	void onEditEventButtonActionPerformed(ActionEvent event) {
		CalendarPageController.getInstance().onEditEvent(calendarEvent);
	}

	public SelectedDayEventController(CalendarEvent calendarEvent) {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/control/SelectedDayEvent.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			
			loader.load();
			
			this.calendarEvent = calendarEvent;
			eventTitleLabel.setTooltip(new Tooltip(getTootltipText()));
			initFields();
			setStyle();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getTootltipText() {
	
		StringBuilder sb = new StringBuilder(calendarEvent.getTitle());
		
		String descr = calendarEvent.getDescription();
		if (descr != null && !descr.isBlank())
			sb.append("\n - ").append(descr);
		
		String loc = calendarEvent.getLocality();
		if (loc != null && !loc.isBlank())
			sb.append("\n - " + loc);
		
		return sb.toString();
	
	}

	private void initFields() {
		
		initButtons();
		initTimeIcons();
		
		eventTitleLabel.setText(calendarEvent.getTitle());
		timeStartLabel.setText(calendarEvent.getStartDate().substring(11, 16)); // from YYYY-MM-DD HH:MM
		circle.setFill(Paint.valueOf(calendarEvent.getType().getColor()));
		
	}
	
	private void initTimeIcons() {
		
		minus1.setText("");
		//minus1.setGraphic(IconsHandler.MINUS());
		
		minus2.setText("");
		//minus2.setGraphic(IconsHandler.MINUS());
	}

	private void initButtons() {
		
		editEventButton.setText("");
		editEventButton.setGraphic(IconsHandler.PENCIL());
		editEventButton.setCursor(Cursor.HAND);
		
		deleteEventButton.setText("");
		deleteEventButton.setGraphic(IconsHandler.TRASH());
		deleteEventButton.setCursor(Cursor.HAND);
		setDeleteButtonAction();
		
	}

	

	private void setDeleteButtonAction() {
		deleteEventButton.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				CalendarPageDialogsHandler.getInstance().SHOW_DELETE_EV_INPUT_DIALOG(calendarEvent);
			}
		});
	}

	private void setStyle() {
		editEventButton.getStyleClass().add("selected-event-edit-icon");
		deleteEventButton.getStyleClass().add("selected-event-delete-icon");
		timeStartLabel.getStyleClass().add("selected-day-event-time-start");
		getStyleClass().add("selected-event");
	}
	
}
