package mycalendar.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import mycalendar.util.IconsHandler;

public abstract class CustomInputDialog extends StackPane {
	
	@FXML private VBox mainContainer;
    @FXML private VBox titleAndContentContainer;
    @FXML private Label titleLabel;
    @FXML private HBox exitButtonContainer;
    @FXML private HBox buttonsContainer;
    @FXML private Label contentLabel;
    
    @FXML private Button exitButton;
    @FXML private Button greenButton;
    @FXML private Button redButton;
    
	@FXML 
	abstract void onExitButtonAction(ActionEvent event);

	@FXML
	abstract void onGreenButtonAction(ActionEvent event);

	@FXML
	abstract void onRedButtonAction(ActionEvent event);
	
	public CustomInputDialog(String title, String content, String okButtonText, String closeButtonText) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/dialog/CustomInputDialog.fxml"));

		loader.setRoot(this);
		loader.setController(this);

		try {

			loader.load();

			initExitButton();
			
			titleLabel.setText(title);
			contentLabel.setText(content);
			greenButton.setText(okButtonText);
			redButton.setText(closeButtonText);
			
			setStyle();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void initExitButton() {
		exitButton.setText("");
		exitButton.setGraphic(IconsHandler.SMALL_CLOSE_DIALOG());
	}

	private void setStyle() {
		
		exitButton.setCursor(Cursor.HAND);
		redButton.setCursor(Cursor.HAND);
		greenButton.setCursor(Cursor.HAND);
		exitButton.setCursor(Cursor.HAND);
		
		getStyleClass().add("input-dlg-base");
		exitButton.getStyleClass().add("input-dlg-exit-btn");
		mainContainer.getStyleClass().add("input-dlg-main-container");
		titleAndContentContainer.getStyleClass().add("input-dlg-title-content-container");
		titleLabel.getStyleClass().add("input-dlg-title");
		contentLabel.getStyleClass().add("input-dlg-content");
		buttonsContainer.getStyleClass().add("input-dlg-btns-container");
		greenButton.getStyleClass().add("input-dlg-ok-btn");
		redButton.getStyleClass().add("input-dlg-no-btn");
		
	}
	
}
