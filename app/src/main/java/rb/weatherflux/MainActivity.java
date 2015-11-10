package rb.weatherflux;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DownloadWeatherTask.Listener {

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private TextView mLocation;
    private TextView mDataLabel;

    private android.location.Location mLatestLocation;
    private TwoDaysWeatherData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bindViews();
        setSupportActionBar(mToolbar);

        mFab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showData(false);
                    Log.d("=====", "down");
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    showData(true);
                    Log.d("=====", "up");
                    return true;
                }
                return false;
            }
        });

        checkLocationPermission();
    }

    @Override
    public void onDownloadCompleted(TwoDaysWeatherData data) {
        mData = data;
        showData(true);
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
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mFab = (FloatingActionButton)findViewById(R.id.fab);
        mLocation = (TextView)findViewById(R.id.location);
        mDataLabel = (TextView)findViewById(R.id.data);
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

    private void showData(boolean today) {
        mDataLabel.setText(today ? mData.getTodayData().toString() : mData.getYesterdayData().toString());
    }
}
