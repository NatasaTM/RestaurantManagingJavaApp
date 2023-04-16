package restaurant.client.ui.form.employee.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.AccountTableModel;
import restaurant.client.ui.form.employee.EmployeeAccountUpdateDeleteForm;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class EmployeeAccountUpdateDeleteFormController {

    public static void prepareView(EmployeeAccountUpdateDeleteForm employeeAccountUpdateDeleteForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        employeeAccountUpdateDeleteForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
    }

    public static void setTableModel(JTable tblAccounts) {
        try {
            tblAccounts.setModel(new AccountTableModel(getAllUsers()));

        } catch (Exception ex) {
            Logger.getLogger(EmployeeAccountUpdateDeleteFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<User> getAllUsers() throws Exception {
        Request request = new Request(Operation.USER_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<User>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void btnSelectActionPerformed(JTable tblAccounts, JTextField txtEmployee, JTextField txtUsername, JTextField txtPassword) {
        int selectedRow = tblAccounts.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) tblAccounts.getValueAt(selectedRow, 0);
            try {
                User user = findUserById(username);
                String employee = user.getEmployee().getId() + ", " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname();
                txtEmployee.setText(employee);
                txtUsername.setText(user.getUsername());
                txtPassword.setText(user.getPassword());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Niste izabrali red");
        }
    }

    public static User findUserById(String username) throws Exception {
        Request request = new Request(Operation.USER_FIND_BY_ID, username);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (User) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void btnDeleteActionPerformed(JTextField txtUsername,JTable tblAccounts,JTextField txtEmployee,JTextField txtPassword) {
        String username = txtUsername.getText().trim();
        
        try {
           User user = findUserById(username);
            deleteUser(user);
            JOptionPane.showMessageDialog(null, "Nalog je uspesno obrisan");
            txtUsername.setText("");
            txtEmployee.setText("");
            txtPassword.setText("");
            setTableModel(tblAccounts);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public static Response deleteUser(User user) throws Exception {
        Request request = new Request(Operation.USER_DELETE, user);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
    
    public static void btnUpdateActionPerformed(JTextField txtUsername,JTable tblAccounts,JTextField txtEmployee,JTextField txtPassword){
          String username = txtUsername.getText().trim();
          
        try {
            User user = findUserById(username);
            user.setUsername(txtUsername.getText().trim());
            user.setPassword(txtPassword.getText().trim());
            updateUser(user);
            JOptionPane.showMessageDialog(null, "Nalog je uspesno izmenjen!");
            setTableModel(tblAccounts);
            txtUsername.setText("");
            txtEmployee.setText("");
            txtPassword.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public static void updateUser(User user) throws Exception{
        Request request = new Request(Operation.USER_UPDATE, user);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if(response.getException()!=null)
            throw new Exception(response.getException().getMessage());
    }
}
