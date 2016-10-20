package pomis.app.tuturustations.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import pomis.app.tuturustations.BuildConfig;
import pomis.app.tuturustations.R;
import pomis.app.tuturustations.adapters.TutusAdapter;
import pomis.app.tuturustations.data.RealmInstance;
import pomis.app.tuturustations.models.CityFrom;
import pomis.app.tuturustations.models.Country;
import pomis.app.tuturustations.models.CountryFrom;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationsFragment extends Fragment {

    @BindView(R.id.lv_stations)
    ListView lvStations;

    private Unbinder unbinder;
    private TutusAdapter adapter;
    private Realm realm;

    public StationsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stations, container, false);
        realm = RealmInstance.getInstance(getContext());
        unbinder = ButterKnife.bind(this, view);
        initAdapter();

        return view;
    }

    private void initAdapter() {
        adapter = new TutusAdapter(getContext(), 0,
                realm.where(CountryFrom.class)
                        .findAll());
        lvStations.setAdapter(adapter);

        lvStations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String country = ((Country)adapter.get(position)).getTitle();
                adapter.removeCities();
                adapter.addAll(position,
                        realm.where(CityFrom.class)
                                .equalTo("country", country)
                                .findAll());
                adapter.notifyDataSetChanged();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    lvStations.scrollListBy(position);
                }
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
