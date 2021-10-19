package mycalendar.view.control;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.scene.paint.Paint;
import mycalendar.config.ColorsSettings;

public class CustomMFXPasswordField extends MFXPasswordField {
	
	public CustomMFXPasswordField(String promptText, int prefWidth) {
		setPromptText(promptText);
		setPrefWidth(prefWidth);
		allowCopyProperty().set(false);
		setLineColor(Paint.valueOf(ColorsSettings.BORDER_COLOR));
		setStyle();
	}

	private void setStyle() {
		getStyleClass().add("custom-text-field");
	}
	
}
