package pomis.app.tuturustations.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import pomis.app.tuturustations.R;
import pomis.app.tuturustations.data.RealmInstance;
import pomis.app.tuturustations.models.Station;
import pomis.app.tuturustations.models.StationFrom;
import pomis.app.tuturustations.models.StationTo;

public class StationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Class stationClass;
    @BindView(R.id.tv_station_title)
    TextView stationTitle;
    Station station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        ButterKnife.bind(this);
        readIntent();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    void readIntent() {
        String name = getIntent().getStringExtra("name");
        String type = getIntent().getStringExtra("type");
        if (type.equals("from"))
            stationClass = StationFrom.class;
        else
            stationClass = StationTo.class;

        station = (Station) RealmInstance.getInstance(this)
                .where(stationClass)
                .equalTo("title", name)
                .findFirst();
        stationTitle.setText(station.getName());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng stationPosition = new LatLng(station.getLat(), station.getLon());
        mMap.addMarker(new MarkerOptions().position(stationPosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(station.getLat()-0.015, station.getLon()), 13));
    }
}
