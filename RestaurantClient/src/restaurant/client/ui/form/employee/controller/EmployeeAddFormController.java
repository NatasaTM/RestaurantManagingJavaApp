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
public class EmployeeAddFormController {
    
    public static void prepareView(JDialog employeeAForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        employeeAForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
    }
    
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
    
    public static void btnSaveActionPerformed(JDialog employeeAForm,JTextField txtJmbg,JTextField txtName,JTextField txtLastname,JDateChooser dateChooser,JTextField txtAdress,JComboBox comboCity){
              boolean isValid = true;
       
        try {
            String jmbg = txtJmbg.getText().trim();
        String firsname = txtName.getText().trim();
        String lastname = txtLastname.getText().trim();
        Date date = dateChooser.getDate();
        LocalDate birthdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String adress = txtAdress.getText().trim();
        City city = (City) comboCity.getSelectedItem();
            if (jmbg.isEmpty() || firsname.isEmpty() || lastname.isEmpty() || adress.isEmpty() || city == null){
            isValid=false;
            JOptionPane.showMessageDialog(employeeAForm, "Morate uneti sve podatke!","error",JOptionPane.ERROR_MESSAGE);
            
        }
        if(isValid){
            Employee employee = new Employee(null, jmbg, firsname, lastname, birthdate, adress, city);
            addEmployee(employee);
            JOptionPane.showMessageDialog(employeeAForm, "Zaposleni je uspesno sacuvan!");
            txtJmbg.setText("");
            txtName.setText("");
            txtLastname.setText("");
            txtAdress.setText("");
            dateChooser.setDate(null);
            
        }
        
        }catch(NullPointerException e){
            isValid=false;
            e.printStackTrace();
            JOptionPane.showMessageDialog(employeeAForm, "Morate uneti sve podatke!","error",JOptionPane.ERROR_MESSAGE);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(employeeAForm, ex.getMessage());
        }
    }
    
    public static Response addEmployee(Employee employee) throws Exception{
        Request request = new Request(Operation.EMPLOYEE_ADD,employee);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
}
