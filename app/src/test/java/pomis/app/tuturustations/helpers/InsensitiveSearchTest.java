package pomis.app.tuturustations.helpers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pomis.app.tuturustations.models.City;
import pomis.app.tuturustations.models.CityTo;

import static org.junit.Assert.*;

/**
 * Тест поиска, нечувствительного к регистру
 */
public class InsensitiveSearchTest {
    @Test
    public void search() throws Exception {
        List<City> oldList = new ArrayList<>();
        String[] names = new String[]{
                "уфа", "москва", "УФА", "владивосток", "мОсКвА", "Москва", "Иркутск", "Ростов-на-Дону"
        };
        for (int i = 0; i < names.length; i++) {
            City city = new CityTo();
            city.setName(names[i]);
            oldList.add(city);
        }
        List<City> newList = InsensitiveSearch.search(oldList, "москва");
        assert newList.size()==3;
        assert newList.contains(oldList.get(1));
        assert newList.contains(oldList.get(4));
        assert newList.contains(oldList.get(5));
    }

}