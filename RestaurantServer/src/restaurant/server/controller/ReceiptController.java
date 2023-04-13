package restaurant.server.controller;

import java.util.List;
import restaurant.common.domain.Receipt;
import restaurant.server.service.ReceiptService;
import restaurant.server.service.impl.ReceiptServiceImpl;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ReceiptController {
    
    private static final ReceiptController instance = new ReceiptController();
    
    private ReceiptService receiptService;

    private ReceiptController() {
        this.receiptService = new ReceiptServiceImpl();
    }

    public static ReceiptController getInstance() {
        return instance;
    }
    
   public Receipt receiptAdd(Receipt receipt) throws Exception{
       receiptService.add(receipt);
       return receipt;
   } 
   
   public List<Receipt> getAllReceipts() throws Exception{
       return  receiptService.getAll();
   }
   
   public Receipt findById(Long id) throws Exception{
       return receiptService.findById(id);
   }
   
   public List<Receipt> findUnpaiedReceipts() throws Exception{
       return receiptService.findUnpaiedReceipts();
   }
}
