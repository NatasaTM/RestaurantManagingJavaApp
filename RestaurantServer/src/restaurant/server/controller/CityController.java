package restaurant.server.controller;

import java.util.List;
import restaurant.common.domain.City;
import restaurant.server.service.CityService;
import restaurant.server.service.impl.CityServiceImpl;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class CityController {
    
    private final static CityController instance = new CityController();
    
    private CityService cityService;

    public CityController() {
        this.cityService = new CityServiceImpl();
    }

    public static CityController getInstance() {
        return instance;
    }
    
    public List<City> getAllCities() throws Exception{
        return cityService.getAll();
    }
    
    public void addCity(City city) throws Exception{
        cityService.add(city);
    }
    
    
    
}
