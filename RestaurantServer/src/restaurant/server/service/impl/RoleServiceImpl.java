package restaurant.server.service.impl;

import java.util.List;
import restaurant.common.domain.Role;
import restaurant.server.repository.GenericRepository;
import restaurant.server.repository.impl.RoleRepositoryImpl;
import restaurant.server.service.RoleService;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class RoleServiceImpl implements RoleService{
    
    private GenericRepository<Role,Integer> roleRepository;

    public RoleServiceImpl() {
        this.roleRepository = new RoleRepositoryImpl();
    }
    
    

    @Override
    public List<Role> getAll() throws Exception {
         return roleRepository.getAll();
    }

    @Override
    public void add(Role role) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Role role) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Role role) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Role findById(Integer id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Role> findByQuery(String query) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
