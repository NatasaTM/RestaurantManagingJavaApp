package restaurant.client.ui.form.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.EmployeeTableModel;
import restaurant.client.ui.form.employee.EmployeeCreateAccountForm;
import restaurant.common.domain.Employee;
import restaurant.common.domain.Role;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class EmployeeCreateAccountFormController {
    
    public static void prepareView(JDialog createAccountForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        createAccountForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
    }
    
    public static void setTableModel(JTable tblEmployee) {
        try {
            tblEmployee.setModel(new EmployeeTableModel(getAllEmployees()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static List<Employee> getAllEmployees() throws Exception {
        Request request = new Request(Operation.EMPLOYEE_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Employee>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }        
    }
    
    public static void populateComboRoles(JComboBox comboRoles) {
        
        try {
            List<Role> roles = getAllRoles();
            comboRoles.setModel(new DefaultComboBoxModel(roles.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(EmployeeCreateAccountFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static List<Role> getAllRoles() throws Exception {
        Request request = new Request(Operation.ROLE_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Role>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }        
    }
    
    public static void btnSelectEmployeeActionPerformed(JTable tblEmployee, EmployeeCreateAccountForm employeeCreateAccountForm,JTextField txtEmployeeSelected) {
        int selectedRow = tblEmployee.getSelectedRow();
        if (selectedRow != -1) {
            Integer id = (Integer) tblEmployee.getValueAt(selectedRow, 0);
            try {
                Employee employee = findEmployeeById(id);
                employeeCreateAccountForm.setEmployee(employee);
                JOptionPane.showMessageDialog(null, "Zaposleni: " + employee.getFirstname()+" " +employee.getLastname()+" je uspesno odabran");
                txtEmployeeSelected.setText(employee.getId()+" "+ employee.getFirstname()+" " + employee.getLastname());
                
            } catch (Exception ex) {
                Logger.getLogger(EmployeeCreateAccountFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Niste izabrali red");
        }
    }
    
    public static Employee findEmployeeById(Integer id) throws Exception {
        Request request = new Request(Operation.EMPLOYE_FIND_BY_ID, id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (Employee) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }        
    }
    
    public static void btnSelectRoleActionPerformed(JComboBox comboRoles, EmployeeCreateAccountForm employeeCreateAccountForm) {
        Role role = (Role) comboRoles.getSelectedItem();
        employeeCreateAccountForm.setRole(role);
    }
    
    public static void btnSaveActionPerformed(JTextField txtUsername,JTextField txtPassword,EmployeeCreateAccountForm employeeCreateAccountForm,JTextField txtEmployeeSelected){
        String userName = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        Role role = employeeCreateAccountForm.getRole();
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Employee employee = employeeCreateAccountForm.getEmployee();
        boolean isValid = true;
        if(userName.isEmpty() || password.isEmpty() || role==null){
            JOptionPane.showMessageDialog(null, "Morate uneti sve podatke!");
            isValid=false;
        }
        
        if(isValid){
            User user = new User(userName, password, employee, roles);
            try {
                addUserProfile(user);
                JOptionPane.showMessageDialog(null, "Nalog za zaposlenog je uspesno kreiran");
                employeeCreateAccountForm.setEmployee(null);
                employeeCreateAccountForm.setRole(null);
                txtUsername.setText("");
                txtPassword.setText("");
                txtEmployeeSelected.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
        }
            
        
    }
    
    public static void addUserProfile(User user) throws Exception{
        Request request = new Request(Operation.USER_ADD,user);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
    }
    
}
