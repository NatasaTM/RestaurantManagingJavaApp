package restaurant.client.ui.component.table.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import restaurant.common.domain.OrderItem;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderItemsTableModel extends AbstractTableModel {

   // private static OrderItemsTableModel instance;

    private List<OrderItem> orderitems;

    public OrderItemsTableModel() {
        orderitems = new ArrayList<>();
    }


    public List<OrderItem> getOrderitems() {
        return orderitems;
    }

    public OrderItemsTableModel setOrderitems(List<OrderItem> orderitems) {
        this.orderitems = orderitems;
        return this;
    }

    public void addOrderItem(OrderItem orderItem) {
        if (!orderitems.contains(orderItem)) {
            orderitems.add(orderItem);
        }else{
            for (int i = 0; i < orderitems.size(); i++) {
                if(orderitems.get(i).equals(orderItem)){
                     orderitems.get(i).setQuantity(orderitems.get(i).getQuantity() + orderItem.getQuantity());
                     break;
                }

           
        }
            
        }
        

        fireTableDataChanged();
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderitems.remove(orderItem);
        fireTableDataChanged();

    }

    @Override
    public int getRowCount() {
        if (orderitems == null) {
            return 0;
        }
        return orderitems.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "ID";
        }
        if (column == 1) {
            return "Naziv";
        }
        if (column == 2) {
            return "Cena";
        }
        if (column == 3) {
            return "Kolicina";
        }
        if (column == 4) {
            return "Ukupno";
        }

        return "n/a";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DecimalFormat df = new DecimalFormat("0.00");
        OrderItem orderItem = orderitems.get(rowIndex);
        if (columnIndex == 0) {
            return orderItem.getMenuItem().getId();
        }
        if (columnIndex == 1) {
            return orderItem.getMenuItem().getName();
        }
        if (columnIndex == 2) {
            return df.format(orderItem.getMenuItem().getPrice());
        }
        if (columnIndex == 3) {
            return orderItem.getQuantity();
        }
        BigDecimal amount = orderItem.getMenuItem().getPrice().multiply(BigDecimal.valueOf((double) orderItem.getQuantity()));
        if (columnIndex == 4) {
            return df.format(amount);
        }

        return "n/a";
    }

}
