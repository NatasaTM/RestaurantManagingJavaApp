package restaurant.server.service;

import java.util.List;
import restaurant.common.domain.Employee;
import restaurant.common.domain.Receipt;

/**
 *
 * @author Natasa Todorov Markovic
 */
public interface ReceiptService {
    void add(Receipt receipt) throws Exception;
    List<Receipt> getAll() throws Exception;
    Receipt findById(Long id) throws Exception;
    List<Receipt> findUnpaiedReceipts() throws Exception;
    List<Receipt> findUnpaiedReceiptsByEmployee(Employee employee) throws Exception;
}
