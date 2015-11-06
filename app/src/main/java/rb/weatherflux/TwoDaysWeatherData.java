package rb.weatherflux;

/**
 * Holds the weather data for yesterday and today.
 */
public class TwoDaysWeatherData {

    private OneDayWeatherData m_yesterdayData;
    private OneDayWeatherData m_todayData;

    public TwoDaysWeatherData(OneDayWeatherData yesterdayData, OneDayWeatherData todayData) {
        m_yesterdayData = yesterdayData;
        m_todayData = todayData;
    }

    public OneDayWeatherData getYesterdayData() {
        return m_yesterdayData;
    }

    public OneDayWeatherData getTodayData() {
        return m_todayData;
    }
}
