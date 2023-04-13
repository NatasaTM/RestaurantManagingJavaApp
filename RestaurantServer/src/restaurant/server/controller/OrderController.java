package restaurant.server.controller;

import java.time.LocalDate;
import java.util.List;
import restaurant.common.domain.Employee;
import restaurant.common.domain.MenuItemType;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;
import restaurant.common.domain.Table;
import restaurant.server.service.OrderService;
import restaurant.server.service.impl.OrderServiceImpl;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderController {
    
    private final static OrderController instance = new OrderController();
    
    private final OrderService orderService;

    public OrderController() {
        this.orderService = new OrderServiceImpl();
    }

    public static OrderController getInstance() {
        return instance;
    }
    
    public void addOrder (Order order) throws Exception{
        orderService.add(order);
    }
    
    public List<Order> findByCondition(LocalDate date,Boolean statusReady,Boolean statusPaied,Employee employee,Table table) throws Exception{
        return orderService.findByCondition(date, statusReady, statusPaied, employee, table);
    }
    
    public List<Order> getAllOrders() throws Exception{
        return orderService.getAll();
    }
    
    public Order findOrderById(Long id) throws Exception{
        return orderService.findById(id);
    }
    
    public void orderUpdate(Order order) throws Exception{
        orderService.update(order);
    }
    
    public List<OrderItem> findOrderItemsByQuery(Order order,MenuItemType menuItemType,boolean isReady) throws Exception{
        return orderService.findOrderItemByQuery(order, menuItemType, isReady);
    }
    
    public OrderItem findOrderItemById(Integer id) throws Exception{
        return orderService.findOrderItemById(id);
    }
    
    public void udateOrderItem(OrderItem orderItem) throws Exception{
        orderService.updateOrderItem(orderItem);
    }
}
