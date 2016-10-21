package pomis.app.tuturustations.helpers;

import java.util.ArrayList;
import java.util.List;

import pomis.app.tuturustations.models.City;

/**
 * Поиск, нечувствительный к регистру
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
