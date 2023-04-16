package restaurant.server.service;

import java.util.List;
import restaurant.common.domain.User;



/**
 *
 * @author Natasa Todorov Markovic
 */
public interface UserService {
    User login(String email, String password) throws Exception;
    void add(User user) throws Exception;
    User findById(String username) throws Exception;
    void delete(User user) throws Exception;
    void update(User user) throws Exception;
    List<User> getAll() throws Exception ;
}
