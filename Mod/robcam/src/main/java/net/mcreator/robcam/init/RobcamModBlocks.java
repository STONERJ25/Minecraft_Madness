
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.robcam.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.mcreator.robcam.block.ScreenBlock;
import net.mcreator.robcam.RobcamMod;

public class RobcamModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, RobcamMod.MODID);
	public static final RegistryObject<Block> SCREEN = REGISTRY.register("screen", () -> new ScreenBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
