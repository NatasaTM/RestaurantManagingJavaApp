package restaurant.server.repository.impl;

import java.math.BigDecimal;
import java.util.List;
import restaurant.common.domain.Payment;
import restaurant.server.repository.GenericRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import restaurant.common.domain.PaymentMethodType;
import restaurant.common.domain.Receipt;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class PaymentRepositoryImpl implements GenericRepository<Payment, Long>{

    @Override
    public List<Payment> getAll() throws Exception {
        try {
            String query = "SELECT p.`paymentId`,p.`receiptId`,p.`paymentMethodId`,p.`date`,pm.`paymentMethodName`,r.`date`,r.`totalAmount` FROM `payment` p\n" +
"JOIN `paymentmethod` pm ON pm.`paymentMethodId` = p.`paymentMethodId`\n" +
"JOIN `receipt` r ON r.`receiptId` = p.`receiptId`";
            List<Payment> payments = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                Long paymentId = rs.getLong("paymentId");
                Long receiptId = rs.getLong("receiptId");
                PaymentMethodType paymentMethodType = Enum.valueOf(PaymentMethodType.class, rs.getString("paymentMethodName"));
                java.sql.Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
                
                Receipt receipt = new Receipt(receiptId);
                Payment payment = new Payment(paymentId, date, receipt, paymentMethodType);
                payments.add(payment);
            }
            rs.close();
            statement.close();
            return payments;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode getAll() klase PaymentRepositoryImpl" + e.getMessage()); 
        }
     
    }

    @Override
    public void add(Payment payment) throws Exception {
         try {
            String query = "INSERT INTO `payment` (`receiptId`,`paymentMethodId`,`date`) VALUES(?,?,?)";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, payment.getReceipt().getId());
            preparedStatement.setInt(2, getPaymentMethodId(payment.getPaymentMethodType()));
            preparedStatement.setObject(3, payment.getDate(),java.sql.Types.TIMESTAMP);
            preparedStatement.executeUpdate();
            
            preparedStatement.close();
        } catch (Exception e) {
           e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode add() klase PaymentRepositoryImpl" + e.getMessage()); 
        }
    }

    @Override
    public void update(Payment entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Payment entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Payment findById(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Payment> findByQuery(String query) throws Exception {
        try {
//            String query1 = "SELECT p.`paymentId`,p.`receiptId`,p.`paymentMethodId`,p.`date`,pm.`paymentMethodName`,r.`date`,r.`totalAmount` FROM `payment` p\n" +
//"JOIN `paymentmethod` pm ON pm.`paymentMethodId` = p.`paymentMethodId`\n" +
//"JOIN `receipt` r ON r.`receiptId` = p.`receiptId` \n" +
//"WHERE r.`receiptId` = 25;";
            List<Payment> payments = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                Long paymentId = rs.getLong("paymentId");
                Long receiptId = rs.getLong("receiptId");
                PaymentMethodType paymentMethodType = Enum.valueOf(PaymentMethodType.class, rs.getString("paymentMethodName"));
                System.out.println(paymentMethodType.toString());
                java.sql.Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime date = timestamp.toLocalDateTime();
                BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
                
                Receipt receipt = new Receipt(receiptId);
                Payment payment = new Payment(paymentId, date, receipt, paymentMethodType);
                payments.add(payment);
            }
            rs.close();
            statement.close();
            return payments;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode findByQuery() klase PaymentRepositoryImpl" + e.getMessage()); 
        }
    }

    private Integer getPaymentMethodId(PaymentMethodType paymentMethodType) throws Exception {
        
        try {
            Integer id = null;
            String query = "SELECT `paymentMethodId` FROM `paymentmethod` WHERE `paymentMethodName` = '" +paymentMethodType.toString()+"'";
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                id = rs.getInt(1);
            }
            rs.close();
            statement.close();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode getPaymentMethodId() klase PaymentRepositoryImpl" + e.getMessage());
        }
        
    }
    
}
