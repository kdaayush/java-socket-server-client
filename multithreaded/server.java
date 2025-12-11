import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class server {
    
    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                Socket socket = clientSocket
            ) {
                String clientMessage = fromClient.readLine();
                if (clientMessage != null) {
                    System.out.println("Received from client: " + clientMessage);
                }
                toClient.println("hello from the server");
            } catch (IOException ex) {
                System.err.println("Error handling client connection: " + ex.getMessage());
            }
        };
    }
    
    public static void main(String[] args) {
        int port = 8080;
        server server = new server();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("server is listining on port : " + port);
            while (true) {
                Socket acceptedConnectionSocket = serverSocket.accept();
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedConnectionSocket));
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}