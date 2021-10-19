package mycalendar.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import mycalendar.view.control.LanguagesComboBox;
import mycalendar.view.control.ToggleThemeButton;

public class InitialPageController extends StackPane {

	private BorderPane mainBorderPane;

	private ToggleThemeButton themeButton;
	private LanguagesComboBox languagesCBox;
	private HBox topContainer;

	private static InitialPageController instance = null;

	public static InitialPageController getInstance() {
		if (instance == null)
			instance = new InitialPageController();
		return instance;
	}

	private InitialPageController() {
		mainBorderPane = new BorderPane();
		getChildren().add(mainBorderPane);
		StackPane.setAlignment(mainBorderPane, Pos.CENTER);
		mainBorderPane.setCenter(InitialPageLeftController.getInstance());
		setLoginForm();
		initTopContainer();
		setStyle();
	}

	private void setStyle() {
		getStyleClass().add("init-page-base");
		topContainer.getStyleClass().add("init-page-top");
	}

	private void initTopContainer() {
		
		topContainer = new HBox(20);
		Insets insets = new Insets(5, 0, 5, 10);
		topContainer.setPadding(insets);
		topContainer.setAlignment(Pos.CENTER_LEFT);

		themeButton = new ToggleThemeButton();
		languagesCBox = new LanguagesComboBox();

		topContainer.getChildren().add(languagesCBox);
		topContainer.getChildren().add(themeButton);

		mainBorderPane.setTop(topContainer);

	}

	private void setLightThemeBG() {
		mainBorderPane.setCenter(InitialPageLeftController.getInstance());
		/*
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
		Image image = new Image(getClass().getResourceAsStream("/application/images/login-light-bg.png"), this.getWidth(), this.getHeight(), true, true);
		this.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize)));*/
	}
	
	private void setDarkThemeBG() {
		mainBorderPane.setCenter(InitialPageLeftController.getInstance());
		/*
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
		Image image = new Image(getClass().getResourceAsStream("/application/images/login-dark-bg.png"), this.getWidth(), this.getHeight(), true, true);
		this.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize)));*/
	}
	
	public void onThemeChanged(int theme) {
		if (theme == ScenesController.DARK_THEME)
			setDarkThemeBG();
		else
			setLightThemeBG();
	}

	public void setRegistrationForm() {
		RegistrationFormController registrationForm = RegistrationFormController.getInstance();
		mainBorderPane.setRight(registrationForm);
	}
	
	public void setLoginForm() {
		LoginFormController loginForm = LoginFormController.getInstance();
		mainBorderPane.setRight(loginForm);
	}
	
	public void setLoginForm(String username) {
		setLoginForm();
		LoginFormController.getInstance().afterRegistration(username);
	}

}
