package net.mcreator.robcam.procedures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import java.util.Random;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class StartProcedure {
	    private static final Map<String, Color> WOOL_COLORS = new HashMap<>();

    static {
        WOOL_COLORS.put("White Wool", new Color(0xFFFFFF));
        WOOL_COLORS.put("Orange Wool", new Color(0xF9801D));
        WOOL_COLORS.put("Magenta Wool", new Color(0xC74EBD));
        WOOL_COLORS.put("Light Blue Wool", new Color(0x3AB3DA));
        WOOL_COLORS.put("Yellow Wool", new Color(0xFED83D));
        WOOL_COLORS.put("Lime Wool", new Color(0x80C71F));
        WOOL_COLORS.put("Pink Wool", new Color(0xF38BAA));
        WOOL_COLORS.put("Gray Wool", new Color(0x474F52));
        WOOL_COLORS.put("Light Gray Wool", new Color(0x9D9D97));
        WOOL_COLORS.put("Cyan Wool", new Color(0x169C9C));
        WOOL_COLORS.put("Purple Wool", new Color(0x8932B8));
        WOOL_COLORS.put("Blue Wool", new Color(0x3C44AA));
        WOOL_COLORS.put("Brown Wool", new Color(0x835432));
        WOOL_COLORS.put("Green Wool", new Color(0x5E7C16));
        WOOL_COLORS.put("Red Wool", new Color(0xB02E26));
        WOOL_COLORS.put("Black Wool", new Color(0x1D1D21));
    }    public static String getClosestWoolColor(int hexColor) {
        // Extract RGB from the input color (ignore alpha, top 8 bits)
        Color inputColor = new Color(hexColor & 0xFFFFFF);

        String closestWool = null;
        double smallestDistance = Double.MAX_VALUE;

        for (Map.Entry<String, Color> entry : WOOL_COLORS.entrySet()) {
            double distance = calculateColorDistance(inputColor, entry.getValue());
            if (distance < smallestDistance) {
                smallestDistance = distance;
                closestWool = entry.getKey();
            }
        }

        return closestWool;
    }

    private static double calculateColorDistance(Color c1, Color c2) {
        int rDiff = c1.getRed() - c2.getRed();
        int gDiff = c1.getGreen() - c2.getGreen();
        int bDiff = c1.getBlue() - c2.getBlue();
        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    }

    public static void execute(LevelAccessor world, double x, double y, double z) {
        int[][] shadeArray = Shade.exe(); // Call the method to get the color array

        if (shadeArray == null) {
            System.err.println("Failed to retrieve shade array.");
            return;
        }

        for (int index0 = 0; index0 < shadeArray.length; index0++) {
            for (int index1 = 0; index1 < shadeArray[index0].length; index1++) {
                int num = shadeArray[index0][index1];
				String hexString = "0x" + String.format("%06X", num);
                // Map the RGB value to your wool blocks (you may need to adjust this mapping)
           switch (getClosestWoolColor(num)) {
            case "0xFFFFFF":
            case "White Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.WHITE_WOOL.defaultBlockState(), 3);
                break;

            case "0xFFA500":
            case "Orange Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.ORANGE_WOOL.defaultBlockState(), 3);
                break;

            case "0xFF00FF":
            case "Magenta Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.MAGENTA_WOOL.defaultBlockState(), 3);
                break;

            case "0xADD8E6":
            case "Light Blue Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.LIGHT_BLUE_WOOL.defaultBlockState(), 3);
                break;

            case "0xFFFF00":
            case "Yellow Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.YELLOW_WOOL.defaultBlockState(), 3);
                break;

            case "0x00FF00":
            case "Lime Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.LIME_WOOL.defaultBlockState(), 3);
                break;

            case "0x008000":
            case "Green Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.GREEN_WOOL.defaultBlockState(), 3);
                break;

            case "0x00FFFF":
            case "Cyan Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.CYAN_WOOL.defaultBlockState(), 3);
                break;

            case "0x0000FF":
            case "Blue Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.BLUE_WOOL.defaultBlockState(), 3);
                break;

            case "0x800080":
            case "Purple Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.PURPLE_WOOL.defaultBlockState(), 3);
                break;

            case "0xFF0000":
            case "Red Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.RED_WOOL.defaultBlockState(), 3);
                break;

            case "0x000000":
            case "Black Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.BLACK_WOOL.defaultBlockState(), 3);
                break;

            case "0x808080":
            case "Gray Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.GRAY_WOOL.defaultBlockState(), 3);
                break;

            case "0xD3D3D3":
            case "Light Gray Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.LIGHT_GRAY_WOOL.defaultBlockState(), 3);
                break;

            case "0xFFC0CB":
            case "Pink Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.PINK_WOOL.defaultBlockState(), 3);
                break;

            case "0xA52A2A":
            case "Brown Wool":
                world.setBlock(BlockPos.containing(x + index1, 52 + y - (index0 + 1), z + 1), Blocks.BROWN_WOOL.defaultBlockState(), 3);
                break;

            default:
                System.out.println("No matching wool color");
                break; // Handle unexpected colors
        }
            }
        }
    }
}
