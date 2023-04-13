package restaurant.server.service.impl;

import java.util.List;
import restaurant.common.domain.MenuItem;
import restaurant.common.domain.MenuItemType;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.impl.MenuItemRepositoryImpl;
import restaurant.server.service.MenuItemService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuItemServiceImpl implements MenuItemService {

    private final GenericRepository<MenuItem,Integer> menuItemRepositoryImpl;

    public MenuItemServiceImpl() {
        menuItemRepositoryImpl = new MenuItemRepositoryImpl();
    }

    @Override
    public List<MenuItem> getAll() throws Exception {


        return menuItemRepositoryImpl.getAll();
    }

    @Override
    public void addMenuItem(MenuItem menuItem) throws Exception {

        String query = "SELECT m.menuItemId,m.name menuItemName,m.description,m.price, c.categoryId,c.categoryName FROM menuItem m\n"
                + "JOIN menucategory c ON c.categoryId = m.categoryId\n"
                + "WHERE m.name='" + menuItem.getName() + "' AND m.price=" + menuItem.getPrice();
        List<MenuItem> menuItems = menuItemRepositoryImpl.findByQuery(query);
        if (menuItems.isEmpty()) {
            menuItemRepositoryImpl.add(menuItem);
        } else {
            throw new Exception("Obrok sa tim imenom i cenom vec postoji u bazi");
        }
    }

    @Override
    public List<MenuItem> getAllByCategory(String categoryName) throws Exception {
        
        String query = "SELECT m.menuItemId,m.name menuItemName,m.description,m.price,m.`menuItemType`, c.categoryId,c.categoryName FROM menuItem m\n" +
" JOIN menucategory c ON c.categoryId = m.categoryId WHERE c.categoryName = '"+categoryName+"' ORDER BY categoryName";
        //return menuItemRepositoryImpl.getByCategory(categoryName);
        return menuItemRepositoryImpl.findByQuery(query);
    }

    @Override
    public void delete(MenuItem menuItem) throws Exception {

        menuItemRepositoryImpl.delete(menuItem);
    }

    @Override
    public void update(MenuItem menuItem) throws Exception {
        menuItemRepositoryImpl.update(menuItem);
    }

    @Override
    public MenuItem findById(Integer id) throws Exception {
        return menuItemRepositoryImpl.findById(id);
    }

    @Override
    public List<MenuItem> getAllByType(MenuItemType menuItemType) throws Exception {
        String query = "SELECT m.menuItemId,m.name menuItemName,m.description,m.price,m.`menuItemType`, c.categoryId,c.categoryName FROM menuItem m\n" +
"JOIN menucategory c ON c.categoryId = m.categoryId WHERE m.`menuItemType` = '"+menuItemType+"' ORDER BY categoryName";
        return menuItemRepositoryImpl.findByQuery(query);
    }

}
