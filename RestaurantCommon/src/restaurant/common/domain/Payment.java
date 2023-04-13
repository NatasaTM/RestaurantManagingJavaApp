package restaurant.common.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Payment implements Serializable{
    
    private Long id;
    private LocalDateTime date;
    private Receipt receipt;
    private PaymentMethodType paymentMethodType;

    public Payment() {
    }

    public Payment(Long id, LocalDateTime date, Receipt receipt, PaymentMethodType paymentMethodType) {
        this.id = id;
        this.date = date;
        this.receipt = receipt;
        this.paymentMethodType = paymentMethodType;
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

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    @Override
    public String toString() {
        return "Payment{" + "id=" + id + ", date=" + date + ", receipt=" + receipt + ", paymentMethodType=" + paymentMethodType + '}';
    }

    

    
    
    
}
