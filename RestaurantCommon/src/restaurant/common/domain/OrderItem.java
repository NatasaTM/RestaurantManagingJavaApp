package restaurant.common.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderItem implements Serializable{
    private Integer orderItemId;
    private MenuItem menuItem;
    private int quantity;
    private Boolean isReady;

    public OrderItem() {
    }

    public OrderItem(Integer orderItemId, MenuItem menuItem, int quantity,Boolean isReady) {
        this.orderItemId = orderItemId;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.isReady = isReady;
    }

    public Boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }
    
    

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return menuItem.getName() +" cena: "+menuItem.getPrice()+ " rsd, Kolicina:" + quantity ;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderItem other = (OrderItem) obj;
        return Objects.equals(this.menuItem, other.menuItem);
    }

    
    
    
    
}
