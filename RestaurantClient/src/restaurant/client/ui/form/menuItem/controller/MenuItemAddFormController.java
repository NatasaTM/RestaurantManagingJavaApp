package restaurant.client.ui.form.menuItem.controller;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.form.menuItem.MenuItemAddForm;
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
public class MenuItemAddFormController {

    public static void populateComboCategory(JComboBox comboCategory) {

        try {
            List<MenuCategory> menuCategories = getAllMenuCategory();
            comboCategory.setModel(new DefaultComboBoxModel<>(menuCategories.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MenuItemAddForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboCategory.setSelectedIndex(-1);
    }

    public static void save(JTextField txtName, JComboBox comboCategory, JTextField txtDescription, JComboBox comboMenuItemType, JTextArea txtError, JTextField txtPrice) {

        String error = "";
        boolean isValid = true;

        try {

            String name = txtName.getText().trim();
            String menuCategoryName = comboCategory.getSelectedItem().toString();
            String description = txtDescription.getText().trim();
            MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, comboMenuItemType.getSelectedItem().toString());

            BigDecimal price = new BigDecimal(txtPrice.getText().trim());

            if (name.isEmpty()) {
                error = error + "Morate uneti naziv jela";
                isValid = false;
                txtError.setText(error);

            }
            if (description.isEmpty()) {
                error = error + "\nMorate uneti opis jela";
                isValid = false;
                txtError.setText(error);
            }

            if (menuCategoryName.isEmpty()) {
                error = error + "\nMorate izabrati kategoriju";
                isValid = false;
                txtError.setText(error);
            }

            if (isValid) {
                txtError.setText("");
                MenuCategory menuCategory = new MenuCategory(null, menuCategoryName);
                MenuItem menuItem = new MenuItem(null, name, description, price, menuCategory, menuItemType);
                addMenuItem(menuItem);
                txtError.setForeground(Color.BLACK);
                txtError.setText("Proizvod je uspesno sacuvan");
                txtName.setText("");
                txtDescription.setText("");
                txtPrice.setText("");
                comboCategory.setSelectedIndex(-1);

            } else {
                txtError.setText(error);
            }

        } catch (NullPointerException p) {
            error = error + "Morate uneti ispravne podatke";
            txtError.setText(error);

        } catch (NumberFormatException e) {
            error = error + "Morate uneti ispravne podatke";
            txtError.setText(error);
        } catch (Exception e) {
            error = error + "\n" + e.getMessage();
            txtError.setText(error);
        }

    }

    public static void prepareView(JDialog menuItemAddForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        menuItemAddForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
    }

    public static void populateComboMenuItemType(JComboBox comboMenuItemType) {
        comboMenuItemType.setModel(new DefaultComboBoxModel<>(MenuItemType.values()));
    }

    public static List<MenuCategory> getAllMenuCategory() throws Exception {
        Request request = new Request(Operation.MENU_CATEGORY_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<MenuCategory>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static Response addMenuItem(MenuItem menuItem) throws Exception {
        Request request = new Request(Operation.MENU_ITEM_ADD, menuItem);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }

    }
    
    

}
