package restaurant.server.repository.impl;

import java.util.ArrayList;
import java.util.List;
import restaurant.common.domain.Table;
import java.sql.*;
import restaurant.server.repository.GenericRepository;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class TableRepositoryImpl implements GenericRepository<Table, Integer> {

    @Override
    public List<Table> getAll() throws Exception {
        List<Table> tables = new ArrayList<>();
        String query = "SELECT `tableId`,`numberOfSeats`,`isAvailable` FROM `table`";
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Integer id = rs.getInt("tableId");
                Integer numberOfSeats = rs.getInt("numberOfSeats");
                Boolean isAvailable = rs.getBoolean("isAvailable");
                Table table = new Table(id, numberOfSeats, isAvailable);
                tables.add(table);
            }
            rs.close();
            statement.close();
            return tables;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode getAll() klase TableRepository");
        }

    }

    @Override
    public void add(Table table) throws Exception {
        String query = "INSERT INTO `table` (numberOfSeats) VALUES(?)";
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, table.getNumberOfSeats());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode add() klase TableRepository");
        }

    }

    @Override
    public void delete(Table table) throws Exception {
        String query = "DELETE FROM `table` WHERE tableId=?";
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, table.getTableNumber());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode delete() klase TableRepository");
        }
    }

    @Override
    public void update(Table table) throws Exception {
        String query = "UPDATE `table` SET `numberOfSeats`=?, `isAvailable`=? WHERE `tableId`=?";
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, table.getNumberOfSeats());
            preparedStatement.setBoolean(2, table.isIsAvailable());
            preparedStatement.setInt(3, table.getTableNumber());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode update() klase TableRepository" + e.getMessage());
        }
    }

    @Override
    public Table findById(Integer id) throws Exception {
        Table table = null;
        String query = "SELECT `tableId`,`numberOfSeats`,`isAvailable` FROM `table` WHERE tableId=?";
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return table;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode getById() klase TableRepository");
        }

    }

    @Override
    public List<Table> findByQuery(String query) throws Exception {
        List<Table> tables = new ArrayList<>();
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("tableId");
                Integer numberOfSeats = rs.getInt("numberOfSeats");
                Boolean isAvailable = rs.getBoolean("isAvailable");
                Table table = new Table(id, numberOfSeats, isAvailable);
                tables.add(table);
            }
            rs.close();
            preparedStatement.close();
            return tables;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode setIsAvailable() klase TableRepository");
        }
    }

}
