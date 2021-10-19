package mycalendar.view.control;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.paint.Paint;
import mycalendar.config.ColorsSettings;

public class CustomMFXTextField extends MFXTextField {

	public CustomMFXTextField(String text, String promptText, int prefWidth) {
		super(text);
		setPromptText(promptText);
		setPrefWidth(prefWidth);
		setLineColor(Paint.valueOf(ColorsSettings.BORDER_COLOR));
		setStyle();
	}

	private void setStyle() {
		getStyleClass().add("custom-text-field");
	}
	
}
