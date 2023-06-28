package restaurant.server.thread;

import java.util.List;
import javax.swing.JTable;
import restaurant.server.session.Session;
import restaurant.server.ui.ClientsTableModel.ClientsTableModel;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ActiveClientsThread extends Thread{
    
    private final JTable tblClients;
    private final ClientsTableModel clientsTableModel;
   

    public ActiveClientsThread(JTable tblClients,ClientsTableModel clientsTableModel) {
        this.tblClients=tblClients;
        this.clientsTableModel = clientsTableModel;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                List<ProcessClientRequests> clients = Session.getInstance().getClients();
                clientsTableModel.updateTable(clients);
                
              
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            System.out.println("Active Clients Thread End");
        }
      

    }

    
}
