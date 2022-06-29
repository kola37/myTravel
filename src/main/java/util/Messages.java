package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("language", locale)
                .getString(messageKey);
    }

    public static String getMessage(String messageKey){
        return ResourceBundle.getBundle("language")
                .getString(messageKey);
    }

}
