package dev.sterner.geocluster.common.components;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface IWorldDepositComponent {
    void putPendingBlock(BlockPos pos, BlockState state);

    void removePendingBlocksForChunk(ChunkPos p);

    int getPendingBlockCount();

    ConcurrentLinkedQueue<WorldDepositComponent.PendingBlock> getPendingBlocks(ChunkPos chunkPos);
}
