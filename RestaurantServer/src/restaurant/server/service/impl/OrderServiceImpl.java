package restaurant.server.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import restaurant.common.domain.Employee;
import restaurant.common.domain.MenuItem;
import restaurant.common.domain.MenuItemType;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;
import restaurant.common.domain.Table;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.impl.MenuItemRepositoryImpl;
import restaurant.server.repository.impl.OrderItemRepositoryImpl;
import restaurant.server.repository.impl.OrderRepositoryImpl;
import restaurant.server.service.OrderService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderServiceImpl implements OrderService {

    private GenericRepository<OrderItem, Integer> orderItemRepository;
    private GenericRepository<MenuItem, Integer> menuItemRepository;

    public OrderServiceImpl() {
        this.orderItemRepository = new OrderItemRepositoryImpl();
        this.menuItemRepository = new MenuItemRepositoryImpl();
    }

    @Override
    public List<Order> getAll() throws Exception {
        return OrderRepositoryImpl.getInstance().getAll();
    }

    @Override
    public void add(Order order) throws Exception {
        OrderRepositoryImpl.getInstance().add(order);
    }

    @Override
    public void addOrderItemsToOrder(Order order) throws Exception {

    }

    @Override
    public List<Order> findByQuery(Employee employee, Table table) throws Exception {
        LocalDate localDate = LocalDate.now();

        localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String query = "SELECT o.`orderId`,o.`date`,o.`totalAmount`,o.`orderReadyStatus`,o.`orderPaidStatus`,o.`employeeId`,e.`firstname`,e.`lastname`,e.`birthdate`,e.`adress`,e.`jmbg`,e.`cityZipcode`,c.`name`,o.`tableId`,t.`numberOfSeats`,t.`isAvailable` FROM `order` o\n"
                + " JOIN `employee` e ON e.`employeeId`=o.`employeeId`\n"
                + " JOIN city c ON c.`zipcode`=e.`cityZipcode`\n"
                + " JOIN `table` t ON t.`tableId` = o.`tableId`\n"
                + " WHERE o.`orderPaidStatus`=0 AND o.`employeeId`=" + employee.getId() + " AND o.tableId =" + table.getTableNumber() + " AND o.`date` RLIKE '" + localDate + "'\n"
                + " ORDER BY 2;";

        List<Order> orders = OrderRepositoryImpl.getInstance().findByQuery(query);
        return orders;
    }

    @Override
    public Order findById(Long id) throws Exception {
        return OrderRepositoryImpl.getInstance().findById(id);
    }

    @Override
    public List<Order> findByCondition(LocalDate date, Boolean ready, Boolean paied, Employee employee, Table table) throws Exception {

        return OrderRepositoryImpl.getInstance().findByCondition(date, ready, paied, employee, table);
    }

    @Override
    public void update(Order order) throws Exception {
        OrderRepositoryImpl.getInstance().update(order);
    }

    @Override
    public List<OrderItem> findOrderItemByQuery(Order order, MenuItemType menuItemType, boolean isReady) throws Exception {
        String query = "SELECT oi.`orderId`,oi.`orderItemsId`,oi.`menuItemId`,oi.`quantity`,oi.`orderItemReadyStatus`,mi.`name`,mi.`description`,mi.`price`,mi.`categoryId`,mi.`menuItemType`,c.`categoryName` FROM `orderitems` oi\n"
                + "JOIN `menuitem` mi ON mi.`menuItemId` = oi.`menuItemId`\n"
                + "JOIN `menucategory` c ON c.`categoryId` = mi.`categoryId`\n"
                + "WHERE oi.`orderId` =" + order.getOrderId() + " AND mi.`menuItemType` = '" + menuItemType + "' AND oi.`orderItemReadyStatus` = " + isReady;

        return orderItemRepository.findByQuery(query);
    }

    @Override
    public OrderItem findOrderItemById(Integer id) throws Exception {
        OrderItem orderItem = orderItemRepository.findById(id);
        MenuItem menuItem = orderItem.getMenuItem();
        menuItem = menuItemRepository.findById(menuItem.getId());
        orderItem.setMenuItem(menuItem);

        return orderItem;
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) throws Exception {
         orderItemRepository.update(orderItem);
    }

}
