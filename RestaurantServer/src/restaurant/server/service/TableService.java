package restaurant.server.service;

import java.util.List;
import restaurant.common.domain.Table;


/**
 *
 * @author Natasa Todorov Markovic
 */
public interface TableService {
     List<Table> getAll()throws Exception;
    void add(Table table)throws Exception;
    void delete(Table table)throws Exception;
    void update(Table table) throws Exception;
     List<Table> isAvailable()throws Exception;
     Table getById(Integer id)throws Exception;
    void setIsAvailable(Table table,boolean isAvailable) throws Exception;
    
}
