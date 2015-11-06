package rb.weatherflux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements DownloadWeatherTask.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadWeatherTask(this).execute(new Location("32.0900", "34.7800", "Tel Aviv, Israel"));
    }


    @Override
    public void onDownloadCompleted(OneDayWeatherData yesterdayData, OneDayWeatherData todayData) {
        Log.d("MainActivity", yesterdayData.toString());
        Log.d("MainActivity", todayData.toString());
    }
}
