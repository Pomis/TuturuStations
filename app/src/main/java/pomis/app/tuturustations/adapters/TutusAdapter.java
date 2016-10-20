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
import pomis.app.tuturustations.models.Tutu;

/**
 * Created by romanismagilov on 20.10.16.
 */

public class TutusAdapter extends ArrayAdapter {

    private final List<Tutu> countryList;
    List<Tutu> toRemove;


    public TutusAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.countryList = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (countryList.get(position) instanceof Country) {
            CountryViewHolder holder;
//            if (convertView == null) {
                convertView = LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.item_country, null);
                holder = new CountryViewHolder();
                holder.country = (TextView) convertView.findViewById(R.id.tv_country);
                convertView.setTag(holder);
//            } else {
//                holder = (CountryViewHolder) convertView.getTag();
//            }
            Country country = (Country) countryList.get(position);
            holder.country.setText(country.getTitle());
        } else if (countryList.get(position) instanceof City) {
            CityViewHolder holder;
//            if (convertView == null) {
                convertView = LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.item_city, null);
                holder = new CityViewHolder();
                holder.city = (TextView) convertView.findViewById(R.id.tv_city);
                convertView.setTag(holder);
//            }
//            else {
//                holder = (CityViewHolder) convertView.getTag();
//            }
            City city = (City) countryList.get(position);
            holder.city.setText(city.getName());
        }
        return convertView;
    }

    public void removeCities() {
        toRemove = new ArrayList<>();
        for (Tutu tutu : countryList){
            if (tutu instanceof City){
                toRemove.add(tutu);
            }
        }
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    class CountryViewHolder {
        TextView country;

    }

    class CityViewHolder {
        TextView city;
    }

    public void addAll(int location, List objects){
        countryList.addAll(location+1, objects);
        countryList.removeAll(toRemove);
    }

    public Tutu get(int position){
        return countryList.get(position);
    }
}
