package restaurant.server.service;

import java.time.LocalDate;
import java.util.List;
import restaurant.common.domain.Employee;
import restaurant.common.domain.MenuItemType;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;
import restaurant.common.domain.Table;

/**
 *
 * @author Natasa Todorov Markovic
 */
public interface OrderService {

    List<Order> getAll() throws Exception;

    void add(Order order) throws Exception;

    void addOrderItemsToOrder(Order order) throws Exception;

    List<Order> findByQuery(Employee employee, Table table) throws Exception;

    Order findById(Long id) throws Exception;

    List<Order> findByCondition(LocalDate date, Boolean ready, Boolean paied, Employee employee, Table table) throws Exception;

    void update(Order order) throws Exception;

    List<OrderItem> findOrderItemByQuery(Order order,MenuItemType menuItemType,boolean isReady) throws Exception;
    
    OrderItem findOrderItemById(Integer id) throws Exception;
    
    void updateOrderItem(OrderItem orderItem) throws Exception;

}
