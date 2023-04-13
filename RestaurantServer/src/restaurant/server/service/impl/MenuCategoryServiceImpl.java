package restaurant.server.service.impl;

import java.util.List;
import restaurant.common.domain.MenuCategory;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.impl.MenuCategoryRepositoryImpl;
import restaurant.server.service.MenuCategoryService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuCategoryServiceImpl implements MenuCategoryService{
    
    private final GenericRepository<MenuCategory,Integer> menuCategoryRepository;

    public MenuCategoryServiceImpl() {
        this.menuCategoryRepository = new MenuCategoryRepositoryImpl();
    }
    
    

    @Override
    public List<MenuCategory> getAll() throws Exception {
        return menuCategoryRepository.getAll();
    }

    @Override
    public void addCategory(MenuCategory menuCategory) throws Exception {
        String query = "SELECT * FROM menucategory WHERE categoryName='"+menuCategory.getName()+"'";
         List<MenuCategory> categories = menuCategoryRepository.findByQuery(query);
         if(categories.isEmpty()){
             menuCategoryRepository.add(menuCategory);
         }else
             throw new Exception("Kategorija vec postoji!");
    }
    
}
