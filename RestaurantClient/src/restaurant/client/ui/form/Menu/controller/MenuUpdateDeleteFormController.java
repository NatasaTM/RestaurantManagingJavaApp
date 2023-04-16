package restaurant.client.ui.form.Menu.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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
public class MenuUpdateDeleteFormController {

    public static void populateComboMenu(JComboBox comboMenu, Menu menu) {
        try {
            List<Menu> menus = menuGetAll();
            comboMenu.setModel(new DefaultComboBoxModel<>(menus.toArray()));
            if (menu == null || menu.getId() == null) {
                comboMenu.setSelectedIndex(-1);

            } else {
                comboMenu.setSelectedItem(menu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void populateComboMenuItems(JComboBox comboMenuItems) {
        try {
            comboMenuItems.setModel(new DefaultComboBoxModel<>(getAllMenuItems().toArray()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setTableModel(Menu menu, JTable tblMenuItems) {
        List<MenuItem> menuItems = null;
        if (menu == null || menu.getId() == null) {
            menuItems = new ArrayList<>();
            tblMenuItems.setModel(new MenuItemTableModel(menuItems));
        } else {
            try {
                menuItems = findMenuById(menu.getId()).getMenuItems();
                tblMenuItems.setModel(new MenuItemTableModel(menuItems));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

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

    public static void prepareView(JDialog menuUDForm, Menu menu, JTextField txtMenuName, JRadioButton rbtnActive, JRadioButton rbtnNonActive, JComboBox comboMenu, JTable tblMenuItems) {

        User user = ApplicationSession.getInstance().getLoginUser();
        menuUDForm.setTitle("Ulogovani ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());

        if (menu == null || menu.getName() == null) {
            txtMenuName.setText("");
        } else {
            txtMenuName.setText("Jelovnik: " + menu.getName());
            if (menu.isIsActiv()) {
                rbtnActive.setSelected(true);

            } else if (!menu.isIsActiv()) {
                rbtnNonActive.setSelected(true);
            }
        }

    }

    public static void setMenu(Menu menu) {

        try {
            List<Menu> menus = menuGetAll();
            menu = menus.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void btnSelectMenuActionPerformed(Menu menu, JComboBox comboMenu, JTextField txtMenuName, JRadioButton rbtnActive, JRadioButton rbtnNonActive, JDialog menuUDForm, JTable tblMenuItems, JButton btnSetIsActive, JButton btnAdd, JTextField txtError, JComboBox comboMenuItems) {
        menu = (Menu) comboMenu.getSelectedItem();
        prepareView(menuUDForm, menu, txtMenuName, rbtnActive, rbtnNonActive, comboMenu, tblMenuItems);
        setTableModel(menu, tblMenuItems);
        //setTableModel();
        btnSetIsActive.setEnabled(true);
        rbtnActive.setEnabled(true);
        rbtnNonActive.setEnabled(true);
        comboMenuItems.setEnabled(true);
        btnAdd.setEnabled(true);
        txtError.setText("");
    }

    public static void btnSetIsActiveActionPerformed(Menu menu, JRadioButton rbtnActive, JRadioButton rbtnNonActive, JDialog menuUDForm) {
        try {
            if (rbtnActive.isSelected()) {
                menu.setIsActiv(true);
                updateMenu(menu);
                JOptionPane.showMessageDialog(menuUDForm, "Jelovnik je uspesno aktiviran!");

            } else if (rbtnNonActive.isSelected()) {
                menu.setIsActiv(false);
                updateMenu(menu);
                JOptionPane.showMessageDialog(menuUDForm, "Jelovnik je uspesno deaktiviran!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(menuUDForm, e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Response updateMenu(Menu menu) throws Exception {
        Request request = new Request(Operation.MENU_UPDATE, menu);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void btnDeleteMenuActionPerformed(Menu menu, JTextField txtError, JTextField txtMenuName, JComboBox comboMenuItems, JButton btnAdd, JTable tblMenuItems, JButton btnSetIsActive, JRadioButton rbtnActive, JRadioButton rbtnNonActive, JComboBox comboMenu, JDialog menuUDForm) {
        int response = JOptionPane.showConfirmDialog(menuUDForm, "Da li ste sigurni da zelite da izbrisete meni: " + menu.getName(), "Brisanje jelovnika", JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION) {
            try {
                System.out.println("Meni za brisanje: " + menu.getId());
                deleteMenu(menu);
                JOptionPane.showMessageDialog(menuUDForm, "Jelovnik je uspesno obrisan!");
                txtError.setForeground(Color.black);
                txtError.setText("Jelovnik je uspesno obrisan!");
                txtMenuName.setText("");
                comboMenuItems.setEnabled(false);
                btnAdd.setEnabled(false);
                List<MenuItem> menuItems = new ArrayList<>();
                tblMenuItems.setModel(new MenuItemTableModel(menuItems));
                btnSetIsActive.setEnabled(false);
                rbtnActive.setEnabled(false);
                rbtnNonActive.setEnabled(false);
                populateComboMenu(comboMenu, menu);

                comboMenu.setSelectedIndex(-1);

            } catch (Exception ex) {
                ex.printStackTrace();
                txtError.setText(ex.getMessage());
                JOptionPane.showMessageDialog(menuUDForm, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static Response deleteMenu(Menu menu) throws Exception {
        Request request = new Request(Operation.MENU_DELETE, menu);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void btnAddActionPerformed(JComboBox comboMenuItems, Menu menu, JTextField txtError, JTable tblMenuItems, JDialog menuUDForm) {
        MenuItem menuItem = (MenuItem) comboMenuItems.getSelectedItem();
        try {
            addMenuItemToMenu(menuItem, menu);

            txtError.setText("Jelo je uspesno dodato u jelovnik.");
            JOptionPane.showMessageDialog(menuUDForm, "Jelo je uspesno dodato u jelovnik");
            setTableModel(menu, tblMenuItems);

        } catch (Exception ex) {
            ex.printStackTrace();
            txtError.setForeground(Color.red);
            txtError.setText(ex.getMessage());
            JOptionPane.showMessageDialog(menuUDForm, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void addMenuItemToMenu(MenuItem menuItem, Menu menu) throws Exception {
        List<Object> arguments = new ArrayList<>();
        arguments.add(menuItem);
        arguments.add(menu);
        Request request = new Request(Operation.MENU_ADD_MENU_ITEM, arguments);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
    }

    public static void btnDeleteMenuItemActionPerformed(JTextField txtError, JTable tblMenuItems, Menu menu, JDialog menuUDForm, JComboBox comboMenu) {
        txtError.setText("");
        int selectedRow = tblMenuItems.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Integer id = Integer.parseInt(tblMenuItems.getValueAt(selectedRow, 0).toString());

                MenuItem menuItem = findMenuItemById(id);
                menu = (Menu) comboMenu.getSelectedItem();
                deleteMenuItemFromMenu(menu, menuItem);

                txtError.setText("Jelo je uspesno obrisano iz jelovnika.");
                JOptionPane.showMessageDialog(menuUDForm, "Jelo je uspesno obrisano iz jelovnika");
                setTableModel(menu, tblMenuItems);

            } catch (Exception e) {
                txtError.setForeground(Color.red);
                txtError.setText(e.getMessage());
                JOptionPane.showMessageDialog(menuUDForm, e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(menuUDForm, "Niste izbrali stavku jelovnika");
        }
    }

    public static MenuItem findMenuItemById(Integer id) throws Exception {
        Request request = new Request(Operation.MENU_ITEM_FIND_BY_ID, id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (MenuItem) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void deleteMenuItemFromMenu(Menu menu, MenuItem menuItem) throws Exception {
        List<Object> arguments = new ArrayList<>();
        arguments.add(menu);
        arguments.add(menuItem);
        Request request = new Request(Operation.MENU_DELETE_MENU_ITEM, arguments);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
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

    public static List<MenuItem> getAllMenuItems() throws Exception {
        Request request = new Request(Operation.MENU_ITEM_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<MenuItem>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

}
