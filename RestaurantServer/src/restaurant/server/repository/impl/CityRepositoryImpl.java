package restaurant.server.repository.impl;

import java.util.ArrayList;
import java.util.List;
import restaurant.common.domain.City;
import java.sql.*;
import restaurant.server.repository.GenericRepository;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class CityRepositoryImpl implements GenericRepository<City, Long> {

    @Override
    public List<City> getAll() throws Exception {

        try {

            List<City> cities = new ArrayList<>();
            
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            String query = "select * from city order by `name`";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Long zipcode = rs.getLong("zipcode");
                String name = rs.getString("name");
                City city = new City(zipcode, name);
                cities.add(city);
            }

            rs.close();
            statement.close();
            

            return cities;

        } catch (Exception e) {

            System.out.println(e.getMessage());

            throw new Exception("Greska u izvrsenju metode getAll() klase CityRepository ->" + e.getMessage());
        }

    }

    @Override
    public List<City> findByQuery(String query) throws Exception {

        try {
            List<City> cities = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Long zipcode = rs.getLong("zipcode");
                String name = rs.getString("name");
                City city = new City(zipcode, name);
                cities.add(city);

            }

            rs.close();
            statement.close();
           

            return cities;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode findByQuery() klase CityRepository ->" + e.getMessage());
        }
    }

    @Override
    public void add(City city) throws Exception {
        try {
           Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "insert into city (zipcode,`name`) values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, city.getZipcode());
            preparedStatement.setString(2, city.getName());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode add() klase CityRepository ->" + e.getMessage());
        }
    }

    @Override
    public void update(City entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(City entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public City findById(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
