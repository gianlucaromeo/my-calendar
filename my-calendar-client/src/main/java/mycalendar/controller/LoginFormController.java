package mycalendar.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import mycalendar.i18n.I18NHelper;
import mycalendar.net.Client;
import mycalendar.net.Protocol;
import mycalendar.util.LoginErrorsHandler;
import mycalendar.util.UpcomingEventsUtil;

public class LoginFormController extends BorderPane implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		if (usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty())
			InitialPageDialogsHandler.getInstance().SHOW_USER_AUTHENTICATION_ERROR();
		else
			onLogin();
	}
	
	@FXML private Button loginButton;
	@FXML private BorderPane loginForm;
    @FXML private Hyperlink forgotPasswordHyperlink;
    @FXML private PasswordField passwordField;
    @FXML private Label dontHaveAccountLabel;
    @FXML private Label usernameLabel;
    @FXML private Label loginLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField usernameTextField;
    @FXML private Hyperlink createAccountHyperlink;
    @FXML private HBox topContainer;
    
    @FXML
    void onCreateAccountHyperlinkActionPerformed(ActionEvent event) {
    	InitialPageController.getInstance().setRegistrationForm();
    }
	
    private void onLogin() {
    	
    	String username = usernameTextField.getText();
    	String password = passwordField.getText();
    	
    	String loginStatus = null;
    	
    	try {
    		
			loginStatus = Client.getInstance().login(username, password);
			System.out.println("Login: " + loginStatus);
			if (loginStatus == null || !loginStatus.equals(Protocol.OK)) {
	    		LoginErrorsHandler.getInstance().handleError(loginStatus);
				Client.getInstance().reset();
				return;
	    	}
			
		} catch (Exception e) {
			
			System.err.println("[LoginFormController] Error in onLoginButtonActionPerformed():");
			Client.getInstance().reset();
			e.printStackTrace();
			
		}
    	
    	Client.getInstance().initCalendarEvents();
    	ScenesController.getInstance().setMyCalendarPage();	
    	CalendarPageController.getInstance().setUserUsername(username);
    	
    	startUpcomingEventsService();
    	
    }
    
	private void startUpcomingEventsService() {
		UpcomingEventsUtil.getInstance().startService();;
	}

	private static LoginFormController instance = null;
	
	public static LoginFormController getInstance() {
		if (instance == null)
			instance = new LoginFormController();
		return instance;
	}

	private LoginFormController() {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/form/LoginForm.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			
			loader.load();
			setNamesFromLocale();
			initLoginButton();
			initUsernameAndPasswordFields();
			setStyle();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	} // constructor

	private void initUsernameAndPasswordFields() {
		usernameTextField.setOnAction(this);
		passwordField.setOnAction(this);
	}

	private void initLoginButton() {
		loginButton.setCursor(Cursor.HAND);
		loginButton.setOnAction(this);
	}

	private void setNamesFromLocale() {
		
		I18NHelper i18n = I18NHelper.getInstance();
		
		loginLabel.textProperty().bind(i18n.createBinding("LOGIN_TITLE_LABEL"));
		usernameLabel.textProperty().bind(i18n.createBinding("LOGIN_USERNAME_LABEL"));
		passwordLabel.textProperty().bind(i18n.createBinding("LOGIN_PASSWORD_LABEL"));
		
		usernameTextField.promptTextProperty().bind(i18n.createBinding("LOGIN_USERNAME_PROMPT"));
		
		forgotPasswordHyperlink.textProperty().bind(i18n.createBinding("LOGIN_FORGOTPASSWORD_HLINK"));
		dontHaveAccountLabel.textProperty().bind(i18n.createBinding("LOGIN_DONTHAVEACCOUNT_HLINK"));
		createAccountHyperlink.textProperty().bind(i18n.createBinding("LOGIN_CREATE_ACCOUNT_HLINK"));
		
		loginButton.textProperty().bind(i18n.createBinding("LOGIN_LOGIN_BUTTON"));
		
	}

	private void setStyle() {
		
		forgotPasswordHyperlink.setVisible(false);
		
		getStyleClass().add("login-page");
		topContainer.getStyleClass().add("login-form-top-container");
		loginLabel.getStyleClass().add("login-label");
		createAccountHyperlink.getStyleClass().add("create-account-hlink");
		loginButton.getStyleClass().add("login-button");
		loginForm.getStyleClass().add("login-form");
		forgotPasswordHyperlink.getStyleClass().add("forgot-password-hyperlink");	    
	}

	public void afterRegistration(String username) {
		passwordField.setText("");
		usernameTextField.setText(username);
	}
	
}
