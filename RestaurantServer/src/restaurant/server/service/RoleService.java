package restaurant.server.service;

import java.util.List;
import restaurant.common.domain.Role;

/**
 *
 * @author Natasa Todorov Markovic
 */
public interface RoleService {
    
    List<Role> getAll() throws Exception;

    void add(Role role) throws Exception;

    void update(Role role) throws Exception;

    void delete(Role role) throws Exception;

    Role findById(Integer id) throws Exception;

    List<Role> findByQuery(String query) throws Exception;
    
}
