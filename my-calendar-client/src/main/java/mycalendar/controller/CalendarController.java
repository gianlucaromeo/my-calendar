package mycalendar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import mycalendar.config.CalendarSettings;
import mycalendar.i18n.I18NHelper;
import mycalendar.model.CalendarDay;
import mycalendar.model.CalendarEvent;
import mycalendar.model.MyCalendarModel;
import mycalendar.net.Client;
import mycalendar.view.control.MonthButton;

public class CalendarController extends BorderPane {
    
	private CalendarSettings settings = new CalendarSettings();
    private MyCalendarModel mycalendar;

    @FXML private GridPane grid;
    @FXML private HBox buttonsContainerHBox;
    @FXML private ComboBox<Integer> yearsComboBox;
    @FXML private Label currentDayLabel;
    @FXML private Button addEventButton;
    
    private List<MonthButton> monthButtons;


	/**
	 * 
	 * This method is called if the user selected a new month or year
	 * or if the user added, edited or deleted an event.
	 * 
	 * After the Client receives the updated events, this method looks
	 * for the Events of the DateTime the User selected. 
	 * 
	 * Then, the method updateGrid() is called.
	 *
	 **/
    private void updateUserEvents() {
        
        List<CalendarEvent> allEvents = Client.getInstance().getUserEvents();
        List<CalendarEvent> currentEvents = new ArrayList<CalendarEvent>();
        
        String selectedYear = String.valueOf(mycalendar.getSelectedYear());
        String selectedMonth = String.valueOf(mycalendar.getSelectedMonth());
        
        for (CalendarEvent event : allEvents) {
        	
            String eventYear = event.getStartDate().substring(0, 4);
            String eventMonth = event.getStartDate().substring(5, 7);
            
            if (eventMonth.charAt(0) == '0')
                eventMonth = eventMonth.substring(1);
            
            if (eventYear.equals(selectedYear) && eventMonth.equals(selectedMonth)) {
                currentEvents.add(event);
            }       
        
        }
        
        updateGrid(currentEvents);
        
    }

    /**
     * 
     * Updates the Grid, after an event has occurred.
     * 
     * First, this method will update the support days to add before
     * the first day of the selected DateTime.
     * 
     * Then, it updates the day of the selected DateTime,
     * by calling the method updateDays(), which will call
     * the method updateRightDays() to fill the grid.
     * 
     * */
    private void updateGrid(List<CalendarEvent> currentEvents) {

        grid.getChildren().clear();
        showDaysLabels();
        updateSelectedMonth();
        
        DateTime currentDateTimeStart = new DateTime(mycalendar.getSelectedYear(), mycalendar.getSelectedMonth(), 1, 0, 0);        
        
        int row = 1; // Days labels are in row 0. So, starts from 1
        int col = currentDateTimeStart.getDayOfWeek() % MyCalendarModel.COLUMNS; // Columns start from Sunday
        
        updateLeftSupportDays(currentDateTimeStart, col, row);
        updateDays(currentDateTimeStart, col, row, currentEvents); 
        
    }
    
    /**
     * 
     * Updates the grid by setting a support day
     * for each day of the month before the selected month,
     * in order to fill the grid.
     * 
     * A support day is a day in the grid which doesn't belong to the selected Month.
     * 
     * */
    private void updateLeftSupportDays(DateTime currentDateTimeStart, int col, int row) {
		
		if (col == 0) // First day of the selected month (is it Sunday?)
			return;
		
    	int count = 0;
    	int daysCounter = 0;
    	
        while (count != col) {
            DateTime date = currentDateTimeStart.minusDays(++daysCounter);
            CalendarDay day = new CalendarDay(date, null);
            CalendarDayController dayController = new CalendarDayController(day);
            dayController.isSupportCalendarDay(true);
            grid.add(dayController, col - count - 1, row);
            ++count;
        }
        
	}
    
    /**
     * 
     * Creates a CalendarDayController for each day of the selected month.
     * 
     * */
	private void updateDays(DateTime currentDateTimeStart, int col, int row, List<CalendarEvent> currentEvents) {

        boolean flag = true; // True while adding days of the selected month
        
        int nextMonth = currentDateTimeStart.plusMonths(1).getMonthOfYear();
        int daysCounter = 0;
        
        while (flag) {

            DateTime date = currentDateTimeStart.plusDays(daysCounter++);
            if (date.getMonthOfYear() == nextMonth)
                break;
            
            List<CalendarEvent> currentDayEvents = getEventsOfCurrentDay(date, currentEvents);
            CalendarDay day = new CalendarDay(date, currentDayEvents);
            CalendarDayController dayController = new CalendarDayController(day);
            
            grid.add(dayController, col, row);

            //if (mycalendar.isInitialSetup())
            	if (date.toString("YYYY-MM-dd").equals(DateTime.now().toString("YYYY-MM-dd")))
            		SelectedDayCardController.getInstance().onSelectedDayChanged(dayController);
            
            col = (col+1) % MyCalendarModel.COLUMNS;
            if (col == 0) {
            	row = (row+1) % MyCalendarModel.ROWS;
            	flag = !(row == 0);
            }
            
        }
        
        updateRightSupportDays(currentDateTimeStart, col, row);       
        
	}
    
