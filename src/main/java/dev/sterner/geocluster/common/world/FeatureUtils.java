package dev.sterner.geocluster.common.world;

import dev.sterner.geocluster.Geocluster;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;

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
            Geocluster.LOGGER.error("world was not ChunkRegion");
            return false;
        }
    }
/*
    public static boolean enqueueBlockPlacement(StructureWorldAccess worldAccess, ChunkPos chunk, BlockPos pos, BlockState state, IDepositCapability depCap, @Nullable IChunkGennedCapability cgCap) {
        // It's too late to enqueue so just bite the bullet and force placement
        if (cgCap != null && cgCap.hasChunkGenerated(new ChunkPos(pos))) {
            Chunk chunkaccess = worldAccess.getChunk(pos);
            BlockState blockstate = chunkaccess.setBlockState(pos, state, false);
            if (blockstate != null) {
                worldAccess.toServerWorld().onBlockChanged(pos, blockstate, state);
            }
            return true;
        }

        if (!ensureCanWriteNoThrow(worldAccess, pos)) {
            depCap.putPendingBlock(pos, state);
            return false;
        }

        if (!worldAccess.setBlockState(pos, state, 2 | 16)) {
            depCap.putPendingBlock(pos, state);
            return false;
        }

        return true;
    }

 */

    public static void fixSnowyBlock(StructureWorldAccess worldAccess, BlockPos posPlaced) {
        BlockState below = worldAccess.getBlockState(posPlaced.down());
        if (below.contains(Properties.SNOWY)) {
            worldAccess.setBlockState(posPlaced.down(), below.with(Properties.SNOWY, Boolean.FALSE), 2 | 16);
        }
    }
}
