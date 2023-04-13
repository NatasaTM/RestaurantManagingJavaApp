package restaurant.client.session;

import restaurant.common.domain.User;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ApplicationSession {

    private static ApplicationSession instance;

    private User loginUser;

    private Integer idEmployee;

    private ApplicationSession() {
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public static ApplicationSession getInstance() {
        if (instance == null) {
            instance = new ApplicationSession();
        }

        return instance;
    }

}
