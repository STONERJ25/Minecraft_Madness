package net.mcreator.robcam.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class ScreenOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
	//	for (int index0 = 0; index0 < 50; index0++) {
		//	for (int index1 = 0; index1 < 50; index1++) {
			//	world.setBlock(BlockPos.containing(x+index0, y+index1+1, z+1), Blocks.WHITE_WOOL.defaultBlockState(), 3);

			//}
//		}
		StartProcedure.execute(world, x ,y ,z);

	}


}
