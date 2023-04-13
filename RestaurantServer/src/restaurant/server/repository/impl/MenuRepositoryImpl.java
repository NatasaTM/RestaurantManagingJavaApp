package restaurant.server.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import restaurant.common.domain.Menu;
import restaurant.common.domain.MenuItem;
import restaurant.server.repository.MenuRepository;
import java.sql.*;
import java.time.LocalDate;
import restaurant.common.domain.MenuCategory;
import restaurant.common.domain.MenuItemType;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuRepositoryImpl implements MenuRepository {

    @Override
    public List<Menu> getAll() throws Exception {

        try {
            List<Menu> menus = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "SELECT `concreteMenuId`,`concreteMenuName`,`date`,`isActive` FROM concretemenu ORDER BY `date` DESC";
//            String query = "SELECT cm.`concreteMenuId`,cm.`concreteMenuName`,cm.`date`,cm.`isActive`,mcm.`menuItemId`,mi.`name`,mi.`description`,mi.`price`,mi.`categoryId`,c.`categoryName` FROM concretemenu cm \n" +
//"JOIN `menuitem_concrete_menu` mcm ON mcm.`concrete_menuId`= cm.`concreteMenuId`\n" +
//"JOIN `menuitem` mi ON mi.`menuItemId`=mcm.`menuItemId`\n" +
//"JOIN `menucategory` c ON c.`categoryId`=mi.`categoryId`\n" +
//"ORDER BY 3 DESC";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Integer id = rs.getInt("concreteMenuId");
                String name = rs.getString("concreteMenuName");
                LocalDate date = rs.getDate("date").toLocalDate();
                Boolean isActive = rs.getBoolean("isActive");
                
                Menu menu = new Menu(id, name, date,isActive);
                
                List<MenuItem> menuItems = getAllItems(menu);
                menu.setMenuItems(menuItems);
                menus.add(menu);
            }
            rs.close();
            statement.close();
            return menus;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode getAll() klase MenuRepository ");
        }

    }

   
    private List<MenuItem> getAllItems(Menu menu) throws Exception {
        try {
            List<MenuItem> menuItems = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "SELECT mcm.menuItemId, mi.name menuItemName,mi.description,mi.price,mi.`menuItemType`,mc.categoryId,mc.categoryName FROM menuitem_concrete_menu mcm\n"
                    + "JOIN menuitem mi ON mcm.menuItemId = mi.menuItemId\n"
                    + "JOIN concretemenu cm ON mcm.concrete_menuId=cm.concreteMenuId\n"
                    + "JOIN menuCategory mc ON mc.categoryId=mi.categoryId\n"
                    + "WHERE cm.concreteMenuId = ?\n"
                    + "ORDER BY mc.categoryName";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, menu.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                Integer id = rs.getInt("menuItemId");
                String menuItemname = rs.getString("menuItemName");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                 MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, rs.getObject("menuItemType").toString());

                String categoryName = rs.getString("categoryName");
                Integer categoryId = rs.getInt("categoryId");
                MenuCategory category = new MenuCategory(categoryId, categoryName);
                MenuItem menuItem = new MenuItem(id, menuItemname, description, price, category,menuItemType);
                menuItems.add(menuItem);

            }
            rs.close();
            preparedStatement.close();
            return menuItems;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode getAllItems() klase MenuRepository ");

        }
    }

    @Override
    public List<MenuItem> findMenuItemByQuery(String query) throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                Integer id = rs.getInt("menuItemId");
           String name = rs.getString("name");
           BigDecimal price = rs.getBigDecimal("price");
           MenuItem menuItem = new MenuItem(id, name, null, price, null,null);
           menuItems.add(menuItem);
            }
            rs.close();
           statement.close();
           return menuItems;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode findByQuery() klase MenuRepository ");
        }

        
