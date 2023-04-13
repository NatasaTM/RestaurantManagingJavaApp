package restaurant.client.ui.component.table.model;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import restaurant.common.domain.Order;
import restaurant.common.domain.Receipt;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ReceiptTableModel extends AbstractTableModel{
    
    private List<Receipt> receipts;

    public ReceiptTableModel(List<Receipt> receipts) {
        this.receipts = receipts;
    }
    
    

    @Override
    public int getRowCount() {
        if(receipts == null){
            return  0;
        }
        return receipts.size();
    }

    @Override
    public int getColumnCount() {
       return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Receipt receipt = receipts.get(rowIndex);
        if(columnIndex==0) return receipt.getId();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy."+" "+" HH:mm:ss");
        if(columnIndex==1) return receipt.getDate().format(dtf);
        DecimalFormat df = new DecimalFormat("0.00");
        if (columnIndex==2) return  df.format(receipt.getAmount());
        if(columnIndex==3) return getOrdersid(receipt);
        
        return null;
    }

    @Override
    public String getColumnName(int column) {
        if (column==0)return"ID";
        if(column==1) return "Datum";
        if(column==2) return "Iznos";
        if(column==3) return "Porudzbine";
        
        return null;
    }
    
    private String getOrdersid(Receipt receipt){
       StringBuilder stringBuilder = new StringBuilder();
       List<Order> orders = receipt.getOrders();
        for (int i = 0; i < orders.size(); i++) {
        Order order = orders.get(i);
        stringBuilder.append(order.getOrderId());
        if (i < orders.size() - 1) {
            stringBuilder.append(", ");
        }
    }
    return stringBuilder.toString();
    }
    
}
