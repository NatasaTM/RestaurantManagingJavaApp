package restaurant.client.ui.component.table.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderItemChefTableModel extends AbstractTableModel {

    private List<OrderItem> orderitems;
    private Order order;
    private boolean isReady;

    public OrderItemChefTableModel(List<OrderItem> orderitems,Order order) {
        if(orderitems==null){
            this.orderitems = new ArrayList<>();
        }
        this.orderitems = orderitems;
        this.order = order;
        isReady = false;
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
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        OrderItem orderItem = orderitems.get(rowIndex);
       
                if (columnIndex == 0) {
            return order.getOrderId();
        }
                if (columnIndex == 1) {
            return orderItem.getOrderItemId();
        }
        if (columnIndex == 2) {
            return orderItem.getMenuItem().getName();
        }
        if (columnIndex == 3) {
            return orderItem.getMenuItem().getDescription();
        }
        if (columnIndex == 4) {
            return orderItem.getQuantity();
        }
        if (columnIndex == 5) {
            return orderItem.getIsReady();
        }
        
    
        return null;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "ID porudzbine";
        }
        if (column == 1) {
            return "ID stavke";
        }
        if (column == 2) {
            return "Naziv";
        }
        if (column == 3) {
            return "Opis";
        }
        if (column == 4) {
            return "Kolicina";
        }
        if (column == 5) {
            return "Spremno";
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 5) {
            return Boolean.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 5) {
            isReady = (Boolean) aValue;
            OrderItem orderItem = orderitems.get(rowIndex);
            orderItem.setIsReady(isReady);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 5) {
            return true;
        }
        return false;
    }

    public void setOrderitems(List<OrderItem> orderitems) {
        this.orderitems = orderitems;
    }
    
 
    
    

}
