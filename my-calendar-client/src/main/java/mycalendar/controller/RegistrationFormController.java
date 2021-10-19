package mycalendar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mycalendar.config.RegistrationFormSettings;
import mycalendar.i18n.I18NHelper;
import mycalendar.model.User;
import mycalendar.net.Client;
import mycalendar.net.Protocol;
import mycalendar.util.IconsHandler;
import mycalendar.view.control.CustomMFXPasswordField;
import mycalendar.view.control.CustomMFXTextField;

public class RegistrationFormController extends BorderPane {

	private CustomMFXTextField mfxFirstNameField;
	private CustomMFXTextField mfxLastNameField;
	private CustomMFXTextField mfxUsernameField;
	private CustomMFXTextField mfxEmailField;
	private CustomMFXPasswordField mfxPasswordField;
	private CustomMFXPasswordField mfxConfirmPasswordField;
	
	private List<VBox> fields;
	private List<Label> fieldsInfo;
	
	@FXML private VBox fieldsContainer;
	@FXML private Label registerTitleLabel;
    @FXML private Label usernameLabel;
    @FXML private VBox usernameContainer;
    @FXML private Label passwordLabel;
    @FXML private Label lastNameLabel;
    @FXML private BorderPane rightPane;
    @FXML private VBox lNameContainer;
    @FXML private VBox emailContainer;
    @FXML private VBox passwordContainer;
    @FXML private Button gotoLoginButton;
    @FXML private Button registerButton;
    @FXML private HBox registerButtonContainer;
    @FXML private VBox fNameContainer;
    @FXML private Label confirmPasswordLabel;
    @FXML private Label firstNameLabel;
    @FXML private VBox confPasswordContainer;
    @FXML private Label emailLabel;
    @FXML private HBox topContainer;
    @FXML private Button passwordInformationsButton;
    
	@FXML
	void onGotoLoginButtonActionPerformed(ActionEvent event) {
		InitialPageController.getInstance().setLoginForm();
	}

	@FXML
	void onRegisterButtonActionPerformed(ActionEvent event) {

		String fname = mfxFirstNameField.getText().trim();
		String lname = mfxLastNameField.getText().trim();
		String email = mfxEmailField.getText().trim();
		String username = mfxUsernameField.getText().trim();
		String password = mfxPasswordField.getPassword();
		String confirmedPassword = mfxConfirmPasswordField.getPassword();

		if (fname.isEmpty() || lname.isEmpty() || username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty() || email.isEmpty()) {
			InitialPageDialogsHandler.getInstance().SHOW_REGISTRATION_ERROR(Protocol.ERROR);
			return;
		}
		
		if (password.equals(confirmedPassword)) {

			User newUser = new User(fname, lname, username, password, email);

			String registrationStatus = null;

			try {
				
				String res = registrationStatus = Client.getInstance().register(newUser);
				
				if (!res.equals(Protocol.OK)) {
					InitialPageDialogsHandler.getInstance().SHOW_REGISTRATION_ERROR(res);
				} else {
					InitialPageDialogsHandler.getInstance().SHOW_REGISTRATION_OK();
					InitialPageController.getInstance().setLoginForm(username);
				}
				
			} catch (Exception e) {
				
				System.err.println("[RegistrationFormController] Error in onRegisterButtonActionPerformed():");
				Client.getInstance().reset();
				e.printStackTrace();
				
			}

			System.out.println("Regitration status: " + registrationStatus);
			Client.getInstance().reset();

		} else {
			InitialPageDialogsHandler.getInstance().SHOW_PASSWORDS_MATCH_ERROR();
		}

	} // onRegisterButtonActionPerformed

	private static RegistrationFormController instance = null;

	public static RegistrationFormController getInstance() {
		if (instance == null)
			instance = new RegistrationFormController();
		//instance.cleanFields(); // Binding problems
		return instance;
	}

