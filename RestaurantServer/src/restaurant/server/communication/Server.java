package restaurant.server.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import restaurant.server.session.Session;
import restaurant.server.thread.ProcessClientRequests;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Server extends Thread {

    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9010);
            while (!isInterrupted()) {
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connected!");
                handleCLient(socket);
            }
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Server was interrupted!");
            e.printStackTrace();
        }
    }

    private void handleCLient(Socket socket) {
        ProcessClientRequests processClientRequests = new ProcessClientRequests(socket);
        processClientRequests.start();
    }

    public void stopServer() {
        try {
            for (ProcessClientRequests client : Session.getInstance().getClients()) {
                try {
                    client.interrupt();
                    client.closeClient();
                } catch (IOException ex) {
                    System.out.println("Client closed: " + ex.getMessage());
                }
            }
            Session.getInstance().getClients().clear();
            interrupt();
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Server was interrupted!");
        }
    }

}
