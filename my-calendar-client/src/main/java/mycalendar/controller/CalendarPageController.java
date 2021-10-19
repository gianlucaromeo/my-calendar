package mycalendar.controller;

import java.io.IOException;

import org.joda.time.DateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mycalendar.i18n.I18NHelper;
import mycalendar.model.CalendarEvent;
import mycalendar.net.Client;
import mycalendar.net.Protocol;
import mycalendar.util.IconsHandler;
import mycalendar.util.UpcomingEventsUtil;

public class CalendarPageController extends StackPane {

	@FXML private BorderPane myCalendarBorderPane;
	@FXML private HBox topContainer;
	@FXML private Label myCalendarLabel;
	@FXML private Label userUsernameLabel;
	@FXML @Getter private VBox leftCardsContainer;
	
	@FXML private MenuButton settingsMenu;
	@FXML private MenuItem changeThemeMenuItem;
	@FXML private MenuItem changeLangMenuItem;
	@FXML private MenuItem logoutMenuItem;
	
	
	private CalendarEventDialogController calendarEventDialog = null;
	private boolean dialogIsOpen = false;
	private boolean settingsControlIsOpen = false;
	
	public void showSettingsControl() {
		
		if (settingsControlIsOpen) { // Shortcuts: clicked more than once --> Toggle it
			closeSettingsControl();
			return;
		}
		
		if (dialogIsOpen) {
			getChildren().get(getChildren().size() - 1).setEffect(new GaussianBlur());
		}
		
		myCalendarBorderPane.setEffect(new GaussianBlur());		
		getChildren().add(SettingsController.getInstance());
		settingsControlIsOpen = true;

	}
	
	public void closeSettingsControl() {
	
		getChildren().remove(getChildren().size() - 1);

		if (dialogIsOpen)
			getChildren().get(getChildren().size() - 1).setEffect(null);
		else
			myCalendarBorderPane.effectProperty().set(null);
		
		settingsControlIsOpen = false;
	
	}

	@FXML
    void onChangeThemeAction(ActionEvent event) {
		
		if (settingsControlIsOpen)
			return;
		
		showSettingsControl();
		SettingsController.getInstance().setThemeOption();
		
    }

    @FXML
    void onChangeLangAction(ActionEvent event) {
    	
    	if (settingsControlIsOpen)
			return;
    	
    	showSettingsControl();
		SettingsController.getInstance().setLangOption();
		
    }

    @FXML
    void onLogoutAction(ActionEvent event) {
    	settingsMenu.hide();
    	ScenesController.getInstance().onLogout();
    }
    
    public void onLogout() {
    	instance = null;
	}

	public void setUserUsername(String username) {
		userUsernameLabel.setText(username);
	}

	public void onAddEvent() {
		
		if (dialogIsOpen || settingsControlIsOpen)
			return;
		
		calendarEventDialog = CalendarEventDialogController.getInstance();
		calendarEventDialog.cleanFields();
		calendarEventDialog.setOperationType(CalendarEventDialogController.ADD_EVENT_TYPE);
		myCalendarBorderPane.setEffect(new GaussianBlur());
		
		getChildren().add(calendarEventDialog);
		dialogIsOpen = true;
		
	}
	
	public void onAddEvent(DateTime selectedDT) {
		onAddEvent();
		calendarEventDialog.setDateStart(selectedDT);
		calendarEventDialog.setDateEnd(selectedDT);
	}
	
	public void onEditEvent(CalendarEvent calendarEvent) {
		
		if (dialogIsOpen)
			return;
		
		calendarEventDialog = CalendarEventDialogController.getInstance();
		calendarEventDialog.setEditEventDialog(calendarEvent);
		myCalendarBorderPane.setEffect(new GaussianBlur());
		
		getChildren().add(calendarEventDialog);
		dialogIsOpen = true;
		
	}
	
