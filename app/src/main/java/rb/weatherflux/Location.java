package rb.weatherflux;

/**
 * A physical location in the world
 */
public class Location {

    private String mLatitude;
    private String mLongitude;
    private String mName;

    public Location(String latitude, String longitude, String name) {
        mLatitude = latitude;
        mLongitude = longitude;
        mName = name;
    }

    public Location(double latitude, double longitude, String name) {
        this(latitude+"", longitude+"", name);
    }

    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public String getName() {
        return mName;
    }
}
