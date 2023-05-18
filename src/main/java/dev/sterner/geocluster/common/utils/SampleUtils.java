package dev.sterner.geocluster.common.utils;

import dev.sterner.geocluster.common.registry.GeoclusterTagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import org.jetbrains.annotations.Nullable;

public class SampleUtils {
    @Nullable
    public static BlockPos getSamplePosition(StructureWorldAccess level, ChunkPos chunkPos) {
        return getSamplePosition(level, chunkPos, -1);
    }

    @Nullable
    public static BlockPos getSamplePosition(StructureWorldAccess level, ChunkPos chunkPos, int spread) {

        if (!(level instanceof ChunkRegion world)) {
            return null;
        }

        int usedSpread = Math.max(8, spread);
        int xCenter = (chunkPos.getStartX() + chunkPos.getEndX()) / 2;
        int zCenter = (chunkPos.getStartZ() + chunkPos.getEndZ()) / 2;

        int blockPosX = xCenter + (level.getRandom().nextInt(usedSpread) * ((level.getRandom().nextBoolean()) ? 1 : -1));
        int blockPosZ = zCenter + (level.getRandom().nextInt(usedSpread) * ((level.getRandom().nextBoolean()) ? 1 : -1));

        BlockPos searchPos = new BlockPos(blockPosX, world.getHeight(), blockPosZ);

        while (searchPos.getY() > world.getBottomY()) {
            BlockState blockToPlaceOn = world.getBlockState(searchPos);
            if (Block.isFaceFullSquare(blockToPlaceOn.getOutlineShape(world, searchPos), Direction.UP)) {
                if (!blockToPlaceOn.isIn(GeoclusterTagRegistry.SUPPORTS_SAMPLE)) {
                    searchPos = searchPos.down();
                    continue;
                }
                BlockPos actualPlacePos = searchPos.up();
                if (canReplace(world, actualPlacePos)) {
                    return actualPlacePos;
                }
            }
            searchPos = searchPos.down();
        }

        return null;
    }

    public static boolean canReplace(StructureWorldAccess level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.getMaterial().isReplaceable() || state.isAir();
    }

    public static boolean isInWater(StructureWorldAccess level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() == Blocks.WATER;
    }

    public static boolean inNonWaterFluid(StructureWorldAccess level, BlockPos pos) {
        return level.getBlockState(pos).getMaterial().isLiquid() && !isInWater(level, pos);
    }
}