//       

    }

    @Override
    public void addMenuItem(MenuItem menuItem,Menu menu) throws Exception {
        
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
        String query = "INSERT INTO menuitem_concrete_menu(menuItemId,concrete_menuId) VALUES(?,?)";
        
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, menuItem.getId());
        preparedStatement.setInt(2, menu.getId());
        preparedStatement.executeUpdate();
        
        preparedStatement.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode add() klase MenuRepository ");
        }
        
        
         
    }

    @Override
    public void add(Menu menu) throws Exception {
         String query = "INSERT INTO concretemenu(`concreteMenuName`,`date`) VALUES(?,?)";
         try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, menu.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(menu.getDate()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                Integer id = rs.getInt(1);
                menu.setId(id);
            }
            rs.close();
            preparedStatement.close();
            
            List<MenuItem> menuItems = menu.getMenuItems();
            for(int i=0;i<menuItems.size();i++){
                addMenuItem(menuItems.get(i), menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju create() klase MenuRepository ");
        }
         
    }

//    @Override
//    public List<MenuItem> getAllItemsByCategory(String categoryName, Menu menu) throws Exception {
//        try {
//            List<MenuItem> menuItems = new ArrayList<>();
//            Connection connection = MyDatabaseConnection.getInstance().getConnection();
//            String query = "SELECT mcm.menuItemId, mi.name menuItemName,mi.description,mi.price,mi.`menuItemType`,mc.categoryId,mc.categoryName FROM menuitem_concrete_menu mcm\n"
//                    + "JOIN menuitem mi ON mcm.menuItemId = mi.menuItemId\n"
//                    + "JOIN concretemenu cm ON mcm.concrete_menuId=cm.concreteMenuId\n"
//                    + "JOIN menuCategory mc ON mc.categoryId=mi.categoryId\n"
//                    + "WHERE cm.concreteMenuId = ? AND mc.categoryName = ?\n"
//                    + "ORDER BY mc.categoryName";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, menu.getId());
//            preparedStatement.setString(2, categoryName);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                Integer id = rs.getInt("menuItemId");
//                String menuItemname = rs.getString("menuItemName");
//                String description = rs.getString("description");
//                BigDecimal price = rs.getBigDecimal("price");
//                 MenuItemType menuItemType = Enum.valueOf(MenuItemType.class, rs.getObject("menuItemType").toString());
//
//                //String categoryName = rs.getString("categoryName");
//                Integer categoryId = rs.getInt("categoryId");
//                MenuCategory category = new MenuCategory(categoryId, categoryName);
//                MenuItem menuItem = new MenuItem(id, menuItemname, description, price, category,menuItemType);
//                menuItems.add(menuItem);
//
//            }
//            rs.close();
//            preparedStatement.close();
//            return menuItems;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("Greska pri izvrsenju metode getAllItemsByCategory() klase MenuRepository ");
//        }
//    }

    @Override
    public void update(Menu menu) throws Exception {
         try {
            String query = "UPDATE `concretemenu` SET `isActive`=? WHERE `concreteMenuId`=?";
             Connection connection = MyDatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             preparedStatement.setBoolean(1, menu.isIsActiv());
             preparedStatement.setInt(2, menu.getId());
             preparedStatement.executeUpdate();
             
             preparedStatement.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode update() klase MenuRepository ");
        }
    }

    @Override
    public void delete(Menu menu) throws Exception {
        
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query = "DELETE FROM concretemenu WHERE `concreteMenuId`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, menu.getId());
            preparedStatement.executeUpdate();
            
            preparedStatement.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode delete() klase MenuRepository ");
        }
       
       
    }

    @Override
    public void deleteMenuItem(Menu menu, MenuItem menuItem) throws Exception {
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
       String query = "DELETE FROM `menuitem_concrete_menu` WHERE menuItemId=? AND `concrete_menuId`=?;";
       
       PreparedStatement preparedStatement = connection.prepareStatement(query);
       preparedStatement.setInt(1, menuItem.getId());
       preparedStatement.setInt(2, menu.getId());
       preparedStatement.executeUpdate();
       
       preparedStatement.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode deleteMenuItem() klase MenuRepository ");
        }
       
    }

    @Override
    public Menu findById(Integer id)throws Exception {
        String query = "SELECT `concreteMenuId`,`concreteMenuName`,`date`,`isActive` FROM concretemenu WHERE `concreteMenuId`=?";
        Menu menu = null;
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Integer idp = rs.getInt("concreteMenuId");
                String menuName = rs.getString("concreteMenuName");
                LocalDate date = rs.getDate("date").toLocalDate();
                boolean isActive = rs.getBoolean("isActive");
                
                menu = new Menu(id, menuName, date,isActive);
                
                List<MenuItem> menuItems = getAllItems(menu);
                menu.setMenuItems(menuItems);
            }
            
            rs.close();
            preparedStatement.close();
            return menu;
                    
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode getByName() klase MenuRepository ");
        }
    }

//    @Override
//    public List<Menu> getAllByStatus() throws Exception {
//        try {
//            String query = "SELECT `concreteMenuId`,`concreteMenuName`,`date` FROM concretemenu WHERE isActive=1 ORDER BY `date` DESC";
//            List<Menu> menus = new ArrayList<>();
//            Connection connection = MyDatabaseConnection.getInstance().getConnection();
//            
//            Statement statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery(query);
//            while (rs.next()) {
//                Integer id = rs.getInt("concreteMenuId");
//                String name = rs.getString("concreteMenuName");
//                LocalDate date = rs.getDate("date").toLocalDate();
//                
//                Menu menu = new Menu(id, name, date);
//                menus.add(menu);
//            }
//            rs.close();
//            statement.close();
//            return menus;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("Greska pri izvrsenju metode getAllByStatus() klase MenuRepository ");
//        }
//    }

    

    

    

    @Override
    public List<Menu> findByQuery(String query) throws Exception {
              
        List<Menu> menus = new ArrayList<>();
        
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            try {
                Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Integer id = rs.getInt("concreteMenuId");
                String name = rs.getString("concreteMenuName");
                LocalDate date = rs.getDate("date").toLocalDate();
                Boolean isActive = rs.getBoolean("isActive");
                
                Menu menu = new Menu(id, name, date,isActive);
                
                
               List<MenuItem> menuItems = getAllItems(menu);
                menu.setMenuItems(menuItems);
                menus.add(menu);


            }
            rs.close();
            statement.close();
            
            return menus;
                
            
        } catch (Exception e) {
             e.printStackTrace();
            throw new Exception("Greska pri izvrsenju metode findByQuery() klase MenuRepository ");
        }
          
           
    }

}
