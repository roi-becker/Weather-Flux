package rb.weatherflux;

import android.os.AsyncTask;
import android.util.Log;

import com.github.dvdme.ForecastIOLib.ForecastIO;

import java.lang.ref.WeakReference;
import java.util.Calendar;

/**
 * Retrieves all weather data for two days.
 */
public class DownloadWeatherTask extends AsyncTask<Location, Void, TwoDaysWeatherData> {

    public interface Listener {

        void onDownloadCompleted(OneDayWeatherData yesterdayData, OneDayWeatherData todayData);
    }

    private WeakReference<Listener> m_listener; // Don't keep the listener alive just to get your message.

    public DownloadWeatherTask(Listener listener) {
        m_listener = new WeakReference<>(listener);
    }

    @Override
    protected TwoDaysWeatherData doInBackground(Location... locations) {
        Log.d("DownloadWeatherTask", "doInBackground");

        Location location = locations[0];

        Calendar yesterdayCalendar = CalendarExtensions.yesterday();
        OneDayWeatherData yesterdayData = downloadOneDayData(yesterdayCalendar, location);

        Calendar todayCalendar = CalendarExtensions.today();
        OneDayWeatherData todayData = downloadOneDayData(todayCalendar, location);

        // Possible improvement - download two days in parallel.
        return new TwoDaysWeatherData(yesterdayData, todayData);
    }

    @Override
    protected void onPostExecute(TwoDaysWeatherData twoDaysWeatherData) {
        Log.d("DownloadWeatherTask", "onPostExecute");

        Listener listener = m_listener.get();
        if (listener != null) {
            listener.onDownloadCompleted(
                    twoDaysWeatherData.getYesterdayData(),
                    twoDaysWeatherData.getTodayData());
        }
    }

    private OneDayWeatherData downloadOneDayData(Calendar calendar, Location location) {
        ForecastIO fio = new ForecastIO(ForecastIOKey.API_KEY);

        fio.setUnits(ForecastIO.UNITS_SI);
        fio.setExcludeURL("[alerts,flags]"); // Save some bandwidth on unneeded fields
        fio.setTime(CalendarExtensions.ForecastIOFormat(calendar));
        fio.getForecast(location.getLatitude(), location.getLongitude());

        return new OneDayWeatherData(calendar, fio);
    }
}
