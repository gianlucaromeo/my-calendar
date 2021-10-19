package mycalendar.controller;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import mycalendar.i18n.I18NHelper;
import mycalendar.model.CalendarEvent;
import mycalendar.util.IconsHandler;

public class UpcomingEventsCardController extends StackPane {

	@FXML private BorderPane mainBorderPane;
	@FXML private HBox topContainer;
	@FXML private ScrollPane scrollPane;
	@FXML private Label titleLabel;
	@FXML private VBox upcomingEventsContainer;
	
	private static UpcomingEventsCardController instance = null;

	public static UpcomingEventsCardController getInstance() {
		if (instance == null)
			instance = new UpcomingEventsCardController();
		return instance;
	}

	private UpcomingEventsCardController() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/control/UpcomingEventsCard.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try { 
			
			loader.load();
			setNamesFromLocale();
			setStyle();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setStyle() {
		getStyleClass().add("upcoming-ev-card");
		topContainer.getStyleClass().add("upcoming-ev-top-container");
		mainBorderPane.getStyleClass().add("upcoming-ev-container");
		titleLabel.getStyleClass().add("upcoming-ev-title");
		upcomingEventsContainer.getStyleClass().add("upcoming-ev-events-container");
	}
	
	private void setNamesFromLocale() {
		titleLabel.textProperty().bind(I18NHelper.getInstance().createBinding("UPCOMING_EVENTS_TITLE"));
	}

	public void reset() {
		upcomingEventsContainer.getChildren().clear();
	}

	public void update(List<CalendarEvent> upcomingEvents) {
		
		reset();
		
		if (upcomingEvents != null && upcomingEvents.size() > 0) {
			for (CalendarEvent ev : upcomingEvents)
				upcomingEventsContainer.getChildren().add(new UpcomingEvent(ev));
		} else {
			
			Label noUpcomingEvents = new Label("");
			noUpcomingEvents.setGraphic(IconsHandler.NO_EVENTS());
			upcomingEventsContainer.getChildren().add(noUpcomingEvents);
			
		}
		
	}

}
