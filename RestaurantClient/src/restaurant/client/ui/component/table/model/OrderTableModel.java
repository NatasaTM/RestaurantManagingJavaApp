package restaurant.client.ui.component.table.model;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import restaurant.common.domain.Order;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderTableModel extends AbstractTableModel {

    private List<Order> orders;
   

    public OrderTableModel(List<Order> orders) {
        this.orders = orders;
       

    }

    @Override
    public int getRowCount() {
        if (orders == null) {
            return 0;
        }
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DecimalFormat df = new DecimalFormat("0.00");
        Order order = orders.get(rowIndex);
        if (columnIndex == 0) {
            return order.getOrderId();
        }
        if (columnIndex == 1) {
            return order.getTable().getTableNumber().toString();
        }
        if (columnIndex == 2) {
            return order.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy." + " " + "HH:mm:ss"));
        }
        if (columnIndex == 3) {
            return df.format(order.getTotalAmount());
        }

        

        return "n/a";

    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "ID";
        }
        if (column == 1) {
            return "Sto";
        }
        if (column == 2) {
            return "Datum";
        }
        if (column == 3) {
            return "Ukupan iznos";
        }
        

        return "n/a";

    }

    
  
    
    

}
