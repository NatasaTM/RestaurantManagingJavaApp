package restaurant.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Natasa Todorov Markovic
 */
public abstract class PaymentMethod implements Serializable{

    private BigDecimal amount;
    

    protected abstract boolean paymentAuthorization();

    protected abstract boolean checkingSpecificPaymentPrerequisite();

    protected abstract void paymentExecution();

    protected String paymentReport() {
        return "Payment successful";
    }

    public final void executePayment(BigDecimal amount) {
        this.amount = amount;

        if (paymentAuthorization()) {
            if (checkingSpecificPaymentPrerequisite()) {
                paymentExecution();
                paymentReport();

            }
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }
    

}
