package mycalendar.controller;

import javafx.event.ActionEvent;
import mycalendar.model.CalendarEvent;

public class DeleteEventInputDialog extends CustomInputDialog {
	
	private boolean onGreen = false; // NO, STAY
	private boolean onRed = true; // YES, DELETE EVENT
	private boolean onExit = false; // NO, STAY
	
	private CalendarEvent toDelete = null;
	
	public DeleteEventInputDialog(CalendarEvent toDelete, String title, String content, String okButtonText, String closeButtonText) {
		super(title, content, okButtonText, closeButtonText);
		this.toDelete = toDelete;
	}

	@Override
	void onExitButtonAction(ActionEvent event) {
		CalendarPageController.getInstance().onDeleteEventInputDialog(toDelete, onExit);
	}

	@Override
	void onGreenButtonAction(ActionEvent event) {
		CalendarPageController.getInstance().onDeleteEventInputDialog(toDelete, onGreen);
	}

	@Override
	void onRedButtonAction(ActionEvent event) {
		CalendarPageController.getInstance().onDeleteEventInputDialog(toDelete, onRed);
	}
}
