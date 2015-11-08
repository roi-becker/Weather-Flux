package rb.weatherflux;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DownloadWeatherTask.Listener {

    private TextView mLocation;
    private TextView mYesterday;
    private TextView mToday;

    private android.location.Location mLatestLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bindViews();

        checkLocationPermission();
    }

    @Override
    public void onDownloadCompleted(OneDayWeatherData yesterdayData, OneDayWeatherData todayData) {
        mYesterday.setText(yesterdayData.toString());
        mToday.setText(todayData.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onLocationPermissionGranted();
        } else {
            Toast.makeText(this, "Please allow the app to access the GPS and reopen", Toast.LENGTH_LONG).show();
        }
    }

    private void bindViews() {
        mLocation = (TextView)findViewById(R.id.location);
        mYesterday = (TextView)findViewById(R.id.yesterday);
        mToday = (TextView)findViewById(R.id.today);
    }

    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                onLocationPermissionGranted();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        } else {
            onLocationPermissionGranted();
        }
    }

    @SuppressWarnings("ResourceType")
    private void onLocationPermissionGranted() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLatestLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mLocation.setText(mLatestLocation.getLatitude() + "," + mLatestLocation.getLongitude());

        downloadWeather();
    }

    private void downloadWeather() {
        new DownloadWeatherTask(this).execute(new Location(mLatestLocation.getLatitude(), mLatestLocation.getLongitude(), ""));
    }
}
