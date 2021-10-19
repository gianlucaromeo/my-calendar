package mycalendar.view.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import mycalendar.controller.CalendarEventDialogController;
import mycalendar.i18n.I18NHelper;

public class LanguagesComboBox extends ComboBox<String> implements ChangeListener<String> {
	
	public static StringProperty ITALIAN = new SimpleStringProperty();
	public static StringProperty ENGLISH_AMERICAN = new SimpleStringProperty();
	
	public LanguagesComboBox() {
		setNamesFromLocale();
		initCBox();
		setStyle();
		valueProperty().addListener(this);
	}

	private void setStyle() {
		setPrefWidth(USE_COMPUTED_SIZE);
		setCursor(Cursor.HAND);
	}

	private void initCBox() {
		ObservableList<String> languages = FXCollections.observableArrayList();
		languages.add(ENGLISH_AMERICAN.get());
		languages.add(ITALIAN.get());
		getItems().addAll(languages);
	}

	private void setNamesFromLocale() {
		ITALIAN.bind(I18NHelper.getInstance().createBinding("ITALIAN"));
		ENGLISH_AMERICAN.bind(I18NHelper.getInstance().createBinding("ENGLISH_AMERICAN"));
		promptTextProperty().bind(I18NHelper.getInstance().createBinding("LANGUAGE"));
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		if (newValue.equals(ITALIAN.get())) {
			I18NHelper.getInstance().setItalian();
		} else {
			I18NHelper.getInstance().setAmericanEnglish();
		}
		CalendarEventDialogController.getInstance().updateDurations();
	}
	
}
