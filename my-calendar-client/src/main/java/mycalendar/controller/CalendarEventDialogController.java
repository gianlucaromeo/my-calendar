package mycalendar.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.joda.time.DateTime;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import mycalendar.config.DurationSettings;
import mycalendar.config.GeneralSettings;
import mycalendar.config.Sizes;
import mycalendar.i18n.I18NHelper;
import mycalendar.model.CalendarEvent;
import mycalendar.model.CalendarEventType;
import mycalendar.net.Client;
import mycalendar.net.Protocol;
import mycalendar.util.IconsHandler;
import mycalendar.util.UpcomingEventsUtil;
import mycalendar.view.control.CustomMFXTextField;

/**
 * This class will be used for both Adding and Editing events.
 * */
public class CalendarEventDialogController extends StackPane {
	
	public static final int ADD_EVENT_TYPE = 0;
	public static final int EDIT_EVENT_TYPE = 1;

	/* Custom Components */
	private DateTimePickerController startDateTimePicker;
	private DateTimePickerController endDateTimePicker;
	private ColorPickerController colorPicker;
	private CustomMFXTextField eventTitleField; 
	private CustomMFXTextField localityDescriptionField;
	private CustomMFXTextField eventDescriptionField;
	
	@FXML private BorderPane mainBorderPane;
    @FXML private ComboBox<String> durationComboBox;
    @FXML private VBox localityContainer;
    @FXML private VBox durationContainer;
    @FXML private Label localityIcon;
    @FXML private Label localityLabel;
    @FXML private Label descriptionIcon;
    @FXML private VBox descriptionContainer;
    @FXML private HBox dateAndTimeContainer;
    @FXML private Label eventTitleLabel;
    @FXML private HBox titleContainer;
    @FXML private Label durationLabel;
    @FXML private Label eventDescriptionLabel;
    @FXML private Label durationIcon;
    @FXML private Button operationButton;
    @FXML private Button closeDialogButton;
    @FXML private Circle colorPickerSelector;
    @FXML private HBox colorPickerSelectorContainer;
    @FXML private BorderPane internalBorderPane;

	private boolean openColorPicker = false;
	private boolean colorSelectorClicked = false;
	private int operationType = ADD_EVENT_TYPE;
	private CalendarEvent oldEvent; // For Editing operations
	
	private GeneralSettings generalSettings = GeneralSettings.getInstance();
	private DurationSettings durationSettings = DurationSettings.getInstance();
	

	public void setOperationType(int operationType) {
		
		this.operationType = operationType;
		
		operationButton.textProperty().unbind();
		
		switch (operationType) {
		case ADD_EVENT_TYPE:
			operationButton.textProperty().bind(I18NHelper.getInstance().createBinding("OPERATION_TYPE_ADD_EVENT"));
			return;
		case EDIT_EVENT_TYPE:
			operationButton.textProperty().bind(I18NHelper.getInstance().createBinding("OPERATION_TYPE_EDIT_EVENT"));
			return;
		}
	}
	


	@FXML
	void onOperationButtonActionPerformed(ActionEvent event) {

		if (operationType == ADD_EVENT_TYPE)
			handleAddEventOperation();
		else
			handleEditExistingEventOperation();

	}

	private void handleEditExistingEventOperation() {
		
		if (oldEvent == null) {
			CalendarPageDialogsHandler.getInstance().SHOW_GENERIC_ERROR();
			return;
		}

		CalendarEvent editedEvent = getEvent();
		String res = "";
		try {
			res = Client.getInstance().editEvent(oldEvent, editedEvent);
			if (res.equals(Protocol.OK)) {
				CalendarPageController.getInstance().closeEventDialog();
				CalendarPageDialogsHandler.getInstance().SHOW_EVENT_EDITED();
				UpcomingEventsUtil.getInstance().startService();
			} else {
				CalendarPageDialogsHandler.getInstance().SHOW_GENERIC_ERROR();
			}
		} catch (Exception e) {
			System.out.println("Error in handleEditExistingEventOperation");
			e.printStackTrace();
		}
		System.out.println("Edit event: " + res);
		
	}

