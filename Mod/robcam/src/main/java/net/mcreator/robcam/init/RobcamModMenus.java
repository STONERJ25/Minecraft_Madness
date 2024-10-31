
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.robcam.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import net.mcreator.robcam.world.inventory.MMenu;
import net.mcreator.robcam.RobcamMod;

public class RobcamModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, RobcamMod.MODID);
	public static final RegistryObject<MenuType<MMenu>> M = REGISTRY.register("m", () -> IForgeMenuType.create(MMenu::new));
}
