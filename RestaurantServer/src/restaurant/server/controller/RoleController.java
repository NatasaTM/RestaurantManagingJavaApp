package restaurant.server.controller;

import java.util.List;
import restaurant.common.domain.Role;
import restaurant.server.service.RoleService;
import restaurant.server.service.impl.RoleServiceImpl;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class RoleController {
    
    private static final RoleController instance  = new RoleController();
    
    private RoleService roleService;

    private RoleController() {
        this.roleService = new RoleServiceImpl();
    }

    public static RoleController getInstance() {
        return instance;
    }
    
    public List<Role> getAllRoles() throws Exception{
       return roleService.getAll();
    }
}
