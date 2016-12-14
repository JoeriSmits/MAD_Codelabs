package nl.han.ica.mad.android.firebasedemo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static String getCurrentTimeString() {
        return TIMESTAMP_FORMAT.format(new Date());
    }
}
