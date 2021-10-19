package mycalendar.controller;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import mycalendar.config.GeneralSettings;
import mycalendar.config.Sizes;
import mycalendar.net.Client;
import mycalendar.util.UpcomingEventsUtil;

public class ScenesController {

	public static final int DARK_THEME = 0;
	public static final int LIGHT_THEME = 1;
	@Getter private static int currentTheme = LIGHT_THEME;
	
	private Stage stage = null;
	private Scene scene = null;
	
	@SuppressWarnings("unused")
	private Font font = Font.loadFont(getClass().getResourceAsStream("/application/fonts/Helvetica.ttf"), Sizes.FONT_SIZE);
	
	
	public void init(Stage stage) throws IOException {
		this.stage = stage;
		initScene();
	}
	
	private void initScene() throws IOException {
		
		StackPane root = InitialPageController.getInstance();
		scene = new Scene(root, Sizes.INIT_PAGE_WIDTH, Sizes.INIT_PAGE_HEIGTH);
		stage.setScene(scene);
		stage.setTitle(GeneralSettings.APP_TITLE);
		scene.getStylesheets().add(getClass().getResource(GeneralSettings.LIGHT_THEME_RES_PATH).toExternalForm());
	
		setSceneCurrentTheme(); // Also updates background
		
		stage.setResizable(GeneralSettings.INIT_PAGE_RESIZABLE);
		stage.centerOnScreen(); 
		stage.show();
		
		ChangeListener<Number> stageSizeListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				CalendarEventDialogController.getInstance().updateColorPickerCoordinates();
			}
		};

		stage.widthProperty().addListener(stageSizeListener);
		stage.heightProperty().addListener(stageSizeListener);		
		
		stage.getIcons().add(new Image(getClass().getResourceAsStream(GeneralSettings.APP_ICON_PATH)));
		
	}
	
	private void setSceneCurrentTheme() {
		InitialPageController.getInstance().onThemeChanged(currentTheme);
	}

	public void setMyCalendarPage() {
		
		StackPane root = CalendarPageController.getInstance();
		
		scene.setRoot(root);
		
		stage.setMinWidth(Sizes.MYCAL_PAGE_MIN_WIDTH);
		stage.setMinHeight(Sizes.MYCAL_PAGE_MIN_HEIGHT);
		stage.setResizable(GeneralSettings.MYCAL_PAGE_RESIZABLE);
		
		NotificationsController.getInstance().showWelcomeNotification();
		
	}
	
	public void toggleTheme() {
		
		scene.getStylesheets().clear();
		
		if (currentTheme == DARK_THEME) {
			currentTheme = LIGHT_THEME;
			scene.getStylesheets().add(getClass().getResource(GeneralSettings.LIGHT_THEME_RES_PATH).toExternalForm());
		} else {
			currentTheme = DARK_THEME;
			scene.getStylesheets().add(getClass().getResource(GeneralSettings.DARK_THEME_RES_PATH).toExternalForm());
		}
		
		InitialPageController.getInstance().onThemeChanged(currentTheme);
		CalendarEventDialogController.getInstance().updateIcons();
		SelectedDayCardController.getInstance().updateIcons();
		UpcomingEventsUtil.getInstance().startService();
		
	}
	
	public void onLogout() {
		
		CalendarPageController.getInstance().onLogout();
		CalendarController.getInstance().onLogout();
    	UpcomingEventsUtil.getInstance().onLogout();
    	Client.getInstance().reset();
    	
		StackPane root = InitialPageController.getInstance();
		scene.setRoot(root);
		stage.setResizable(false);
		
	}
	
	private static ScenesController instance = null;
	
	public static ScenesController getInstance() {
		if (instance == null)
			instance = new ScenesController();
		return instance;
	}
	
	private ScenesController() {
	}
	
}
