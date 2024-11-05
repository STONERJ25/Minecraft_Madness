
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.robcam.init;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.robcam.network.MoverMessage;
import net.mcreator.robcam.network.MovelMessage;
import net.mcreator.robcam.network.MovefMessage;
import net.mcreator.robcam.network.MovebMessage;
import net.mcreator.robcam.RobcamMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class RobcamModKeyMappings {
	public static final KeyMapping MOVEF = new KeyMapping("key.robcam.movef", GLFW.GLFW_KEY_I, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				RobcamMod.PACKET_HANDLER.sendToServer(new MovefMessage(0, 0));
				MovefMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping MOVEB = new KeyMapping("key.robcam.moveb", GLFW.GLFW_KEY_K, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				RobcamMod.PACKET_HANDLER.sendToServer(new MovebMessage(0, 0));
				MovebMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping MOVEL = new KeyMapping("key.robcam.movel", GLFW.GLFW_KEY_J, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				RobcamMod.PACKET_HANDLER.sendToServer(new MovelMessage(0, 0));
				MovelMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping MOVER = new KeyMapping("key.robcam.mover", GLFW.GLFW_KEY_L, "key.categories.misc") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				RobcamMod.PACKET_HANDLER.sendToServer(new MoverMessage(0, 0));
				MoverMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(MOVEF);
		event.register(MOVEB);
		event.register(MOVEL);
		event.register(MOVER);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if (Minecraft.getInstance().screen == null) {
				MOVEF.consumeClick();
				MOVEB.consumeClick();
				MOVEL.consumeClick();
				MOVER.consumeClick();
			}
		}
	}
}
