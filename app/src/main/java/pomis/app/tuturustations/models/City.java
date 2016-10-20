package pomis.app.tuturustations.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by romanismagilov on 20.10.16.
 */

public interface City {
    public void setCityId(int cityId);

    public void setName(String name);

    public void setLat(double lat);

    public void setLon(double lon);

    public void setCountry(String country);

    String getName();
}
