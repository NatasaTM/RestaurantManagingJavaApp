package restaurant.server.service;

import java.util.List;
import restaurant.common.domain.MenuCategory;


/**
 *
 * @author Natasa Todorov Markovic
 */
public interface MenuCategoryService {
    
    List<MenuCategory> getAll() throws Exception;
    void addCategory(MenuCategory menuCategory) throws Exception;
    
}
