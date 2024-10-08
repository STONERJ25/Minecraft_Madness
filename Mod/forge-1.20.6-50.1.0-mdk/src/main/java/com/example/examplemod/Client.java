package com.example.examplemod;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static com.example.examplemod.robcam.*;

public class Client{


    public static void send(String message) throws IOException {





                //System.out.print("Enter message (type 'exit' to quit): ");
              //  message = scanner.nextLine();

                writer.println(message);


                String response = reader.readLine();
                System.out.println(response);


    }
}
