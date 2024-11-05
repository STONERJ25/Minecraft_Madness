/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.robcam as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/

package net.mcreator.robcam.procedures;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Comm {
    private static final String SERVER_ADDRESS = "localhost"; // Change to server IP if not local
    private static final int PORT = 12345;
    public static BufferedReader reader;
    public static PrintWriter writer;
    public static Scanner scanner;
    public static Socket socket;

    public static void onModStartup() throws IOException {

            socket = new Socket(SERVER_ADDRESS, PORT);
            System.out.println("Connected to the server");

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

             reader = new BufferedReader(new InputStreamReader(input));
             writer = new PrintWriter(output, true);
             scanner = new Scanner(System.in);
       // System.out.println("Your mod has started up!");
        // Your startup code here
    }
	public Comm() {
	
	
	}
	 

public static void send(String message) {
    if (writer != null) {
        writer.println(message);
        writer.flush();
    } else {
        System.err.println("Writer is not initialized. Cannot send message.");
    }
}
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		new Comm();
	}

	@Mod.EventBusSubscriber
	private static class CommForgeBusEvents {
		@SubscribeEvent
		public static void serverLoad(ServerStartingEvent event) {
		}

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void clientLoad(FMLClientSetupEvent event) {

		}
	}
}
