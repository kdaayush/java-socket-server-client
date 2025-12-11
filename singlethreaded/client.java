import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.io.InputStreamReader; 

public class client {
    public void run() throws IOException, UnknownHostException {
        try (Socket socket = new Socket(InetAddress.getByName("localhost"), 8080);
             PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true); // 'true' for auto-flush
             BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            toServer.println("hello server, this is client");
            String line = fromServer.readLine();
            if (line != null) {
                System.out.println("message from server :" + line);
            } else {
                System.out.println("Server closed the connection or sent no response.");
            }
        } 
    }

    public static void main(String[] args) {
        try {
            client cl = new client();
            cl.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}