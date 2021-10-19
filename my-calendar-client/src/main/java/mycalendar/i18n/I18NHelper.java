package mycalendar.i18n;

import java.io.File;
import java.util.Locale;

import javafx.beans.binding.StringBinding;

public class I18NHelper {

	private Locale locale = new Locale("en", "US");
	private LocalizedBinding localizedBinding = null;
	
	private static I18NHelper instance = null;
	
	public static I18NHelper getInstance() {
		if (instance == null)
			instance = new I18NHelper();
		return instance;
	}
	
	private I18NHelper() {
		String path = "i18n" + File.separator + "MessagesBundle";
		localizedBinding = new LocalizedBinding(path, locale);
	}
	
	public void setItalian() {
    	locale = new Locale("it", "IT");
    	localizedBinding.setLocale(locale);
    }

    public void setAmericanEnglish() {
    	locale = new Locale("en", "US");
    	localizedBinding.setLocale(locale);
    }
    
    public StringBinding createBinding(String key) {
    	return localizedBinding.createStringBinding(key);
    }
	
}
