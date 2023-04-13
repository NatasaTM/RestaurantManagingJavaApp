package restaurant.client.ui.form.login.controller;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import restaurant.client.communication.Communication;
import restaurant.client.session.ApplicationSession;
import restaurant.common.domain.Role;
import restaurant.common.domain.User;
import restaurant.client.ui.form.mainForm.MainForm;
import restaurant.common.transfer.Operation;
import restaurant.common.transfer.Request;
import restaurant.common.transfer.Response;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class LoginFormController {
    
    
    
    public static void login(JTextField txtUsername,JPasswordField txtPassword,JFrame loginForm){
        
          try {
            String username = txtUsername.getText().trim();
            String password = String.valueOf(txtPassword.getPassword());
            
            User user = login(username, password);
            System.out.println(user.getEmployee().getFirstname() + ", " + user.getEmployee().getLastname());
            System.out.println("Roles:");
            List<Role> roles = user.getRoles();
            ApplicationSession.getInstance().setLoginUser(user);
            new MainForm().setVisible(true);
            loginForm.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static User login(String username, String password) throws Exception {
        // Network Communication
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Request request = new Request(Operation.LOGIN, user);
        Communication.getInstance().getSender().writeObject(request);
        Response response = (Response) Communication.getInstance().getReceiver().readObject();
        if (response.getException() == null) {
            return (User) response.getResult();
        } else {
            throw new Exception(response.getException().getMessage());
        }
    }

    public static void logout() throws Exception {
        Request request = new Request(Operation.END);
        Communication.getInstance().getSender().writeObject(request);
    }
    
}
