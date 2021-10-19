package mycalendar.controller;

import io.github.palexdev.materialfx.controls.base.AbstractMFXDialog;
import io.github.palexdev.materialfx.controls.base.AbstractMFXDialog.MFXDialogEvent;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import io.github.palexdev.materialfx.controls.factories.MFXAnimationFactory;
import io.github.palexdev.materialfx.controls.factories.MFXDialogFactory;
import javafx.event.EventHandler;
import mycalendar.config.DialogsSettings;
import mycalendar.config.PasswordSettings;
import mycalendar.config.RegistrationFormSettings;

public class InitialPageDialogsHandler implements EventHandler<MFXDialogEvent> {

	private InitialPageController initialPageController = InitialPageController.getInstance();
	private DialogsSettings settings = DialogsSettings.getInstance();
	
	private AbstractMFXDialog d;
	
	@Override
	public void handle(MFXDialogEvent event) {
		int pos = initialPageController.getChildren().size() - 1;
		initialPageController.getChildren().remove(pos);
	}
	
	public void SHOW_USER_ALREADY_LOGGED_IN_ERROR() {
		String title = settings.get_USER_ALREADY_LOGGED_IN_TITLE().get();
		String content = settings.get_USER_ALREADY_LOGGED_IN_CONTENT().get();
		d = getErrorDialog(title, content, true, true);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		show(d);
	}
	
	public void SHOW_USER_AUTHENTICATION_ERROR() {
		String title = settings.get_USER_AUTHENTICATION_ERROR_TITLE().get();
		String content = settings.get_USER_AUTHENTICATION_ERROR_CONTENT().get();
		d = getErrorDialog(title, content, true, false);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		show(d);
	}
	
	public void SHOW_PASSWORD_REQUIREMENTS() {
		PasswordSettings ps = PasswordSettings.getInstance();
		String title = ps.getPASSWORD_INFORMATIONS().get();
		String content = ps.toString();
		d = getInfoDialog(title, content, true, false);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		show(d);
	}
	
	public void SHOW_REGISTRATION_ERROR(String res) {
		
		RegistrationFormSettings rfs = RegistrationFormSettings.getInstance();
		
		String title = rfs.getREGISTRATION_ERROR_TITLE().get();
		String content = rfs.getError(res);
		
		d = getErrorDialog(title, content, true, true);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		show(d);
		
	}
	
	public void SHOW_REGISTRATION_OK() {
		
		RegistrationFormSettings rfs = RegistrationFormSettings.getInstance();
		
		String title = rfs.getREGISTRATION_OK_TITLE().get();
		String content =  rfs.getREGISTRATION_OK_CONTENT().get();
		
		d = getInfoDialog(title, content, true, true);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		show(d);
		
	}
	
	public void SHOW_PASSWORDS_MATCH_ERROR() {

		RegistrationFormSettings rfs = RegistrationFormSettings.getInstance();
		
		String title = rfs.getREGISTRATION_ERROR_TITLE().get();
		String content = rfs.getPASSWORDS_MATCH_ERROR().get();
		
		d = getErrorDialog(title, content, true, true);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		show(d);
		
	}
	
	private AbstractMFXDialog getErrorDialog(String title, String content, boolean animateIn, boolean animateOut) {
		d = MFXDialogFactory.buildDialog(DialogType.ERROR, title, content);
		d.setAnimateIn(animateIn);
		d.setAnimateOut(animateOut);
		return d;
	}
	
	private AbstractMFXDialog getInfoDialog(String title, String content, boolean animateIn, boolean animateOut) {
		d = MFXDialogFactory.buildDialog(DialogType.INFO, title, content);
		d.setAnimateIn(animateIn);
		d.setAnimateOut(animateOut);
		return d;
	}
	
	private void show(AbstractMFXDialog d) {
		initialPageController.getChildren().add(d);
		d.show();
		d.setOnClosed(this);
	}

	private static InitialPageDialogsHandler instance = null;
	
	public static InitialPageDialogsHandler getInstance() {
		if (instance == null)
			instance = new InitialPageDialogsHandler();
		return instance;
	}
	
	private InitialPageDialogsHandler() {}
	
}
