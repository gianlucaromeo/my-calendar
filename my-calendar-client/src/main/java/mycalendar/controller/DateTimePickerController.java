package mycalendar.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.joda.time.DateTime;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mycalendar.i18n.I18NHelper;

public class DateTimePickerController extends VBox {

	@FXML private Label titleLabel;
    @FXML private Slider minutesSlider;
    @FXML private DatePicker datePicker;
    @FXML private Label minutesSliderLabel;
    @FXML private Label hoursSliderLabel;
    @FXML private Slider hoursSlider;
    @FXML private TextField hoursField;
    @FXML private TextField minutesField;

	public DateTimePickerController(String title) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/control/MyDateTimePicker.fxml"));

		loader.setRoot(this);
		loader.setController(this);

		try {

			loader.load();
			setStyle();
			titleLabel.textProperty().bind(I18NHelper.getInstance().createBinding(title));
			setListeners();
			setCurrentTime();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void setListeners() {
		
		FieldToSliderController hFieldController = new FieldToSliderController(hoursSlider);
		FieldToSliderController mFieldController = new FieldToSliderController(minutesSlider);
		SliderToFieldController hSliderController = new SliderToFieldController(hoursField);
		SliderToFieldController mSliderController = new SliderToFieldController(minutesField);
		
		hoursField.textProperty().addListener(hFieldController);
		minutesField.textProperty().addListener(mFieldController);
		hoursSlider.valueProperty().addListener(hSliderController);
		minutesSlider.valueProperty().addListener(mSliderController);
		
		setHoursTextFormatter();
		setMinutesTextFormatter();
		
	}

	private void setMinutesTextFormatter() {	
		hoursField.setTextFormatter(new MyTextFormatter(24));
	}

	private void setHoursTextFormatter() {
		minutesField.setTextFormatter(new MyTextFormatter(60));
	}

	private void setStyle() {
		getStyleClass().add("dtpicker-base");
		hoursField.getStyleClass().add("dtpicker-textfield");
		minutesField.getStyleClass().add("dtpicker-textfield");
	}

	public void setCurrentTime() {
		hoursSlider.setValue(DateTime.now().getHourOfDay());
		minutesSlider.setValue(DateTime.now().getMinuteOfHour());
	}

	public String getTime() {
		return hoursField.getText() + ":" + minutesField.getText();
	}
	
	public int getHours() {
		return Integer.parseInt(hoursField.getText());
	}
	
	public int getMinutes() {
		return Integer.parseInt(minutesField.getText());
	}
	
	public void setHours(String hours) {
		hoursField.setText(hours);
	}

	public void setMinutes(String minutes) {
		minutesField.setText(minutes);
	}
	
	public LocalDate getSelectedDate() {
		return datePicker.valueProperty().get();
	}
	
	public DatePicker getDatePicker() {
		return datePicker;
	}
	
	public void setValues(DateTime newDate, int hours, int minutes) {
		LocalDate dtToLocalDate = LocalDate.parse(newDate.toString("YYYY-MM-dd"));
		datePicker.setValue(dtToLocalDate);
		hoursSlider.setValue(hours);
		minutesSlider.setValue(minutes);
	}

}
