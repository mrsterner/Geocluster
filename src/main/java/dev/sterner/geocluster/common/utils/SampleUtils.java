package dev.sterner.geocluster.common.utils;

import dev.sterner.geocluster.common.registry.GeoclusterTagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import org.jetbrains.annotations.Nullable;

public class SampleUtils {
    @Nullable
    public static BlockPos getSamplePosition(StructureWorldAccess level, ChunkPos chunkPos) {
        return getSamplePosition(level, chunkPos, -1);
    }

    @Nullable
    public static BlockPos getSamplePosition(StructureWorldAccess worldAccess, ChunkPos chunkPos, int spread) {

        if (!(worldAccess instanceof ChunkRegion world)) {
            return null;
        }

        int usedSpread = Math.max(8, spread);
        int xCenter = (chunkPos.getStartX() + chunkPos.getEndX()) / 2;
        int zCenter = (chunkPos.getStartZ() + chunkPos.getEndZ()) / 2;

        int blockPosX = xCenter + (worldAccess.getRandom().nextInt(usedSpread) * ((worldAccess.getRandom().nextBoolean()) ? 1 : -1));
        int blockPosZ = zCenter + (worldAccess.getRandom().nextInt(usedSpread) * ((worldAccess.getRandom().nextBoolean()) ? 1 : -1));

        BlockPos searchPos = new BlockPos(blockPosX, worldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, blockPosX, blockPosZ), blockPosZ).down();

        BlockState blockToPlaceOn = world.getBlockState(searchPos);
        if (Block.isFaceFullSquare(blockToPlaceOn.getOutlineShape(world, searchPos), Direction.UP)) {
            if(blockToPlaceOn.isIn(GeoclusterTagRegistry.SUPPORTS_SAMPLE)){
                BlockPos actualPlacePos = searchPos.up();
                if (canReplace(world, actualPlacePos)) {
                    return actualPlacePos;
                }
            }
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
