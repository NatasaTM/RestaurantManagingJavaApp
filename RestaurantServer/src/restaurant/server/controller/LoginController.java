package restaurant.server.controller;

import restaurant.common.domain.Role;
import restaurant.common.domain.User;
import restaurant.server.service.UserService;
import restaurant.server.service.impl.UserServiceImpl;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class LoginController {

    private final static LoginController instance = new LoginController();

    private final UserService userService;

    private LoginController() {
        this.userService = new UserServiceImpl();
    }

    public static LoginController getInstance() {
        return instance;
    }
    
    public User login(String username,String password) throws Exception{
        return userService.login(username, password);
    }
    
    public void addUser(User user) throws Exception{
        userService.add(user);
    }

}
