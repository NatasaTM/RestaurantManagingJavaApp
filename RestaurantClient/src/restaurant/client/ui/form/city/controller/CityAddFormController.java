package restaurant.client.ui.form.city.controller;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.common.domain.City;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class CityAddFormController {
    
    public static void prepareView(JDialog cityAForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        cityAForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
    }
    
    public static void btnSaveActionPerformed(JDialog cityAForm,JTextField txtZipcode,JTextField txtName){
       boolean isValid = true;

        try {
            Long zipcode = Long.valueOf(txtZipcode.getText().trim());
            String name = txtName.getText().trim();
            
            if (name.isEmpty()) {
                isValid = false;
                JOptionPane.showMessageDialog(cityAForm, "Morate unesti ime grada", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (isValid) {
                City city = new City(zipcode, name);
                addCity(city);
                JOptionPane.showMessageDialog(cityAForm, "Grad je uspesno sacuvan!");
                txtName.setText("");
                txtZipcode.setText("");

            }

        } catch (NumberFormatException ex) {
            isValid=false;
            ex.printStackTrace();
            JOptionPane.showMessageDialog(cityAForm, "Morate unesti ispravan PTT broj", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(cityAForm, e.getMessage());
        } 
    }
    
    public static Response addCity(City city) throws Exception{
        Request request = new Request(Operation.CITY_ADD,city);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
}
