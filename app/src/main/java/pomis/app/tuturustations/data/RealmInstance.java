package pomis.app.tuturustations.data;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/*
 * Тут два синглтона realm. Один для фонового потока, другой для UI.
 */
public class RealmInstance {
    static Realm instance;
    static Realm instanceBg;

    static public Realm getInstance(Context context) {
        if (instance==null) {
            Realm.init(context);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            return Realm.getInstance(config);
        } else {
            return instance;
        }
    }

    static public Realm getBackgroundInstance(Context context) {
        if (instanceBg==null) {
            Realm.init(context);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            return Realm.getInstance(config);
        } else {
            return instanceBg;
        }
    }


    public static void closeAll() {
        if (instance!=null) instance.close();
        if (instanceBg!=null) instanceBg.close();
    }
}
