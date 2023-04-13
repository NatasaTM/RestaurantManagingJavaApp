package restaurant.server.repository.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import restaurant.common.domain.MenuCategory;
import java.sql.*;
import restaurant.server.repository.GenericRepository;
import restaurant.server.connection.MyDatabaseConnection;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuCategoryRepositoryImpl implements GenericRepository<MenuCategory, Integer>{

    @Override
    public List<MenuCategory> getAll() throws Exception {
        
        try {
             List<MenuCategory> menuCategories = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query="SELECT * FROM menucategory";
            
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                Integer id = rs.getInt("categoryId");
                String name = rs.getString("categoryName");
                MenuCategory menuCategory = new MenuCategory(id, name);
                menuCategories.add(menuCategory);
            }
            rs.close();
            statement.close();
            
            return menuCategories;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode getAll() klase MenuCategoryRepository: " + e.getMessage());
        }
      
    }

    @Override
    public void add(MenuCategory menuCategory) throws Exception {
        try {
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            String query="INSERT INTO menucategory (categoryName) VALUES(?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, menuCategory.getName().toUpperCase());
            preparedStatement.executeUpdate();
            
            preparedStatement.close();
           
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode addCategory() klase MenuCategoryRepository: " + e.getMessage());
            
        }
         
    }

    @Override
    public List<MenuCategory> findByQuery(String query) throws Exception {
        try {
            List<MenuCategory> menuCategories = new ArrayList<>();
            Connection connection = MyDatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                Integer id = rs.getInt("categoryId");
                String name = rs.getString("categoryName");
                MenuCategory menuCategory = new MenuCategory(id, name);
                menuCategories.add(menuCategory);
            }
            rs.close();
            statement.close();
           
            return menuCategories;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Greska u izvrsenju metode findByQuerry() klase MenuCategoryRepository: " + e.getMessage());
        }
    }

    @Override
    public void update(MenuCategory entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(MenuCategory entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MenuCategory findById(Integer id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}
