# Java Socket Server/Client Examples

This repository contains two primary Java socket examples: a **Multithreaded Server** (for high concurrency) and a **Single-Threaded Server** (for simple, sequential processing), paired with a common multithreaded client for testing.

The examples demonstrate basic message exchange over TCP/IP using standard Java Sockets (`java.net.Socket`) and Threads.

## 📁 Project Structure

| File | Description | Server Type |
| :--- | :--- | :--- |
| `server.java` | **Original/High Performance:** Creates a new Thread for every client connection. | Multithreaded |
| `SingleThreadedServer.java` | **Sequential:** Handles client connections one after the other on the main thread. | Single-Threaded |
| `client.java` | **Test Driver:** Launches 100 concurrent threads to connect and test the server. | Client |

## 🚀 How to Run

### Prerequisites

* Java Development Kit (JDK) 8 or newer installed.

### 1. Compile the Code

Compile the server you wish to test, along with the client:

```bash
# Example to compile the multithreaded server
javac server.java client.java
# Or, compile the single-threaded server
javac SingleThreadedServer.java client.java
2. Start the ServerOpen your first terminal window and run the desired server application (e.g., the multithreaded one):Bashjava server 
# OR
java SingleThreadedServer
3. Run the ClientOpen a second terminal window and run the client. It will initiate 100 connections.Bashjava client
🎯 Expected BehaviorServer TypeExpected Behavior When Client RunsMultithreaded (server.java)Connections are handled instantly and concurrently. Server output shows all "Received from client" messages appearing nearly simultaneously.Single-Threaded (SingleThreadedServer.java)Connections are processed one by one. If you add a small Thread.sleep() delay inside the server's handling logic, the client threads will wait sequentially for their turn.🧠 Core Implementation DetailsServer Logic (getConsumer() method)The server's logic for processing a single connection uses the following pattern to ensure proper two-way communication and resource cleanup:Java// Logic used in both servers
try (
    // 1. Setup Input Stream to read from the client
    BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    // 2. Setup Output Stream to write to the client (autoFlush = true)
    PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
    // Resource for automatic closure
    Socket socket = clientSocket 
) {
    // Read message sent by the client
    String clientMessage = fromClient.readLine();
    System.out.println("Received from client: " + clientMessage);

    // Send the required response
    toClient.println("hello from the server");
} // Socket and streams are closed automatically
Multithreading Implementation (server.java)The server achieves concurrency by creating a dedicated operating system thread for every client that connects:Java// Server's main loop:
while (true) {
    Socket acceptedConnectionSocket = serverSocket.accept();
    // Launch a new thread to handle the accepted socket connection
    Thread thread = new Thread(() -> server.getConsumer().accept(acceptedConnectionSocket));
    thread.start();
}
This model is known as Thread Per Connection.
