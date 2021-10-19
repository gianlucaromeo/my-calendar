package mycalendar.view.control;

import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import javafx.scene.Cursor;
import mycalendar.config.Sizes;
import mycalendar.controller.ThemeHandler;

public class ToggleThemeButton extends MFXCircleToggleNode {
	
	public ToggleThemeButton() {
		setText("");
		setSize(Sizes.TOGGLE_THEME_BUTTON_SIZE);
		setCursor(Cursor.HAND);
		setOnMouseClicked(new ThemeHandler(this));
	}
	
}
