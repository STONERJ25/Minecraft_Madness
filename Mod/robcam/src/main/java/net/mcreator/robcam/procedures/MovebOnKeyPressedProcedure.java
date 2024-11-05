package net.mcreator.robcam.procedures;

public class MovebOnKeyPressedProcedure {
	public static void execute() {
	  String message = "Backward";
            // Perform your custom action here
           // System.out.println("Custom key pressed!");
            Comm.send(message);
	}
}
