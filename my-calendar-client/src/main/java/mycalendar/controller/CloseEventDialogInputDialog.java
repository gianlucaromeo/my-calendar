package mycalendar.controller;

import javafx.event.ActionEvent;

public class CloseEventDialogInputDialog extends CustomInputDialog {

	private boolean onGreen = true; // NO, STAY
	private boolean onRed = false; // YES, CLOSE
	private boolean onExit = true; // NO, STAY
	
	public CloseEventDialogInputDialog(String title, String content, String okButtonText, String closeButtonText) {
		super(title, content, okButtonText, closeButtonText);
	}

	@Override
	void onExitButtonAction(ActionEvent event) {
		CalendarPageController.getInstance().onCloseInputDialog(onExit);
	}

	@Override
	void onGreenButtonAction(ActionEvent event) {
		CalendarPageController.getInstance().onCloseInputDialog(onGreen);
	}

	@Override
	void onRedButtonAction(ActionEvent event) {
		CalendarPageController.getInstance().onCloseInputDialog(onRed);
	}
	
}
