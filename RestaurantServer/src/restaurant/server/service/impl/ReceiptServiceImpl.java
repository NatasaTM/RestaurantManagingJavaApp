package restaurant.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import restaurant.common.domain.Employee;
import restaurant.common.domain.Order;
import restaurant.common.domain.Receipt;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.impl.OrderRepositoryImpl;
import restaurant.server.repository.impl.ReceiptRepositoryImpl;
import restaurant.server.service.ReceiptService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ReceiptServiceImpl implements ReceiptService {

    private GenericRepository<Receipt, Long> receiptRepository;

    public ReceiptServiceImpl() {
        receiptRepository = new ReceiptRepositoryImpl();

    }

    @Override
    public void add(Receipt receipt) throws Exception {
        receiptRepository.add(receipt);
    }

    @Override
    public List<Receipt> getAll() throws Exception {
        List<Receipt> receipts = receiptRepository.getAll();
        List<Order> ordersTemp = null;
        Order orderTemp = null;
        for (int i = 0; i < receipts.size(); i++) {
            ordersTemp = receipts.get(i).getOrders();
            for (int j = 0; j < ordersTemp.size(); j++) {
                orderTemp = ordersTemp.get(j);
                orderTemp = OrderRepositoryImpl.getInstance().findById(orderTemp.getOrderId());
                ordersTemp.set(j, orderTemp);
            }
        }

        return receipts;
    }

    @Override
    public Receipt findById(Long id) throws Exception {
        Receipt receipt = receiptRepository.findById(id);
        List<Order> ordersTemp = receipt.getOrders();
        Order orderTemp = null;
        for (int j = 0; j < ordersTemp.size(); j++) {
            orderTemp = ordersTemp.get(j);
            orderTemp = OrderRepositoryImpl.getInstance().findById(orderTemp.getOrderId());
            ordersTemp.set(j, orderTemp);
        }
        // return receiptRepository.findById(id);
        return receipt;
    }

    @Override
    public List<Receipt> findUnpaiedReceipts() throws Exception {
        String query = " SELECT `receiptId`,`date`,`totalAmount` FROM `receipt` \n"
                + "WHERE `receiptId` NOT IN (SELECT `receiptId` FROM `payment`)";
        return receiptRepository.findByQuery(query);
    }

    @Override
    public List<Receipt> findUnpaiedReceiptsByEmployee(Employee employee) throws Exception {
        List<Receipt> receipts = findUnpaiedReceipts();
        List<Receipt> filteredReceipts = new ArrayList<>();

        for (Receipt receipt : receipts) {
            List<Order> orders = new ArrayList<>(receipt.getOrders());

            for (Order order : orders) {
                Order fullOrder = OrderRepositoryImpl.getInstance().findById(order.getOrderId());
                order.setEmployee(fullOrder.getEmployee());
            }

            for (Order order : orders) {
                if (order.getEmployee().equals(employee)) {
                    filteredReceipts.add(receipt);
                    break;
                }
            }
        }

        return filteredReceipts;

    }

}
