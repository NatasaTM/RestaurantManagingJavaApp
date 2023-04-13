package restaurant.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Receipt implements Serializable{
    private Long id;
    private LocalDateTime date;
    private BigDecimal amount;
    private List<Order> orders;

    public Receipt() {
    }

    public Receipt(Long id, LocalDateTime date, BigDecimal amount, List<Order> orders) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.orders = orders;
    }

    public Receipt(LocalDateTime date) {
        this.date = date;
    }

    public Receipt(LocalDateTime date, BigDecimal amount, List<Order> orders) {
        this.date = date;
        this.amount = amount;
        this.orders = orders;
    }

    public Receipt(Long id) {
        this.id = id;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy."+" "+" HH:mm:ss");
        return "Receipt{" + "id=" + id + ", date=" + date.format(dtf) + ", amount=" + amount + ", orders=" + orders + '}';
    }
    
    
}
