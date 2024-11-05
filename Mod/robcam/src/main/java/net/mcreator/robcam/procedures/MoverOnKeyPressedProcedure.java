package net.mcreator.robcam.procedures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class MoverOnKeyPressedProcedure {
	public static void execute() {
	  String message = "Right";
            // Perform your custom action here
           // System.out.println("Custom key pressed!");
            Comm.send(message);
	}
}
