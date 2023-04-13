package restaurant.client.ui.form.menuItem.controller;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.form.menuItem.MenuItemAddForm;
import static restaurant.client.ui.form.menuItem.controller.MenuItemAddFormController.getAllMenuCategory;
import restaurant.common.domain.MenuCategory;
import restaurant.common.domain.MenuItem;
import restaurant.common.domain.MenuItemType;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuItemDeleteUpdateFormController {
    
    public static void populateComboMenuItemType(JComboBox comboMenuItemType) {
        comboMenuItemType.setModel(new DefaultComboBoxModel<>(MenuItemType.values()));
    }
    
    public static void populateComboCategory(JComboBox comboCategory) {
        
        try {
            List<MenuCategory> menuCategories = getAllMenuCategory();
            comboCategory.setModel(new DefaultComboBoxModel<>(menuCategories.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MenuItemAddForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboCategory.setSelectedIndex(-1);
    }
    
    public static void prepareForm(JTextField txtId, JTextField txtName, JTextField txtDescription, JTextField txtPrice, JComboBox comboCategory, JDialog menuItemDUForm, MenuItem menuItem, JComboBox comboMenuItemType) {
        txtId.setText(menuItem.getId().toString());
        txtName.setText(menuItem.getName());
        txtDescription.setText(menuItem.getDescription());
        txtPrice.setText(menuItem.getPrice().toString());
        comboCategory.setSelectedItem(menuItem.getMenuCategory());
        comboMenuItemType.setSelectedItem(menuItem.getMenuItemType());
        User user = ApplicationSession.getInstance().getLoginUser();        
        menuItemDUForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
        
    }
    
    public static void btnUpdateActionPerformed(JTextField txtError, JTextField txtId, JTextField txtName, JTextField txtDescription, JTextField txtPrice, JComboBox comboCategory, JComboBox comboMenuItemType) {
        txtError.setText("");
        String error = "";
        
        try {
            boolean isValid = true;
            
            Integer id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            String description = txtDescription.getText().trim();
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(txtPrice.getText().trim()));
            if (name.isEmpty()) {
                error = error + "Morate uneti naziv jela! ";
                txtError.setText(error);
                isValid = false;
            }
            if (description.isEmpty()) {
                error = error + "\nMorate uneti opis jela";
                txtError.setText(error);
                isValid = false;                
            }
            if (price == null) {
                error = error + "\nMorate uneti cenu jela";
                txtError.setText(error);
                isValid = false;                
            }
            if (isValid) {
                MenuCategory menuCategory = (MenuCategory) comboCategory.getSelectedItem();
                MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, comboMenuItemType.getSelectedItem().toString());
                MenuItem menuItem = new MenuItem(id, name, description, price, menuCategory, menuItemType);
                updateMenuItem(menuItem);
                txtError.setForeground(Color.black);
                txtError.setText("Jelo je uspesno izmenjeno.");
                
            }
            
        } catch (NumberFormatException ne) {
            txtError.setText("Morate uneti sve podatke" + ne.getMessage());
            ne.printStackTrace();
        } catch (Exception e) {
            txtError.setText(error);
            e.printStackTrace();
        }
        
    }
    
    public static void updateMenuItem(MenuItem menuItem) throws Exception {
        Request request = new Request(Operation.MENU_ITEM_UPDATE, menuItem);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
    }
    
    public static void btnDeleteActionPerformed(JTextField txtError, JTextField txtId, JTextField txtName, JTextField txtDescription, JTextField txtPrice, JComboBox comboCategory, MenuItem menuItem) {
        txtError.setText("");
        
        try {
            deleteMenuItem(menuItem);
            txtError.setForeground(Color.black);
            txtError.setText("Jelo je uspesno obrisano.");
            txtId.setText("");
            txtName.setText("");
            txtDescription.setText("");
            txtPrice.setText("");
            comboCategory.setSelectedIndex(-1);
        } catch (Exception ex) {
            txtError.setText(ex.getMessage());
        }
    }
    
    public static void deleteMenuItem(MenuItem menuItem) throws Exception {
        Request request = new Request(Operation.MENU_ITEM_DELETE, menuItem);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
    }
    
}
