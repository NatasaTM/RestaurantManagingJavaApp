package restaurant.server.repository.impl;

import java.util.List;
import restaurant.common.domain.Employee;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import restaurant.common.domain.City;
import restaurant.server.repository.GenericRepository;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class EmployeeRepositoryImpl implements GenericRepository<Employee, Integer> {

    @Override
    public List<Employee> getAll() throws Exception {
        try {
            List<Employee> employees = new ArrayList<>();

            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "select e.employeeId,e.jmbg,e.firstname,e.lastname,e.birthdate,e.adress,c.zipcode,c.name cityname from employee e\n"
                    + "join city c on e.cityZipcode=c.zipcode";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Integer id = rs.getInt("employeeId");
                String jmbg = rs.getString("jmbg");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                LocalDate date = rs.getObject("birthdate", LocalDate.class);
                String adress = rs.getString("adress");

                Long zipcode = rs.getLong("zipcode");
                String cityName = rs.getString("cityname");

                City city = new City(zipcode, cityName);
                Employee employee = new Employee(id, jmbg, firstname, lastname, date, adress, city);
                employees.add(employee);
            }
            rs.close();
            statement.close();

            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode getAll() klase EmployeeRepository " + e.getMessage());
        }
    }

    @Override
    public List<Employee> findByQuery(String query) throws Exception {
        try {
            List<Employee> employees = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                Integer id = rs.getInt("employeeId");
                String jmbg = rs.getString("jmbg");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                LocalDate date = rs.getObject("birthdate", LocalDate.class);
                String adress = rs.getString("adress");

                Long zipcode = rs.getLong("zipcode");
                String cityName = rs.getString("cityname");

                City city = new City(zipcode, cityName);
                Employee employee = new Employee(id, jmbg, firstname, lastname, date, adress, city);
                employees.add(employee);

            }

            rs.close();
            statement.close();

            return employees;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju findByQuery() klase EmployeeRepository " + e.getMessage());
        }
    }

    @Override
    public void add(Employee employee) throws Exception {
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            String query = "INSERT INTO employee (jmbg,firstname,lastname,birthdate,adress,cityZipcode) VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getJmbg());
            preparedStatement.setString(2, employee.getFirstname());
            preparedStatement.setString(3, employee.getLastname());
            preparedStatement.setDate(4, Date.valueOf(employee.getBirthdate()));
            preparedStatement.setString(5, employee.getAdress());
            preparedStatement.setLong(6, employee.getCity().getZipcode());

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode add() klase EmployeeRepository " + e.getMessage());
        }
    }

    @Override
    public Employee findById(Integer id) throws Exception {
        
        Employee employee=null;

        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "SELECT e.`employeeId`,e.`firstname`,e.`lastname`,e.`birthdate`,e.`adress`,e.`cityZipcode`,e.`jmbg`, c.name FROM employee e \n"
                    + "JOIN city c ON c.zipcode = e.cityZipcode\n"
                    + "\n"
                    + "WHERE employeeId=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
               
                Integer eId = rs.getInt("employeeId");
                String jmbg = rs.getString("jmbg");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                LocalDate date = rs.getObject("birthdate", LocalDate.class);
                String adress = rs.getString("adress");

                Long zipcode = rs.getLong("cityZipcode");
                String cityName = rs.getString("name");

                City city = new City(zipcode, cityName);
                employee = new Employee(eId, jmbg, firstname, lastname, date, adress, city);                
 

            }
            
            rs.close();
            preparedStatement.close();
            return employee;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode findById() klase EmployeeRepository " + e.getMessage());
        }
    }

    @Override
    public void delete(Employee employee) throws Exception {
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "DELETE FROM employee WHERE employeeId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, employee.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode delete() klase EmployeeRepository " + e.getMessage());
        }
    }

    @Override
    public void update(Employee employee) throws Exception {
         try {
            String query = "UPDATE employee SET `jmbg`=?,`firstname`=?, `lastname`=?,`birthdate`=?,`adress`=?,`cityZipcode`=? WHERE `employeeId`=?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getJmbg());
            preparedStatement.setString(2, employee.getFirstname());
            preparedStatement.setString(3, employee.getLastname());
            preparedStatement.setDate(4,java.sql.Date.valueOf( employee.getBirthdate()));
            preparedStatement.setString(5, employee.getAdress());
            preparedStatement.setLong(6, employee.getCity().getZipcode());
            preparedStatement.setInt(7, employee.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
             e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode update() klase EmployeeRepository " + e.getMessage());
        }
    }

}
