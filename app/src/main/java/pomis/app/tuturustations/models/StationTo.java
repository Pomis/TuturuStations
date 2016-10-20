package pomis.app.tuturustations.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by romanismagilov on 20.10.16.
 */

public class StationTo extends RealmObject implements Station, Tutu{
    @PrimaryKey
    public int stationId;
    public double lat;
    public double lon;
    public String title;
    public String city;
    @Override
    public String toString() {
        return title + ", " + lat + ":" + lon + ", id:" + stationId;
    }


    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getName() {
        return title;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }
}
