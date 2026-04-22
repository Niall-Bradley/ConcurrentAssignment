// Import required classes for networking and input/output
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // Port number the server will listen on
    private static final int PORT = 5000;

    public static void main(String[] args) {

        // Display message when server starts
        System.out.println("Server is starting...");
        System.out.println("Waiting for client connection on port " + PORT + "...");

        try (
            // Create a ServerSocket to listen for client connections
            ServerSocket serverSocket = new ServerSocket(PORT);

            // Accept a client connection (program waits here until a client connects)
            Socket socket = serverSocket.accept();

            // Input stream to receive data from client
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // Output stream to send data to client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {

            // Confirm client connection
            System.out.println("Client connected: " + socket.getInetAddress());

            int sum = 0; // Variable to store running sum
            String message; // Variable to store incoming messages

            // Loop continuously to read messages from client
            while ((message = in.readLine()) != null) {

                // Display received message
                System.out.println("Received from client: " + message);

                // Check if client wants to terminate connection
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Exit request received. Server is shutting down.");
                    break; // Exit loop and close connection
                }

                try {
                    // Convert received string into integer
                    int number = Integer.parseInt(message);

                    // Add number to running total
                    sum += number;

                    // Display updated sum on server console
                    System.out.println("Updated sum: " + sum);

                    // Send updated sum back to client
                    out.println(sum);

                } catch (NumberFormatException e) {
                    // Handle invalid (non-integer) input
                    out.println("Invalid input");
                    System.out.println("Invalid number received: " + message);
                }
            }

        } catch (IOException e) {
            // Handle any server-side errors
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }

        // Message when server stops
        System.out.println("Server terminated.");
    }
}