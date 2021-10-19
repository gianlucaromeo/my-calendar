package mycalendar.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mycalendar.i18n.I18NHelper;
import mycalendar.util.IconsHandler;

public class InitialPageLeftController extends BorderPane {

	@FXML private VBox mainContainer;
	@FXML private Label iconLabel;
	@FXML private Label titleLabel;
	@FXML private HBox iconContainer;
	@FXML private HBox contentContainer;
	@FXML private HBox mainContentContainer;
	@FXML private Label mainContentLabel;
	@FXML private Label contentLabel;
	@FXML private HBox titleContainer;

	private static InitialPageLeftController instance = null;
	
	public static InitialPageLeftController getInstance() {
		if (instance == null)
			instance = new InitialPageLeftController();
		instance.updateIcon();
		return instance;
	}

	private void updateIcon() {
		iconLabel.setGraphic(IconsHandler.INIT_CALENDAR());
	}

	private InitialPageLeftController() {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/control/InitialPageLeft.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			loader.load();
			setNamesFromLocale();
			setStyle();
			iconLabel.setText("");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

	private void setStyle() {
		getStyleClass().add("init-left-base");
		mainContainer.getStyleClass().add("init-left-main-container");
		titleLabel.getStyleClass().add("init-left-title");
		mainContentLabel.getStyleClass().add("init-left-main-content");
		contentLabel.getStyleClass().add("init-left-content");
		iconLabel.getStyleClass().add("init-left-icon");
	}

	private void setNamesFromLocale() {
		
		I18NHelper i18n = I18NHelper.getInstance();
		
		titleLabel.textProperty().bind(i18n.createBinding("INITIAL_PAGE_LEFT_TITLE"));
		mainContentLabel.textProperty().bind(i18n.createBinding("INITIAL_PAGE_LEFT_MAIN_CONTENT"));
		contentLabel.textProperty().bind(i18n.createBinding("INITIAL_PAGE_LEFT_CONTENT"));
		
	}
	
}
