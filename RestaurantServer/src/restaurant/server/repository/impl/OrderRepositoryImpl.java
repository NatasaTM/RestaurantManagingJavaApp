package restaurant.server.repository.impl;

import java.math.BigDecimal;
import java.util.List;
import restaurant.common.domain.Order;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import restaurant.common.domain.City;
import restaurant.common.domain.Employee;
import restaurant.common.domain.MenuCategory;
import restaurant.common.domain.MenuItem;
import restaurant.common.domain.MenuItemType;
import restaurant.common.domain.OrderItem;
import restaurant.common.domain.Table;
import restaurant.server.repository.GenericRepository;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderRepositoryImpl implements GenericRepository<Order, Long> {

    private static OrderRepositoryImpl instance;

    private OrderRepositoryImpl() {

    }

    public static OrderRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new OrderRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Order> getAll() throws Exception {
        try {
            String query = "SELECT o.`orderId`,o.`date`,o.`totalAmount`,o.`orderReadyStatus`,o.`orderPaidStatus`,o.`employeeId`,e.`firstname`,e.`lastname`,e.`birthdate`,e.`adress`,e.`jmbg`,e.`cityZipcode`,c.`name`,o.`tableId`,t.`numberOfSeats`,t.`isAvailable` FROM `order` o\n"
                    + "JOIN `employee` e ON e.`employeeId`=o.`employeeId`\n"
                    + "JOIN city c ON c.`zipcode`=e.`cityZipcode`\n"
                    + "JOIN `table` t ON t.`tableId` = o.`tableId` ORDER BY 2 DESC";
            List<Order> orders = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Long orderId = rs.getLong("orderId");
                Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
                boolean orderReadyStatus = rs.getBoolean("orderReadyStatus");
                boolean orderPaidStatus = rs.getBoolean("orderPaidStatus");

                Integer tableId = rs.getInt("tableId");
                Integer numberOfSeats = rs.getInt("numberOfSeats");
                boolean isAvailable = rs.getBoolean("isAvailable");

                Integer employeeId = rs.getInt("employeeId");
                String firtsname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                LocalDate birthdate = rs.getDate("birthdate").toLocalDate();
                String adress = rs.getString("adress");
                String jmbg = rs.getString("jmbg");

                Long cityZipcode = rs.getLong("cityZipcode");
                String cityName = rs.getString("name");

                City city = new City(cityZipcode, cityName);

                Employee employee = new Employee(employeeId, jmbg, firtsname, lastname, birthdate, adress, city);

                Table table = new Table(tableId, numberOfSeats, isAvailable);

                Order order = new Order(orderId, date, totalAmount, orderReadyStatus, orderPaidStatus, null, employee, table);

                //Order order = new Order(orderId, date, totalAmount, orderReadyStatus, orderPaidStatus, null, employee, orderId, table);
                List<OrderItem> orderItems = getOrderItems(order);
                order.setOrderItems(orderItems);
                orders.add(order);

            }
            rs.close();
            statement.close();
            return orders;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode getAll() klase OrderRepository: " + e.getMessage());
        }

    }

    @Override
    public void add(Order order) throws Exception {
        try {

            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "INSERT INTO `order` (`date`,`totalAmount`,`orderReadyStatus`,`orderPaidStatus`,`employeeId`,`tableId`) VALUES(?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, order.getDate(), java.sql.Types.TIMESTAMP);
            preparedStatement.setBigDecimal(2, order.getTotalAmount());
            preparedStatement.setBoolean(3, order.isOrderReadyStatus());
            preparedStatement.setBoolean(4, order.isOrderPaidStatus());
            preparedStatement.setInt(5, order.getEmployee().getId());
            preparedStatement.setInt(6, order.getTable().getTableNumber());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderId(rs.getLong(1));
            }
            rs.close();
            preparedStatement.close();

            query = "INSERT INTO `orderitems`(`orderId`,`menuItemId`,`quantity`) VALUES(?,?,?)";

            addOrderItemsToOrder(query, order);

//            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            List<OrderItem> orderItems = order.getOrderItems();
//            for (int i = 0; i < orderItems.size(); i++) {
//                preparedStatement.setLong(1, order.getOrderId());
//                preparedStatement.setInt(2, orderItems.get(i).getMenuItem().getId());
//                preparedStatement.setInt(3, orderItems.get(i).getQuantity());
//
//                preparedStatement.executeUpdate();
//
//                rs = preparedStatement.getGeneratedKeys();
//                if (rs.next()) {
//                    orderItems.get(i).setOrderItemId(rs.getInt(1));
//                }
//
//            }
//            BigDecimal price = BigDecimal.valueOf(0);
//            for (int i = 0; i < orderItems.size(); i++) {
//                price = price.add(orderItems.get(i).getMenuItem().getPrice().multiply(BigDecimal.valueOf((double) orderItems.get(i).getQuantity())));
//            }
            query = "UPDATE `order` SET `totalAmount`=? WHERE `orderId`=?";
            calculateTotalPrice(order, query);

//            TableRepositoryImpl tableRepositoryImpl = new TableRepositoryImpl();
//            tableRepositoryImpl.setIsAvailable(order.getTable(), false);
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setBigDecimal(1, price);
//            preparedStatement.setLong(2, order.getOrderId());
//            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode add() klase OrderRepository: " + e.getMessage());
        }
    }

    public void addOrderItemsToOrder(String query, Order order) {
        try {

            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            List<OrderItem> orderItems = order.getOrderItems();
            for (int i = 0; i < orderItems.size(); i++) {
                preparedStatement.setLong(1, order.getOrderId());
                preparedStatement.setInt(2, orderItems.get(i).getMenuItem().getId());
                preparedStatement.setInt(3, orderItems.get(i).getQuantity());

                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    orderItems.get(i).setOrderItemId(rs.getInt(1));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void calculateTotalPrice(Order order, String query) {
        try {

            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            List<OrderItem> orderItems = order.getOrderItems();
            BigDecimal price = BigDecimal.valueOf(0);
            for (int i = 0; i < orderItems.size(); i++) {
                price = price.add(orderItems.get(i).getMenuItem().getPrice().multiply(BigDecimal.valueOf((double) orderItems.get(i).getQuantity())));
            }
            //query = "UPDATE `order` SET `totalAmount`=? WHERE `orderId`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, price);
            preparedStatement.setLong(2, order.getOrderId());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Order order) throws Exception {
        List<OrderItem> orderItems = order.getOrderItems();
        if (checkIsReady(orderItems)) {

            try {
                String query = "UPDATE `order` SET `orderReadyStatus`=?,`orderPaidStatus`=? WHERE `orderId`=?";
                Connection connection = MyDatabaseConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setBoolean(1, order.isOrderReadyStatus());
                preparedStatement.setBoolean(2, order.isOrderPaidStatus());
                preparedStatement.setLong(3, order.getOrderId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Greska u izvrsenju metode add() klase OrderRepository: " + e.getMessage());
            }

        }

    }

    @Override
    public void delete(Order order) throws Exception {
        try {
            String query = "DELETE FROM `order` WHERE `orderId`= ?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, order.getOrderId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode delete() klase OrderRepository: " + e.getMessage());
        }
    }

    @Override
    public Order findById(Long id) throws Exception {
        try {
            Order order = null;
            String query = "SELECT o.`orderId`,o.`date`,o.`totalAmount`,o.`orderReadyStatus`,o.`orderPaidStatus`,o.`employeeId`,e.`firstname`,e.`lastname`,e.`birthdate`,e.`adress`,e.`jmbg`,e.`cityZipcode`,c.`name`,o.`tableId`,t.`numberOfSeats`,t.`isAvailable` FROM `order` o\n"
                    + "                    JOIN `employee` e ON e.`employeeId`=o.`employeeId`\n"
                    + "                    JOIN city c ON c.`zipcode`=e.`cityZipcode`\n"
                    + "                    JOIN `table` t ON t.`tableId` = o.`tableId`\n"
                    + "                    WHERE o.`orderId`=?\n"
                    + "                    ORDER BY 2;";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Long orderId = rs.getLong("orderId");
                Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
                boolean orderReadyStatus = rs.getBoolean("orderReadyStatus");
                boolean orderPaidStatus = rs.getBoolean("orderPaidStatus");

                Integer tableId = rs.getInt("tableId");
                Integer numberOfSeats = rs.getInt("numberOfSeats");
                boolean isAvailable = rs.getBoolean("isAvailable");

                Integer employeeId = rs.getInt("employeeId");
                String firtsname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                LocalDate birthdate = rs.getDate("birthdate").toLocalDate();
                String adress = rs.getString("adress");
                String jmbg = rs.getString("jmbg");

                Long cityZipcode = rs.getLong("cityZipcode");
                String cityName = rs.getString("name");

                City city = new City(cityZipcode, cityName);

                Employee employee = new Employee(employeeId, jmbg, firtsname, lastname, birthdate, adress, city);

                Table table = new Table(tableId, numberOfSeats, isAvailable);

                order = new Order(orderId, date, totalAmount, orderReadyStatus, orderPaidStatus, null, employee, table);

                //Order order = new Order(orderId, date, totalAmount, orderReadyStatus, orderPaidStatus, null, employee, orderId, table);
                List<OrderItem> orderItems = getOrderItems(order);
                order.setOrderItems(orderItems);
            }
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode findById() klase OrderRepository: " + e.getMessage());
        }
    }

    @Override
    public List<Order> findByQuery(String query) throws Exception {
        try {

            List<Order> orders = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                Long orderId = rs.getLong("orderId");
                Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
                boolean orderReadyStatus = rs.getBoolean("orderReadyStatus");
                boolean orderPaidStatus = rs.getBoolean("orderPaidStatus");

                Integer tableId = rs.getInt("tableId");
                Integer numberOfSeats = rs.getInt("numberOfSeats");
                boolean isAvailable = rs.getBoolean("isAvailable");

                Integer employeeId = rs.getInt("employeeId");
                String firtsname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                LocalDate birthdate = rs.getDate("birthdate").toLocalDate();
                String adress = rs.getString("adress");
                String jmbg = rs.getString("jmbg");

                Long cityZipcode = rs.getLong("cityZipcode");
                String cityName = rs.getString("name");

                City city = new City(cityZipcode, cityName);

                Employee employee = new Employee(employeeId, jmbg, firtsname, lastname, birthdate, adress, city);

                Table table = new Table(tableId, numberOfSeats, isAvailable);

                Order order = new Order(orderId, date, totalAmount, orderReadyStatus, orderPaidStatus, null, employee, table);

                List<OrderItem> orderItems = getOrderItems(order);
                order.setOrderItems(orderItems);
                orders.add(order);

            }

            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode findByQuery() klase OrderRepository: " + e.getMessage());
        }
    }

    private List<OrderItem> getOrderItems(Order order) throws Exception {
        List<OrderItem> orderItems = new ArrayList<>();
        try {
            String query = "SELECT oi.`orderItemsId`,oi.`menuItemId`,oi.`quantity`,oi.`orderItemReadyStatus`,mi.`name`,mi.`description`,mi.`price`,mi.`categoryId`,mi.`menuItemType`,c.`categoryName` FROM `orderitems` oi\n"
                    + "JOIN `menuitem` mi ON mi.`menuItemId`=oi.`menuItemId`\n"
                    + "JOIN `menucategory` c ON c.`categoryId` = mi.`categoryId`\n"
                    + "WHERE oi.`orderId` = ?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, order.getOrderId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer orderItemsId = rs.getInt("orderItemsId");
                Integer menuItemId = rs.getInt("menuItemId");
                int quantity = rs.getInt("quantity");
                Boolean orderItemReadyStatus = rs.getBoolean("orderItemReadyStatus");
                String manuItemName = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, rs.getObject("menuItemType").toString());

                Integer categoryId = rs.getInt("categoryId");
                String categoryName = rs.getString("categoryName");

                MenuCategory menuCategory = new MenuCategory(categoryId, categoryName);
                MenuItem menuItem = new MenuItem(menuItemId, manuItemName, description, price, menuCategory, menuItemType);
                OrderItem orderItem = new OrderItem(orderItemsId, menuItem, quantity, orderItemReadyStatus);
                orderItems.add(orderItem);
            }
            rs.close();
            preparedStatement.close();
            return orderItems;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode delete() klase OrderRepository: " + e.getMessage());
        }
    }

    public List<Order> findByCondition(LocalDate date, Boolean ready, Boolean paied, Employee employee, Table table) throws Exception {
        try {
            List<Order> orders = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            StringBuilder queryBuilder = new StringBuilder("SELECT o.`orderId`,o.`date`,o.`totalAmount`,o.`orderReadyStatus`,o.`orderPaidStatus`,o.`employeeId`,e.`firstname`,e.`lastname`,e.`birthdate`,e.`adress`,e.`jmbg`,e.`cityZipcode`,c.`name`,o.`tableId`,t.`numberOfSeats`,t.`isAvailable` FROM `order` o\n"
                    + "JOIN `employee` e ON e.`employeeId`=o.`employeeId`\n"
                    + "JOIN city c ON c.`zipcode`=e.`cityZipcode`\n"
                    + "JOIN `table` t ON t.`tableId` = o.`tableId`\n"
                    + "WHERE 1=1");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (date == null && ready == null && paied == null && employee == null && table == null) {
                orders = getAll();
            } else {

                if (date != null) {
                    queryBuilder.append(" AND o.`date` RLIKE ? ");
                }
                if (ready != null) {
                    queryBuilder.append(" AND o.`orderReadyStatus` = ?");
                }
                if (paied != null) {
                    queryBuilder.append(" AND o.`orderPaidStatus` = ?");
                }
                if (employee != null) {
                    queryBuilder.append(" AND o.`employeeId` = ?");
                }
                if (table != null) {
                    queryBuilder.append(" AND o.`tableId` = ?");
                }

                String query = queryBuilder.toString(); // +" ORDER BY o.`date` DESC"
                System.out.println(query);

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                int i = 1;
                if (date != null) {
                    preparedStatement.setDate(i++, java.sql.Date.valueOf(date.format(dtf)));
                }
                if (ready != null) {
                    preparedStatement.setBoolean(i++, ready);
                }
                if (paied != null) {
                    preparedStatement.setBoolean(i++, paied);
                }
                if (employee != null) {
                    preparedStatement.setInt(i++, employee.getId());
                }
                if (table != null) {
                    preparedStatement.setInt(i++, table.getTableNumber());
                }
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Long orderId = rs.getLong("orderId");
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime date1 = timestamp.toLocalDateTime();
                    BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
                    boolean orderReadyStatus = rs.getBoolean("orderReadyStatus");
                    boolean orderPaidStatus = rs.getBoolean("orderPaidStatus");

                    Integer tableId = rs.getInt("tableId");
                    Integer numberOfSeats = rs.getInt("numberOfSeats");
                    boolean isAvailable = rs.getBoolean("isAvailable");

                    Integer employeeId = rs.getInt("employeeId");
                    String firtsname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    LocalDate birthdate = rs.getDate("birthdate").toLocalDate();
                    String adress = rs.getString("adress");
                    String jmbg = rs.getString("jmbg");

                    Long cityZipcode = rs.getLong("cityZipcode");
                    String cityName = rs.getString("name");

                    City city = new City(cityZipcode, cityName);

                    Employee employee1 = new Employee(employeeId, jmbg, firtsname, lastname, birthdate, adress, city);

                    Table table1 = new Table(tableId, numberOfSeats, isAvailable);

                    Order order = new Order(orderId, date1, totalAmount, orderReadyStatus, orderPaidStatus, null, employee1, table1);

                    //Order order = new Order(orderId, date, totalAmount, orderReadyStatus, orderPaidStatus, null, employee, orderId, table);
                    List<OrderItem> orderItems = getOrderItems(order);
                    order.setOrderItems(orderItems);
                    orders.add(order);

                }

                rs.close();
                preparedStatement.close();

            }

            return orders;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsenju metode findByQuerry1() klase OrderRepository: " + e.getMessage());
        }
    }

    private boolean checkIsReady(List<OrderItem> orderItems) {
        boolean isReady = true;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getIsReady() == false) {
                isReady = false;
                break;
            }
        }
        return isReady;
    }

}
