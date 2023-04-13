package restaurant.server.service.impl;

import java.util.List;
import restaurant.common.domain.Employee;
import restaurant.common.domain.Role;
import restaurant.common.domain.User;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.impl.EmployeeRepositoryImpl;
import restaurant.server.repository.impl.RoleRepositoryImpl;
import restaurant.server.repository.impl.UserRepositoryImpl;
import restaurant.server.service.UserService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepositoryImpl;
    private final GenericRepository<Employee,Integer> employeeRepository;
    private final GenericRepository<Role,Integer> roleRepository;

    public UserServiceImpl() {

        userRepositoryImpl = new UserRepositoryImpl();
        employeeRepository = new EmployeeRepositoryImpl();
        roleRepository = new RoleRepositoryImpl();
    }

    @Override
    public User login(String username, String password) throws Exception {

        String query = "SELECT * FROM `user` WHERE username = '"+username+"' AND `password`='"+password+"'";
        System.out.println(query);
        List<User>users = userRepositoryImpl.findByQuery(query);
        if(users.isEmpty()) throw new Exception("Korisnik ne postoji u sistemu!");
        
         User user = users.get(0);
         
         Employee employee =employeeRepository.findById(user.getEmployee().getId());
         
           if (employee == null){
            throw  new Exception("Profil ne mogu da povezem sa Zaposlenim!!!");
        }
        user.setEmployee(employee);
        query = "SELECT r.roleId , r.name  FROM `role` r JOIN `userrole` ur ON r.roleId = ur.roleId WHERE ur.username ='"+user.getUsername()+"'";
        
        try{
            List<Role> roles = roleRepository.findByQuery(query);
        
            user.setRoles(roles);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    @Override
    public void add(User user) throws Exception {
         userRepositoryImpl.add(user);
         
            
    }

}
