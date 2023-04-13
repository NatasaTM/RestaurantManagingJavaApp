package restaurant.server.controller;

import java.util.List;
import restaurant.common.domain.Payment;
import restaurant.common.domain.Receipt;
import restaurant.server.service.PaymentService;
import restaurant.server.service.impl.PaymentServiceImpl;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class PaymentController {
    
    private final static PaymentController instance = new PaymentController();
    private PaymentService paymentService;

    private PaymentController() {
        this.paymentService = new PaymentServiceImpl();
    }

    public static PaymentController getInstance() {
        return instance;
    }
    
    public void addPayment(Payment payment) throws Exception{
        paymentService.add(payment);
    }
    
    public List<Payment> getAllPayments() throws Exception{
      return  paymentService.getAll();
    }
    
    public Payment findByReceipt(Receipt receipt) throws Exception{
        return paymentService.findByReceipt(receipt);
    }
    
}
