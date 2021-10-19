package mycalendar.controller;

import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mycalendar.model.CalendarEvent;
import mycalendar.util.IconsHandler;

public class SelectedDayCardController extends VBox {
	
	@Getter private DateTime selectedDayDT;
	
	@FXML private HBox topContainer;
    @FXML private HBox cardTitleContainer;	
    @FXML private Button addEventButton;
	@FXML private Label selectedDayLabel;
    @FXML private VBox eventsContainer;
    @FXML private ScrollPane scrollPane;
    
	private static SelectedDayCardController instance = null;

	public static SelectedDayCardController getInstance() {
		if (instance == null)
			instance = new SelectedDayCardController();
		return instance;
	}

	private SelectedDayCardController() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/control/SelectedDayCard.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try { 
			
			loader.load();
			initAddEventButton();
			setStyle();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setStyle() {
		
		Insets insets = new Insets(0, 20, 20, 20);
		setMargin(this, insets);
		
		selectedDayLabel.getStyleClass().add("selected-card-selected-day");
		getStyleClass().add("selected-day-card");
		topContainer.getStyleClass().add("selected-day-top-container");
		addEventButton.getStyleClass().add("add-event-button-sel-card");
		eventsContainer.getStyleClass().add("selected-day-events-container");
		
	}

	private void initAddEventButton() {
		
		addEventButton.setText("");
		addEventButton.setGraphic(IconsHandler.SMALL_PLUS_CIRCLE());
		addEventButton.setCursor(Cursor.HAND);
		
		addEventButton.hoverProperty().addListener(new SelectedDayAddButtonController(addEventButton));
		addEventButton.setOnAction(new SelectedDayAddButtonController(addEventButton));
	
	}
	
	void onAddEventButtonAction() {
		CalendarPageController.getInstance().onAddEvent(selectedDayDT);
	}
	
	private CalendarDayController selectedDayController = null;
	
	public void onSelectedDayChanged(CalendarDayController selectedDayController) {
		
		selectedDayDT = selectedDayController.getCalendarDay().getDateTime();
		
		eventsContainer.getChildren().clear();
		this.selectedDayController = selectedDayController;
		
		String selectedDay = selectedDayController.getCalendarDay().getDateTime().toString("YYYY-MM-dd");
		selectedDayLabel.setText(" " + selectedDay);
		selectedDayLabel.setGraphic(IconsHandler.SELECTED_EV_CALENDAR());

		List<CalendarEvent> selectedDayEvents = this.selectedDayController.getCalendarDay().getEvents();
		
		if (selectedDayEvents.isEmpty()) {
			setEmptyFields();
			return;
		} else {
			for (CalendarEvent calendarEvent : selectedDayEvents) {
				SelectedDayEventController event = new SelectedDayEventController(calendarEvent);
				eventsContainer.getChildren().add(event);
			}
		}
		
	}
	
	public void updateIcons() {
		if (selectedDayController == null)
			return;
		if (selectedDayController.getCalendarDay().getEvents().isEmpty()) {
			NoEventsController.getInstance().updateIcons();
		}
	}

	private void setEmptyFields() {
		NoEventsController noEventsController = NoEventsController.getInstance();
		eventsContainer.getChildren().add(noEventsController);
	}

	public void update() {
		onSelectedDayChanged(selectedDayController);
	}
	
}
