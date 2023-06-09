package restaurant.client.ui.component.table.model;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import restaurant.common.domain.Order;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderChefTableModel extends AbstractTableModel {

    private List<Order> orders;

    public OrderChefTableModel(List<Order> orders) {
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
        Order order = orders.get(rowIndex);
        if (columnIndex == 0) {
            return order.getOrderId();
        }
        if (columnIndex == 1) {
            return order.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy." + " " + "HH:mm:ss"));
        }
        if (columnIndex == 2) {
            return order.getEmployee().getId() + ", " + order.getEmployee().getFirstname() + " " + order.getEmployee().getLastname();
        }
        if (columnIndex == 3) {
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
            return "Zaposleni";
        }
        if (column == 3) {
            return "Sto";
        }
        if (column == 4) {
            return "Spremna";
        }

        return null;
    }
    
    public void updateTable(List<Order> orders){
        this.orders = orders;
        fireTableDataChanged();
    }

}
