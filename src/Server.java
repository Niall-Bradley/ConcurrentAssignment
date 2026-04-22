import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Server is starting...");
        System.out.println("Waiting for client connection on port " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket socket = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Client connected: " + socket.getInetAddress());

            int sum = 0;
            String message;

            while ((message = in.readLine()) != null) {
                System.out.println("Received from client: " + message);

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Exit request received. Server is shutting down.");
                    break;
                }

                try {
                    int number = Integer.parseInt(message);
                    sum += number;

                    System.out.println("Updated sum: " + sum);
                    out.println(sum); // send running sum back to client
                } catch (NumberFormatException e) {
                    out.println("Invalid input");
                    System.out.println("Invalid number received: " + message);
                }
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Server terminated.");
    }
}