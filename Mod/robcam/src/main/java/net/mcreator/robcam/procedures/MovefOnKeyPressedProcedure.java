package net.mcreator.robcam.procedures;

public class MovefOnKeyPressedProcedure {
	public static void execute() {
	  String message = "Forward";
            // Perform your custom action here
           // System.out.println("Custom key pressed!");
      Comm.send(message);
	}
}