	public void onDeleteEvent(CalendarEvent calendarEvent) {
		
		try {
			
			if (Client.getInstance().deleteEvent(calendarEvent).equals(Protocol.OK)) {
				
				getChildren().remove(getChildren().size() - 1);
				CalendarPageDialogsHandler.getInstance().SHOW_EVENT_DELETED(true);
				CalendarController.getInstance().refresh();
				UpcomingEventsUtil.getInstance().startService();
				
			} else {
				
				CalendarPageDialogsHandler.getInstance().SHOW_EVENT_DELETED(false);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void closeEventDialog() {
		
		getChildren().remove(getChildren().size() - 1);
		myCalendarBorderPane.effectProperty().set(null);
		CalendarController.getInstance().onNewEventAdded();
		
		dialogIsOpen = false;
		
	}
	
	public void onDeleteEventInputDialog(CalendarEvent toDelete, boolean ok) {
		if (ok) {
			onDeleteEvent(toDelete);
			myCalendarBorderPane.setEffect(null);	
		} else {
			getChildren().remove(getChildren().size() - 1);
			myCalendarBorderPane.setEffect(null);
		}
	}
	
	
	public void onOpenInputDialog(CustomInputDialog d) {
		
		if (dialogIsOpen)
			getChildren().get(getChildren().size()-1).setEffect(new GaussianBlur());
		else
			myCalendarBorderPane.setEffect(new GaussianBlur());
		
		getChildren().add(d);
		
	}
	
	public boolean onCloseInputDialog(boolean input) {
		
		getChildren().remove(getChildren().size() - 1);
		getChildren().get(getChildren().size() - 1).effectProperty().set(null);
		
		if (!input) {
			if (dialogIsOpen) {
				getChildren().remove(getChildren().size() - 1);
				myCalendarBorderPane.effectProperty().set(null);
				dialogIsOpen = false;
			}
		}
		
		return input;
		
	}
	
	
	private static CalendarPageController instance = null;

	public static CalendarPageController getInstance() {

		if (instance == null)
			instance = new CalendarPageController();
		return instance;
	}

	private CalendarPageController() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/page/MyCalendarPage.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			
			loader.load();
			setStyle();
			initCalendar();
			setNamesFromLocale();
			
			setOnKeyPressed(new CalendarShortcutsController());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setStyle() {
		
		myCalendarLabel.setGraphic(IconsHandler.CALENDAR_TOP_BAR());

		myCalendarLabel.getStyleClass().add("mycalendar-label");
		myCalendarBorderPane.getStyleClass().add("calendar-page");
		topContainer.getStyleClass().add("calendar-page-top-container");
		userUsernameLabel.getStyleClass().add("user-username-calendar-page");
		settingsMenu.getStyleClass().add("settings-menu-btn");
		
		changeThemeMenuItem.getStyleClass().add("my-menu-item");
		changeLangMenuItem.getStyleClass().add("my-menu-item");
		logoutMenuItem.getStyleClass().add("my-menu-item");
		
	}
	
	private void initCalendar() {
		
		settingsMenu.setText("");
		updateIcons();

		myCalendarBorderPane.setCenter(CalendarController.getInstance());

		/* Initializes UpcomingEventsCard and add it to this Page */
		UpcomingEventsCardController upcomingEventsCard = UpcomingEventsCardController.getInstance();
		leftCardsContainer.getChildren().add(upcomingEventsCard);
		
		/* Initializes SelectedDayCard and add it to this Page */
		SelectedDayCardController selectedDayCard = SelectedDayCardController.getInstance();
		leftCardsContainer.getChildren().add(selectedDayCard);
		VBox.setVgrow(selectedDayCard, Priority.ALWAYS);
		
		myCalendarLabel.setOnMouseEntered(new CalendarIconController(myCalendarLabel));

	}
	
	public void updateIcons() {
		settingsMenu.setGraphic(IconsHandler.GEAR());
	}
	
	private void setNamesFromLocale() {
		I18NHelper i18n = I18NHelper.getInstance();
		changeLangMenuItem.textProperty().bind(i18n.createBinding("MENU_CHANGE_LANGUAGE"));
		changeThemeMenuItem.textProperty().bind(i18n.createBinding("MENU_CHANGE_THEME"));	
	}

}
