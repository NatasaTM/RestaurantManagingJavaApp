package restaurant.client.ui.form.receipt.controller;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.OrderItemsTableModel;
import restaurant.client.ui.form.receipt.ReceiptForm;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;
import restaurant.common.domain.Payment;
import restaurant.common.domain.PaymentMethodFactory;
import restaurant.common.domain.PaymentMethodType;
import restaurant.common.domain.Receipt;
import restaurant.common.domain.Table;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ReceiptFormController {

    public static void prepareView(JButton btnConfirmCardNumber, JComboBox comboPaymentMethodType, JTextArea txtOrders, JDialog orderPaymentForm, Receipt receipt, OrderItemsTableModel orderItemsTableModel, JTable tblOrderItems, JTextField txtReceiptId, JTextField txtReceiptDateTime, JTextField txtEmployee, JTextField txtTotalAmount) {
        User user = ApplicationSession.getInstance().getLoginUser();
        orderPaymentForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());

        DecimalFormat df = new DecimalFormat("0.00");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy." + " " + " HH:mm:ss");
        txtReceiptId.setText(receipt.getId().toString());

        txtReceiptDateTime.setText(receipt.getDate().format(dtf));
        txtEmployee.setText(user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());

        txtTotalAmount.setText(df.format(receipt.getAmount()));

        List<Long> orderIds = new ArrayList<>();
        for (Order o : receipt.getOrders()) {
            orderIds.add(o.getOrderId());
        }
        txtOrders.setText("Broj porudzbine: \n " + orderIds.toString());
        setTableModel(tblOrderItems, receipt);
        populateComboPaymentMethodType(comboPaymentMethodType);
        btnConfirmCardNumber.setEnabled(false);

    }

    public static void setTableModel(JTable tblOrderItems, Receipt receipt) {
        List<OrderItem> orderItems = getOrderItems(receipt.getOrders());
        OrderItemsTableModel model = new OrderItemsTableModel();
        model.setOrderitems(orderItems);
        tblOrderItems.setModel(model);
    }

    private static List<OrderItem> getOrderItems(List<Order> orders) {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = null;
        List<OrderItem> temp = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            temp = orders.get(i).getOrderItems();
            System.out.println("Temp: " + temp.toString());
            for (int j = 0; j < temp.size(); j++) {
                orderItem = temp.get(j);
                System.out.println("OrderItem: " + orderItem);
                if (!orderItems.contains(orderItem)) {
                    orderItems.add(orderItem);

                } else {
                    for (int k = 0; k < orderItems.size(); k++) {
                        if (orderItems.get(k).equals(orderItem)) {
                            int quantity = orderItem.getQuantity();
                            orderItems.get(k).setQuantity(orderItems.get(k).getQuantity() + quantity);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("Lista ova: " + orderItems.toString());
        return orderItems;
    }

    public static void populateComboPaymentMethodType(JComboBox comboPaymentMethodType) {
        comboPaymentMethodType.setModel(new DefaultComboBoxModel(PaymentMethodType.values()));
    }

    public static void btnSelectPayMethodActionPerformed(JComboBox comboPaymentMethodType, JButton btnConfirmCardNumber, JTextField txtCardNumber, ReceiptForm receiptForm) {
        PaymentMethodType paymentMethodType = (PaymentMethodType) comboPaymentMethodType.getSelectedItem();
        receiptForm.setPaymentMethodType(paymentMethodType);
        if (paymentMethodType.equals(PaymentMethodType.VISA)) {
            btnConfirmCardNumber.setEnabled(true);
            txtCardNumber.setEditable(true);
        }
        receiptForm.setPaymentMethodType(paymentMethodType);
    }

    public static void btnConfirmPaymentActionPerformed(JButton btnConfirmPayment, JComboBox comboPaymentMethodType, ReceiptForm receiptForm, Receipt receipt, JTextArea txtAreaMessage, JTextField txtCardNumber) {
        PaymentMethodType paymentMethodType = receiptForm.getPaymentMethodType();
        String cardNumber = null;
        boolean isValid = true;
        if(paymentMethodType == null){
            JOptionPane.showMessageDialog(null, "Niste izabrali nacin placanja!");
            isValid = false;
        }
        if(isValid){
            
        
        if (paymentMethodType.equals(PaymentMethodType.VISA)) {
            cardNumber = txtCardNumber.getText();
            if (cardNumber.isEmpty()) {
                JOptionPane.showMessageDialog(receiptForm, "Morate uneti broj kartice");

            } else {
                PaymentMethodFactory.getPaymentMethod(paymentMethodType).executePayment(receipt.getAmount());
                Payment payment = new Payment(null, LocalDateTime.now(), receipt, receiptForm.getPaymentMethodType());
                try {
                    addPayment(payment);
                    freeTable(payment);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String message = "Broj kartice: " + cardNumber + "\n" + "Payment Authorization processing... \n " + "The account balance is being checked... \n " + "Payment is being processed...\n" + "Payment successful";
                txtAreaMessage.setText(message);
            
            }  
        }
        if (receiptForm.getPaymentMethodType().equals(PaymentMethodType.CASH)) {
            PaymentMethodFactory.getPaymentMethod(paymentMethodType).executePayment(receipt.getAmount());
                Payment payment = new Payment(null, LocalDateTime.now(), receipt, receiptForm.getPaymentMethodType());
                try {
                    addPayment(payment);
                    freeTable(payment);
                } catch (Exception ex) {
                    Logger.getLogger(ReceiptFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //String message = "Broj kartice: " + cardNumber + "\n" + "Payment Authorization processing... \n " + "The account balance is being checked... \n " + "Payment is being processed...\n" + "Payment successful";
                //txtAreaMessage.setText(message);
            txtAreaMessage.setText("Payment successful");
        }
        }
        
        btnConfirmPayment.setEnabled(false);
    }
    
    public static void addPayment(Payment payment) throws Exception{
        Request request = new Request(Operation.PAYMENT_ADD, payment);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
    }
    
    public static void tableSetIsAvailable(Table table,boolean isAvailable) throws Exception{
        List<Object> arguments = new ArrayList<>();
        arguments.add(table);
        arguments.add(isAvailable);
        Request request = new Request(Operation.TABLE_SET_IS_AVAILABLE,arguments);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject(); 
    }

    private static void freeTable(Payment payment) {
        List<Order> orders = payment.getReceipt().getOrders();
        for (Order o : orders) {
            Table table = o.getTable();
            boolean isAvailable = true;
            try {
                tableSetIsAvailable(table, isAvailable);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    

}
