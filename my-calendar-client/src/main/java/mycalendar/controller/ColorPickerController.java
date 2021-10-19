package mycalendar.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import mycalendar.config.ColorsSettings;

public class ColorPickerController extends GridPane {
	
	private static final int ROWS = 4;
	private static final int COLUMNS = 2;
	
	private static int lastColor = 7;
	private static int currentColor = 7;
	
	private List<CircleColorController> circles;
	
	private void setStyle() {
		setPrefWidth(ColorsSettings.COLOR_PICKER_PREF_WIDTH);
		setPrefHeight(ColorsSettings.COLOR_PICKER_PREF_HEIGHT);
		minWidthProperty().bind(prefWidthProperty());
		minHeightProperty().bind(prefHeightProperty());
		maxWidthProperty().bind(prefWidthProperty());
		maxHeightProperty().bind(prefHeightProperty());
	}
	
	private void setStyleClass() {
		getStyleClass().add("my-color-picker");
	}

	private void initColoredCircles() {
		
		int row = 0;
		int col = 0;
		
		for (String color : ColorsSettings.myColors) {
			
			CircleColorController circle = new CircleColorController(color);
			
			circle.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					onCircleSelected(circle);
				}
			});
			
			addColoredCircle(circle, col, row);
			
			col = (col+1) % COLUMNS;
			if (col == 0)
				row = (row+1) % ROWS;
			
		}
		
	}

	private void addColoredCircle(CircleColorController circle, int col, int row) {
		circles.add(circle);
		add(circle, col, row);
		setHalignment(circle, HPos.CENTER);
		setValignment(circle, VPos.CENTER);
	}

	public void onCircleSelected(Circle circle) {
		lastColor = currentColor;
		Circle c = findCircleFromColor(circle.getFill());
		currentColor = circles.indexOf(c);
		circles.get(lastColor).onDeselected();
		circles.get(currentColor).onSelected();
		CalendarEventDialogController.getInstance().onColorSelected(ColorsSettings.myColors[currentColor]);
	}
	
	private Circle findCircleFromColor(Paint fill) {
		for (Circle c : circles)
			if (c.getFill().equals(fill))
				return c;
		return null;
	}

	public String getColor() {
		return ColorsSettings.myColors[currentColor];
	}
	
	
	private void initGrid() {
		
		for (int r = 0; r < ROWS; r++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100.0 / ROWS);
			getRowConstraints().add(rowConst);
		}
		
		for (int c = 0; c < COLUMNS; c++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPercentWidth(100.0 / COLUMNS);
			getColumnConstraints().add(colConst);
		}
		
	}

	private static ColorPickerController instance = null;
	
	public static ColorPickerController getInstance() {
		if (instance == null)
			instance = new ColorPickerController();
		return instance;
	}
	
	private ColorPickerController() {
	
		circles = new ArrayList<CircleColorController>(ColorsSettings.myColors.length);
		
		initGrid();
		initColoredCircles();
		setStyle();
		setStyleClass();
		
		circles.get(currentColor).onSelected();	
		
	}
	
} // class MyColorPickerController
