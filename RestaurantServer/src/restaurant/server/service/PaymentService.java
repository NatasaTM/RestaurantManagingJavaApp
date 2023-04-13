package restaurant.server.service;

import java.util.List;
import restaurant.common.domain.Payment;
import restaurant.common.domain.Receipt;

/**
 *
 * @author Natasa Todorov Markovic
 */
public interface PaymentService {
    void add(Payment payment) throws Exception;
    List<Payment> getAll() throws Exception;
    Payment findByReceipt(Receipt receipt) throws Exception;
}
