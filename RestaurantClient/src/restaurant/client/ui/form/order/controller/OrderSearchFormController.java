package restaurant.client.ui.form.order.controller;

import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.OrderItemListModel;
import restaurant.client.ui.component.table.model.OrderSearchTableModel;
import restaurant.client.ui.form.order.OrderSearchForm;
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
public class OrderSearchFormController {

    public static void prepareView(OrderSearchForm orderSearchForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        orderSearchForm.setTitle("Ulogovani ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());

        try {
            orderSearchForm.setOrders(getAllOrders());
        } catch (Exception ex) {
            Logger.getLogger(OrderSearchFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void populateComboTable(JComboBox comboTable) {
        try {
            comboTable.setModel(new DefaultComboBoxModel<>(getALlTables().toArray()));
            String allTables = "Svi stolovi";
            comboTable.insertItemAt(allTables, 0);
            comboTable.setSelectedIndex(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Table> getALlTables() throws Exception {
        Request request = new Request(Operation.TABLE_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Table>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void populateComboEmployee(JComboBox comboEmployee) {
        try {
            comboEmployee.setModel(new DefaultComboBoxModel<>(getALlEmployee().toArray()));
            String allEmployees = "Svi zaposleni";
            comboEmployee.insertItemAt(allEmployees, 0);
            comboEmployee.setSelectedIndex(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Employee> getALlEmployee() throws Exception {
        Request request = new Request(Operation.EMPLOYEE_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Employee>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void setListModel(List<OrderItem> orderItems, JList listOrderItems) {

        listOrderItems.setModel(new OrderItemListModel(orderItems));
    }

    public static void setTableModel(List<Order> orders, JTable tblOrders) {

        try {

            tblOrders.setModel(new OrderSearchTableModel(orders));

            TableColumnModel columnModel = tblOrders.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                TableColumn column = columnModel.getColumn(i);

                column.setMinWidth(2);
            }

            tblOrders.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblOrders.getColumnModel().getColumn(2).setPreferredWidth(5);
            tblOrders.getColumnModel().getColumn(3).setPreferredWidth(5);
            tblOrders.getColumnModel().getColumn(4).setPreferredWidth(10);
            tblOrders.getColumnModel().getColumn(5).setPreferredWidth(50);
            tblOrders.getColumnModel().getColumn(6).setPreferredWidth(5);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void btnDateSelectActionPerformed(List<Order> orders, JTable tblOrders, OrderSearchForm orderSearchForm, JDateChooser dateChooser, JRadioButton rbtnNonPaied, JRadioButton rbtnPaied, JComboBox comboEmployee) {
        if (dateChooser.getDate() != null) {
            Date d = dateChooser.getDate();
            LocalDate date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            orderSearchForm.setDate(date);
            try {
                orders = findByCondition(date, orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable());
                setTableModel(orders, tblOrders);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            orderSearchForm.setDate(null);
            try {
                orders = findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable());
                setTableModel(orders, tblOrders);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public static List<Order> findByCondition(LocalDate date, Boolean statusReady, Boolean statusPaied, Employee employee, Table table) throws Exception {
        List<Object> arguments = new ArrayList<>();
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

    public static void btnClearAllActionPerformed(JTable tblOrders, OrderSearchForm orderSearchForm, JComboBox comboEmployee, JComboBox comboTable, ButtonGroup groupPaied, ButtonGroup groupReady, JDateChooser dateChooser) {
        comboEmployee.setSelectedIndex(0);
        comboTable.setSelectedIndex(0);
        groupPaied.clearSelection();
        groupReady.clearSelection();
        dateChooser.setDate(null);
        orderSearchForm.setDate(null);
        orderSearchForm.setEmployee(null);
        orderSearchForm.setTable(null);
        orderSearchForm.setStatusPaied(null);
        orderSearchForm.setStatusReady(null);
        try {

            orderSearchForm.setOrders(getAllOrders());
            setTableModel(orderSearchForm.getOrders(), tblOrders);
            //setTableModel(orders);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Order> getAllOrders() throws Exception {
        Request request = new Request(Operation.ORDER_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Order>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void btnReadyClearActionPerformed(OrderSearchForm orderSearchForm, ButtonGroup groupReady, JTable tblOrders) {
        orderSearchForm.setStatusReady(null);
        groupReady.clearSelection();
        try {
            orderSearchForm.setOrders(findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable()));
            setTableModel(orderSearchForm.getOrders(), tblOrders);
            // setTableModel(orders);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void btnReadySelectActionPerformed(JRadioButton rbtnReady, OrderSearchForm orderSearchForm, JTable tblOrders, JRadioButton rbtnNonReady) {
        if (rbtnReady.isSelected()) {
            orderSearchForm.setStatusReady(true);
            try {
                orderSearchForm.setOrders(findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable()));
                setTableModel(orderSearchForm.getOrders(), tblOrders);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (rbtnNonReady.isSelected()) {
            orderSearchForm.setStatusReady(false);
            try {
                orderSearchForm.setOrders(findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable()));
                setTableModel(orderSearchForm.getOrders(), tblOrders);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void btnPaiedClearActionPerformed(OrderSearchForm orderSearchForm, JRadioButton rbtnPaied, JRadioButton rbtnNonPaied, ButtonGroup groupPaied, JTable tblOrders) {
        orderSearchForm.setStatusPaied(null);
        rbtnPaied.setSelected(false);
        rbtnNonPaied.setSelected(false);
        groupPaied.clearSelection();
        try {
            orderSearchForm.setOrders(findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable()));
            setTableModel(orderSearchForm.getOrders(), tblOrders);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void btnPaiedSelectActionPerformed(OrderSearchForm orderSearchForm, JRadioButton rbtnPaied, JTable tblOrders, JRadioButton rbtnNonPaied) {
        if (rbtnPaied.isSelected()) {
            orderSearchForm.setStatusPaied(true);
            try {
                orderSearchForm.setOrders(findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable()));
                setTableModel(orderSearchForm.getOrders(), tblOrders);

            } catch (Exception ex) {
                Logger.getLogger(OrderSearchForm.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (rbtnNonPaied.isSelected()) {
            orderSearchForm.setStatusPaied(false);

            try {
                orderSearchForm.setOrders(findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable()));
                setTableModel(orderSearchForm.getOrders(), tblOrders);

            } catch (Exception ex) {
                Logger.getLogger(OrderSearchForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void btnSelectEmployeeActionPerformed(OrderSearchForm orderSearchForm, JComboBox comboEmployee, JTable tblOrders) {
        if (comboEmployee.getSelectedItem().equals("Svi zaposleni")) {
            orderSearchForm.setEmployee(null);

            try {
                orderSearchForm.setOrders(findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable()));
                setTableModel(orderSearchForm.getOrders(), tblOrders);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            Employee e = (Employee) comboEmployee.getSelectedItem();
            orderSearchForm.setEmployee(e);

            try {
                orderSearchForm.setOrders(findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable()));
                setTableModel(orderSearchForm.getOrders(), tblOrders);

            } catch (Exception ex) {
                Logger.getLogger(OrderSearchForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void btnSelectTableActionPerformed(OrderSearchForm orderSearchForm, JComboBox comboTable, JTable tblOrders) {
        if (comboTable.getSelectedItem().equals("Svi stolovi")) {
            orderSearchForm.setTable(null);
            try {
                orderSearchForm.setOrders(findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable()));
                setTableModel(orderSearchForm.getOrders(), tblOrders);

            } catch (Exception ex) {
                Logger.getLogger(OrderSearchForm.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Table table = (Table) comboTable.getSelectedItem();
            orderSearchForm.setTable(table);
            try {
                orderSearchForm.setOrders(findByCondition(orderSearchForm.getDate(), orderSearchForm.getStatusReady(), orderSearchForm.getStatusPaied(), orderSearchForm.getEmployee(), orderSearchForm.getTable()));
                setTableModel(orderSearchForm.getOrders(), tblOrders);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void tblOrdersMouseClicked(JTable tblOrders, JList listOrderItems) {

        int selectecRow = tblOrders.getSelectedRow();
        if (selectecRow != -1) {
            Long id = (Long) tblOrders.getValueAt(selectecRow, 0);
            try {
                Order order = findOrderById(id);
                List<OrderItem> orderItems = order.getOrderItems();

                setListModel(orderItems, listOrderItems);

            } catch (Exception ex) {
                Logger.getLogger(OrderSearchForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static Order findOrderById(Long id) throws Exception {
        Request request = new Request(Operation.ORDER_FIND_BY_ID, id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (Order) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static List<Order> setOrderList() {
        List<Order> orders = null;
        try {
            orders = getAllOrders();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return orders;
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

}
