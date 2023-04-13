package restaurant.server.service;

import java.math.BigDecimal;
import java.util.List;
import restaurant.common.domain.MenuItem;
import restaurant.common.domain.MenuItemType;


/**
 *
 * @author Natasa Todorov Markovic
 */
public interface MenuItemService {
    
    List<MenuItem> getAll()throws Exception;
    void addMenuItem(MenuItem menuItem)throws Exception;
    List<MenuItem> getAllByCategory(String categoryName)throws Exception;
    public void delete(MenuItem menuItem) throws Exception;
    public void update(MenuItem menuItem) throws Exception;
    MenuItem findById(Integer id) throws Exception;
    List<MenuItem> getAllByType(MenuItemType menuItemType)throws Exception;
    
}
