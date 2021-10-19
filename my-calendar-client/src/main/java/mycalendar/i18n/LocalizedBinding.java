package mycalendar.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/*
 * From: https://stackoverflow.com/questions/27189661/changing-language-from-english-to-dutch 
 * */

public class LocalizedBinding {

	private final ObjectProperty<Locale> locale;
	private final ObjectProperty<ResourceBundle> bundle;

    public LocalizedBinding(String bundleName, Locale locale) {

        this.locale = new SimpleObjectProperty<Locale>(locale);
        this.bundle = new SimpleObjectProperty<ResourceBundle>();

        // Update resource bundle whenever locale changes:
        bundle.bind(Bindings.createObjectBinding(() -> {
                Locale l = this.locale.get();
                if (l == null) {
                    return null ;
                } else {
                    ResourceBundle resources = ResourceBundle.getBundle(bundleName, l);
                    return resources;
                }
            },          
            this.locale));
    }   

    // Creates a StringBinding whose value is obtained from the current
    // resource bundle using the provided key. The binding will automatically
    // update if the locale changes.
    public StringBinding createStringBinding(String key) {
        
    	return new StringBinding() {

            {
                bind(bundle);
            }

            @Override
            protected String computeValue() {
                ResourceBundle resources = bundle.get();
                if (resources == null) {
                    return key ;
                } else {
                    return resources.getString(key);
                }
            }

        };
        
    }

    public final ObjectProperty<Locale> localeProperty() {
        return this.locale;
    }

    public final Locale getLocale() {
        return this.localeProperty().get();
    }

    public final void setLocale(final Locale locale) {
        this.localeProperty().set(locale);
    }
	
}
