package pomis.app.tuturustations.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import io.realm.Case;
import io.realm.Realm;
import pomis.app.tuturustations.R;
import pomis.app.tuturustations.activities.StationActivity;
import pomis.app.tuturustations.adapters.TutusAdapter;
import pomis.app.tuturustations.data.RealmInstance;
import pomis.app.tuturustations.helpers.InsensitiveSearch;
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
 * A simple {@link Fragment} subclass.
 */
public class StationsFragment extends Fragment {

    private static final String MY_TAG = "TutuDegug";
    private static final int REQUEST_CODE = 0x1;

    @BindView(R.id.lv_stations)
    ListView lvStations;

    Class cityClass;
    Class stationClass;
    Class countryClass;
    private Unbinder unbinder;
    private TutusAdapter adapter;
    private Realm realm;
    String type;

    private int lastClickedItemPosition = -1;
    private boolean stationsExpanded = false;

    public StationsFragment() {
        Log.d(MY_TAG, String.valueOf(getTargetRequestCode()));
    }


    void readIntent() {
        type = getActivity()
                .getIntent()
                .getStringExtra("type");
        if (type.equals("from")) {
            getActivity().setTitle("Пункт отправления");
            cityClass = CityFrom.class;
            stationClass = StationFrom.class;
            countryClass = CountryFrom.class;
        } else {
            getActivity().setTitle("Пункт назначения");
            cityClass = CityTo.class;
            stationClass = StationTo.class;
            countryClass = CountryTo.class;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stations, container, false);
        realm = RealmInstance.getInstance(getContext());
        unbinder = ButterKnife.bind(this, view);
        readIntent();
        initAdapter("");

        return view;
    }

    private void initAdapter(final String searchParam) {
        if (searchParam.equals(""))
            adapter = new TutusAdapter(getContext(), 0,
                    realm.where(countryClass)
                            .findAll());
        else
            adapter = new TutusAdapter(getContext(), 0,
                    InsensitiveSearch
                            .search(realm
                                    .where(cityClass)
                                    .findAll(),
                                    searchParam));

        lvStations.setAdapter(adapter);

        lvStations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lastClickedItemPosition == position) {
                    if (!stationsExpanded)
                        adapter.selectCitiesToDelete();
                    adapter.selectStationsToDelete();
                    adapter.deleteSelection();
                    adapter.notifyDataSetChanged();
                    stationsExpanded = false;
                } else {
                    lastClickedItemPosition = position;
                    if (adapter.get(position) instanceof Country) {
                        String country = ((Country) adapter.get(position)).getTitle();
                        adapter.selectCitiesToDelete();
                        adapter.selectStationsToDelete();
                        adapter.addAll(position,
                                realm.where(cityClass)
                                        .equalTo("country", country)
                                        .findAll());
                        adapter.notifyDataSetChanged();
                        stationsExpanded = false;
                    } else if (adapter.get(position) instanceof City) {
                        String city = ((City) adapter.get(position)).getTitle();
                        adapter.selectStationsToDelete();
                        adapter.addAll(position,
                                realm.where(stationClass)
                                        .equalTo("city", city)
                                        .findAll());
                        adapter.notifyDataSetChanged();
                        stationsExpanded = true;
                    } else if (adapter.get(position) instanceof Station) {
                        openStation(((Station) adapter.get(position)).getName());
                    }
                }

            }
        });
    }

    private void openStation(String name) {
        startActivityForResult(
                new Intent(getContext(), StationActivity.class)
                        .putExtra("name", name)
                        .putExtra("type", type),
                REQUEST_CODE);
    }

    @OnTextChanged(R.id.et_search)
    void doSearch(Editable editable) {
        initAdapter(editable.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
