package restaurant.client.ui.form.employee.controller;

import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.common.domain.City;
import restaurant.common.domain.Employee;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class EmployeeUpdateDeleteFormController {
    
     public static void populateComboCities(JComboBox comboCity) {
        try {
            comboCity.setModel(new DefaultComboBoxModel<>(getAllCities().toArray()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     
      public static List <City> getAllCities() throws Exception{
          Request request = new Request(Operation.CITY_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return   (List<City>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        } 
    }
      
      public static void prepareView(JDialog employeeUDForm,Employee employee,JTextField txtId,JTextField txtJmbg, JTextField txtFirstName,JTextField txtLastname,JDateChooser dateChooser,JTextField txtAdress,JComboBox comboCity) {
        User user = ApplicationSession.getInstance().getLoginUser();
        employeeUDForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());

        txtId.setText(employee.getId().toString());
        txtJmbg.setText(employee.getJmbg());
        txtFirstName.setText(employee.getFirstname());
        txtLastname.setText(employee.getLastname());
        LocalDate ldate = employee.getBirthdate();
        Date date = java.sql.Date.valueOf(ldate);
        dateChooser.setDate(date);
        txtAdress.setText(employee.getAdress());
        comboCity.setSelectedItem(employee.getCity());
    }
      
      public static void btnUpdateActionPerformed(JDialog employeeUDForm,JTextField txtId,JTextField txtJmbg, JTextField txtFirstName,JTextField txtLastname,JDateChooser dateChooser,JTextField txtAdress,JComboBox comboCity){
         boolean isValid = true;
        Integer id = Integer.parseInt(txtId.getText().trim().toString());
        String jmbg = txtJmbg.getText().trim();
        String firstname = txtFirstName.getText().trim();
        String lastname = txtLastname.getText().trim();
        Date date = dateChooser.getDate();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String adress = txtAdress.getText().trim();
        City city = (City) comboCity.getSelectedItem();
        if (jmbg.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || adress.isEmpty() || city == null) {
            isValid = false;
            JOptionPane.showMessageDialog(employeeUDForm, "Podaci o zaposlenom nisu uspesno izmenjeni", "error", JOptionPane.ERROR_MESSAGE);

        }
        if (isValid == true) {
            Employee employee = new Employee(id, jmbg, firstname, lastname, localDate, adress, city);
            try {
                updateEmployee(employee);
                JOptionPane.showMessageDialog(employeeUDForm, "Podaci o zaposlenom su uspesno izmenjeni");
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(employeeUDForm, "Podaci o zaposlenom nisu uspesno izmenjeni", "error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(employeeUDForm, ex.getMessage());
            }

        } 
      }
      
      public static void updateEmployee(Employee employee) throws Exception{
          Request request = new Request(Operation.EMPLOYEE_UPDATE,employee);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
      }
      
      public static void btnDeleteActionPerformed(JDialog employeeUDForm,JTextField txtId,JTextField txtJmbg, JTextField txtFirstName,JTextField txtLastname,JDateChooser dateChooser,JTextField txtAdress,JComboBox comboCity){
          Integer id = Integer.parseInt(txtId.getText().trim().toString());
        try {
            Employee employee = findById(id);
            deleteEmployee(employee);
            JOptionPane.showMessageDialog(employeeUDForm, "Zaposleni je uspesno obrisan");
            txtId.setText("");
            txtJmbg.setText("");
            txtFirstName.setText("");
            txtLastname.setText("");
            txtAdress.setText("");
            comboCity.setSelectedItem("");
            dateChooser.setDate(null);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(employeeUDForm, ex.getMessage());
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
      
      public static void deleteEmployee(Employee employee) throws Exception{
         Request request = new Request(Operation.EMPLOYEE_DELETE,employee);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject(); 
      }
}
