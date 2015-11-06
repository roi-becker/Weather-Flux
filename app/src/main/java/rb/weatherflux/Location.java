package rb.weatherflux;

/**
 * A physical location in the world
 */
public class Location {

    private String m_latitude;
    private String m_longitude;
    private String m_name;

    public Location(String latitude, String longitude, String name) {
        m_latitude = latitude;
        m_longitude = longitude;
        m_name = name;
    }

    public String getLatitude() {
        return m_latitude;
    }

    public String getLongitude() {
        return m_longitude;
    }

    public String getName() {
        return m_name;
    }
}
