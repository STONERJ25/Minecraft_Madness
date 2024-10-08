package com.example.examplemod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.Scanner;
@Mod.EventBusSubscriber(modid = "robcam", value = Dist.CLIENT)
public class KeyInputHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) throws IOException {
        // Check if the custom key is pressed
        if (robcam.Forward.isDown()) {
            String message = "Forward";
            // Perform your custom action here
           // System.out.println("Custom key pressed!");
            Client.send(message);
        }
        if (robcam.Backward.isDown()) {
            String message = "Backward";
            // Perform your custom action here
          //  System.out.println("Custom key pressed!");
            Client.send(message);
        }
        if (robcam.Right.isDown()) {
            String message = "Right";
            // Perform your custom action here
        //    System.out.println("Custom key pressed!");
            Client.send(message);
        }
        if (robcam.Left.isDown()) {
            String message = "Left";
            // Perform your custom action here
       //     System.out.println("Custom key pressed!");
            Client.send(message);
        }
    }
}


