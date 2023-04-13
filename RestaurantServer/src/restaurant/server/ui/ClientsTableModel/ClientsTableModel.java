package restaurant.server.ui.ClientsTableModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import restaurant.server.thread.ProcessClientRequests;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ClientsTableModel extends AbstractTableModel{

    private  List<ProcessClientRequests> clients;
    private final String[] columns = new String[]{"Name", "Login Time"};
    private final DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public ClientsTableModel(List<ProcessClientRequests> clients) {
        this.clients = clients;
    }

    @Override
    public int getRowCount() {
        if (clients != null) {
            return clients.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
       // return columns.length;
       return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProcessClientRequests client = clients.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return client.getClientName();
            case 1:
                
                return df.format(client.getLoginTime());
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
       // return columns[column];
       if(column==0) return "Name";
       if(column==1) return "Login time";
       
       return null;
    }

    public void addActiveClient(ProcessClientRequests client) {
        this.clients.add(client);
        fireTableDataChanged();
    }

    public void removeActiveClient(ProcessClientRequests client) {
        this.clients.remove(client);
        fireTableDataChanged();
    }
    
    public void updateTable(List<ProcessClientRequests> clients){
        this.clients = clients;
        fireTableDataChanged();
    }

    
}
