package dev.sterner.geocluster.common.world.feature;

import com.mojang.serialization.Codec;
import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.GeoclusterConfig;
import dev.sterner.geocluster.api.GeoclusterAPI;
import dev.sterner.geocluster.api.IDeposit;
import dev.sterner.geocluster.common.components.*;
import dev.sterner.geocluster.common.utils.FeatureUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.concurrent.ConcurrentLinkedQueue;

public class DepositFeature extends Feature<DefaultFeatureConfig> {
    public DepositFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        if (context.getGenerator() instanceof FlatChunkGenerator) {
            return false;
        }

        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();


        WorldDepositComponent depositComponent = GeoclusterComponents.DEPOSIT.get(world.toServerWorld());
        WorldChunkComponent chunkComponent = GeoclusterComponents.CHUNK.get(world.toServerWorld());

        boolean placedPluton = false;
        boolean placedPending = placePendingBlocks(world, depositComponent, chunkComponent, pos);

        if (world.getRandom().nextDouble() > GeoclusterConfig.CHUNK_SKIP_CHANCE) {
            for (int p = 0; p < GeoclusterConfig.NUMBER_PLUTONS_PER_CHUNK; p++) {
                IDeposit deposit = GeoclusterAPI.depositCache.pick(world, pos);
                if (deposit == null) {
                    continue;
                }

                boolean anyGenerated = deposit.generate(world, pos, depositComponent, chunkComponent) > 0;
                if (anyGenerated) {
                    placedPluton = true;
                    deposit.generatePost(world, pos, depositComponent, chunkComponent);
                }
            }
        }

        chunkComponent.setChunkGenerated(new ChunkPos(pos));


        return placedPluton || placedPending;
    }

    private boolean placePendingBlocks(StructureWorldAccess worldAccess, IWorldDepositComponent depCap, IWorldChunkComponent cgCap, BlockPos origin) {
        ChunkPos cp = new ChunkPos(origin);
        ConcurrentLinkedQueue<WorldDepositComponent.PendingBlock> q = depCap.getPendingBlocks(cp);
        if (cgCap.hasChunkGenerated(cp) && q.size() > 0) {
            Geocluster.LOGGER.info("Chunk [{}, {}] has already generated but we're trying to place pending blocks anyways", cp.x, cp.z);
        }
        q.forEach(x -> FeatureUtils.enqueueBlockPlacement(worldAccess, x.pos(), x.state(), depCap, cgCap));
        depCap.removePendingBlocksForChunk(cp);
        return q.size() > 0;
    }


}