	private CalendarEvent getEvent() {

		String title = eventTitleField.getText();
		String description = eventDescriptionField.getText();
		String locality = localityDescriptionField.getText();

		// Event's dates must be stored as Text, following this order: YYYY-MM-dd hh-mm
		String dateStart = startDateTimePicker.getSelectedDate().toString();
		String dateTimeStart = dateStart + " " + startDateTimePicker.getTime();
		String dateEnd = endDateTimePicker.getSelectedDate().toString();
		String dateTimeEnd = dateEnd + " " + endDateTimePicker.getTime();
		
		if (title.isBlank() || dateStart.isBlank() || dateEnd.isBlank()) {
			CalendarPageDialogsHandler.getInstance().SHOW_GENERIC_ERROR();
			return null;
		}
		
		if (dateTimeEnd.compareTo(dateTimeStart) < 0) {
			CalendarPageDialogsHandler.getInstance().SHOW_EV_DATE_ERROR();
			return null;
		}

		String typeColor = ColorPickerController.getInstance().getColor();
		String typeTitle = generalSettings.DEFAULT_EVENT_TYPE_TITLE.get(); // TODO: Advanced function, not available
		
		CalendarEventType type = new CalendarEventType(typeTitle, typeColor);

		return new CalendarEvent(title, description, dateTimeStart, dateTimeEnd, type, locality);
	
	}