    /**
     * 
     * Updates the grid by setting a support day
     * for each day of the month before the selected month,
     * in order to fill the grid.
     * 
     * A support day is a day in the grid which doesn't belong to the selected Month.
     * 
     * */
    private void updateRightSupportDays(DateTime currentDateTimeStart, int col, int row) {
    	
    	int daysCounter = 0;
    	boolean flag = true; // There are always right-support days
        
    	while (flag) {
            
    		DateTime date = currentDateTimeStart.plusDays(daysCounter++); // Not ++daysCounter !
            CalendarDay day = new CalendarDay(date, null);
            CalendarDayController dayController = new CalendarDayController(day);
            dayController.isSupportCalendarDay(true);
            
            grid.add(dayController, col, row);
            
            col = (col+1) % MyCalendarModel.COLUMNS;
            if (col == 0) {
            	row = (row+1) % MyCalendarModel.ROWS;
            	flag = !(row == 0);
            }
            
        }
    	
	}

	private List<CalendarEvent> getEventsOfCurrentDay(DateTime date, List<CalendarEvent> currentEvents) {
		List<CalendarEvent> currentDayEvents = new ArrayList<CalendarEvent>();
        for (CalendarEvent ev : currentEvents) {
			String currentEventDate = ev.getStartDate();
			String eventDay = currentEventDate.substring(8, 10);
			if (eventDay.equals(date.toString("dd"))) {
				currentDayEvents.add(ev);
			}
		}
        return currentDayEvents;
	}

	private void updateSelectedMonth() {
    	String selectedMonth = settings.getMonths()[mycalendar.getSelectedMonth()-1].get();
        currentDayLabel.setText(selectedMonth + ", ");
	}

    
    @FXML
    void onAddEventActionPerformed(ActionEvent event) {
        CalendarEventDialogController.getInstance().setOperationType(CalendarEventDialogController.ADD_EVENT_TYPE);
        CalendarPageController.getInstance().onAddEvent();
    }

    public void onNewEventAdded() {
        updateUserEvents();
    }
    
    public void onMonthChanghed(int newMonth) {
    	
        if (mycalendar.getSelectedMonth() == newMonth)
            return;
        
        mycalendar.updateSelectedMonth(newMonth);
        monthButtons.get(mycalendar.getPrevSelectedMonth()-1).onDeselected();
        monthButtons.get(mycalendar.getSelectedMonth()-1).onSelected();
        updateUserEvents();
        
    }

    private void onYearChanged(int newYear) {
        if (mycalendar.getSelectedYear() == newYear)
            return;
        mycalendar.setSelectedYear(newYear);
        updateUserEvents();
    };   
    
    public void refresh() {
		updateUserEvents();
	}
    
    /* Constructor and Initial Setup Methods */
    
    private static CalendarController instance = null;

    public static CalendarController getInstance() {
        if (instance == null)
            instance = new CalendarController();
        return instance;
    }

    private CalendarController() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/control/Calendar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
        	
            loader.load();
            mycalendar = MyCalendarModel.getInstance();
        	initFields();
        	setNamesFromLocale();
        	setStyle();
        	
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    private void setNamesFromLocale() {
    	I18NHelper i18n = I18NHelper.getInstance();
		addEventButton.textProperty().bind(i18n.createBinding("CALENDAR_ADD_EVENT_BUTTON"));
	}


	private void initFields() {
    	initAvailableYearsCBox();
        initAddEventButton();
        initGrid();
	}

	private void initAvailableYearsCBox() {
    	
    	ObservableList<Integer> availableYears = FXCollections.observableArrayList();
        
    	int year = mycalendar.getMinYear();
        int maxYear = mycalendar.getMaxYear();
        while (year <= maxYear) {
            availableYears.add(year);
            ++year;
        }
        
        yearsComboBox.getItems().addAll(availableYears);
        yearsComboBox.valueProperty().set(mycalendar.getSelectedYear());
        yearsComboBox.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                onYearChanged(newValue);
            }
        });
        
        yearsComboBox.setCursor(Cursor.HAND);
        
	}
    
	private void initAddEventButton() {
        addEventButton.setCursor(Cursor.HAND);
	}
    
    private void initGrid() {
        String month = DateTime.now().toString("MMMM");
        StringUtils.capitalize(month);
        currentDayLabel.setText(month + ", ");
        showMonthsButtons();
        updateUserEvents();
    }

    private void showDaysLabels() {
    	StringProperty[] days = settings.getDays();
    	for (int i = 0; i < days.length; ++i) {
    		 Label dayLabel = new Label(days[i].get());
             grid.add(dayLabel, i, 0);
             GridPane.setHalignment(dayLabel, HPos.CENTER);
             GridPane.setValignment(dayLabel, VPos.CENTER);
        }
    }


    private void showMonthsButtons() {
    	StringProperty [] months = settings.getMonths();
        monthButtons = new ArrayList<MonthButton>();
        for (int i = 0; i < months.length; ++i) {
            MonthButton monthButton = new MonthButton(months[i], i+1);
            buttonsContainerHBox.getChildren().add(monthButton);
            monthButtons.add(monthButton);
        }
        monthButtons.get(mycalendar.getSelectedMonth()-1).onSelected();
    }

    private void setStyle() {
    	getStyleClass().add("calendar-base");
    	yearsComboBox.getStyleClass().add("years-combo-box");      
    	addEventButton.getStyleClass().add("add-event-button");
    	grid.getStyleClass().add("calendar-grid");		
	}

	public void onLogout() {
		instance = null;
	}

    /* (End) Constructor and Initial Setup Methods */

}
