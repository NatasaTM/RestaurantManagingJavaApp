package restaurant.server.service;

import java.util.List;
import restaurant.common.domain.Menu;
import restaurant.common.domain.MenuItem;


/**
 *
 * @author Natasa Todorov Markovic
 */
public interface MenuService {

    List<Menu> getAll() throws Exception;

    // List<MenuItem> getAllItems(Menu menu) throws Exception;
    List<MenuItem> getAllItemsByCategory(String categoryName, Menu menu) throws Exception;

    void add(Menu menu) throws Exception;

    void addMenuItem(MenuItem menuItem, Menu menu) throws Exception;

    void deleteMenuItem(Menu menu, MenuItem menuItem) throws Exception;

    public Menu findById(Integer id) throws Exception;

    void update(Menu menu) throws Exception;

    List<Menu> getAllByStatus() throws Exception;

    void delete(Menu menu) throws Exception;

}
