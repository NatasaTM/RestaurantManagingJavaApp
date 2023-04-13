package restaurant.client.ui.form.employee.controller;

import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.EmployeeTableModel;
import restaurant.client.ui.form.employee.EmployeeUpdateDeleteForm;
import restaurant.common.domain.Employee;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class EmployeeSearchFormController {
    
    public static void prepareView(JDialog employeeSForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        employeeSForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname()+" " + user.getEmployee().getLastname());
    }

    public static void setTableModel(JTable tblEmployee) {
         
        try {
            tblEmployee.setModel(new EmployeeTableModel(getAllEmployees()));
               TableColumnModel columnModel = tblEmployee.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                TableColumn column = columnModel.getColumn(i);

                column.setMinWidth(1);
            }
             tblEmployee.getColumnModel().getColumn(0).setPreferredWidth(1);
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static List<Employee> getAllEmployees() throws Exception{
        Request request = new Request(Operation.EMPLOYEE_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Employee>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        } 
    }
    
    public static void btnDetailsActionPerformed(JDialog employeeSForm,JTable tblEmployee){
        int selectedRow = tblEmployee.getSelectedRow();
        if(selectedRow!=-1){
            Integer id = Integer.parseInt(tblEmployee.getValueAt(selectedRow, 0).toString());
            try {
                Employee employee = findById(id);
                System.out.println(employee);
                new EmployeeUpdateDeleteForm(null,true, employee).setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            
        }else{
            JOptionPane.showMessageDialog(employeeSForm, "Niste izabrali red!");
        }
    }
    
    public static Employee findById(Integer id) throws Exception{
        Request request = new Request(Operation.EMPLOYE_FIND_BY_ID,id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return  (Employee) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        } 
    }
}
