package mycalendar.controller;

import io.github.palexdev.materialfx.controls.base.AbstractMFXDialog;
import io.github.palexdev.materialfx.controls.base.AbstractMFXDialog.MFXDialogEvent;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import io.github.palexdev.materialfx.controls.factories.MFXAnimationFactory;
import io.github.palexdev.materialfx.controls.factories.MFXDialogFactory;
import javafx.event.EventHandler;
import mycalendar.config.DialogsSettings;
import mycalendar.config.InputDialogSettings;
import mycalendar.model.CalendarEvent;

public class CalendarPageDialogsHandler implements EventHandler<MFXDialogEvent> {

	private CalendarPageController calendarPageController = CalendarPageController.getInstance();
	private DialogsSettings settings = DialogsSettings.getInstance();
	
	@Override
	public void handle(MFXDialogEvent event) {
		int pos = calendarPageController.getChildren().size() - 1;
		calendarPageController.getChildren().remove(pos);
	}

	public void SHOW_GENERIC_ERROR() {
		
		String title = settings.get_GENERIC_ERROR_TITLE().get();
		String content = settings.get_GENERIC_ERROR_CONTENT().get();
		
		AbstractMFXDialog d = MFXDialogFactory.buildDialog(DialogType.ERROR, title, content);
		
		d.setAnimateIn(true);
		d.setAnimateOut(true);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		
		show(d);
		
	}
	
	public void SHOW_EV_DATE_ERROR() {

		String title = settings.get_EVENT_EV_DATE_ERROR_TITLE().get();
		String content = settings.get_EVENT_EV_DATE_ERROR_CONTENT().get();
		
		AbstractMFXDialog d = MFXDialogFactory.buildDialog(DialogType.ERROR, title, content);
		
		d.setAnimateIn(true);
		d.setAnimateOut(true);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		
		show(d);
		
	}
	
	public void SHOW_EVENT_ADDED() {
		
		String title = settings.get_EVENT_ADDED_TITLE().get();
		String content = settings.get_EVENT_ADDED_CONTENT().get();
		
		AbstractMFXDialog d = MFXDialogFactory.buildDialog(DialogType.INFO, title, content);
		
		d.setAnimateIn(true);
		d.setAnimateOut(true);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		
		show(d);

	}
	
	public void SHOW_EVENT_EDITED() {
		
		String title = settings.get_EVENT_EDITED_TITLE().get();
		String content = settings.get_EVENT_EDITED_CONTENT().get();
		
		AbstractMFXDialog d = MFXDialogFactory.buildDialog(DialogType.INFO, title, content);
		
		d.setAnimateIn(true);
		d.setAnimateOut(true);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		
		show(d);
		
	}	
	
	public void SHOW_CLOSE_EV_INPUT_DIALOG() {
		
		InputDialogSettings ids = InputDialogSettings.getInstance();
		
		String title = ids.get_CLOSE_EVENT_DIALOG_TITLE().get();
		String content = ids.get_CLOSE_EVENT_DIALOG_CONTENT().get();
		String greenButtonText = ids.get_CLOSE_EVENT_DIALOG_GREEN_BUTTON().get();
		String redButtonText = ids.get_CLOSE_EVENT_DIALOG_RED_BUTTON().get();
		
		CloseEventDialogInputDialog inputDialog = new CloseEventDialogInputDialog(title, content, greenButtonText, redButtonText);
		CalendarPageController.getInstance().onOpenInputDialog(inputDialog);
		
	}
	
	public void SHOW_DELETE_EV_INPUT_DIALOG(CalendarEvent toDelete) {
		
		InputDialogSettings ids = InputDialogSettings.getInstance();
		
		String title = ids.get_DELETE_EVENT_DIALOG_TITLE().get();
		String content = ids.get_DELETE_EVENT_DIALOG_CONTENT().get();
		String greenButtonText = ids.get_DELETE_EVENT_DIALOG_GREEN_BUTTON().get();
		String redButtonText = ids.get_DELETE_EVENT_DIALOG_RED_BUTTON().get();
	
		DeleteEventInputDialog inputDialog = new DeleteEventInputDialog(toDelete, title, content, greenButtonText, redButtonText);
		
		CalendarPageController.getInstance().onOpenInputDialog(inputDialog);
		
	}
	
	public void SHOW_EVENT_DELETED(boolean deleted) {

		String title = null;
		String content = null;
		DialogType dType = null;
		
		if (deleted) {
			title = settings.get_EVENT_DELETED_OK_TITLE().get();
			content = settings.get_EVENT_DELETED_OK_CONTENT().get();
			dType = DialogType.INFO;
		} else {
			title = settings.get_EVENT_DELETED_NO_TITLE().get();
			content = settings.get_EVENT_DELETED_NO_CONTENT().get();
			dType = DialogType.ERROR;
		}
		
		AbstractMFXDialog d = MFXDialogFactory.buildDialog(dType, title, content);
		
		d.setAnimateIn(true);
		d.setAnimateOut(true);
		d.setInAnimationType(MFXAnimationFactory.SLIDE_IN_TOP);
		d.setOutAnimationType(MFXAnimationFactory.SLIDE_OUT_BOTTOM);
		
		show(d);
		
	}
	
	private void show(AbstractMFXDialog d) {
		calendarPageController.getChildren().add(d);
		d.show();
		d.setOnClosed(this);
	}

	private static CalendarPageDialogsHandler instance = null;
	public static CalendarPageDialogsHandler getInstance() {
		if (instance == null)
			instance = new CalendarPageDialogsHandler();
		return instance;
	}
	
	private CalendarPageDialogsHandler() {}

	

}
