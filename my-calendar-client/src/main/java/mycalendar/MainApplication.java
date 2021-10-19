package mycalendar;

import javafx.application.Application;
import javafx.stage.Stage;
import mycalendar.controller.ScenesController;

public class MainApplication extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		ScenesController.getInstance().init(primaryStage);
	}

}
