package restaurant.client.ui.form.order.controller;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.OrderChefTableModel;
import restaurant.client.ui.component.table.model.OrderItemChefTableModel;
import restaurant.client.ui.form.order.OrderBartenderForm;
import restaurant.client.ui.form.order.controller.renderer.CustomTableCellRenderer;
import restaurant.common.domain.Employee;
import restaurant.common.domain.MenuItemType;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;
import restaurant.common.domain.Table;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderBartenderFormController {

    public static void prepareView(OrderBartenderForm orderBartenderForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        orderBartenderForm.setTitle("BAR - Ulogovani ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
        JTable table = orderBartenderForm.getTblOrderItems();
        table.setModel(new OrderItemChefTableModel(new ArrayList<OrderItem>(), null));
    }

    public static void setTableModel(List<Order> orders, JTable tblOrders) {

        try {

            List<Order> filteredOrders = new ArrayList<>();
            orders = findByCondition(LocalDate.now(), false, false, null, null);

            for (int i = 0; i < orders.size(); i++) {
                List<OrderItem> orderItems = orders.get(i).getOrderItems();
                List<OrderItem> temp = new ArrayList<>();
                for (int j = 0; j < orderItems.size(); j++) {
                    if (orderItems.get(j).getMenuItem().getMenuItemType() == MenuItemType.DRINKS && !orderItems.get(j).getIsReady()) {
                        temp.add(orderItems.get(j));
                    }
                }
                orders.get(i).setOrderItems(temp);
                if (!temp.isEmpty()) {
                    filteredOrders.add(orders.get(i));
                }
            }

            tblOrders.setModel(new OrderChefTableModel(filteredOrders));

            TableColumnModel columnModel = tblOrders.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                TableColumn column = columnModel.getColumn(i);

                column.setMinWidth(2);
            }

            tblOrders.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblOrders.getColumnModel().getColumn(2).setPreferredWidth(5);
            tblOrders.getColumnModel().getColumn(3).setPreferredWidth(5);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static List<Order> findByCondition(LocalDate date, Boolean statusReady, Boolean statusPaied, Employee employee, Table table) throws Exception {
        List<Object> arguments = new ArrayList<>();
        date = null;
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

    public static void tblOrderMouseClicked(OrderBartenderForm orderBartenderForm) throws Exception {

        JTable tblOrder = orderBartenderForm.getTblOrder();
        int selectedRow = orderBartenderForm.getSelectedRowIndex();
        CustomTableCellRenderer renderer = new CustomTableCellRenderer(selectedRow);
        tblOrder.setDefaultRenderer(Object.class, renderer);

        // Order order = orderChefsForm.getOrder();
        selectedRow = tblOrder.getSelectedRow();

        if (selectedRow != -1) {
            Order order = null;

            Long id = (Long) tblOrder.getValueAt(selectedRow, 0);
            order = getOrderById(id);
            orderBartenderForm.setOrder(order);
            orderBartenderForm.getPnlOrderItems().setVisible(true);
            setOrderItemTableModel(order, orderBartenderForm);
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

    public static void setOrderItemTableModel(Order order, OrderBartenderForm orderBartenderForm) {
        JTable table = orderBartenderForm.getTblOrderItems();
        List<OrderItem> filteredOrderItems = order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getMenuItem().getMenuItemType() == MenuItemType.DRINKS)
                .collect(Collectors.toList());
        table.setModel(new OrderItemChefTableModel(filteredOrderItems, order));

    }

    public static Response updateOrder(Order order) throws Exception {
        Request request = new Request(Operation.ORDER_UPDATE, order);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void btnUpdateActionPerformed(OrderBartenderForm orderBartenderForm) {
        Order order = orderBartenderForm.getOrder();

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem o : orderItems) {
            try {

                updateOrderItem(o);
                JTable table = orderBartenderForm.getTblOrderItems();
                table.setModel(new OrderItemChefTableModel(new ArrayList<OrderItem>(), null));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        order.setOrderReadyStatus(true);
        int result = JOptionPane.showConfirmDialog(orderBartenderForm, "Oznaci porudzbinu kao spremnu", "porudzbina broj: " + order.getOrderId(), JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                boolean isReady = true;
                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getIsReady() == false) {
                        isReady = false;
                    }
                }
                if (isReady) {
                    order.setOrderReadyStatus(true);
                }
                updateOrder(order);
                //proba
                orderBartenderForm.getTblOrder().setSelectionBackground(Color.WHITE);

                orderBartenderForm.setOrder(null);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        
    }

    public static List<OrderItem> findOrderItemsByQuery(Order order, MenuItemType menuItemType, boolean isReady) throws Exception {
        List<Object> arguments = new ArrayList<>();
        arguments.add(order);
        arguments.add(menuItemType);
        arguments.add(isReady);

        Request request = new Request(Operation.ORDERITEM_FIND_BY_QUERY, arguments);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<OrderItem>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void tblOrderItemsMouseClicked(OrderBartenderForm orderBartenderForm) {
        JTable table = orderBartenderForm.getTblOrderItems();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Boolean isReady = (Boolean) table.getValueAt(selectedRow, 5);
            System.out.println("Gotova: " + isReady);
            Integer orderItemId = (Integer) table.getValueAt(selectedRow, 1);
            try {
                OrderItem orderItem = findOrderItemById(orderItemId);
                System.out.println("Stavka: " + orderItem + orderItem.getIsReady());
                orderItem.setIsReady(isReady);
                System.out.println("Stavka: " + orderItem + orderItem.getIsReady());
                // updateOrderItem(orderItem);
            } catch (Exception ex) {
                Logger.getLogger(OrderChefsFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static OrderItem findOrderItemById(Integer id) throws Exception {
        Request request = new Request(Operation.ORDERITEM_FIND_BY_ID, id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (OrderItem) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static Response updateOrderItem(OrderItem orderItem) throws Exception {
        Request request = new Request(Operation.ORDERITEM_UPDATE, orderItem);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
}
