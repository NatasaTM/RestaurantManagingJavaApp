package restaurant.common.domain;

import java.math.BigDecimal;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class CashPayment extends PaymentMethod{
    private  BigDecimal cashAmount=BigDecimal.valueOf(0);
    private BigDecimal change=BigDecimal.valueOf(0);

    
    
    
    @Override
    protected boolean paymentAuthorization() {
         return true;
    }

    @Override
    protected boolean checkingSpecificPaymentPrerequisite() {
        if(cashAmount.compareTo(getAmount())==-1){
             return false;
         }
        return true;
    }

    @Override
    protected void paymentExecution() {
         if(cashAmount.compareTo(getAmount())==1){
             change=cashAmount.subtract(getAmount());
         }
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    
    
}
