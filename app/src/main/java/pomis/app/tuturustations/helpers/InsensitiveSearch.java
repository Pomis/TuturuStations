package pomis.app.tuturustations.helpers;

import java.util.ArrayList;
import java.util.List;

import pomis.app.tuturustations.models.City;

/**
 * Created by romanismagilov on 20.10.16.
 */

public class InsensitiveSearch {
    static public List<City> search(List<City> oldList, String searchParam) {
        ArrayList<City> newList = new ArrayList<>();

        for (City city : oldList) {
            if (city.getTitle().toUpperCase().contains(searchParam.toUpperCase()))
                newList.add(city);
        }

        return newList;
    }
}
