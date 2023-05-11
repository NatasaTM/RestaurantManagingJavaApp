package restaurant.server.connection;

import java.sql.*;
import restaurant.server.util.DbUtil;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MyDatabaseConnection {

    private static MyDatabaseConnection instance;
    private final Connection connection;

    private MyDatabaseConnection() throws SQLException, Exception {

        DbUtil db = new DbUtil();
        connection = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPassword());
    }

    public static MyDatabaseConnection getInstance() throws Exception {
        if (instance == null) {
            instance = new MyDatabaseConnection();
        }
        return instance;
    }

    public java.sql.Connection getConnection() throws SQLException {
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        System.out.println("Connection closed");
    }

}
