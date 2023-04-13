package restaurant.server.repository;

import java.util.List;
import restaurant.common.domain.Menu;
import restaurant.common.domain.MenuItem;

/**
 *
 * @author Natasa Todorov Markovic
 */
public interface MenuRepository extends GenericRepository<Menu, Integer>{

    List<MenuItem> findMenuItemByQuery(String query) throws Exception;

    void addMenuItem(MenuItem menuItem,Menu menu) throws Exception;
    
    void deleteMenuItem(Menu menu, MenuItem menuItem)throws Exception;

   
    

    

    
    

}
