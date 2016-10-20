package pomis.app.tuturustations.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by romanismagilov on 20.10.16.
 */

// Отдельная таблица для городов прибытия
public class CityTo extends RealmObject implements City, Tutu {
    @PrimaryKey
    public int cityId;

    public String name;
    public double lat;
    public double lon;
//    public RealmList<Station> stations;

    public String country; // Является Foreign key. К сожалею, Realm не поддерживает такую аннотацию.


    @Override
    public String toString(){
        return name + ", " + lat + ":" + lon + ", id:" + cityId;
    }

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

    @Override
    public String getTitle() {
        return name;
    }
}
