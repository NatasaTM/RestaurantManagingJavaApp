package restaurantapp.ui.form.menuCategory.controller;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.common.domain.MenuCategory;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuCategoryAddFormController {

    public static void save(JTextArea txtError, JTextField txtName) {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            txtError.setText("Morate uneti naziv kategorije!");
        } else {
            MenuCategory menuCategory = new MenuCategory(null, name);
            try {
                addMenuCategory(menuCategory);
                txtError.setText("Kategorija menija je uspesno dodata!");
            } catch (Exception ex) {
                txtError.setText(ex.getMessage());
                ex.printStackTrace();
            }

        }
    }

    public static void setTitle(JDialog menuCategoryAddForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        menuCategoryAddForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
    }

    public static Response addMenuCategory(MenuCategory menuCategory) throws Exception {
        Request request = new Request(Operation.MENU_CATEGORY_ADD, menuCategory);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

}
