package restaurant.server.repository.impl;

import java.math.BigDecimal;
import java.util.List;
import restaurant.common.domain.Receipt;
import restaurant.server.repository.GenericRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ReceiptRepositoryImpl implements GenericRepository<Receipt, Long> {

    @Override
    public List<Receipt> getAll() throws Exception {
        try {
            List<Receipt> receipts = new ArrayList<>();
            String query = "SELECT `receiptId`,`date`,`totalAmount` FROM `receipt`";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Long id = rs.getLong("receiptId");
                java.sql.Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                BigDecimal amount = rs.getBigDecimal("totalAmount");

                Receipt receipt = new Receipt(id, date, amount, null);
                List<Order> orders = getAllOrders(receipt);
                receipt.setOrders(orders);
                receipts.add(receipt);
            }
            rs.close();
            statement.close();
            return receipts;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode getAll() klase ReceiptRepositoryImpl: " + e.getMessage());
        }
    }

    @Override
    public void add(Receipt receipt) throws Exception {
        try {
            if (checkOrder(receipt)) {
                throw new Exception("Za porudzbinu je vec kreiran racun!");
            }

            String query = "INSERT INTO `receipt`(`date`) VALUES (?)";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, receipt.getDate(), java.sql.Types.TIMESTAMP);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                receipt.setId(rs.getLong(1));
            }
            rs.close();
            preparedStatement.close();

            query = "INSERT INTO `receiptorder`(`receiptId`,`orderId`) VALUES (?,?)";
            List<Order> orders = receipt.getOrders();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query);
            for (Order o : orders) {
                preparedStatement1.setLong(1, receipt.getId());
                preparedStatement1.setLong(2, o.getOrderId());
                preparedStatement1.executeUpdate();
            }
            preparedStatement1.close();

            query = "UPDATE `receipt` SET `totalAmount` = ? WHERE `receiptId`=?";
            calculateTotalAmount(receipt, query);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode add() klase ReceiptRepositoryImpl: " + e.getMessage());
        }
    }

    @Override
    public void update(Receipt entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Receipt entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Receipt findById(Long id) throws Exception {

        try {
            Receipt receipt = null;
            String query = "SELECT `receiptId`,`date`,`totalAmount` FROM `receipt` WHERE `receiptId` = ?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Long rid = rs.getLong("receiptId");
                java.sql.Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                BigDecimal amount = rs.getBigDecimal("totalAmount");

                receipt = new Receipt(rid, date, amount, null);
                List<Order> orders = getAllOrders(receipt);
                receipt.setOrders(orders);

            }
            rs.close();
            preparedStatement.close();
            return receipt;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode findById() klase ReceiptRepositoryImpl: " + e.getMessage());
        }
    }

    @Override
    public List<Receipt> findByQuery(String query) throws Exception {
        try {
            List<Receipt> receipts = new ArrayList<>();
//            String q = " SELECT `receiptId`,`date`,`totalAmount` FROM `receipt` \n"
//                    + "WHERE `receiptId` NOT IN (SELECT `receiptId` FROM `payment`);";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
               Long receiptId = rs.getLong("receiptId");
               Receipt receipt = findById(receiptId);
               receipts.add(receipt);
            }
            rs.close();
            statement.close();
            return receipts;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode findByQuery() klase ReceiptRepositoryImpl: " + e.getMessage());
        }
        
    }

    private void calculateTotalAmount(Receipt receipt, String query) {
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            List<Order> orders = receipt.getOrders();
            BigDecimal price = BigDecimal.valueOf(0);
            for (Order o : orders) {
                price = price.add(o.getTotalAmount());
            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // query = "UPDATE `receipt` SET `totalAmount` = ? WHERE `receiptId`=?";
            preparedStatement.setBigDecimal(1, price);
            preparedStatement.setLong(2, receipt.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Order> getAllOrders(Receipt receipt) throws Exception {
        try {
            List<Order> orders = new ArrayList<>();
            String query = "SELECT `orderId` FROM `receiptorder`WHERE `receiptId` = ?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, receipt.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("orderId");
                Order order = new Order();
                order.setOrderId(id);
                orders.add(order);
            }
            rs.close();
            preparedStatement.close();
            return orders;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsavanju metode getAllOrders() klase ReceiptRepository" + e.getMessage());
        }
    }

    private boolean checkOrder(Receipt receipt) throws Exception {
        try {
            List<Order> orders = receipt.getOrders();
            boolean exists = false;
            String query = "SELECT `orderId` FROM `receiptorder` WHERE `orderId` = ?";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Order o : orders) {
                preparedStatement.setLong(1, o.getOrderId());
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    exists = true;
                    break;
                }

            }
            preparedStatement.close();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska u izvrsavanju metode checkOrder() klase ReceiptRepository" + e.getMessage());
        }
    }

}
