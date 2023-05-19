package dev.sterner.geocluster.common.utils;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldView;

import java.util.Objects;

public class GeoclusterUtils {

    public static String getRegistryName(Block block) {
        return Objects.requireNonNull(Registry.BLOCK.getId(block)).toString();
    }

    public static String getRegistryName(BlockState state) {
        return getRegistryName(state.getBlock());
    }

    public static BlockPos getTopSolidBlock(WorldView world, BlockPos start) {
        return new BlockPos(start.getX(), world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, start.getX(), start.getZ()), start.getZ()).down();
    }

    public static MutableText tryTranslate(String transKey, Object... values) {
        try {
            TranslatableTextContent contents = new TranslatableTextContent(transKey, values);
            return contents.parse(null, null, 0);
        } catch (CommandSyntaxException ex) {
            return Text.empty().append(transKey);
        }
    }
}
