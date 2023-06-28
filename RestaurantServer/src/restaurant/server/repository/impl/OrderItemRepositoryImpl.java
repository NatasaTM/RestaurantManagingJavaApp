package restaurant.server.repository.impl;

import java.math.BigDecimal;
import java.util.List;
import restaurant.common.domain.OrderItem;
import restaurant.server.repository.GenericRepository;
import java.sql.*;
import java.util.ArrayList;
import restaurant.common.domain.MenuCategory;
import restaurant.common.domain.MenuItem;
import restaurant.common.domain.MenuItemType;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderItemRepositoryImpl implements GenericRepository<OrderItem, Integer> {

    @Override
    public List<OrderItem> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(OrderItem entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(OrderItem orderItem) throws Exception {
        try {
            String query = "UPDATE `orderitems` SET `orderItemReadyStatus`=? WHERE `orderItemsId` = ?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, orderItem.getIsReady());
            preparedStatement.setInt(2, orderItem.getOrderItemId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvodjenju metode update() klase OrderItemRepositoryImpl" + e.getMessage());
        }
    }

    @Override
    public void delete(OrderItem entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<OrderItem> findByQuery(String query) throws Exception {
        try {
            List<OrderItem> orderItems = new ArrayList<>();
//            String query1 = "SELECT oi.`orderId`,oi.`orderItemsId`,oi.`menuItemId`,oi.`quantity`,oi.`orderItemReadyStatus`,mi.`name`,mi.`description`,mi.`price`,mi.`categoryId`,mi.`menuItemType`,c.`categoryName` FROM `orderitems` oi\n" +
//"JOIN `menuitem` mi ON mi.`menuItemId` = oi.`menuItemId`\n" +
//"JOIN `menucategory` c ON c.`categoryId` = mi.`categoryId`\n" +
//"WHERE oi.`orderId` = 9 AND mi.`menuItemType` = 'FOOD' AND oi.`orderItemReadyStatus` = 0;";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Integer orderItemsId = rs.getInt("orderItemsId");

                Integer menuItemId = rs.getInt("menuItemId");
                int quantity = rs.getInt("quantity");
                Boolean orderItemReadyStatus = rs.getBoolean("orderItemReadyStatus");
                String menuItemName = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, rs.getObject("menuItemType").toString());

                String categoryName = rs.getString("categoryName");
                Integer categoryId = rs.getInt("categoryId");

                MenuCategory menuCategory = new MenuCategory(categoryId, categoryName);

                MenuItem menuItem = new MenuItem(menuItemId, menuItemName, description, price, menuCategory, menuItemType);

                OrderItem orderItem = new OrderItem(orderItemsId, menuItem, quantity, orderItemReadyStatus);
                orderItems.add(orderItem);
            }
            rs.close();
            statement.close();
            return orderItems;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvodjenju metode findByQuery() klase OrderItemRepositoryImpl" + e.getMessage());
        }
    }

    @Override
    public OrderItem findById(Integer id) throws Exception {
        try {
            OrderItem orderItem = null;
            String query = "SELECT `orderItemsId`,`menuItemId`,`quantity`,`orderItemReadyStatus` FROM `orderitems` WHERE `orderItemsId`=?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Integer orderItemsId = rs.getInt("orderItemsId");
                Integer menuItemId = rs.getInt("menuItemId");
                int quantity = rs.getInt("quantity");
                Boolean orderItemReadyStatus = rs.getBoolean("orderItemReadyStatus");

                MenuItem menuItem = new MenuItem();
                menuItem.setId(menuItemId);
                orderItem = new OrderItem(orderItemsId, menuItem, quantity, orderItemReadyStatus);
            }
            rs.close();
            preparedStatement.close();
            return orderItem;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvodjenju metode findById() klase OrderItemRepositoryImpl" + e.getMessage());
        }
    }

}
