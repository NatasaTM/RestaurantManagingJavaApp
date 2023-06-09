package restaurant.server.service.impl;

import java.util.List;
import restaurant.common.domain.Employee;
import restaurant.common.domain.Role;
import restaurant.common.domain.User;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.UserRepository;
import restaurant.server.repository.impl.EmployeeRepositoryImpl;
import restaurant.server.repository.impl.RoleRepositoryImpl;
import restaurant.server.repository.impl.UserRepositoryImpl;
import restaurant.server.service.UserService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepositoryImpl;
    private final GenericRepository<Employee, Integer> employeeRepository;
    private final GenericRepository<Role, Integer> roleRepository;
    
    public UserServiceImpl() {
        
        userRepositoryImpl = new UserRepositoryImpl();
        employeeRepository = new EmployeeRepositoryImpl();
        roleRepository = new RoleRepositoryImpl();
    }
    
    @Override
    public User login(String username, String password) throws Exception {
        
       // String query = "SELECT * FROM `user` WHERE username = '" + username + "' AND `password`='" + password + "'";
        //System.out.println(query);
        List<User> users = userRepositoryImpl.findByEmailAndPassword(username, password);
        if (users.isEmpty()) {
            throw new Exception("Korisnik ne postoji u sistemu!");
        }
        
        User user = users.get(0);
        
        Employee employee = employeeRepository.findById(user.getEmployee().getId());
        
        if (employee == null) {
            throw new Exception("Profil ne mogu da povezem sa Zaposlenim!!!");
        }
        user.setEmployee(employee);
       String query = "SELECT r.roleId , r.name  FROM `role` r JOIN `userrole` ur ON r.roleId = ur.roleId WHERE ur.username ='" + user.getUsername() + "'";
        
        try {
            List<Role> roles = roleRepository.findByQuery(query);
            
            user.setRoles(roles);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        System.out.println("Check sada roles: " + user.getRoles());
        return user;
    }
    
    @Override
    public void add(User user) throws Exception {
        userRepositoryImpl.add(user);
        
    }
    
    @Override
    public User findById(String username) throws Exception {
        User user = userRepositoryImpl.findById(username);
        Employee employee = employeeRepository.findById(user.getEmployee().getId());
        user.setEmployee(employee);
        return user;
    }
    
    @Override
    public void delete(User user) throws Exception {
        userRepositoryImpl.delete(user);
    }
    
    @Override
    public void update(User user) throws Exception {
        userRepositoryImpl.update(user);
    }

    @Override
    public List<User> getAll() throws Exception {
        return userRepositoryImpl.getAll();
    }
    
}
