package restaurant.client.ui.form.Menu.controller;

import java.awt.Color;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.MenuItemTableModel;
import restaurant.common.domain.Menu;
import restaurant.common.domain.MenuItem;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuAddFormController {
    
    public static void prepareForm(JDialog menuAddForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        menuAddForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
    }
    public static void populateComboMenuItems(JComboBox comboMenuItems) {
        try {
            comboMenuItems.setModel(new DefaultComboBoxModel<>(getAllMenuItems().toArray()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void setTableModel(JTable tblMenuItems,List<MenuItem> menuItems){
        tblMenuItems.setModel(new MenuItemTableModel(menuItems));
    
}
    
    public static void btnSaveActionPerformed(JTextField txtError,JTextField txtAddName,List<MenuItem> menuItems){
                txtError.setText("");
        boolean isValid = true;
        String name = txtAddName.getText().trim();
        if (name.isEmpty()) {
            isValid = false;
            // JOptionPane.showMessageDialog(this, "Niste uneli naziv jelovnika!", "Error", JOptionPane.ERROR_MESSAGE);

        }
        Menu menu = new Menu(name);
        if (menuItems.isEmpty()) {
            isValid = false;
            // JOptionPane.showMessageDialog(this, "Niste uneli ni jedno jelo u jelovnik!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        menu.setMenuItems(menuItems);
        if (isValid) {
            try {
                addMenu(menu);

                txtError.setText("Jelovnik je uspesno kreiran!");
                JOptionPane.showMessageDialog(null, "Jelovnik je uspesno sacuvan!");

            } catch (Exception ex) {
                ex.printStackTrace();
                txtError.setForeground(Color.red);
                txtError.setText(ex.getMessage());
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Niste uneli sve trazene podatke!", "Error", JOptionPane.ERROR_MESSAGE);
            txtError.setForeground(Color.red);
                txtError.setText("Niste uneli sve trazene podatke!");
        }
    }
    
    public static Response addMenu(Menu menu) throws Exception{
        Request request = new Request(Operation.MENU_ADD, menu);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if(response.getException()==null){
            return null;
        }else{
            throw new Exception(response.getException().getMessage());
        }
    }
    
    public static void btnSelectActionPerformed(JComboBox comboMenuItems,List<MenuItem> menuItems,JTable tblMenuItems){
           MenuItem menuItem = (MenuItem) comboMenuItems.getSelectedItem();
        if (menuItems.contains(menuItem)) {
            JOptionPane.showMessageDialog(null, "Jelo je vec dodato u jelovnik!");
        } else {
            menuItems.add(menuItem);
        }

        setTableModel(tblMenuItems,menuItems);
    }
    
    public static void btnDeleteActionPerformed(JTable tblMenuItems,List<MenuItem> menuItems){
        int selectedRow = tblMenuItems.getSelectedRow();
        if (selectedRow != -1) {
            Integer id = Integer.parseInt(tblMenuItems.getValueAt(selectedRow, 0).toString());
            try {
                MenuItem menuItem = findById(id);
                menuItems.remove(menuItem);
                setTableModel(tblMenuItems,menuItems);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Niste izabrali red!");
        }
    }
    
    public static MenuItem findById(Integer id) throws Exception{
        Request request = new Request(Operation.MENU_ITEM_FIND_BY_ID,id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (MenuItem) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
    public static List<Menu> menuGetAll() throws Exception {
        Request request = new Request(Operation.MENU_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Menu>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
    public static List<MenuItem> getAllMenuItems() throws Exception{
        Request request = new Request(Operation.MENU_ITEM_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return  (List<MenuItem>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
}
