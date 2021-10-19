package mycalendar.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class SliderToFieldController implements ChangeListener<Number> {

	private TextField target;
	
	public SliderToFieldController(TextField target) {
		this.target = target;
	}
	
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		String value = newValue.intValue() < 10 ? "0" : "";
		value += newValue.intValue();
		target.textProperty().setValue(value);
	}
	
}
