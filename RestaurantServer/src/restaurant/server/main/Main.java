package restaurant.server.main;

import restaurant.server.communication.Server;
import restaurant.server.ui.form.MainForm;
import restaurant.server.ui.form.login.LoginForm;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Server server = new Server();
//        server.start();

      //  new MainForm().setVisible(true);
      new LoginForm().setVisible(true);
    }

}