	private void handleAddEventOperation() {

		try {
			
			CalendarEvent newEvent = getEvent();
			if (newEvent == null)
				return;
			
			String res = Client.getInstance().addEvent(newEvent);
			
			if (res.equals(Protocol.OK)) {
				
				CalendarPageController.getInstance().closeEventDialog();
				CalendarController.getInstance().onNewEventAdded();
				SelectedDayCardController.getInstance().update();
				CalendarPageDialogsHandler.getInstance().SHOW_EVENT_ADDED();
				UpcomingEventsUtil.getInstance().startService(); 
				
			} else {
				
				CalendarPageDialogsHandler.getInstance().SHOW_GENERIC_ERROR();
				
			}
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void onCloseDialogButtonActionPerformed(ActionEvent event) {
		CalendarPageDialogsHandler.getInstance().SHOW_CLOSE_EV_INPUT_DIALOG();
	}

	private static CalendarEventDialogController instance = null;

	public static CalendarEventDialogController getInstance() {

		if (instance == null) {
			instance = new CalendarEventDialogController();
		}

		instance.initDialog();

		return instance;

	}
	
	private void initDialog() {

		durationComboBox.setValue(durationComboBox.getItems().get(0));
		
		// Option 'Create custom event types'
		// eventTypeControlsContainer.setVisible(Client.getInstance().isEventTypeAdvancedOptionActive());
		// // TODO: Advanced option not available now

	}
	
	public void updateIcons() {
		setIcons();
	}



	public void cleanFields() {
		
		eventTitleField.setText("");		
		localityDescriptionField.setText("");
		eventDescriptionField.setText("");
		startDateTimePicker.setHours(GeneralSettings.CAL_EVENT_DEFAULT_H_START);
		startDateTimePicker.setMinutes(GeneralSettings.CAL_EVENT_DEFAULT_M_START);
		endDateTimePicker.setHours(GeneralSettings.CAL_EVENT_DEFAULT_H_END);
		endDateTimePicker.setMinutes(GeneralSettings.CAL_EVENT_DEFAULT_M_END);
		startDateTimePicker.getDatePicker().setValue(LocalDate.now());
		endDateTimePicker.getDatePicker().setValue(LocalDate.now());
		
	}

	public void setEditEventDialog(CalendarEvent event) {
		
		cleanFields();
		
		durationComboBox.setDisable(false);
		endDateTimePicker.setDisable(false);
		durationComboBox.setValue(durationComboBox.getItems().get(0));
		
		setOperationType(EDIT_EVENT_TYPE);
		
		eventTitleField.setText(event.getTitle());
		
		if (event.getDescription() != null)
			eventDescriptionField.setText(event.getDescription());
		
		if (event.getLocality() != null)
			localityDescriptionField.setText(event.getLocality());
		
		setDateStart(event.getStartDate());
		setTimeStart(event.getStartDate());
		
		setDateEnd(event.getEndDate());
		setTimeEnd(event.getEndDate());
		
		
		String colorStr = event.getType().getColor();
		colorPickerSelector.setFill(Paint.valueOf(colorStr));
		CircleColorController circle = new CircleColorController(colorStr);
		colorPicker.onCircleSelected(circle);
		
		oldEvent = getEvent(); // It might change
		
	}

	private void setTimeEnd(String endDate) {
		String hours = endDate.substring(11, 13);
		String minutes = endDate.substring(14, 16);
		endDateTimePicker.setHours(hours);
		endDateTimePicker.setMinutes(minutes);
	}

	private void setTimeStart(String startDate) {
		String hours = startDate.substring(11, 13);
		String minutes = startDate.substring(14, 16);
		startDateTimePicker.setHours(hours);
		startDateTimePicker.setMinutes(minutes);
	}



	/**
	 * Checks if the user selected a custom duration for the new event.
	 */
	private void onDurationChanged(String newDuration) {
		if (newDuration == null)
			return;
		endDateTimePicker.setDisable(!newDuration.equals(durationSettings.getEVENT_DURATION_CUSTOM().get()));
	}

	private CalendarEventDialogController() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/dialog/CalendarEventDialog.fxml"));

		loader.setRoot(this);
		loader.setController(this);

		try {

			loader.load();
			initFields();
			setNamesFromLocale();
			setListeners();
			setStyle();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setListeners() {
		
		startDateTimePicker.getDatePicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
				
				if (durationComboBox.getValue().equals(durationSettings.getEVENT_DURATION_CUSTOM().get())) {
					
					if (endDateTimePicker.getDatePicker().getValue() == null) {
						endDateTimePicker.getDatePicker().setValue(newValue);
						endDateTimePicker.setDisable(false);
					}
					
				}
				
				if (startDateTimePicker.getDatePicker().getValue().compareTo(endDateTimePicker.getDatePicker().getValue()) > 0) {
					endDateTimePicker.getDatePicker().setValue(newValue);
					endDateTimePicker.setDisable(false);
				}
				
				if (durationComboBox.isDisabled())
					durationComboBox.setDisable(false);
			
			};
		});
		
		durationComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				onDurationChanged(newValue);
			}
		});
		
		internalBorderPane.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				if (!colorSelectorClicked) {
					closeColorPicker();
				}
				colorSelectorClicked = false;
			};
		});
		
		colorPickerSelector.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				colorSelectorClicked = true;
				onColorPickerSelectorClicked(!openColorPicker);
			}
		});
		
	}

	private void initFields() {
		
		initColorPicker();
		initOperationButton();
		
		startDateTimePicker = new DateTimePickerController("DT_START");
		endDateTimePicker = new DateTimePickerController("DT_END");
		
		initDurationComboBox();
		
		eventTitleField = new CustomMFXTextField("", "", Sizes.CAL_EVENT_TITLE_FIELD_WIDTH);
		localityDescriptionField = new CustomMFXTextField("", "", Sizes.CAL_EVENT_LOCALITY_FIELD_WIDTH);
		eventDescriptionField = new CustomMFXTextField("", "", Sizes.CAL_EVENT_DESCRIPTION_FIELD_WIDTH);
		
		addFieldsToParents();
		
	}

	private void initOperationButton() {
		operationButton.setCursor(Cursor.HAND);
	}



	private void addFieldsToParents() {
		dateAndTimeContainer.getChildren().add(startDateTimePicker);
		dateAndTimeContainer.getChildren().add(endDateTimePicker);
		titleContainer.getChildren().add(titleContainer.getChildren().size()-1, eventTitleField);
		localityContainer.getChildren().add(localityDescriptionField);
		descriptionContainer.getChildren().add(eventDescriptionField);
	}

	private void initColorPicker() {
		colorPicker = ColorPickerController.getInstance();
		getChildren().add(colorPicker);
		colorPicker.setVisible(false);
		setAlignment(colorPicker, Pos.TOP_LEFT);
		onColorSelected(colorPicker.getColor());
	}

	private void setStyle() {
		durationComboBox.setCursor(Cursor.HAND);
		getStyleClass().add("cal-event-dialog-base");
		eventTitleLabel.getStyleClass().add("cal-event-dialog-title-label");
		closeDialogButton.getStyleClass().add("close-event-dialog-button");
		mainBorderPane.getStyleClass().add("cal-event-dialog-main-container");
		durationContainer.getStyleClass().add("cal-event-dialog-duration-container");
		operationButton.getStyleClass().add("cal-event-operation-button");
		internalBorderPane.getStyleClass().add("cal-event-internal");
		colorPickerSelector.getStyleClass().add("colored-circle");
		setIcons();
	}

	private void setIcons() {

		closeDialogButton.setText("");
		closeDialogButton.setGraphic(IconsHandler.CLOSE_DIALOG());

		localityIcon.setText("");
		localityIcon.setGraphic(IconsHandler.MAP_MARKER());

		descriptionIcon.setText("");
		descriptionIcon.setGraphic(IconsHandler.DESCRIPTION());

		durationIcon.setText("");
		durationIcon.setGraphic(IconsHandler.CLOCK());

	}

	public void onColorPickerSelectorClicked(boolean open) {
		openColorPicker = open;
		if (openColorPicker) {
			updateColorPickerCoordinates();
			String color = ColorPickerController.getInstance().getColor();
			colorPickerSelector.setFill(Paint.valueOf(color));
		}
		colorPicker.setVisible(openColorPicker);
	}

	public void closeColorPicker() {
		openColorPicker = false;
		colorPicker.setVisible(false);
	}

	public void onColorSelected(String color) {
		colorPickerSelector.setFill(Paint.valueOf(color));
		closeColorPicker();
	}

	public void updateColorPickerCoordinates() {
		if (openColorPicker) {
			Point2D p = colorPickerSelector.localToScene(colorPickerSelector.getTranslateX(),
					colorPickerSelector.getTranslateY());
			p = sceneToLocal(p);
			colorPicker.setTranslateX(p.getX() + colorPickerSelector.getRadius() * 2);
			colorPicker.setTranslateY(p.getY());
		}
	}
	
	public void updateDurations() {
		initDurationComboBox();
	}

	private void initDurationComboBox() {
		ObservableList<String> durations = FXCollections.observableArrayList();
		StringProperty[] eventDurations = durationSettings.getEventDurations();
		for (StringProperty eventDuration : eventDurations) {
			durations.add(eventDuration.get());
		}
		durationComboBox.getItems().clear();
		durationComboBox.getItems().addAll(durations);
		durationComboBox.valueProperty().set(durations.get(0));
		durationComboBox.setDisable(startDateTimePicker.getSelectedDate() == null);
		durationComboBox.valueProperty().addListener(new EventDurationChangeController(startDateTimePicker, endDateTimePicker));
	}

	public void setDateStart(DateTime startDate) {
		LocalDate localDate = LocalDate.parse(startDate.toString("YYYY-MM-dd"));
		startDateTimePicker.getDatePicker().setValue(localDate);
	}

	public void setDateEnd(DateTime endDate) {
		LocalDate localDate = LocalDate.parse(endDate.toString("YYYY-MM-dd"));
		endDateTimePicker.getDatePicker().setValue(localDate);
	}
	
	public void setDateStart(String startDate) {
		LocalDate localDate = LocalDate.parse(startDate.substring(0, 10));
		startDateTimePicker.getDatePicker().setValue(localDate);
	}

	private void setDateEnd(String endDate) {
		LocalDate localDate = LocalDate.parse(endDate.substring(0, 10));
		endDateTimePicker.getDatePicker().setValue(localDate);
	}

	private void setNamesFromLocale() {
		
		I18NHelper i18n = I18NHelper.getInstance();
		
		eventTitleLabel.textProperty().bind(i18n.createBinding("CAL_EV_TITLE_LABEL"));
		eventDescriptionLabel.textProperty().bind(i18n.createBinding("CAL_EV_DESCR_LABEL"));
		localityLabel.textProperty().bind(i18n.createBinding("CAL_EV_LOCALITY_LABEL"));
		durationLabel.textProperty().bind(i18n.createBinding("CAL_EV_DURATION_LABEL"));
		
		eventTitleField.promptTextProperty().bind(i18n.createBinding("CAL_EV_TITLE_PROMPT"));
		eventDescriptionField.promptTextProperty().bind(i18n.createBinding("CAL_EV_DESCR_PROMPT"));
		localityDescriptionField.promptTextProperty().bind(i18n.createBinding("CAL_EV_LOCALITY_PROMPT"));
		
	}

}