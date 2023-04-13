package restaurant.server.repository.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import restaurant.common.domain.MenuItem;
import java.sql.*;
import restaurant.common.domain.MenuCategory;
import restaurant.common.domain.MenuItemType;
import restaurant.server.repository.GenericRepository;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuItemRepositoryImpl implements GenericRepository<MenuItem, Integer> {

    @Override
    public List<MenuItem> getAll() throws Exception {
        try {

            List<MenuItem> menuItems = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            String query = "SELECT m.menuItemId,m.name menuItemName,m.description,m.price,m.`menuItemType`, c.categoryId,c.categoryName FROM menuItem m\n"
                    + "JOIN menucategory c ON c.categoryId = m.categoryId ORDER BY categoryName";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Integer id = rs.getInt("menuItemId");
                String menuItemname = rs.getString("menuItemName");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, rs.getObject("menuItemType").toString());

                String categoryName = rs.getString("categoryName");
                Integer categoryId = rs.getInt("categoryId");
                MenuCategory category = new MenuCategory(categoryId, categoryName);
                MenuItem menuItem = new MenuItem(id, menuItemname, description, price, category,menuItemType);
                menuItems.add(menuItem);
            }

            rs.close();
            statement.close();

            return menuItems;

        } catch (Exception e) {

            System.out.println(e.getMessage());

            throw new Exception("Greska u izvrsenju metode getAll() klase MenuItemRepository ->" + e.getMessage());
        }
    }

    @Override
    public List<MenuItem> findByQuery(String query) throws Exception {
        try {
            List<MenuItem> menuItems = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Integer id = rs.getInt("menuItemId");
                String menuItemname = rs.getString("menuItemName");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                 MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, rs.getObject("menuItemType").toString());

                String categoryName = rs.getString("categoryName");
                Integer categoryId = rs.getInt("categoryId");
                MenuCategory category = new MenuCategory(categoryId, categoryName);
                MenuItem menuItem = new MenuItem(id, menuItemname, description, price, category,menuItemType);
                menuItems.add(menuItem);

            }
            rs.close();
            statement.close();

            return menuItems;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            throw new Exception("Greska u izvrsenju metode findByQuery() klase MenuItemRepository ->" + e.getMessage());
        }
    }

    @Override
    public void add(MenuItem menuItem) throws Exception {

        Integer id = getCategoryId(menuItem).get(0);
        if (id == null) {
            throw new Exception("Kategorija ne postoji u bazi");
        }

        try {

            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            String query = "INSERT INTO menuItem (`name`,`description`,price,categoryId) VALUES(?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, menuItem.getName());
            preparedStatement.setString(2, menuItem.getDescription());
            preparedStatement.setBigDecimal(3, menuItem.getPrice());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode add() klase MenuItemRepository: " + e.getMessage());
        }

    }

    private List<Integer> getCategoryId(MenuItem menuItem) throws Exception {
        List<Integer> integers = new ArrayList<>();
        try {
            String query = "SELECT categoryId FROM menucategory WHERE categoryName LIKE '" + menuItem.getMenuCategory().getName() + "'";
            System.out.println(query);
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Integer id = rs.getInt("categoryId");
                integers.add(id);
            }

            rs.close();
            statement.close();

            return integers;

        } catch (Exception e) {

            System.out.println(e.getMessage());

            throw new Exception("Greska u izvrsenju metode getAll() klase MenuItemRepository ->" + e.getMessage());
        }

    }

//    @Override
//    public List<MenuItem> getByCategory(String name) throws Exception {
//        try {
//            List<MenuItem> menuItems = new ArrayList<>();
//            Connection connection = MyDatabaseConnection.getInstance().getConnection();
//            String query = "SELECT m.menuItemId,m.name menuItemName,m.description,m.price,m.`menuItemType`, c.categoryId,c.categoryName FROM menuItem m\n"
//                    + "JOIN menucategory c ON c.categoryId = m.categoryId\n"
//                    + "WHERE c.categoryName='" + name + "'";
//            Statement statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery(query);
//            while (rs.next()) {
//                Integer id = rs.getInt("menuItemId");
//                String menuItemname = rs.getString("menuItemName");
//                String description = rs.getString("description");
//                BigDecimal price = rs.getBigDecimal("price");
//                MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, rs.getObject("menuItemType").toString());
//
//                String categoryName = rs.getString("categoryName");
//                Integer categoryId = rs.getInt("categoryId");
//                MenuCategory category = new MenuCategory(categoryId, categoryName);
//                MenuItem menuItem = new MenuItem(id, menuItemname, description, price, category,menuItemType);
//                menuItems.add(menuItem);
//
//            }
//            rs.close();
//            statement.close();
//
//            return menuItems;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//
//            throw new Exception("Greska u izvrsenju metode getByCategory() klase MenuItemRepository ->" + e.getMessage());
//        }
//    }

    @Override
    public void delete(MenuItem menuItem) throws Exception {

        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "DELETE FROM menuItem WHERE menuItemId = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, menuItem.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode getByCategory() klase MenuItemRepository ->" + e.getMessage());
        }

    }

    @Override
    public void update(MenuItem menuItem) throws Exception {
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "UPDATE menuitem SET `name`=?,`description` = ?, price=?, categoryId=? WHERE menuItemId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, menuItem.getName());
            preparedStatement.setString(2, menuItem.getDescription());
            preparedStatement.setBigDecimal(3, menuItem.getPrice());
            preparedStatement.setInt(4, menuItem.getMenuCategory().getId());
            preparedStatement.setInt(5, menuItem.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode update() klase MenuItemRepository: " + e.getMessage());
        }
    }

    @Override
    public MenuItem findById(Integer id) throws Exception {
        MenuItem menuItem = null;
        try {
            String query = "SELECT mi.`name`,mi.`description`,mi.`price`,mi.`categoryId`,mi.`menuItemType`,c.`categoryName` FROM `menuitem` mi \n"
                    + "JOIN `menucategory` c ON c.`categoryId`=mi.`categoryId`\n"
                    + "WHERE mi.`menuItemId`=?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                Integer categoryId = rs.getInt("categoryId");
                String categoryName = rs.getString("categoryName");
                MenuCategory menuCategory = new MenuCategory(categoryId, categoryName);
                MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, rs.getObject("menuItemType").toString());
                menuItem = new MenuItem(id, name, description, price, menuCategory,menuItemType);
            }
            rs.close();
            preparedStatement.close();
            return menuItem;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode findById() klase MenuItemRepository: " + e.getMessage());
        }
    }

}
