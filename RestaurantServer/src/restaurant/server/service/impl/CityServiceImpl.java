package restaurant.server.service.impl;

import java.util.List;
import restaurant.common.domain.City;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.impl.CityRepositoryImpl;
import restaurant.server.service.CityService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class CityServiceImpl implements CityService {

    private final GenericRepository<City,Long> cityRepository;

    public CityServiceImpl() {
        cityRepository = new CityRepositoryImpl();
    }

    @Override
    public List<City> getAll() throws Exception {
        return cityRepository.getAll();
    }

    @Override
    public void add(City city) throws Exception {
        String query = "SELECT * FROM city WHERE zipcode = "+city.getZipcode();
        
        List<City> cities = cityRepository.findByQuery(query);
        if(cities.isEmpty()){
            cityRepository.add(city);
        }else
            throw new Exception("Grad vec postoji");
    }

    @Override
    public void update(City city) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(City city) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public City finfById(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
