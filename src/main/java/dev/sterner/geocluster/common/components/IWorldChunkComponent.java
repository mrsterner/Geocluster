package dev.sterner.geocluster.common.components;

import net.minecraft.util.math.ChunkPos;

public interface IWorldChunkComponent {
    boolean hasChunkGenerated(ChunkPos pos);

    void setChunkGenerated(ChunkPos pos);
}
