import java.io.IOException;

public class client {
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
             int port = 8080;
                try (java.net.Socket socket = new java.net.Socket(java.net.InetAddress.getByName("localhost"), port);
                    java.io.PrintWriter toServer = new java.io.PrintWriter(socket.getOutputStream(), true);
                    java.io.BufferedReader fromServer = new java.io.BufferedReader(new java.io.InputStreamReader(socket.getInputStream()))) {
                    toServer.println("hello server, this is client");
                    String line = fromServer.readLine();
                    if (line != null) {
                        System.out.println("message from server :" + line);
                    } else {
                        System.out.println("Server closed the connection or sent no response.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        };
    }

    public static void main(String[]args){
        client client = new client();
        for(int i = 0 ;i < 100 ;i++){
        Thread thread = new Thread(client.getRunnable());
        thread.start();

        }
    }
}
