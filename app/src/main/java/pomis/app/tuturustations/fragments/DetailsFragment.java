package pomis.app.tuturustations.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import pomis.app.tuturustations.R;
import pomis.app.tuturustations.data.RealmInstance;
import pomis.app.tuturustations.helpers.AddressHelper;
import pomis.app.tuturustations.models.CityFrom;
import pomis.app.tuturustations.models.CityTo;
import pomis.app.tuturustations.models.Station;
import pomis.app.tuturustations.models.StationFrom;
import pomis.app.tuturustations.models.StationTo;

/**
 * Фрагмент детального просмотра станции
 */
public class DetailsFragment extends Fragment {

    @BindView(R.id.tv_country_title)
    TextView tvCountry;

    @BindView(R.id.tv_full_address)
    TextView tvFullAddress;

    @BindView(R.id.tv_city_region)
    TextView tvCityRegion;


    Activity activity;
    private Class stationClass;
    private Class cityClass;
    private Station station;
    Realm realm;

    public DetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        activity = getActivity();
        ButterKnife.bind(this, view);
        readIntent();
        return view;
    }

    // Заполнение тестовых полей в зависимости от полученных из интента данных
    void readIntent() {
        String name = activity.getIntent().getStringExtra("name");
        String type = activity.getIntent().getStringExtra("type");
        String regionAndCity = "";
        if (type.equals("from")) {
            stationClass = StationFrom.class;
            cityClass = CityFrom.class;
        } else {
            stationClass = StationTo.class;
            cityClass = CityTo.class;
        }
        realm = RealmInstance.getInstance(activity);
        station = (Station) realm
                .where(stationClass)
                .equalTo("title", name)
                .findFirst();

        tvCityRegion.setText(station.getRegion());
        tvFullAddress.setText(
                AddressHelper
                        .with(activity)
                        .from(station.getLat(), station.getLon())
        );
        if (station.getRegion() != null && !station.getRegion().equals("")) {
            regionAndCity += station.getRegion();
        }
        if (station.getCity() != null && !station.getCity().equals("")) {
            if (regionAndCity.length() > 0)
                regionAndCity += ", ";
            regionAndCity += station.getCity();
        }
        tvCityRegion.setText(regionAndCity);
        tvCountry.setText(station.getCountry());
    }
}
