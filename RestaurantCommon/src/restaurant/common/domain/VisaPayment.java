package restaurant.common.domain;

import java.io.Serializable;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class VisaPayment extends PaymentMethod implements Serializable{

    @Override
    protected boolean paymentAuthorization() {
         System.out.println("Payment Authorization processing");
         return true;
    }

    @Override
    protected boolean checkingSpecificPaymentPrerequisite() {
        System.out.println("The account balance is being checked.");
        return true;
    }

    @Override
    protected void paymentExecution() {
         System.out.println("Payment is being processed");
    }
    
}
