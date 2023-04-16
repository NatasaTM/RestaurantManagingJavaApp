package restaurant.client.ui.form.receipt.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.ReceiptTableModel;
import restaurant.client.ui.form.receipt.ReceiptDetailsForm;
import restaurant.client.ui.form.receipt.ReceiptForm;
import restaurant.client.ui.form.receipt.ReceiptUnpaiedForm;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;
import restaurant.common.domain.Receipt;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ReceiptUnpaiedFormController {
    public static void prepareView(ReceiptUnpaiedForm receiptUnpaiedForm){
        User user = ApplicationSession.getInstance().getLoginUser();
        receiptUnpaiedForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
    }
    
    public static void setTableModel(JTable tblReceipt){
        try {
            tblReceipt.setModel(new ReceiptTableModel(getAllUnpaiedReceipts()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static List<Receipt> getAllUnpaiedReceipts() throws Exception {
        Request request = new Request(Operation.RECEIPT_FIND_BY_QUERY);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Receipt>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
    
    public static void btnPayActionPerformed(JTable tblReceipt,ReceiptUnpaiedForm receiptUnpaiedForm){
        int selectedRow = tblReceipt.getSelectedRow();
        if(selectedRow!=-1){
            Long id = (Long) tblReceipt.getValueAt(selectedRow, 0);
            try {
                Receipt receipt = findReceiptById(id);
                prepareReceipt(receipt);
                new ReceiptForm(null, true, receipt).setVisible(true);
                receiptUnpaiedForm.dispose();
            } catch (Exception ex) {
                Logger.getLogger(ReceiptUnpaiedFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Niste izabrali red!");
        }
    }
    public static Receipt findReceiptById(Long id) throws Exception {
        Request request = new Request(Operation.RECEIPT_FIND_BY_ID, id);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (Receipt) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
    
    public static void btnDetailsActionPerformed(JTable tblReceipt){
         int selectedRow = tblReceipt.getSelectedRow();
        if(selectedRow!=-1){
            Long id = (Long) tblReceipt.getValueAt(selectedRow, 0);
            try {
                Receipt receipt = findReceiptById(id);
                 writeDataToTextFile(receipt);
                 new ReceiptDetailsForm(null, true).setVisible(true);
                 
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }else{
            JOptionPane.showMessageDialog(null, "Niste izabrali red!");
        }
    }

    private static void writeDataToTextFile(Receipt receipt) {
          try {
            FileWriter fOut = new FileWriter("receipt_details.txt");
            PrintWriter out = new PrintWriter(fOut);
            // upisivanje podataka o racunu
            out.println("Broj racuna: " + receipt.getId());
            out.println("Datum i vreme izdavanja racuna: " + receipt.getDate());
            out.println("Iznos racuna: " + receipt.getAmount());
            out.println("Porudzbine: ");
            List<Order> orders = receipt.getOrders();
            for (Order o : orders) {
                out.println("Porudzbina : " + o.toString());
            }


            out.flush();
            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void prepareReceipt(Receipt receipt) {
        List<Order> orders = receipt.getOrders();
        List<OrderItem> orderItems = null;
        
        for(int i=0;i<orders.size();i++){
           orderItems = orders.get(i).getOrderItems();
           for(int j=0;j<orderItems.size();j++){
               orderItems.get(j).setIsReady(true);
           }
            try {
                updateOrder(orders.get(i));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    
     public static Response updateOrder(Order order) throws Exception {
        Request request = new Request(Operation.ORDER_UPDATE, order);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return null;
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }
}
