package mycalendar.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

public class FieldToSliderController implements ChangeListener<String> {
	
	private Slider slider;

	public FieldToSliderController(Slider slider) {
		this.slider = slider;
	}
	
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		try {
			int n = Integer.parseInt(newValue);
			slider.setValue(n);
		} catch (Exception e) {
			return;
		}
	}
	
}
