package restaurant.server.session;

import java.util.ArrayList;
import java.util.List;
import restaurant.server.thread.ProcessClientRequests;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Session {
    
    private static Session instance;
    private final List<ProcessClientRequests> clients = new ArrayList<>();

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public List<ProcessClientRequests> getClients() {
        return clients;
    }
    
    public synchronized void addClient(ProcessClientRequests client){
        clients.add(client);
          
    }
    public synchronized void removeClient(ProcessClientRequests client){
        clients.remove(client);
          
    }

    
}
