package rb.weatherflux;

/**
 * Holds the weather data for yesterday and today.
 */
public class TwoDaysWeatherData {

    private OneDayWeatherData mYesterdayData;
    private OneDayWeatherData mTodayData;

    public TwoDaysWeatherData(OneDayWeatherData yesterdayData, OneDayWeatherData todayData) {
        mYesterdayData = yesterdayData;
        mTodayData = todayData;
    }

    public OneDayWeatherData getYesterdayData() {
        return mYesterdayData;
    }

    public OneDayWeatherData getTodayData() {
        return mTodayData;
    }
}
