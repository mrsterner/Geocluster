package dev.sterner.geocluster.common.world;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

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
        /*

        IDepositCapability depCap = level.getLevel().getCapability(DepositCapability.CAPABILITY)
                .orElseThrow(() -> new RuntimeException("Geolosys Pluton Capability Is Null.."));

        IChunkGennedCapability cgCap = level.getLevel().getCapability(ChunkGennedCapability.CAPABILITY)
                .orElseThrow(() -> new RuntimeException("Geolosys Pluton Capability Is Null.."));



        boolean placedPluton = false;
        boolean placedPending = placePendingBlocks(level, depCap, cgCap, pos);

        if (level.getRandom().nextDouble() > CommonConfig.CHUNK_SKIP_CHANCE.get()) {
            for (int p = 0; p < CommonConfig.NUMBER_PLUTONS_PER_CHUNK.get(); p++) {
                IDeposit pluton = GeolosysAPI.plutonRegistry.pick(level, pos);
                if (pluton == null) {
                    continue;
                }

                boolean anyGenerated = pluton.generate(level, pos, depCap, cgCap) > 0;
                if (anyGenerated) {
                    placedPluton = true;
                    pluton.afterGen(level, pos, depCap, cgCap);
                }
            }
        }
        // Let our tracker know that we did in fact traverse this chunk
        cgCap.setChunkGenerated(new ChunkPos(pos));


        return placedPluton || placedPending;

         */
        return false;
    }
/*
    private boolean placePendingBlocks(StructureWorldAccess worldAccess, IDepositCapability depCap, IChunkGennedCapability cgCap,
                                       BlockPos origin) {
        ChunkPos cp = new ChunkPos(origin);
        ConcurrentLinkedQueue<PendingBlock> q = depCap.getPendingBlocks(cp);
        if (cgCap.hasChunkGenerated(cp) && q.size() > 0) {
            Geolosys.getInstance().LOGGER.info(
                    "Chunk [{}, {}] has already generated but we're trying to place pending blocks anyways", cp.x,
                    cp.z);
        }
        q.stream().forEach(x -> FeatureUtils.enqueueBlockPlacement(worldAccess, cp, x.pos(), x.state(), depCap, cgCap));
        depCap.removePendingBlocksForChunk(cp);
        return q.size() > 0;
    }

 */
}
