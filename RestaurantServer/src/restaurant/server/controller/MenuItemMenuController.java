package restaurant.server.controller;

import java.util.List;
import restaurant.common.domain.Menu;
import restaurant.common.domain.MenuCategory;
import restaurant.common.domain.MenuItem;
import restaurant.common.domain.MenuItemType;
import restaurant.server.service.MenuCategoryService;
import restaurant.server.service.MenuItemService;
import restaurant.server.service.MenuService;
import restaurant.server.service.impl.MenuCategoryServiceImpl;
import restaurant.server.service.impl.MenuItemServiceImpl;
import restaurant.server.service.impl.MenuServiceImpl;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuItemMenuController {

    private final static MenuItemMenuController instance = new MenuItemMenuController();

    private final MenuItemService menuItemService;
    private final MenuCategoryService menuCategoryService;
    private final MenuService menuService;

    private MenuItemMenuController() {
        this.menuItemService = new MenuItemServiceImpl();
        this.menuCategoryService = new MenuCategoryServiceImpl();
        this.menuService = new MenuServiceImpl();
    }

    public static MenuItemMenuController getInstance() {
        return instance;
    }

    public void addMenuItem(MenuItem menuItem) throws Exception {
        menuItemService.addMenuItem(menuItem);
    }

    public List<MenuCategory> getAllMenuCategories() throws Exception {
        return menuCategoryService.getAll();
    }
    
    public List<MenuItem> getAllMenuItems() throws Exception{
        return menuItemService.getAll();
    }
    
    public List<MenuItem> getAllMenuItemsByCategory (String categoryName) throws Exception{
       return menuItemService.getAllByCategory(categoryName);
    }
    public List<MenuItem> getAllMenuItemsByType(MenuItemType menuItemType) throws Exception{
        return menuItemService.getAllByType(menuItemType);
    }
    
    public void updateMenuItem(MenuItem menuItem) throws Exception{
        menuItemService.update(menuItem);
    }
    
    public void deleteMenuItem(MenuItem menuItem) throws Exception{
        menuItemService.delete(menuItem);
    }
    
    public void addMenu(Menu menu) throws Exception{
        menuService.add(menu);
    }
    
    public MenuItem menuItemFindById(Integer id) throws Exception{
       return menuItemService.findById(id);
    }
    
    public Menu findMenuById(Integer id) throws Exception{
        return menuService.findById(id);
    }
    
    public List<Menu> menuGetAll() throws Exception{
        return menuService.getAll();
    }
    
    public List<MenuItem> menuItemGetAllByCategoryFromMenu(String categoryName,Menu menu) throws Exception{
        return menuService.getAllItemsByCategory(categoryName, menu);
    }
    
    public void menuUpdate(Menu menu) throws Exception{
        menuService.update(menu);
    }
    
    public void menuDelete(Menu menu) throws Exception{
        menuService.delete(menu);
    }
    
    public void menuAddItem(MenuItem menuItem,Menu menu) throws Exception{
        menuService.addMenuItem(menuItem, menu);
    }
    
    public void menuDeleteMenuItem(Menu menu, MenuItem menuItem) throws Exception{
        menuService.deleteMenuItem(menu, menuItem);
    }
    
    public void menuCategoryAdd(MenuCategory menuCategory) throws Exception{
        menuCategoryService.addCategory(menuCategory);
    }
    
    public List<Menu> getAllMenuByStatus() throws Exception{
        return menuService.getAllByStatus();
    }
    

}
