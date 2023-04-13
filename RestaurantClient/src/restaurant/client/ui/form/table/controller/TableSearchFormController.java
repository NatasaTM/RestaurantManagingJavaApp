package restaurant.client.ui.form.table.controller;

import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.TablesTableModel;
import restaurant.client.ui.form.table.TableUpdateDeleteForm;
import restaurant.common.domain.MenuItem;
import restaurant.common.domain.Table;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class TableSearchFormController {
    
    public static void prepareView(JDialog tableSearrchForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        tableSearrchForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname()+" " + user.getEmployee().getLastname());
    }
    
    public static void setTableModel(JTable tblTables) {
            try {
                tblTables.setModel(new TablesTableModel(getAllTables()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
    
    public static List<Table> getAllTables() throws Exception{
        Request request = new Request(Operation.TABLE_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Table>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        } 
    }
    
    public static void btnDetailsActionPerformed(JDialog tableSForm,JTable tblTables){
        int selectedRow = tblTables.getSelectedRow();
        if(selectedRow!=-1){
            Integer id = Integer.parseInt(tblTables.getValueAt(selectedRow, 0).toString());
            Integer numberOfSeats = Integer.parseInt(tblTables.getValueAt(selectedRow, 1).toString());
            boolean isAvailable = (boolean) tblTables.getValueAt(selectedRow, 2);
            Table table = new Table(id, numberOfSeats, isAvailable);
            new TableUpdateDeleteForm(null, true,table).setVisible(true);
            
        }else{
            JOptionPane.showMessageDialog(tableSForm, "Niste izabrali red", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
