import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class server {

    public void run() throws IOException {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(10000);
            System.out.println("Server started and listening on port : " + port);
            while (true) {
                Socket acceptedConnection = null;
                try {
                    acceptedConnection = serverSocket.accept();
                    System.out.println("Connection accepted from : " + acceptedConnection.getRemoteSocketAddress());
                    try (Socket clientSocket = acceptedConnection;
                         PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true); // Auto-flush
                         BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                        String clientMessage = fromClient.readLine();
                        if (clientMessage != null) {
                            System.out.println("Message from client: " + clientMessage);
                        }
                        toClient.println("hello client, you are connected to server");

                    }

                } catch (IOException ex) {
                    System.err.println("Error handling client connection: " + ex.getMessage());
                    if (acceptedConnection != null && !acceptedConnection.isClosed()) {
                        acceptedConnection.close();
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Could not start server: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        server server = new server();
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}