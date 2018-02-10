package util;

/*import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntityUtils {
    public static final String PATH_XML_FILES = "/";
    public static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat defaultFormatDate = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat formatFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final SimpleDateFormat formatTime = new SimpleDateFormat("mm:ss");
    //public static final Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-dd-MM hh:mm:ss").create();

    private EntityUtils() {
    }

    public static Date defaultParseDate(String source) throws ParseException {
        return defaultFormatDate.parse(source);
    }

    public static String defaultFormatDate(Date date) {
        return defaultFormatDate.format(date);
    }

    public static Date parseFullDate(String source) throws ParseException {
        return formatFull.parse(source);
    }

    public static String formatDate(Date date) {
        return formatDate.format(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return formatDate.parse(date);
    }

    public static String formatTime(Date time) {
        return formatTime.format(time);
    }

    public static Date parseTime(String time) throws ParseException {
        return formatTime.parse(time);
    }
}
