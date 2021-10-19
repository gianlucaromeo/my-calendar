package mycalendar.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mycalendar.i18n.I18NHelper;
import mycalendar.util.IconsHandler;

public class NoEventsController extends BorderPane {
	
	@FXML private Label infoLabel;
    @FXML private Button addEventButton;
    @FXML private VBox mainContainer;

    private static NoEventsController instance = null;
    
    public static NoEventsController getInstance() {
    	if (instance == null)
    		instance = new NoEventsController();
    	return instance;
    }
    
    private NoEventsController() {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/control/NoEventsControl.fxml"));
    	
    	loader.setRoot(this);
		loader.setController(this);
		
    	try {
    		
			loader.load();
			
			setStyle();
			setNamesFromLocale();
			initAddEventButton();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

	private void setStyle() {
		
		infoLabel.setGraphic(IconsHandler.NO_EVENTS());
		
		getStyleClass().add("noevents-control-base");
		infoLabel.getStyleClass().add("noevents-control-info-label");
		addEventButton.getStyleClass().add("noevents-control-add-button");
		mainContainer.getStyleClass().add("noevents-main-container");
		
	}
	
	private void initAddEventButton() {
		addEventButton.setCursor(Cursor.HAND);
		addEventButton.setOnAction(new SelectedDayAddButtonController(addEventButton));
	}
	
	private void setNamesFromLocale() {
		addEventButton.textProperty().bind(I18NHelper.getInstance().createBinding("NO_EVENTS_ADD_ONE_BUTTON"));
		infoLabel.textProperty().bind(I18NHelper.getInstance().createBinding("NO_EVENTS_THIS_DAY"));
	}

	public void updateIcons() {
		infoLabel.setGraphic(IconsHandler.NO_EVENTS());
	}
	
}
