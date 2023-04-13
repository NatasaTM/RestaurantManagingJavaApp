package restaurant.client.ui.form.menuItem.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.MenuItemTableModel;
import restaurant.client.ui.form.menuItem.MenuItemAddForm;
import restaurant.client.ui.form.menuItem.MenuItemDeleteUpdateForm;
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
public class MenuItemSearchFormController {
    
    public static void prepareView(JDialog menuItemAddForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        menuItemAddForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
    }
    
    public static void fillComboType(JComboBox comboMenuItemType) {
        comboMenuItemType.setModel(new DefaultComboBoxModel<>(MenuItemType.values()));
        comboMenuItemType.setSelectedIndex(-1);
    }
    
    public static void populateComboCategory(JComboBox comboCategory) {

        try {
            List<MenuCategory> menuCategories = getAllMenuCategory();
            comboCategory.setModel(new DefaultComboBoxModel<>(menuCategories.toArray()));
            String all = "Sve kategorije";
        comboCategory.insertItemAt(all, 0);
        comboCategory.setSelectedIndex(0);
        } catch (Exception ex) {
            Logger.getLogger(MenuItemAddForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboCategory.setSelectedIndex(-1);
    }
    
    public static void setTableModel(JTable tblMenuItems){
        try {
            List<MenuItem> menuItems = getAllMenuItems();
            tblMenuItems.setModel(new MenuItemTableModel(menuItems));
        } catch (Exception ex) {
            ex.printStackTrace();
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
    
    public static void btnSelectCategoryActionPerformed(JComboBox comboCategory,JTable tblMenuItems){
         try {
            if (!comboCategory.getSelectedItem().toString().equals("Sve kategorije")) {
                String categoryName = comboCategory.getSelectedItem().toString();
                
                List<MenuItem> menuItems = getAllMenuItemsByCategory(categoryName);
                tblMenuItems.setModel(new MenuItemTableModel(menuItems));

            } else {
                setTableModel(tblMenuItems);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static List<MenuItem> getAllMenuItemsByCategory(String categoryName) throws Exception{
       Request request = new Request(Operation.MENU_ITEM_GET_ALL_BY_CATEGORY,categoryName);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return  (List<MenuItem>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        } 
    }
    
    public static void btnSelectTypeActionPerformed(JComboBox comboMenuItemType,JTable tblMenuItems){
        try {
            int index = comboMenuItemType.getSelectedIndex();
            if (index!=-1) {
                MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, comboMenuItemType.getSelectedItem().toString());
                
                List<MenuItem> menuItems = getAllMenuItemsByType(menuItemType);
                tblMenuItems.setModel(new MenuItemTableModel(menuItems));

            } else {
                setTableModel(tblMenuItems);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static List<MenuItem> getAllMenuItemsByType(MenuItemType menuItemType) throws Exception{
        Request request = new Request(Operation.MENU_ITEM_GET_ALL_BY_TYPE,menuItemType);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return  (List<MenuItem>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        } 
    }
    
    public static void btnDetailsActionPerformed(JTable tblMenuItems,JComboBox comboMenuItemType){
        int selectedRow=tblMenuItems.getSelectedRow();
        try {
            if(selectedRow!=-1){
            Integer id = Integer.parseInt(tblMenuItems.getValueAt(selectedRow, 0).toString());
            String name = tblMenuItems.getValueAt(selectedRow, 1).toString();
            String description = tblMenuItems.getValueAt(selectedRow, 2).toString();
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(tblMenuItems.getValueAt(selectedRow, 3).toString()));
            String categoryName = tblMenuItems.getValueAt(selectedRow, 4).toString();
            MenuCategory menuCategory = new MenuCategory(null, categoryName);
            MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, tblMenuItems.getValueAt(selectedRow, 5).toString());
            MenuItem menuItem = new MenuItem(id, name, description, price, menuCategory,menuItemType);
             new MenuItemDeleteUpdateForm(null, true,menuItem).setVisible(true);
            
            }else{
               JOptionPane.showMessageDialog(null, "Niste izabrali jelo!"); 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            
           
        }
    }
    
    
    
}
