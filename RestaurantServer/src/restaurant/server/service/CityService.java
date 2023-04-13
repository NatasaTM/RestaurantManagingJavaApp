package restaurant.server.service;

import java.util.List;
import restaurant.common.domain.City;

/**
 *
 * @author Natasa Todorov Markovic
 */
public interface CityService {

    List<City> getAll() throws Exception;

    void add(City city) throws Exception;

    void update(City city) throws Exception;

    void delete(City city) throws Exception;

    City finfById(Long id) throws Exception;

}
