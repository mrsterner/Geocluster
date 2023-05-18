package dev.sterner.geocluster.common.utils;

import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.common.components.IWorldDepositComponent;
import dev.sterner.geocluster.common.components.IWorldChunkComponent;
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
            // writeRadiusCutoff is not accessible, so use a constant 1 for 3x3 generation.
            return k <= 1 && l <= 1;
        } else {
            // All feature levels *should* be WorldGenRegions (this has not thrown yet)
            Geocluster.LOGGER.error("level was not WorldGenRegion");
            return false;
        }
    }

    public static boolean enqueueBlockPlacement(StructureWorldAccess level, ChunkPos chunk, BlockPos pos, BlockState state, IWorldDepositComponent depCap, @Nullable IWorldChunkComponent cgCap) {
        // It's too late to enqueue so just bite the bullet and force placement
        if (cgCap != null && cgCap.hasChunkGenerated(new ChunkPos(pos))) {
            Chunk chunkaccess = level.getChunk(pos);
            BlockState blockstate = chunkaccess.setBlockState(pos, state, false);
            if (blockstate != null) {
                level.toServerWorld().onBlockChanged(pos, blockstate, state);
            }
            return true;
        }

        if (!ensureCanWriteNoThrow(level, pos)) {
            depCap.putPendingBlock(pos, state);
            return false;
        }

        if (!level.setBlockState(pos, state, 2 | 16)) {
            depCap.putPendingBlock(pos, state);
            return false;
        }

        return true;
    }

    public static void fixSnowyBlock(StructureWorldAccess level, BlockPos posPlaced) {
        BlockState below = level.getBlockState(posPlaced.down());
        if (below.get(Properties.SNOWY)) {
            level.setBlockState(posPlaced.down(), below.with(Properties.SNOWY, Boolean.FALSE), 2 | 16);
        }
    }
}
