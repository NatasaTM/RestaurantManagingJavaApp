package restaurant.client.ui.form.receipt.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.client.ui.component.table.model.ReceiptTableModel;
import restaurant.client.ui.form.receipt.ReceiptDetailsForm;
import restaurant.client.ui.form.receipt.ReceiptSearchForm;
import restaurant.client.ui.form.receipt.ReceiptUnpaiedForm;
import restaurant.common.domain.Order;
import restaurant.common.domain.Payment;
import restaurant.common.domain.Receipt;
import restaurant.common.domain.User;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ReceiptSearchFormController {

    public static void prepareView(ReceiptSearchForm receiptSearchForm) {
        User user = ApplicationSession.getInstance().getLoginUser();
        receiptSearchForm.setTitle("Prijavljeni ste kao: " + user.getEmployee().getFirstname() + " " + user.getEmployee().getLastname());
    }

    public static void setTableModel(JTable tblReceipts) {
        try {
            tblReceipts.setModel(new ReceiptTableModel(getAllReceipts()));
           
            TableColumnModel columnModel = tblReceipts.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                TableColumn column = columnModel.getColumn(i);

                column.setMinWidth(5);
            }
            tblReceipts.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblReceipts.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblReceipts.getColumnModel().getColumn(2).setPreferredWidth(10);
            tblReceipts.getColumnModel().getColumn(3).setPreferredWidth(20);
        } catch (Exception ex) {
            Logger.getLogger(ReceiptSearchFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Receipt> getAllReceipts() throws Exception {
        Request request = new Request(Operation.RECEIPT_GET_ALL);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (List<Receipt>) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void btnDetailsActionPerformed(JTable tblReceipts) {
        int selectedRow = tblReceipts.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) tblReceipts.getValueAt(selectedRow, 0);
            try {
                Receipt receipt = findReceiptById(id);
                Payment payment = findPaymentByReceipt(receipt);

                writeDataToTextFile(receipt, payment);
                new ReceiptDetailsForm(null, true).setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(ReceiptSearchFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Niste izabrali red iz tabele za prikaz detalja!");
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

    public static Payment findPaymentByReceipt(Receipt receipt) throws Exception {
        Request request = new Request(Operation.PAYMENT_FIND_BY_RECEIPT, receipt);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (Payment) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    private static void writeDataToTextFile(Receipt receipt, Payment payment) {

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

            // upisivanje podataka o placanju
            out.println("Podaci o placanju za racun: " + receipt.getId());
            out.println("Broj placanja: " + payment.getId());
            out.println("Datum i vreme placanja: " + payment.getDate());
            out.println("Nacin placanja: " + payment.getPaymentMethodType());

            out.flush();
            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void btnUnpaiedActionPerformed(JTable tblReceipts) {
        new ReceiptUnpaiedForm(null, true).setVisible(true);
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
}
