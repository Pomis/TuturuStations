package pomis.app.tuturustations.models;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by romanismagilov on 20.10.16.
 */

// Отдельная таблица для городов отправления
public class CityFrom extends RealmObject implements City {
    @PrimaryKey
    public int cityId;

    public String name;
    public double lat;
    public double lon;

    public String country; // Является Foreign key. К сожалею, Realm не поддерживает такую аннотацию.

    @Override
    public String toString() {
        return name + ", " + lat + ":" + lon + ", id:" + cityId;
    }


    //    public RealmList<Station> stations;
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
