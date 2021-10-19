package mycalendar.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import mycalendar.i18n.I18NHelper;
import mycalendar.view.control.LanguagesComboBox;
import mycalendar.view.control.ToggleThemeButton;

public class SettingsController extends StackPane {

	@FXML private HBox mainContainer;
    @FXML private Button langButton;
    @FXML private Button themeButton;
    @FXML private HBox selectedOptionContainer;
    @FXML private VBox buttonsContainer;

    private boolean clickedOutside = true;
    
    @FXML
    void onThemeButtonAction(ActionEvent event) {
    	setThemeOption();
    }

    @FXML
    void onLangButtonAction(ActionEvent event) {
    	setLangOption();
    }
    
    public void setLangOption() {
    	if (selectedOptionContainer.getChildren().size() > 0)
    		selectedOptionContainer.getChildren().remove(selectedOptionContainer.getChildren().size()-1);
    	selectedOptionContainer.getChildren().add(new LanguagesComboBox());   	
    }
    
    public void setThemeOption() {
    	if (selectedOptionContainer.getChildren().size() > 0)
    		selectedOptionContainer.getChildren().remove(selectedOptionContainer.getChildren().size()-1);
    	selectedOptionContainer.getChildren().add(new ToggleThemeButton());
    }
    
    private static SettingsController instance = null;
    
    public static SettingsController getInstance() {
    	if (instance == null)
    		instance = new SettingsController();
    	return instance;
    }
    
    private SettingsController() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/control/Settings.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			
			loader.load();
			setStyle();
			setNamesFromLocale();
			setLangOption();
			setListener();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setStyle() {
		getStyleClass().add("settings-base");
		mainContainer.getStyleClass().add("settings-container");
		buttonsContainer.getStyleClass().add("settings-btn-container");
		themeButton.getStyleClass().add("settings-option-button");
		langButton.getStyleClass().add("settings-option-button");
	}
	
	private void setNamesFromLocale() {
		I18NHelper i18n = I18NHelper.getInstance();
		langButton.textProperty().bind(i18n.createBinding("LANGUAGE"));
		themeButton.textProperty().bind(i18n.createBinding("THEME_BUTTON"));
	}
	
	private void setListener() {

		setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				if (clickedOutside) {
					CalendarPageController.getInstance().closeSettingsControl();
					//System.out.println("outside");
					clickedOutside = true;
				} else {
					//System.out.println("inside");
				}
				clickedOutside = true;
			};
		});
		
		mainContainer.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				clickedOutside = false;
				//System.out.println(event.getSource());
			};
		});
		
	}
	
}
