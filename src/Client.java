// TCP Client-Server coursework
// The client sends integers from an ArrayList to the server one at a time.
// The server calculates a running sum and sends the updated result back.
// The client displays each returned value and finally sends "exit".

// Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    // Server address and port number
    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {

        // Create an ArrayList of integers (what client send)
        ArrayList<Integer> numbers = new ArrayList<>(
                Arrays.asList(5, 10, 15, 20, 25)
        );

        // Display starting messages
        System.out.println("Client is starting");
        System.out.println("Numbers to send: " + numbers);

        try (
            // Create socket to connect to server
            Socket socket = new Socket(HOST, PORT);

            // Input stream to receive server responses
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // Output stream to send data to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {

            // Confirm connection
            System.out.println("Connected to server at " + HOST + " port: " + PORT);

            // Loop through each number in ArrayList
            for (Integer number : numbers) {

                // Send number to server
                System.out.println("Sending number: " + number);
                out.println(number);

                // Receive response from server
                String response = in.readLine();

                // Display server response
                System.out.println("Server returned sum: " + response);
            }

            // After sending all numbers, send exit command
            out.println("exit");
            System.out.println("Exit message sent to server.");

        } catch (IOException e) {
            // Handle errors
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }

        // Message when client stops
        System.out.println("Client terminated.");
    }
}