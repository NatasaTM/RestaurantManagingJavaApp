package restaurant.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Order implements Serializable {

    private Long orderId;
    private LocalDateTime date;
    private BigDecimal totalAmount;
    private boolean orderReadyStatus;
    private boolean orderPaidStatus;
    private List<OrderItem> orderItems;
    private Employee employee;
    private Table table;

    public Order() {
        orderItems = new ArrayList<>();
    }

    public Order(Long orderId, LocalDateTime date, BigDecimal totalAmount, boolean orderReadyStatus, boolean orderPaidStatus, List<OrderItem> orderItems, Employee employee, Table table) {
        this.orderId = orderId;
        this.date = date;
        this.totalAmount = totalAmount;
        this.orderReadyStatus = orderReadyStatus;
        this.orderPaidStatus = orderPaidStatus;
        this.orderItems = orderItems;
        this.employee = employee;
        this.table = table;
    }

    public Order(BigDecimal totalAmount, List<OrderItem> orderItems, Employee employee, Table table) {
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.employee = employee;
        this.date = LocalDateTime.now();
        this.table = table;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isOrderReadyStatus() {
        return orderReadyStatus;
    }

    public void setOrderReadyStatus(boolean orderReadyStatus) {
        this.orderReadyStatus = orderReadyStatus;
    }

    public boolean isOrderPaidStatus() {
        return orderPaidStatus;
    }

    public void setOrderPaidStatus(boolean orderPaidStatus) {
        this.orderPaidStatus = orderPaidStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy." + " " + " HH:mm:ss");
        DecimalFormat df = new DecimalFormat("0.00");
        return "ID: " + orderId + ", Datum: " + date.format(dtf) + ", Ukupan iznos: " + df.format(totalAmount) + ", Status gotova: " + orderReadyStatus + ", Status placena: " + orderPaidStatus + ", Zaposleni: " + employee + ", Sto = " + table + ",\nStavke porudzbine: " + orderItems;
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
        final Order other = (Order) obj;
        if (!Objects.equals(this.orderId, other.orderId)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return Objects.equals(this.employee, other.employee);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

}
