package mycalendar.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mycalendar.i18n.I18NHelper;
import mycalendar.model.CalendarEvent;
import mycalendar.util.IconsHandler;

public class UpcomingEvent extends VBox  {
	
	@FXML private Label dateLabel;
	@FXML private Label titleLabel;
	@FXML private Label localityLabel;
	@FXML private HBox descriptionContainer;
	@FXML private HBox timeContainer;
	@FXML private HBox titleContainer;
	@FXML private HBox localityContainer;
	@FXML private Label timeLabel;
	@FXML private Label descriptionLabel;
	@FXML private HBox dateContainer;
	
	@Getter private CalendarEvent calendarEvent;

	
	public UpcomingEvent(CalendarEvent calendarEvent) {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/control/UpcomingEvent.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			
			loader.load();
			
			this.calendarEvent = calendarEvent;
			initFields();
			setStyle();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setStyle() {
		
		getStyleClass().add("upcoming-event-base");
		titleContainer.getStyleClass().add("upcoming-event-title-container");
		titleLabel.getStyleClass().add("upcoming-event-title");
		descriptionLabel.getStyleClass().add("upcoming-event-description");
		
		String color = calendarEvent.getType().getColor();
		setStyle("-fx-border-color: " + color + ";");
		
	}

	private void initFields() {
		initTitle();
		initLocality();
		initDate();
		initTime();
		initDescription();
	}

	private void initDescription() {
		
		descriptionLabel.setGraphic(IconsHandler.SMALL_DESCRIPTION());
		
		String description = calendarEvent.getDescription();
		if (description == null || description.isBlank()) {
			descriptionLabel.textProperty().bind(I18NHelper.getInstance().createBinding("UPCOMING_EV_NO_DESCRIPTION"));
		} else {
			descriptionLabel.setText(calendarEvent.getDescription());
		}
		
	}

	private void initTime() {
		timeLabel.setGraphic(IconsHandler.SMALL_CLOCK());
		String timeStart = calendarEvent.getStartDate().substring(11, 16);
		String timeEnd = calendarEvent.getEndDate().substring(11, 16);
		timeLabel.setText(timeStart + " - " + timeEnd);
	}

	private void initDate() {
		dateLabel.setText(calendarEvent.getStartDate().substring(0, 10));
	}

	private void initLocality() {
		
		localityLabel.setGraphic(IconsHandler.SMALL_MAP_MARKER());
		
		String locality = calendarEvent.getLocality();
		if (locality == null || locality.trim().isEmpty()) {
			localityLabel.textProperty().bind(I18NHelper.getInstance().createBinding("UPCOMING_EV_NO_LOCALITY"));
		} else {
			localityLabel.setText(calendarEvent.getLocality());
		}
		
	}

	private void initTitle() {
		titleLabel.setText(calendarEvent.getTitle());
	}
	
}
