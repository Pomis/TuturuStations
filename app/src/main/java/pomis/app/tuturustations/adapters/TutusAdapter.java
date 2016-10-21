package pomis.app.tuturustations.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pomis.app.tuturustations.R;
import pomis.app.tuturustations.models.City;
import pomis.app.tuturustations.models.Country;
import pomis.app.tuturustations.models.Station;
import pomis.app.tuturustations.models.Tutu;

/**
 * Адаптер для станций, городов, стран
 */

public class TutusAdapter extends ArrayAdapter {

    private final List<Tutu> tutusList;
    List<Tutu> toRemove = new ArrayList<>();
    ;


    public TutusAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.tutusList = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (tutusList.get(position) instanceof Country) {
            CountryViewHolder holder;
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.item_country, null);
            holder = new CountryViewHolder();
            holder.country = (TextView) convertView.findViewById(R.id.tv_country);
            convertView.setTag(holder);
            Country country = (Country) tutusList.get(position);
            holder.country.setText(country.getTitle());
        } else if (tutusList.get(position) instanceof City) {
            CityViewHolder holder;
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.item_city, null);
            holder = new CityViewHolder();
            holder.city = (TextView) convertView.findViewById(R.id.tv_city);
            convertView.setTag(holder);
            City city = (City) tutusList.get(position);
            holder.city.setText(city.getTitle());
        } else if (tutusList.get(position) instanceof Station) {
            StationViewHolder holder;
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.item_station, null);
            holder = new StationViewHolder();
            holder.station = (TextView) convertView.findViewById(R.id.tv_station);
            convertView.setTag(holder);
            Station station = (Station) tutusList.get(position);
            holder.station.setText(station.getName());
        }
        return convertView;
    }

    public void selectCitiesToDelete() {
        for (Tutu tutu : tutusList) {
            if (tutu instanceof City) {
                toRemove.add(tutu);
            }
        }
    }

    public void selectStationsToDelete() {
        for (Tutu tutu : tutusList) {
            if (tutu instanceof Station) {
                toRemove.add(tutu);
            }
        }
    }

    @Override
    public int getCount() {
        return tutusList.size();
    }

    public void deleteSelection() {
        tutusList.removeAll(toRemove);
        toRemove.clear();
    }

    class CountryViewHolder {
        TextView country;

    }

    class CityViewHolder {
        TextView city;
    }

    class StationViewHolder {
        TextView station;
    }

    public void addAll(int location, List objects) {
        tutusList.addAll(location + 1, objects);
        tutusList.removeAll(toRemove);
        toRemove.clear();
    }

    public Tutu get(int position) {
        return tutusList.get(position);
    }
}
