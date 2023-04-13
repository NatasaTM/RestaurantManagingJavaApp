package restaurant.server.service.impl;

import java.util.List;
import restaurant.common.domain.Order;
import restaurant.common.domain.Payment;
import restaurant.common.domain.Receipt;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.impl.OrderRepositoryImpl;
import restaurant.server.repository.impl.PaymentRepositoryImpl;
import restaurant.server.service.PaymentService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class PaymentServiceImpl implements PaymentService {

    private GenericRepository<Payment, Long> paymentRepository;

    public PaymentServiceImpl() {
        this.paymentRepository = new PaymentRepositoryImpl();

    }

    @Override
    public void add(Payment payment) throws Exception {
        paymentRepository.add(payment);
        List<Order> orders = payment.getReceipt().getOrders();
        for (Order o : orders) {
            o.setOrderPaidStatus(true);
            o.setOrderReadyStatus(true);
            OrderRepositoryImpl.getInstance().update(o);
        }
    }

    @Override
    public List<Payment> getAll() throws Exception {
        return paymentRepository.getAll();
    }

    @Override
    public Payment findByReceipt(Receipt receipt) throws Exception {
        String query = "SELECT p.`paymentId`,p.`receiptId`,p.`paymentMethodId`,p.`date`,pm.`paymentMethodName`,r.`date`,r.`totalAmount` FROM `payment` p\n"
                + "JOIN `paymentmethod` pm ON pm.`paymentMethodId` = p.`paymentMethodId`\n"
                + "JOIN `receipt` r ON r.`receiptId` = p.`receiptId` \n"
                + "WHERE r.`receiptId` =" + receipt.getId();
        List<Payment> payments = paymentRepository.findByQuery(query);
        return payments.get(0);
    }

}
