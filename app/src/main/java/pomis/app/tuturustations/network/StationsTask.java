package pomis.app.tuturustations.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import io.realm.Realm;
import pomis.app.tuturustations.BuildConfig;
import pomis.app.tuturustations.data.RealmInstance;
import pomis.app.tuturustations.models.City;

/**
 * Created by romanismagilov on 20.10.16.
 */

public class StationsTask extends AsyncTask {
    private static final String MY_TAG = "TutuRu";
    Context context;

    final String API_HOST = "https://raw.githubusercontent.com/tutu-ru/hire_android_test/master/";
    final String GET_ALL_STATIONS = "allStations.json";

    public StationsTask(Context context) {
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            URL url = new URL(API_HOST + GET_ALL_STATIONS);
            publishProgress("Подключение...");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            publishProgress("Подключено");
            InputStream in = new BufferedInputStream(connection.getInputStream());
            new StationsParse().parse(this, context, in);
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit()
                .putBoolean("done", true)
                .apply();
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        Log.d(MY_TAG, (String) values[0]);
    }

    public void publish(String value){
        publishProgress(value);
    }
}
