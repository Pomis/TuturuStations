package pomis.app.tuturustations.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by romanismagilov on 20.10.16.
 */

public interface Station {
    void setStationId(int stationId);

    void setLat(double lat);

    void setLon(double lon);

    void setTitle(String title);

    void setCity(String city);
    String getName();

    double getLat();

    double getLon();

    String getCity();

    String getRegion();

    String getCountry();

    void setRegion(String region);
}
