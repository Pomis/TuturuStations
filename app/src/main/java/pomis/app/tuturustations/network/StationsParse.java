package pomis.app.tuturustations.network;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmModel;
import pomis.app.tuturustations.data.RealmInstance;
import pomis.app.tuturustations.models.City;
import pomis.app.tuturustations.models.CityFrom;
import pomis.app.tuturustations.models.CityTo;
import pomis.app.tuturustations.models.Country;
import pomis.app.tuturustations.models.CountryFrom;
import pomis.app.tuturustations.models.CountryTo;
import pomis.app.tuturustations.models.Station;
import pomis.app.tuturustations.models.StationFrom;
import pomis.app.tuturustations.models.StationTo;

/**
 * Created by romanismagilov on 20.10.16.
 */

public class StationsParse {

    enum Type {
        TO, FROM
    }

    private static final String MY_TAG = "TutuDebug";

    // Ссылка на таск для публикации прогресса
    StationsTask asyncTask;
    // Времененные переменные для записи в бд во время парсинга
    String cityTitle;
    String countryName;
    String lastCountryName = "";
    double cityLat;
    double cityLon;
    int cityId;
    String regionName;
    int lastCityId = 0;
    Realm realm;
    String stationTitle;
    int stationId;
    double stationLat;
    double stationLon;

    public void parse(StationsTask stationsTask, Context context, InputStream in) throws Exception {
        realm = RealmInstance.getBackgroundInstance(context)
        ;
        this.asyncTask = stationsTask;
        JsonFactory jfactory = new JsonFactory();

        JsonParser jParser = jfactory.createParser(in);

        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jParser.getCurrentName();
            if (fieldname != null) {
                jParser.nextToken();
                switch (fieldname) {
                    case "citiesFrom":
                        stationsTask.publish("Загрузка станций отправления...");
                        parseStationArray(Type.FROM, jParser);
                        break;
                    case "citiesTo":
                        stationsTask.publish("Загрузка станция прибытия...");
                        parseStationArray(Type.TO, jParser);
                        break;
                }
            }
        }
        stationsTask.publish("Готово!");

        jParser.close();
    }


    void parseStationArray(final Type type, JsonParser jParser) throws Exception {
        String token = "";
        while (!token.equals("]")) {
            jParser.nextValue();
            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                String currentName = jParser.getCurrentName();
                if (currentName != null) {
                    jParser.nextToken();

                    switch (currentName) {
                        case "countryTitle":
                            countryName = jParser.getText();
                            //Log.d(MY_TAG, "==================="+countryName);
                            break;
                        case "cityTitle":
                            cityTitle = jParser.getText();
//                            Log.d(MY_TAG, cityTitle);
                            break;
                        case "cityId":
                            cityId = jParser.getIntValue();
                            break;
                        case "point":
                            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                                currentName = jParser.getCurrentName();
                                if (currentName != null) {
                                    jParser.nextToken();
                                    switch (currentName) {
                                        case "longitude":
                                            cityLon = jParser.getDoubleValue();
                                            break;
                                        case "latitude":
                                            cityLat = jParser.getDoubleValue();
                                            break;
                                    }
                                }
                            }
                            break;
                        case "stations":
                            while (jParser.nextToken() != JsonToken.END_ARRAY) {
                                while (jParser.nextToken() != JsonToken.END_OBJECT) {
                                    currentName = jParser.getCurrentName();
                                    if (currentName != null) {
                                        jParser.nextToken();
                                        switch (currentName) {
                                            case "countryTitle":
                                                countryName = jParser.getText();
                                                break;
                                            case "regionTitle":
                                                regionName = jParser.getText();
                                                break;
                                            case "stationTitle":
                                                stationTitle = jParser.getText();
                                                break;
                                            case "stationId":
                                                stationId = jParser.getIntValue();
                                                break;
                                            case "point":
                                                while (jParser.nextToken() != JsonToken.END_OBJECT) {
                                                    currentName = jParser.getCurrentName();
                                                    if (currentName != null) {
                                                        jParser.nextToken();
                                                        switch (currentName) {
                                                            case "longitude":
                                                                stationLon = jParser.getDoubleValue();
                                                                break;
                                                            case "latitude":
                                                                stationLat = jParser.getDoubleValue();
                                                                break;
                                                        }
                                                    }
                                                }
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }

                            }
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    switch (type) {
                                        case FROM:
                                            StationFrom station = new StationFrom();
                                            station.setLat(stationLat);
                                            station.setCountry(countryName);
                                            station.setLon(stationLon);
                                            station.setTitle(stationTitle);
                                            station.setRegion(regionName);
                                            station.setStationId(stationId);
                                            station.setCity(cityTitle);
                                            realm.copyToRealmOrUpdate(station);
                                            break;

                                        case TO:
                                            StationTo stationTo = new StationTo();
                                            stationTo.setLat(stationLat);
                                            stationTo.setLon(stationLon);
                                            stationTo.setCountry(countryName);
                                            stationTo.setTitle(stationTitle);
                                            stationTo.setRegion(regionName);
                                            stationTo.setStationId(stationId);
                                            stationTo.setCity(cityTitle);
                                            realm.copyToRealmOrUpdate(stationTo);
                                            break;
                                    }
                                }
                            });
                            break;
                        default:
                            break;
                    }

                }
                if (!lastCountryName.equals(countryName))
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            lastCountryName = countryName;
                            switch (type) {
                                case TO:

                                    CountryTo countryTo = new CountryTo();
                                    countryTo.title = countryName;
                                    realm.copyToRealmOrUpdate(countryTo);
                                    break;

                                case FROM:

                                    CountryFrom countryFrom = new CountryFrom();
                                    countryFrom.title = countryName;
                                    realm.copyToRealmOrUpdate(countryFrom);

//                                     Log.d(MY_TAG, "country written."+countryName+" Count: " + realm.where(CountryFrom.class).count());
//                            realm.copyToRealm(countryFrom);
                                    break;
                            }
                        }
                    });
            }
            if (lastCityId != cityId)
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        lastCityId = cityId;
                        switch (type) {
                            case TO:
                                CityTo cityTo = new CityTo();
                                cityTo.cityId = cityId;
                                cityTo.name = cityTitle;
                                cityTo.lat = cityLat;
                                cityTo.lon = cityLon;
                                cityTo.country = countryName;
                                realm.copyToRealmOrUpdate(cityTo);
                                break;

                            case FROM:
                                CityFrom cityFrom = new CityFrom();
                                cityFrom.cityId = cityId;
                                cityFrom.name = cityTitle;
                                cityFrom.lat = cityLat;
                                cityFrom.lon = cityLon;
                                cityFrom.country = countryName;

                                realm.copyToRealmOrUpdate(cityFrom);
                                //Log.d(MY_TAG, "city saved/ " + cityFrom.toString());
                                break;
                        }
                    }
                });

            token = jParser.nextToken().asString();

        }

    }
}
