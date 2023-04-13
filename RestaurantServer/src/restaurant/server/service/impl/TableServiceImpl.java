package restaurant.server.service.impl;

import java.util.List;
import restaurant.common.domain.Table;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.impl.TableRepositoryImpl;
import restaurant.server.service.TableService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class TableServiceImpl implements TableService{
     private final GenericRepository<Table,Integer> tableRepository;

    public TableServiceImpl() {
       tableRepository = new TableRepositoryImpl();
    }

    @Override
    public List<Table> getAll() throws Exception {
         return tableRepository.getAll();
    }

    @Override
    public void add(Table table) throws Exception {
      tableRepository.add(table);
    }

    @Override
    public void delete(Table table) throws Exception {
      tableRepository.delete(table);
    }

    @Override
    public void update(Table table) throws Exception {
         tableRepository.update(table);
    }

    @Override
    public List<Table> isAvailable() throws Exception {
        String query = "SELECT `tableId`,`numberOfSeats`,`isAvailable` FROM `table` WHERE `isAvailable`=1";
        return tableRepository.findByQuery(query);
        //return tableRepository.isAvailable();
    }

    @Override
    public Table getById(Integer id) throws Exception {
         return tableRepository.findById(id);
    }

    @Override
    public void setIsAvailable(Table table,boolean isAvailable) throws Exception {
       table.setIsAvailable(isAvailable);
       tableRepository.update(table);
    }
     
     
    
    
}
