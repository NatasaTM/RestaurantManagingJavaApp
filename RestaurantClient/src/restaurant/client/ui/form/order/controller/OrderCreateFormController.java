package restaurant.client.ui.form.order.controller;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.MenuItemTableModel;
import restaurant.client.ui.component.table.model.OrderItemsTableModel;
import restaurant.client.ui.form.order.OrderCreateForm;
import restaurant.common.domain.Employee;
import restaurant.common.domain.Menu;
import restaurant.common.domain.MenuCategory;
import restaurant.common.domain.MenuItem;
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
public class OrderCreateFormController {

    public static void prepareView(OrderCreateForm orderCreateForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        orderCreateForm.setTitle("Ulogovani ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
        
    }

    public static void populateComboAllTables(JComboBox comboAllTables) {
        try {
            comboAllTables.setModel(new DefaultComboBoxModel<>(getAllTables().toArray()));
            comboAllTables.setSelectedIndex(-1);
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
    
    public static void populateComboFreeTables(JComboBox comboFreeTables) {
        try {
            List<Table> tables = tableIsAvailable();
            if (tables.isEmpty()) {
                comboFreeTables.removeAllItems();
                comboFreeTables.addItem("Trenutno nema slobodnih stolova");
            } else {
                comboFreeTables.setModel(new DefaultComboBoxModel<>(tables.toArray()));
                comboFreeTables.setSelectedIndex(-1);
            }

        } catch (Exception e) {
        }
    }
    
    public static List<Table> tableIsAvailable() throws Exception{
        Request request = new Request(Operation.TABLE_IS_AVAILABLE);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Table>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
    
    private static BigDecimal calculateTotalAmount(OrderItemsTableModel orderItemsTableModel) {

        List<OrderItem> orderItems = orderItemsTableModel.getOrderitems();
        BigDecimal total = BigDecimal.valueOf(0);
        for (OrderItem o : orderItems) {
            total = total.add(o.getMenuItem().getPrice().multiply(BigDecimal.valueOf((double) o.getQuantity())));

        }
        return total;
    }
    
    public static void setOrderTableModel(JTable tblOrder,OrderItemsTableModel orderItemsTableModel) {
        //OrderItemsTableModel otm = new OrderItemsTableModel();
        tblOrder.setModel(orderItemsTableModel);

        //tblOrder.setModel(OrderItemsTableModel.getInstance());
    }
    
    public static void populateComboCategory(JComboBox comboCategory) {
        try {
            comboCategory.setModel(new DefaultComboBoxModel<>(getAllMenuCategory().toArray()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String all = "Sve kategorije";
        comboCategory.insertItemAt(all, 0);
        comboCategory.setSelectedIndex(0);
    }
    
    public static List<MenuCategory> getAllMenuCategory() throws Exception{
        Request request = new Request(Operation.MENU_CATEGORY_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<MenuCategory>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
    
    public static void populateComboMenu(JComboBox comboMenu,OrderCreateForm orderCreateForm) {
        try {
            List<Menu> menus = getAllMenuByStatus();
            comboMenu.setModel(new DefaultComboBoxModel<>(getAllMenuByStatus().toArray()));
            comboMenu.setSelectedItem(menus.get(0));
            orderCreateForm.setMenu((Menu) comboMenu.getSelectedItem());
            

        } catch (Exception e) {
        }
    }
    
    public static List<Menu> getAllMenuByStatus() throws Exception{
       Request request = new Request(Operation.MENU_GET_ALL_BY_STATUS);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return  (List<Menu>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        } 
    }
    
    public static void setMenuTableModel(JTable tblMenu,Menu menu) {
        
        try {
            List<MenuItem> menuItems = findMenuById(menu.getId()).getMenuItems();
            tblMenu.setModel(new MenuItemTableModel(menuItems));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Menu findMenuById(Integer id) throws Exception{
        Request request = new Request(Operation.MENU_FIND_BY_ID,id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return   (Menu) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        } 
    }
    
    public static void btnSelectTableActionPerformed(OrderCreateForm orderCreateForm,JTextArea txtAreaSelectedTable,JComboBox comboAllTables) {                                               
        if (comboAllTables.getSelectedIndex() > -1) {
           Table table = (Table) comboAllTables.getSelectedItem();
           orderCreateForm.setTable(table);
            
            txtAreaSelectedTable.setForeground(Color.black);
            txtAreaSelectedTable.setText("Izabrani sto:\n" + table);
            JOptionPane.showMessageDialog(orderCreateForm, "Sto je uspesno odabran.");
        } 
    }
    
    public static void btnSelectFreeTableActionPerformed(JComboBox comboFreeTables,JTextArea txtAreaSelectedTable,OrderCreateForm orderCreateForm) {                                                   
        if (comboFreeTables.getSelectedIndex() > -1) {
           Table table = (Table) comboFreeTables.getSelectedItem();
           orderCreateForm.setTable(table);
            
            txtAreaSelectedTable.setForeground(Color.black);
            txtAreaSelectedTable.setText("Izabrani sto:\n" + table);
            JOptionPane.showMessageDialog(orderCreateForm, "Sto je uspesno odabran.");
        } 


    } 
    
    public static void btnSaveActionPerformed( OrderCreateForm orderCreateForm,JTextArea txtAreaSelectedTable,OrderItemsTableModel orderItemsTableModel,JTextField txtTotalAmount){
        boolean isValid = true;
        Table table = orderCreateForm.getTable();
        try {

            List<OrderItem> orderItems = orderItemsTableModel.getOrderitems();

            Employee employee = ApplicationSession.getInstance().getLoginUser().getEmployee();
            BigDecimal totalAmount = BigDecimal.valueOf(Double.parseDouble(txtTotalAmount.getText().toString()));
            if (table == null) {
                isValid = false;
                
                txtAreaSelectedTable.setForeground(Color.red);
                txtAreaSelectedTable.setText("Sto nije izabran");
                JOptionPane.showMessageDialog(orderCreateForm, "Niste odabrali sto", "error", JOptionPane.ERROR_MESSAGE);
            }
            if (isValid) {
                Order order = new Order(totalAmount, orderItems, employee,table);
                addOrder(order);
                boolean isAvailable = false;
                
                tableSetIsAvailable(table, isAvailable);
                
                JOptionPane.showMessageDialog(orderCreateForm, "Porudzbina je uspesno sacuvana");
               
                orderCreateForm.dispose();
                System.out.println(orderItems);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(orderCreateForm, "Ne mozete sacuvati praznu porudzbinu", "error", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(orderCreateForm, e.getMessage());
            isValid = false;
        }
    }
    
    public static void updateTable(Table table) throws Exception{
       Request request = new Request(Operation.TABLE_UPDATE,table);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject(); 
    }
    
    public static void addOrder(Order order) throws Exception{
       Request request = new Request(Operation.ORDER_ADD,order);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();  
    }
    
    public static void btnDeleteActionPerformed(JDialog orderCreateForm,OrderItemsTableModel orderItemsTableModel,JTextField txtTotalAmount,JTable tblOrder){
        int selectedRow = tblOrder.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Integer id = Integer.parseInt(tblOrder.getValueAt(selectedRow, 0).toString());
                MenuItem menuItem = findMenuItemById(id);
                int quantity = Integer.parseInt(tblOrder.getValueAt(selectedRow, 3).toString());
                OrderItem orderItem = new OrderItem(menuItem, quantity);
                BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(tblOrder.getValueAt(selectedRow, 4).toString()));

//                OrderItemsTableModel.getInstance().removeOrderItem(orderItem);
                orderItemsTableModel.removeOrderItem(orderItem);
                setOrderTableModel(tblOrder, orderItemsTableModel);
                //setOrderTableModel();

                BigDecimal totalAmount = BigDecimal.valueOf(Double.parseDouble(txtTotalAmount.getText())).subtract(amount);
                txtTotalAmount.setText(totalAmount.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(orderCreateForm, "Niste izabrali red!");
        }
        
        
    }
    
    public static MenuItem findMenuItemById(Integer id) throws Exception{
            Request request = new Request(Operation.MENU_ITEM_FIND_BY_ID,id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return    (MenuItem) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
        }
    
    public static void btnSelectMenuItemActionPerformed(JDialog orderCreateForm,JTextField txtTotalAmount,JTable tblMenu,OrderItemsTableModel orderItemsTableModel,JTable tblOrder){
        int selectedRow = tblMenu.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Integer id = Integer.parseInt(tblMenu.getValueAt(selectedRow, 0).toString());
                MenuItem menuItem = findMenuItemById(id);
                String kol = JOptionPane.showInputDialog(orderCreateForm, "Unesite kolicinu za izabrano jelo: " + menuItem.getName());

                int quantity = Integer.parseInt(kol);
                OrderItem orderItem = new OrderItem(menuItem, quantity);

                orderItemsTableModel.addOrderItem(orderItem);
                setOrderTableModel(tblOrder, orderItemsTableModel);
                

                BigDecimal totalAmount = calculateTotalAmount( orderItemsTableModel);
                DecimalFormat df = new DecimalFormat("0.00");
                txtTotalAmount.setText(df.format(totalAmount).toString());

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                
            } catch (Exception e) {
            }

        } else {
            JOptionPane.showMessageDialog(orderCreateForm, "Niste izabrali red!");
        }
    }
    
    public static void btnSelectMenuActionPerformed(JComboBox comboMenu,JTable tblMenu,OrderCreateForm orderCreateForm){
        
        Menu menu = (Menu) comboMenu.getSelectedItem();
        orderCreateForm.setMenu(menu);
        System.out.println(menu);
        setMenuTableModel(tblMenu, menu);
        //setMenuTableModel();
       
    }
    
    public static void btnSelectCategoryActionPerformed(JComboBox comboCategory,JTable tblMenu,OrderCreateForm orderCreateForm){
       Menu menu = orderCreateForm.getMenu();
        try {
            if (!comboCategory.getSelectedItem().toString().equals("Sve kategorije")) {
                String categoryName = comboCategory.getSelectedItem().toString();

                List<MenuItem> menuItems = getMenuItemFromMenuByCategory(categoryName, menu);
                tblMenu.setModel(new MenuItemTableModel(menuItems));

            } else {
                setMenuTableModel(tblMenu, menu);
                //setMenuTableModel();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /*
    try {
            if (!comboCategory.getSelectedItem().toString().equals("Sve kategorije")) {
                String categoryName = comboCategory.getSelectedItem().toString();

                List<MenuItem> menuItems = getMenuItemFromMenuByCategory(categoryName, menu);
                tblMenuItems.setModel(new MenuItemTableModel(menuItems));

            } else {
                setTableModel(comboMenu, tblMenuItems);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    */
    
    public static List<MenuItem> getMenuItemFromMenuByCategory(String categoryName,Menu menu) throws Exception{
           List<Object> arguments = new ArrayList<>();
           arguments.add(categoryName);
           arguments.add(menu);
           Request request = new Request(Operation.MENU_GET_MENU_ITEMS_BY_CATEGORY,arguments);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return  (List<MenuItem>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        } 
       }
    
    public static void tableSetIsAvailable(Table table,boolean isAvailable) throws Exception{
        List<Object> arguments = new ArrayList<>();
        arguments.add(table);
        arguments.add(isAvailable);
        Request request = new Request(Operation.TABLE_SET_IS_AVAILABLE,arguments);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject(); 
    }
    
}
