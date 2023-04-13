package restaurant.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class User implements Serializable{
    
    private String username;
    private String password;
    private Employee employee;
    private List<Role> roles;

    public User() {
        roles = new ArrayList<>();
    }

    public User(String username, String password, Employee employee, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.employee = employee;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", employee=" + employee + ", roles=" + roles + '}';
    }
    
    public int intRole() {
        for (Role r: getRoles()){
            if (r.getName().toUpperCase().equals("administrator".toUpperCase())) return 1;
            if(r.getName().toUpperCase().equals("sef kuhinje".toUpperCase())) return 2;
            if(r.getName().toUpperCase().equals("konobar".toUpperCase())) return 3;
        }
        return -1;
    }
    
    
    
}
