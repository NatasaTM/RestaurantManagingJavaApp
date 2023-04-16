package restaurant.client.ui.form.table.controller;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.common.domain.Table;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class TableUpdateDeleteFormController {
    public static void prepareView(JTextField txtTableId,JTextField txtNumberOfSeats,Table table,JDialog tableUDForm,JCheckBox chckIsAvailable ) {
       txtTableId.setText(table.getTableNumber().toString());
       txtNumberOfSeats.setText(table.getNumberOfSeats().toString());
       chckIsAvailable.setSelected(table.isIsAvailable());
        User user = ApplicationSession.getInstance().getLoginUser();
        tableUDForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname()+" " + user.getEmployee().getLastname());
    }
    
    public static void btnDeleteActionPerformed(JDialog tableUDForm,JTextField txtTableId,JTextField txtNumberOfSeats,JCheckBox chckIsAvailable){
        try {
            Integer id = Integer.parseInt(txtTableId.getText().trim());
       Integer numberOfSeats = Integer.parseInt(txtNumberOfSeats.getText().trim());
       Boolean isAvailable =null;
       if(chckIsAvailable.isSelected()){
           isAvailable=true;
       }else
           isAvailable=false;
       Table table = new Table(id, numberOfSeats, isAvailable);
       deleteTable(table);
        JOptionPane.showMessageDialog(tableUDForm, "Sto je uspesno obrisan.");
        txtTableId.setText("");
        txtNumberOfSeats.setText("");
        }catch(NumberFormatException ex) {
            ex.printStackTrace();
           JOptionPane.showMessageDialog(tableUDForm, "Niste uneli broj mesta za sedenje","error",JOptionPane.ERROR_MESSAGE); 
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(tableUDForm, e.getMessage(),"error",JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    public static Response deleteTable(Table table) throws Exception{
        Request request = new Request(Operation.TABLE_DELETE,table);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
    
    public static void btnUpdateActionPerformed(JDialog tableUDForm,JCheckBox chckIsAvailable,JTextField txtTableId,JTextField txtNumberOfSeats){
        try {
            Integer id = Integer.parseInt(txtTableId.getText().trim());
       Integer numberOfSeats = Integer.parseInt(txtNumberOfSeats.getText().trim());
       Boolean isAvailable =null;
       if(chckIsAvailable.isSelected()){
           isAvailable=true;
       }else
           isAvailable=false;
       Table table = new Table(id, numberOfSeats, isAvailable);
       updateTable(table);
            JOptionPane.showMessageDialog(tableUDForm, "Sto je uspesno izmenjen.");
            
            tableUDForm.dispose();
            
        } catch(NumberFormatException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(tableUDForm, "Niste uneli broj mesta za sedenje","error",JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(tableUDForm, e.getMessage(),"error",JOptionPane.ERROR_MESSAGE); 
        }
    }
    public static Response updateTable(Table table) throws Exception{
         Request request = new Request(Operation.TABLE_UPDATE,table);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
}
