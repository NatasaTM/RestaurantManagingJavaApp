package restaurant.client.ui.component.table.model;

import java.util.List;
import javax.swing.AbstractListModel;
import restaurant.common.domain.OrderItem;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderItemListModel extends AbstractListModel{
    private List<OrderItem> orderItems;

    public OrderItemListModel(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    
    @Override
    public int getSize() {
        return orderItems.size();
    }

    @Override
    public OrderItem getElementAt(int index) {
        return orderItems.get(index);
    }
    
}
