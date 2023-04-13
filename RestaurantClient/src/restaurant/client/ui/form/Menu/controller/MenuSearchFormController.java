package restaurant.client.ui.form.Menu.controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.MenuItemTableModel;
import restaurant.client.ui.form.Menu.MenuSearchForm;
import restaurant.client.ui.form.Menu.MenuUpdateDeleteForm;
import static restaurant.client.ui.form.menuItem.controller.MenuItemAddFormController.getAllMenuCategory;
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
public class MenuSearchFormController {

    public static void prepareView(MenuSearchForm menuSearchForm, JButton btnDetails, JComboBox comboMenu, JTextField txtName, JRadioButton rbtnActive, JRadioButton rbtnNonActive) {
        User user = ApplicationSession.getInstance().getLoginUser();
        menuSearchForm.setTitle("Ulogovani ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
        if (user.intRole() == 1) {
            btnDetails.setEnabled(true);
        } else {
            btnDetails.setEnabled(false);
        }

        Menu menu = (Menu) comboMenu.getSelectedItem();
        menuSearchForm.setMenu(menu);
        txtName.setText("Jelovnik: " + menu.getName());
        setIsSelected(menuSearchForm, rbtnActive, rbtnNonActive);

    }

    public static void setIsSelected(MenuSearchForm menuSearchForm, JRadioButton rbtnActive, JRadioButton rbtnNonActive) {
        Menu menu = menuSearchForm.getMenu();
        if (menu.isIsActiv()) {

            rbtnActive.setEnabled(true);
            rbtnActive.setSelected(true);
            rbtnNonActive.setEnabled(false);

        }
        if (!menu.isIsActiv()) {

            rbtnNonActive.setEnabled(true);
            rbtnNonActive.setSelected(true);
            rbtnActive.setEnabled(false);
            rbtnActive.setFocusable(false);
        }

    }

    public static void setTableModel(JComboBox comboMenu, JTable tblMenuItems) {

        Menu menu = (Menu) comboMenu.getSelectedItem();
        try {

            List<MenuItem> menuItems = findMenuById(menu.getId()).getMenuItems();
            tblMenuItems.setModel(new MenuItemTableModel(menuItems));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static Menu findMenuById(Integer id) throws Exception {
        Request request = new Request(Operation.MENU_FIND_BY_ID, id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (Menu) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
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

    public static void populateComboMenu(JComboBox comboMenu) {
        try {

            comboMenu.setModel(new DefaultComboBoxModel<>(menuGetAll().toArray()));

        } catch (Exception e) {
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

    public static void btnDetailsActionPerformed(JComboBox comboMenu) {
        Menu menu = (Menu) comboMenu.getSelectedItem();
        System.out.println("Meni: " + menu);
        new MenuUpdateDeleteForm(null, true, menu).setVisible(true);
    }

    public static void btnSelectCategoryActionPerformed(JComboBox comboMenu, JComboBox comboCategory, JTable tblMenuItems) {
        Menu menu = (Menu) comboMenu.getSelectedItem();
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
    }

    public static List<MenuItem> getMenuItemFromMenuByCategory(String categoryName, Menu menu) throws Exception {
        List<Object> arguments = new ArrayList<>();
        arguments.add(categoryName);
        arguments.add(menu);
        Request request = new Request(Operation.MENU_GET_MENU_ITEMS_BY_CATEGORY, arguments);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<MenuItem>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void btnSelectMenuActionPerformed(MenuSearchForm menuSearchForm, ButtonGroup btnGroupMenuIsActive, JComboBox comboMenu, JTextField txtName, JTable tblMenuItems, JRadioButton rbtnActive, JRadioButton rbtnNonActive) {
        //btnGroupMenuIsActive.clearSelection();
        Menu menu = (Menu) comboMenu.getSelectedItem();
        menuSearchForm.setMenu(menu);
        txtName.setText("Jelovnik: " + menu.getName());
        System.out.println(menu);
        setTableModel(comboMenu, tblMenuItems);
        
        if (menu != null && menu.isIsActiv()) {
           // rbtnActive.setSelected(true);
            //rbtnNonActive.setSelected(false);
            setIsSelected(menuSearchForm, rbtnActive, rbtnNonActive);

        }
        if (!menu.isIsActiv()) {
            //rbtnNonActive.setSelected(true);
             //rbtnActive.setSelected(false);
            setIsSelected(menuSearchForm, rbtnActive, rbtnNonActive);
           
        }
    }

}
