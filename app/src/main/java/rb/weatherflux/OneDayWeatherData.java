package rb.weatherflux;

import com.github.dvdme.ForecastIOLib.FIODataPoint;
import com.github.dvdme.ForecastIOLib.FIOHourly;
import com.github.dvdme.ForecastIOLib.ForecastIO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Holds the weather data for a day. Can be a mix of historical and forecast data.
 */
public class OneDayWeatherData {

    private Calendar mCalendar;
    private FIODataPoint[] mDataPoints = new FIODataPoint[24];

    public OneDayWeatherData(Calendar calendar, ForecastIO fio) {
        mCalendar = calendar;

        FIOHourly hourly = new FIOHourly(fio);
        for (int i = 0; i < 24; i++) {
            FIODataPoint dataPoint = hourly.getHour(i);
            dataPoint.setTimezone(calendar.getTimeZone().getID());
            mDataPoints[i] = dataPoint;
        }
    }

    public FIODataPoint[] getDataPoints() {
        return mDataPoints;
    }

    @Override
    public String toString() {
        String ret = String.format("%1$-10s", new SimpleDateFormat("EEEE", Locale.US).format(mCalendar.getTime()));
        for (FIODataPoint dataPoint : mDataPoints) {
            ret += " " + Math.round(dataPoint.temperature());
        }
        return ret;
    }
}
