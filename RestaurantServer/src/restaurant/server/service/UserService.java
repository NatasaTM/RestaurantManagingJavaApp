package restaurant.server.service;

import restaurant.common.domain.Role;
import restaurant.common.domain.User;



/**
 *
 * @author Natasa Todorov Markovic
 */
public interface UserService {
    User login(String email, String password) throws Exception;
    void add(User user) throws Exception;
}
