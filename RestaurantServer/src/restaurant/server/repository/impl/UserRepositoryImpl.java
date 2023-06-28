package restaurant.server.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import restaurant.common.domain.City;
import restaurant.common.domain.Employee;
import restaurant.common.domain.Role;
import restaurant.common.domain.User;
import restaurant.server.connection.MyDatabaseConnection;
import java.sql.*;
import restaurant.server.repository.UserRepository;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> findByQuery(String query) throws Exception {
        try {
            List<User> users = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                User user = new User();
                user.setUsername(username);
                user.setPassword(null);

                user.setEmployee(new Employee(resultSet.getInt("employeeId")));
                user.setRoles(null);

                users.add(user);
            }

            resultSet.close();
            statement.close();

            return users;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode findByQuery() klase UserRepository ->" + e.getMessage());
        }
    }

    @Override
    public List<User> getAll() throws Exception {
        try {
            String query = "SELECT u.`username`,u.`password`,u.`employeeId`,e.`jmbg`,e.`firstname`,e.`lastname`,e.`birthdate`,e.`adress`,e.`cityZipcode`,c.`name` cityName FROM `userrole` ur\n"
                    + " JOIN `user` u ON u.`username`=ur.`username`\n"
                    + " JOIN employee e ON e.`employeeId`=u.`employeeId`\n"
                    + " JOIN city c ON c.`zipcode` = e.`cityZipcode`;";
            List<User> users = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                Integer employeeId = rs.getInt("employeeId");
                String jmbg = rs.getString("jmbg");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                LocalDate date = rs.getObject("birthdate", LocalDate.class);
                String adress = rs.getString("adress");
                Long cityZipcode = rs.getLong("cityZipcode");
                String cityName = rs.getString("cityName");

                City city = new City(cityZipcode, cityName);
                Employee employee = new Employee(employeeId, jmbg, firstname, lastname, date, adress, city);

                List<Role> roles = getRoles(username);
                User user = new User(username, password, employee, roles);
                users.add(user);

            }
            rs.close();
            statement.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode getAll() klase UserRepository ->" + e.getMessage());
        }
    }

    @Override
    public void add(User user) throws Exception {
        try {
            String query = "INSERT INTO `user` (`username`,`password`,`employeeId`) VALUES (?,?,?)";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getEmployee().getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();

            addUserRole(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode add() klase UserRepository ->" + e.getMessage());
        }
    }

    @Override
    public void update(User user) throws Exception {
        try {
            String query = "UPDATE `user` SET `username` =? ,`password` =? WHERE `employeeId` =?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getEmployee().getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode update() klase UserRepository ->" + e.getMessage());
        }
    }

    @Override
    public void delete(User user) throws Exception {
        try {
            String query = "DELETE FROM `user` WHERE `username` = ?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode delete() klase UserRepository ->" + e.getMessage());
        }
    }

    @Override
    public User findById(String username) throws Exception {
        try {
            User user = null;
            String query = "SELECT `username`,`password`,`employeeId` FROM `user` WHERE `username`=?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String usernameRs = rs.getString("username");
                String password = rs.getString("password");
                Integer employeeId = rs.getInt("employeeId");

                Employee employee = new Employee(employeeId);
                List<Role> roles = getRoles(usernameRs);
                user = new User(usernameRs, password, employee, roles);
            }
            rs.close();
            preparedStatement.close();
            return user;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode findById() klase UserRepository ->" + e.getMessage());
        }
    }

    private List<Role> getRoles(String username) throws Exception {
        try {
            List<Role> roles = new ArrayList<>();
            String query = "SELECT ur.`roleId`,r.`name` FROM `userrole` ur\n"
                    + " JOIN `role` r ON r.`roleId` = ur.`roleId` WHERE ur.`username` = ?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer roleId = rs.getInt("roleId");
                String name = rs.getString("name");
                Role role = new Role(roleId, name);
                roles.add(role);
            }
            rs.close();
            preparedStatement.close();
            return roles;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode getRoles() klase UserRepository ->" + e.getMessage());
        }

    }

    public void addUserRole(User user) throws Exception {
        try {
            String query = "INSERT INTO `userrole` (`username`,`roleId`) VALUES (?,?);";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            List<Role> roles = user.getRoles();
            for (Role r : roles) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setInt(2, r.getId());
                preparedStatement.executeUpdate();
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode addUserRole() klase UserRepository ->" + e.getMessage());
        }
    }

    @Override
    public List<User> findByEmailAndPassword(String username, String password) throws Exception {
        try {
            List<User> users = new ArrayList<>();
            String query = "select `username`,`password`,`employeeId` from `user` where BINARY `username`=? and BINARY `password`=?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String usernameRS = rs.getString("username");
                String passwordRS = rs.getString("password");
                Integer id = rs.getInt("employeeId");
                Employee employee = new Employee(id);
                User user = new User(usernameRS, passwordRS, employee, null);
                users.add(user);

            }
            rs.close();
            preparedStatement.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode findByEmailAndPassword() klase UserRepository ->" + e.getMessage());
        }
    }

}
