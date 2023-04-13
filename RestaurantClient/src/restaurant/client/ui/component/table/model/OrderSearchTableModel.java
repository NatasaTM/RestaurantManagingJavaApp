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
public class OrderSearchTableModel extends AbstractTableModel {

    private List<Order> orders;

    public OrderSearchTableModel(List<Order> orders) {
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
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DecimalFormat df = new DecimalFormat("0.00");
        Order order = orders.get(rowIndex);
        if (columnIndex == 0) {
            return order.getOrderId();
        }
        if (columnIndex == 1) {
            return order.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy." + " " + "HH:mm:ss"));
        }
        if (columnIndex == 2) {
            return df.format(order.getTotalAmount());
        }
        if (columnIndex == 3) {
            return order.isOrderReadyStatus();
        }
        if (columnIndex == 4) {
            return order.isOrderPaidStatus();
        }
        
        if (columnIndex == 5) {
            return order.getEmployee().getId() + ", " + order.getEmployee().getFirstname() + " " + order.getEmployee().getLastname();
        }
        if (columnIndex == 6) {
            return order.getTable().getTableNumber();
        }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "ID";
        }
        if (column == 1) {
            return "Datum";
        }
        if (column == 2) {
            return "Iznos";
        }
        if (column == 3) {
            return "Spremna";
        }
        if (column == 4) {
            return "Placena";
        }
        
        if (column == 5) {
            return "Zaposleni";
        }
        if (column == 6) {
            return "Sto";
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex==3 ){
            return Boolean.class;
        }
        if(columnIndex==4 ){
            return Boolean.class;
        }
        return super.getColumnClass(columnIndex); 
    }

    

}
