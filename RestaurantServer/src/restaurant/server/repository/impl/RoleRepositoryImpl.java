package restaurant.server.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import restaurant.common.domain.Role;
import restaurant.server.repository.GenericRepository;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class RoleRepositoryImpl implements GenericRepository<Role, Integer> {

    @Override
    public List<Role> findByQuery(String query) throws Exception {

        try {
            List<Role> roles = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Role r = new Role(resultSet.getInt("roleId"), resultSet.getString("name"));
                roles.add(r);
            }
            resultSet.close();
            statement.close();

            return roles;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode findByQuery() klase  RoleRepository:" + e.getMessage());
        }

    }

    @Override
    public List<Role> getAll() throws Exception {
        try {
            List<Role> roles = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "SELECT `roleId`,`name` FROM `role`";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Integer id = rs.getInt("roleId");
                String name = rs.getString("name");
                Role role = new Role(id, name);
                roles.add(role);
            }
            rs.close();
            statement.close();
            return roles;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode getAll() klase  RoleRepository:" + e.getMessage());
        }
    }

    @Override
    public void add(Role role) throws Exception {
        try {
            String query = "INSERT INTO `role` (`name`)VALUES (?)";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, role.getName());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode add() klase  RoleRepository:" + e.getMessage());
        }
    }

    @Override
    public void update(Role role) throws Exception {
        try {
            String query = "UPDATE `role` SET `name`=? WHERE `roleId`=?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setInt(2, role.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode update() klase  RoleRepository:" + e.getMessage());
        }
    }

    @Override
    public void delete(Role role) throws Exception {
        try {
            String query = "DELETE FROM `role` WHERE `roleId`=?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, role.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
        }
    }

    @Override
    public Role findById(Integer id) throws Exception {

        try {
            Role role = null;
            String query = "SELECT `roleId`,`name` FROM `role` WHERE `roleId`=?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Integer iddb = rs.getInt("roleId");
                String name = rs.getString("name");
                role = new Role(id, name);
            }
            rs.close();
            preparedStatement.close();
            return role;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode findById() klase  RoleRepository:" + e.getMessage());
        }
    }

}
