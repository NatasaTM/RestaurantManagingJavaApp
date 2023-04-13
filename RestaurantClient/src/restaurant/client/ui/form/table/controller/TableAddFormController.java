package restaurant.client.ui.form.table.controller;

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
public class TableAddFormController {
    public static void prepareView(JDialog tableAddForm) {
       User user = ApplicationSession.getInstance().getLoginUser();
        tableAddForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname()+" " + user.getEmployee().getLastname());
    }
    
    public static void btnSaveActionPerformed(JDialog tableAddForm,JTextField txtNumberOfSeats){
        try {
            Integer numberOfSeats = Integer.parseInt(txtNumberOfSeats.getText().trim());
            
                Table table = new Table(numberOfSeats);
                addTable(table);
                JOptionPane.showMessageDialog(tableAddForm, "Sto je uspesno sacuvan");
            
            
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(tableAddForm, "Morate uneti broj mesta za sedenje","Greska",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addTable(Table table) throws Exception{
        Request request = new Request(Operation.TABLE_ADD, table);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
    }
}
