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
    public void update(City city) throws Exception {
        try {
            String query = "update `city` set `zipcode`=?,`name`=? where `zipcode`=?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, city.getZipcode());
            preparedStatement.setString(2, city.getName());
            preparedStatement.setLong(3, city.getZipcode());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode update() klase CityRepository ->" + e.getMessage());
        }
    }

    @Override
    public void delete(City city) throws Exception {
        try {
            String query = "DELETE FROM `city` WHERE `zipcode`=?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, city.getZipcode());
            preparedStatement.executeUpdate();
            
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode delete() klase CityRepository ->" + e.getMessage());
        }
    }

    @Override
    public City findById(Long id) throws Exception {
        try {
            City city = null;
            String query = "SELECT `zipcode`,`name` FROM `city` WHERE `zipcode`=?" ;
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Long zipcodeRs = rs.getLong("zipcode");
                String name = rs.getString("name");
                
                city = new City(zipcodeRs, name);
            }
            rs.close();
            preparedStatement.close();
            return city;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode findById() klase CityRepository ->" + e.getMessage());
        }
    }

}
