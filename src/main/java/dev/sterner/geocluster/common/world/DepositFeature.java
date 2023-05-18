package dev.sterner.geocluster.common.world;

import com.mojang.serialization.Codec;
import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.GeoclusterConfig;
import dev.sterner.geocluster.api.GeoclusterAPI;
import dev.sterner.geocluster.api.IDeposit;
import dev.sterner.geocluster.common.components.GeoclusterComponents;
import dev.sterner.geocluster.common.components.IWorldChunkComponent;
import dev.sterner.geocluster.common.components.IWorldDepositComponent;
import dev.sterner.geocluster.common.components.WorldDepositComponent;
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


        IWorldDepositComponent depCap = GeoclusterComponents.DEPOSIT.get(world);
        IWorldChunkComponent cgCap = GeoclusterComponents.CHUNK.get(world);

        boolean placedPluton = false;
        boolean placedPending = placePendingBlocks(world, depCap, cgCap, pos);

        if (world.getRandom().nextDouble() > GeoclusterConfig.CHUNK_SKIP_CHANCE) {
            for (int p = 0; p < GeoclusterConfig.NUMBER_PLUTONS_PER_CHUNK; p++) {
                IDeposit pluton = GeoclusterAPI.plutonRegistry.pick(world, pos);
                if (pluton == null) {
                    continue;
                }

                boolean anyGenerated = pluton.generate(world, pos, depCap, cgCap) > 0;
                if (anyGenerated) {
                    placedPluton = true;
                    pluton.afterGen(world, pos, depCap, cgCap);
                }
            }
        }
        // Let our tracker know that we did in fact traverse this chunk
        cgCap.setChunkGenerated(new ChunkPos(pos));


        return placedPluton || placedPending;
    }

    private boolean placePendingBlocks(StructureWorldAccess worldAccess, IWorldDepositComponent depCap, IWorldChunkComponent cgCap, BlockPos origin) {
        ChunkPos cp = new ChunkPos(origin);
        ConcurrentLinkedQueue<WorldDepositComponent.PendingBlock> q = depCap.getPendingBlocks(cp);
        if (cgCap.hasChunkGenerated(cp) && q.size() > 0) {
            Geocluster.LOGGER.info("Chunk [{}, {}] has already generated but we're trying to place pending blocks anyways", cp.x, cp.z);
        }
        q.stream().forEach(x -> FeatureUtils.enqueueBlockPlacement(worldAccess, cp, x.pos(), x.state(), depCap, cgCap));
        depCap.removePendingBlocksForChunk(cp);
        return q.size() > 0;
    }


}
