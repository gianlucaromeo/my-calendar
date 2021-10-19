package mycalendar.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import mycalendar.config.ColorsSettings;

public class CircleColorController extends Circle implements ChangeListener<Boolean> {

	private final static double SELECTED_SIZE = 2.0;
	private final static double NOT_SELECTED_SIZE = 0.0;
	private final static double HOVER_RADIUS = 12.0;
	private final static double NOT_HOVER_RADIUS = 10.0;
	
	@Getter @Setter private String color;
	
	public CircleColorController(String color) {
		this.color = color;
		setStyle();
		setStyleClass();
		setListeners();
	}

	private void setStyleClass() {
		getStyleClass().add("colored-circle");
	}

	private void setStyle() {
		setCursor(Cursor.HAND);
		fillProperty().set(Paint.valueOf(color));
		setStroke(Paint.valueOf(ColorsSettings.BORDER_COLOR));
		setRadius(NOT_HOVER_RADIUS);
		onDeselected();
	}
	
	private void setListeners() {
		hoverProperty().addListener(this);
	}

	public void onSelected() {
		setStrokeWidth(SELECTED_SIZE);
	}
	
	public void onDeselected() {
		setStrokeWidth(NOT_SELECTED_SIZE);
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		setRadius(newValue ? HOVER_RADIUS : NOT_HOVER_RADIUS);
	}
	
}
