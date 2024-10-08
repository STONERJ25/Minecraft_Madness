package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost"; // Change to server IP if not local
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            System.out.println("Connected to the server");

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            PrintWriter writer = new PrintWriter(output, true);
            Scanner scanner = new Scanner(System.in);

            String message;
            while (true) {
                System.out.print("Enter message (type 'exit' to quit): ");
                message = scanner.nextLine();

                writer.println(message);
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                String response = reader.readLine();
                System.out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
