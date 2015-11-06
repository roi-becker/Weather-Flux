package rb.weatherflux;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarExtensions {

    public static Calendar yesterday() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.clear();
        yesterday.setTime(today().getTime());
        yesterday.add(Calendar.DATE, -1);
        return yesterday;
    }

    public static Calendar today() {
        Calendar now = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.clear();
        today.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        return today;
    }

    public static String ForecastIOFormat(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).format(calendar.getTime());
    }
}
