package dev.sterner.geocluster.common.utils;

import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.common.components.IWorldChunkComponent;
import dev.sterner.geocluster.common.components.IWorldDepositComponent;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.Nullable;

public class FeatureUtils {
    private static boolean ensureCanWriteNoThrow(StructureWorldAccess level, BlockPos pos) {
        if (level instanceof ChunkRegion region) {
            ChunkPos center = region.getCenterPos();
            int i = ChunkSectionPos.getSectionCoord(pos.getX());
            int j = ChunkSectionPos.getSectionCoord(pos.getZ());
            int k = Math.abs(center.x - i);
            int l = Math.abs(center.z - j);
            return k <= 1 && l <= 1;
        } else {
            Geocluster.LOGGER.error("level was not WorldGenRegion");
            return false;
        }
    }

    public static boolean enqueueBlockPlacement(StructureWorldAccess level, BlockPos pos, BlockState state, IWorldDepositComponent depositComponent, @Nullable IWorldChunkComponent chunkComponent) {
        if (chunkComponent != null && chunkComponent.hasChunkGenerated(new ChunkPos(pos))) {
            Chunk chunkaccess = level.getChunk(pos);
            BlockState blockstate = chunkaccess.setBlockState(pos, state, false);
            if (blockstate != null) {
                level.toServerWorld().onBlockChanged(pos, blockstate, state);
            }
            return true;
        }

        if (!ensureCanWriteNoThrow(level, pos)) {
            depositComponent.putPendingBlock(pos, state);
            return false;
        }

        if (!level.setBlockState(pos, state, 2 | 16)) {
            depositComponent.putPendingBlock(pos, state);
            return false;
        }

        return true;
    }

    public static void fixSnowyBlock(StructureWorldAccess level, BlockPos posPlaced) {
        BlockState below = level.getBlockState(posPlaced.down());
        if (below.contains(Properties.SNOWY)) {
            level.setBlockState(posPlaced.down(), below.with(Properties.SNOWY, Boolean.FALSE), 2 | 16);
        }
    }
}
