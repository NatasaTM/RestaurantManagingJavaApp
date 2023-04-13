package restaurant.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import restaurant.server.service.MenuService;
import restaurant.common.domain.Menu;
import restaurant.common.domain.MenuItem;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.MenuRepository;
import restaurant.server.repository.impl.MenuItemRepositoryImpl;
import restaurant.server.repository.impl.MenuRepositoryImpl;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final GenericRepository<MenuItem, Integer> menuItemRepository;

    public MenuServiceImpl() {
        menuRepository = new MenuRepositoryImpl();
        menuItemRepository = new MenuItemRepositoryImpl();

    }

    @Override
    public List<Menu> getAll() throws Exception {
        return menuRepository.getAll();

    }

//    @Override
//    public List<MenuItem> getAllItems(Menu menu) throws Exception {
//        return menuRepository.getAllItems(menu);
//        
//    }
    @Override
    public void add(Menu menu) throws Exception {
        String query = "SELECT `concreteMenuId`,`concreteMenuName`,`date`,`isActive` FROM concretemenu WHERE concreteMenuName='" + menu.getName() + "'";
        List<Menu> menus = menuRepository.findByQuery(query);
        if (menus.isEmpty()) {
            menuRepository.add(menu);
        } else {
            throw new Exception("Jelovnik sa tim imenom vec postoji!");
        }

    }

    @Override
    public List<MenuItem> getAllItemsByCategory(String categoryName, Menu menu) throws Exception {

//         List<MenuItem> mi = menu.getMenuItems();
//         List<MenuItem> menuItems = new ArrayList<>();
//         for(MenuItem m : mi){
//             if (m.getMenuCategory().getName().equals(categoryName)){
//                 menuItems.add(m);
//             }
//         }
//        
//        return menuItems;
        Menu m = findById(menu.getId());
        List<MenuItem> menuItems = m.getMenuItems();
        List<MenuItem> filteredItems = new ArrayList<>();
        for (MenuItem mi : menuItems) {
            if(mi.getMenuCategory().getName().equals(categoryName)){
                filteredItems.add(mi);
            }
        }
        
        return filteredItems;
        //return menuRepository.getAllItemsByCategory(categoryName, menu);

    }

    @Override
    public void deleteMenuItem(Menu menu, MenuItem menuItem) throws Exception {
        menuRepository.deleteMenuItem(menu, menuItem);
    }

    @Override
    public void addMenuItem(MenuItem menuItem, Menu menu) throws Exception {
        String query = "SELECT mcm.`menuItemId`, mi.name, mi.price,cm.`concreteMenuId` FROM `menuitem_concrete_menu` mcm \n"
                + "JOIN menuItem mi ON mcm.`menuItemId`=mi.`menuItemId`\n"
                + "JOIN concreteMenu cm ON cm.`concreteMenuId` = mcm.concrete_MenuId\n"
                + "WHERE mcm.`menuItemId`=" + menuItem.getId() + " AND mcm.`concrete_menuId`=" + menu.getId();
        List<MenuItem> menuItems = menuRepository.findMenuItemByQuery(query);
        if (menuItems.isEmpty()) {
            menuRepository.addMenuItem(menuItem, menu);
        } else {
            throw new Exception("Jelo vec postoji u jelovniku!");
        }

    }

    @Override
    public Menu findById(Integer id) throws Exception {
        return menuRepository.findById(id);
    }

    @Override
    public void update(Menu menu) throws Exception {
        menuRepository.update(menu);
    }

    @Override
    public List<Menu> getAllByStatus() throws Exception {
        List<Menu> menus = menuRepository.getAll();
        List<Menu> filteredMenus = new ArrayList<>();
        for (Menu m : menus) {
            if(m.isIsActiv()){
                filteredMenus.add(m);
            }
        }
        return filteredMenus;
        //return menuRepository.getAllByStatus();
    }

    @Override
    public void delete(Menu menu) throws Exception {
        menuRepository.delete(menu);
    }

}
