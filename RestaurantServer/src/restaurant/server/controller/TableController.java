package restaurant.server.controller;

import java.util.List;
import restaurant.common.domain.Table;
import restaurant.server.service.TableService;
import restaurant.server.service.impl.TableServiceImpl;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class TableController {
    
    private final static TableController instance = new TableController();
    
    private TableService tableService;

    private TableController() {
        this.tableService = new TableServiceImpl();
    }

    public static TableController getInstance() {
        return instance;
    }
    
    public void addTable(Table table) throws Exception{
        tableService.add(table);
    }
    
    public List<Table> getAllTables() throws Exception{
        return tableService.getAll();
    }
    
    public void deleteTable(Table table) throws Exception{
        tableService.delete(table);
    }
    
    public void updateTable(Table table) throws Exception{
        tableService.update(table);
    }
    
    public List<Table> tableIsAvailable() throws Exception{
        return tableService.isAvailable();
    }
    
    public void tableSetIsAvailable(Table table,boolean isAvailable) throws Exception{
        tableService.setIsAvailable(table, isAvailable);
    }
    
}
