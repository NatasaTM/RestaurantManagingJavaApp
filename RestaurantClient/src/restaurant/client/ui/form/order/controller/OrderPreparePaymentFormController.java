package restaurant.client.ui.form.order.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.OrderItemListModel;
import restaurant.client.ui.component.table.model.OrderTableModel;
import restaurant.client.ui.form.order.OrderPreparePaymentForm;
import restaurant.client.ui.form.receipt.ReceiptForm;
import restaurant.client.ui.form.receipt.ReceiptUnpaiedForm;
import restaurant.common.domain.Employee;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;
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
public class OrderPreparePaymentFormController {

    public static void prepareView(JTextArea textAreaTables, OrderPreparePaymentForm orderPreparePaymentForm, JButton btnSubmit, JTable tblOrder, JButton btnAddAllOrders, JButton btnAddOrder, JTable tblPaymentOrder, JButton btnDelete, JButton btnDeleteAll) {
        User user = ApplicationSession.getInstance().getLoginUser();
        orderPreparePaymentForm.setTitle("Ulogovani ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
        btnSubmit.setEnabled(false);
        tblOrder.setModel(new OrderTableModel(new ArrayList<>()));
        btnAddAllOrders.setEnabled(false);
        btnAddOrder.setEnabled(false);
        tblPaymentOrder.setModel(new OrderTableModel(new ArrayList<>()));
        btnDelete.setEnabled(false);
        btnDeleteAll.setEnabled(false);
        Employee employee = user.getEmployee();
        orderPreparePaymentForm.setEmployee(employee);

    }

    public static void populateComboTables(JComboBox comboTable) {
        try {
            comboTable.setModel(new DefaultComboBoxModel<>(getAllTables().toArray()));
            comboTable.setSelectedIndex(-1);
        } catch (Exception e) {
        }
    }

    public static List<Table> getAllTables() throws Exception {
        Request request = new Request(Operation.TABLE_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Table>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void setListOrderItemsModel(OrderPreparePaymentForm orderPreparePaymentForm, JList jListOrderItems, Order order) {

        List<OrderItem> orderItems = order.getOrderItems();
        jListOrderItems.setModel(new OrderItemListModel(orderItems));
    }

    public static void setTableModel(OrderPreparePaymentForm orderPreparePaymentForm, JComboBox comboTable, JTable tblOrder) {

        try {

            List<Order> orders = getOrdersByCondition(orderPreparePaymentForm, comboTable);
            // List<Order> orders = findByCondition(null, true, false, orderPreparePaymentForm.getEmployee(), orderPreparePaymentForm.getTable());
            tblOrder.setModel(new OrderTableModel(orders));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Order> findByCondition(LocalDate date, Boolean statusReady, Boolean statusPaied, Employee employee, Table table) throws Exception {
        List<Object> arguments = new ArrayList<>();
        date = null;
        //statusReady = true;        
        arguments.add(date);
        arguments.add(statusReady);
        arguments.add(statusPaied);
        arguments.add(employee);
        arguments.add(table);
        Request request = new Request(Operation.ORDER_FIND_BY_CONDITION, arguments);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Order>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void btnSelectTableActionPerformed(JButton btnAddAllOrders, JButton btnAddOrder, OrderPreparePaymentForm orderPreparePaymentForm, JComboBox comboTable, JPanel pnlTableOrder, JTable tblOrder) {
        Table table = orderPreparePaymentForm.getTable();
        table = (Table) comboTable.getSelectedItem();
        pnlTableOrder.setVisible(true);
        setTableModel(orderPreparePaymentForm, comboTable, tblOrder);
        btnAddAllOrders.setEnabled(true);
        btnAddOrder.setEnabled(true);
    }

    public static void tblOrderMouseClicked(OrderPreparePaymentForm orderPreparePaymentForm, JTable tblOrder, JList jListOrderItems, JPanel pnlOrderItems) {
        int selectedRow = tblOrder.getSelectedRow();

        Long id = (Long) tblOrder.getValueAt(selectedRow, 0);
        try {

            Order order = getOrderById(id);
            pnlOrderItems.setVisible(true);
            setListOrderItemsModel(orderPreparePaymentForm, jListOrderItems, order);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static Order getOrderById(Long id) throws Exception {
        Request request = new Request(Operation.ORDER_FIND_BY_ID, id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (Order) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void btnDeleteActionPerformed(JTable tblPaymentOrder, JComboBox comboTable, JTable tblOrder, JTextField txtTotalAmount, OrderPreparePaymentForm orderPreparePaymentForm) {
        int selectedRow = tblPaymentOrder.getSelectedRow();
        DecimalFormat df = new DecimalFormat("0.00");
        List<Order> orders = orderPreparePaymentForm.getOrders();
        if (selectedRow != -1) {
            try {
                Long id = (Long) tblPaymentOrder.getValueAt(selectedRow, 0);
                Order order = getOrderById(id);

                orders.remove(order);

                setPaymentOrderTableModel(orders, tblPaymentOrder);
                BigDecimal total = calculateTotalPrice(orders, orderPreparePaymentForm);
                txtTotalAmount.setText(df.format(total));

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Niste izabrali red!");
        }
    }

    public static void setPaymentOrderTableModel(List<Order> orders, JTable tblPaymentOrder) {
        tblPaymentOrder.setModel(new OrderTableModel(orders));

    }

    public static void btnAddAllOrdersActionPerformed(JButton btnSubmit, JPanel pnlPaymentOrder, JTextField txtTotalAmount, JTable tblPaymentOrder, OrderPreparePaymentForm orderPreparePaymentForm, JComboBox comboTable, JButton btnDelete, JButton btnDeleteAll) {
        btnSubmit.setEnabled(true);
        btnDelete.setEnabled(true);
        btnDeleteAll.setEnabled(true);
        List<Order> orders = orderPreparePaymentForm.getOrders();
        DecimalFormat df = new DecimalFormat("0.00");
        orders.clear();
        try {
            List<Order> or = getOrdersByCondition(orderPreparePaymentForm, comboTable);
            for (Order o : or) {
                orders.add(o);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setPaymentOrderTableModel(orders, tblPaymentOrder);
        pnlPaymentOrder.setVisible(true);
        BigDecimal total = calculateTotalPrice(orders, orderPreparePaymentForm);
        txtTotalAmount.setText(df.format(total));

    }

    private static BigDecimal calculateTotalPrice(List<Order> orders, OrderPreparePaymentForm orderPreparePaymentForm) {

        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (Order o : orders) {
            totalPrice = totalPrice.add(o.getTotalAmount());
        }
        return totalPrice;
    }

    public static void btnAddOrderActionPerformed(JButton btnSubmit, JTable tblPaymentOrder, JPanel pnlPaymentOrder, JTextField txtTotalAmount, JTable tblOrder, OrderPreparePaymentForm orderPreparePaymentForm, JButton btnDelete, JButton btnDeleteAll) {
        btnSubmit.setEnabled(true);
        btnDelete.setEnabled(true);
        btnDeleteAll.setEnabled(true);
        int selectedRow = tblOrder.getSelectedRow();
        DecimalFormat df = new DecimalFormat("0.00");

        List<Order> orders = orderPreparePaymentForm.getOrders();

        if (selectedRow != -1) {
            Long id = (Long) tblOrder.getValueAt(selectedRow, 0);
            try {
                Order order = getOrderById(id);
                if (!orders.contains(order)) {
                    orders.add(order);
                } else {
                    JOptionPane.showMessageDialog(null, "Porudzbina je vec dodata!");
                }

                tblPaymentOrder.setModel(new OrderTableModel(orders));
                pnlPaymentOrder.setVisible(true);
                BigDecimal total = calculateTotalPrice(orders, orderPreparePaymentForm);
                txtTotalAmount.setText(df.format(total));

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Niste izabrali red");
        }
    }

    public static void btnDeleteAllActionPerformed(JTextField txtTotalAmount, JTable tblPaymentOrder, OrderPreparePaymentForm orderPreparePaymentForm) {

        List<Order> orders = orderPreparePaymentForm.getOrders();
        orders.clear();
        tblPaymentOrder.setModel(new OrderTableModel(orders));
        txtTotalAmount.setText("");

    }

    private static List<Order> getOrdersByCondition(OrderPreparePaymentForm orderPreparePaymentForm, JComboBox comboTable) throws Exception {
        User user = ApplicationSession.getInstance().getLoginUser();
        Employee employee = user.getEmployee();
        Table table = orderPreparePaymentForm.getTable();
        table = (Table) comboTable.getSelectedItem();
        Boolean isPaied = false;

        List<Order> orders = findByCondition(LocalDate.now(), true, isPaied, employee, table);
        return orders;
    }

    public static void btnSubmitActionPerformed(OrderPreparePaymentForm orderPreparePaymentForm) {
        boolean isValid = true;
        List<Order> orders = orderPreparePaymentForm.getOrders();
        if (orders == null || orders.isEmpty()) {
            isValid = false;
        }
        BigDecimal total = calculateTotalPrice(orders, orderPreparePaymentForm);
        Receipt receipt = new Receipt(LocalDateTime.now(), total, orders);
        if (total.compareTo(BigDecimal.valueOf(0)) <= 0) {
            JOptionPane.showMessageDialog(null, "Racun je prazan!!", "error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }

        if (isValid) {
            try {
                receipt = addReceipt(receipt);
                System.out.println("Racun broj: " + receipt.getId());
                orderPreparePaymentForm.dispose();
                new ReceiptForm(null, true, receipt).setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        }

    }

    public static Receipt addReceipt(Receipt receipt) throws Exception {
        Request request = new Request(Operation.RECEIPT_ADD, receipt);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (Receipt) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void setTextAreaTables(JTextArea textAreaTables, OrderPreparePaymentForm orderPreparePaymentForm, JButton btnSelectTable) {

        User user = ApplicationSession.getInstance().getLoginUser();
        try {
            List<Order> orders = findByCondition(null, null, false, user.getEmployee(), null);
            if (orders.isEmpty()) {
                textAreaTables.setText("Trenutno nemate nenaplacenih porudzbina!");
                btnSelectTable.setEnabled(false);
            } else {
                StringBuilder message = new StringBuilder();
                for (Order o : orders) {
                    String status = null;
                    if (o.isOrderReadyStatus()) {
                        status = "Spremna";
                    } else {
                        status = "U pripremi";
                    }
                    message.append("Sto: ").append(o.getTable().getTableNumber()).append(", Status porudzbine: ").append(status).append(", Iznos porudzbine: ").append(o.getTotalAmount()).append(".00 ").append("\n");
                }
                textAreaTables.setText(message.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static boolean areOrdersReady(List<Order> order) {
        boolean isReady = true;
        for (Order o : order) {
            if (!o.isOrderReadyStatus()) {
                isReady = false;
                break;
            }
        }
        return isReady;
    }
    
    public static void btnUnpaiedReceiptsActionPerformed(){
        User user = ApplicationSession.getInstance().getLoginUser();
        Employee employee = user.getEmployee();
        System.out.println("Employee: " + employee);
        try {
            List<Receipt> receipts = getAllUnpaiedReceiptsByEmployee(employee);
            System.out.println("Lista neplacenih");
            for (Receipt r : receipts) {
                System.out.println(r);
            }
            if(receipts==null || receipts.isEmpty()){
                JOptionPane.showMessageDialog(null, "Nemate nenaplacenih racuna!");
            }else{
                new ReceiptUnpaiedForm(null, true).setVisible(true);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        
    }
    
    public static List<Receipt> getAllUnpaiedReceiptsByEmployee(Employee employee) throws Exception {
        Request request = new Request(Operation.RECEIPT_FIND_UNPAIED_BY_EMPLOYEE, employee);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Receipt>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
}
