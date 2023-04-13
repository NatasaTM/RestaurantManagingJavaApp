package restaurant.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class PaymentMethodFactory implements Serializable{
    
    public static PaymentMethod getPaymentMethod(PaymentMethodType paymentMethodType) {
        PaymentMethod payment = null;
        switch (paymentMethodType) {
            case VISA:
                payment = new VisaPayment();
                break;
            case CASH:
                payment = new CashPayment();
                break;
            // add more cases here for other payment methods
        }
        return payment;
    }
}
