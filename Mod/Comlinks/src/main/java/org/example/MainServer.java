package org.example;

import java.io.*;
import java.net.*;

public class MainServer {

    static Socket client = null;
    static ServerSocket server = null;



    public static void main(String[] args) {
        System.out.println("Server is starting...");
        System.out.println("Server is listening...");

        try {
            server = new ServerSocket(5050);
        } catch (IOException ex) {
            System.out.println("Could not listen on port 5050");
            System.exit(-1);

        }
        try {
            boolean done = false;
            while (!done) {

                client = server.accept();
                System.out.println("Client Connected...");
                BufferedReader streamIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter streamOut = new PrintWriter(client.getOutputStream(),true);
                String line = streamIn.readLine();
                if (line.equalsIgnoreCase("bye")) {
                    streamIn.close();
                    client.close();
                    server.close();
                    done = true;
                } else {
                    System.out.println(line);
                    streamOut.println(line);
                }
            }

        } catch (IOException e) {
            System.out.println("IO Error in streams " + e);
        }
    }}