	private RegistrationFormController() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mycalendar/view/form/RegistrationForm.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			
			loader.load();
			initFields();
			setNamesFromLocale();
			setStyle();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	} // constructor


	private void initFields() {
		initCustomTextAndPasswordFields();
		addFieldsToParents();
		initFieldsList();
		initFieldsInfoList();
		initGoToLoginButton();
		initRegisterButton();
		initPasswordInformationButton();
	}

	private void initRegisterButton() {
		registerButton.setCursor(Cursor.HAND);
	}

	private void initPasswordInformationButton() {
		passwordInformationsButton.setCursor(Cursor.HAND);
		passwordInformationsButton.setText("");
		passwordInformationsButton.setGraphic(IconsHandler.QUESTION_CIRCLE());
		passwordInformationsButton.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				InitialPageDialogsHandler.getInstance().SHOW_PASSWORD_REQUIREMENTS();
			}
		});
	}

	private void initFieldsInfoList() {
		fieldsInfo = new ArrayList<Label>();
		fieldsInfo.add(firstNameLabel);
		fieldsInfo.add(lastNameLabel);
		fieldsInfo.add(passwordLabel);
		fieldsInfo.add(confirmPasswordLabel);
		fieldsInfo.add(usernameLabel);
		fieldsInfo.add(emailLabel);
	}

	private void initGoToLoginButton() {
		gotoLoginButton.setCursor(Cursor.HAND);
		gotoLoginButton.setGraphic(IconsHandler.GO_BACK());
	}

	private void initCustomTextAndPasswordFields() {
		
		RegistrationFormSettings rfs = RegistrationFormSettings.getInstance();
		
		int tfPrefWidth = rfs.getREGISTRATION_TEXT_FIELD_PREF_WIDTH();		
		mfxFirstNameField = new CustomMFXTextField("", "", tfPrefWidth);
		mfxLastNameField = new CustomMFXTextField("", "", tfPrefWidth);
		mfxUsernameField = new CustomMFXTextField("", "", tfPrefWidth);
		mfxEmailField = new CustomMFXTextField("", "", tfPrefWidth);
		
		int pwPrefWidth = rfs.getREGISTRATION_PASSWORD_FIELD_PREF_WIDTH();
		mfxPasswordField = new CustomMFXPasswordField("Password", pwPrefWidth);
		mfxConfirmPasswordField = new CustomMFXPasswordField("Password", pwPrefWidth);
				
	}

	private void initFieldsList() {
		fields = new ArrayList<VBox>();
		fields.add(fNameContainer);
		fields.add(lNameContainer);
		fields.add(usernameContainer);
		fields.add(passwordContainer);
		fields.add(confPasswordContainer);
		fields.add(emailContainer);
	}

	private void addFieldsToParents() {
		fNameContainer.getChildren().add(mfxFirstNameField);
		lNameContainer.getChildren().add(mfxLastNameField);
		usernameContainer.getChildren().add(mfxUsernameField);
		passwordContainer.getChildren().add(mfxPasswordField);
		confPasswordContainer.getChildren().add(mfxConfirmPasswordField);
		emailContainer.getChildren().add(mfxEmailField);
	}

	private void setStyle() {
		
		getStyleClass().add("registration-page");
		registerTitleLabel.getStyleClass().add("registration-form-title");
		fieldsContainer.getStyleClass().add("registration-form");
		registerButton.getStyleClass().add("registration-button");
		gotoLoginButton.getStyleClass().add("goto-login-button");
		topContainer.getStyleClass().add("registration-form-top-container");
		passwordInformationsButton.getStyleClass().add("password-info-btn");
		
		for (VBox v : fields)
			v.getStyleClass().add("reg-field-container");

		for (Label l : fieldsInfo)
			l.getStyleClass().add("reg-fields-info");
		
	}

	private void setNamesFromLocale() {
		
		I18NHelper i18n = I18NHelper.getInstance();
		
		registerTitleLabel.textProperty().bind(i18n.createBinding("REGISTRATION_FORM_TITLE"));
		gotoLoginButton.textProperty().bind(i18n.createBinding("REGISTRATION_GO_TO_LOGIN_BUTTON"));
		
		firstNameLabel.textProperty().bind(i18n.createBinding("REGISTRATION_FIRST_NAME_LABEL"));
		lastNameLabel.textProperty().bind(i18n.createBinding("REGISTRATION_LAST_NAME_LABEL"));
		passwordLabel.textProperty().bind(i18n.createBinding("REGISTRATION_PASSWORD_LABEL"));
		confirmPasswordLabel.textProperty().bind(i18n.createBinding("REGISTRATION_CONFIRM_PASSWORD_LABEL"));
		emailLabel.textProperty().bind(i18n.createBinding("REGISTRATION_EMAIL_LABEL"));
		
		mfxFirstNameField.promptTextProperty().bind(i18n.createBinding("REGISTRATION_FIRST_NAME_PROMPT"));
		mfxLastNameField.promptTextProperty().bind(i18n.createBinding("REGISTRATION_LAST_NAME_PROMPT"));
		mfxUsernameField.promptTextProperty().bind(i18n.createBinding("REGISTRATION_USERNAME_PROMPT"));
		mfxEmailField.promptTextProperty().bind(i18n.createBinding("REGISTRATION_EMAIL_PROMPT"));
		
		registerButton.textProperty().bind(i18n.createBinding("REGISTRATION_REGISTER_BUTTON"));
		
	}
	
}
