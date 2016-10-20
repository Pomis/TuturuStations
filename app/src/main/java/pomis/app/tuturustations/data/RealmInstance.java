package pomis.app.tuturustations.data;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class RealmInstance {
    static public Realm getInstance(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        return Realm.getInstance(config);
    }

}
