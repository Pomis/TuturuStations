package pomis.app.tuturustations.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by romanismagilov on 20.10.16.
 */

public class CountryTo extends RealmObject implements Country{
    @PrimaryKey
    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
