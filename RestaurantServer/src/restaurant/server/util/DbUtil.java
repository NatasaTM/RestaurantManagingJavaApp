package restaurant.server.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class DbUtil {
    
    private Properties properties;

    public DbUtil() throws Exception {
        properties=new Properties();
        properties.load(new FileInputStream("db.properties"));
    }

    public String getUrl(){
        return properties.getProperty("url");
    }
    
    public String getUser(){
        return properties.getProperty("user");
    }
    public String getPassword(){
        return properties.getProperty("password");
    }
    
    
    
}
