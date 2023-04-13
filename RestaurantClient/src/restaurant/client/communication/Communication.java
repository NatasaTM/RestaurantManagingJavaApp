package restaurant.client.communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Communication {

    private static Communication instance;
    private Socket socket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;

    private Communication() throws Exception {
        socket = new Socket("localhost", 9010);
        sender = new ObjectOutputStream(socket.getOutputStream());
        receiver = new ObjectInputStream(socket.getInputStream());
    }

    public static Communication getInstance() throws Exception {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public ObjectOutputStream getSender() {
        return sender;
    }

    public ObjectInputStream getReceiver() {
        return receiver;
    }
}
