package dev.sterner.common.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ReplaceOresFeature extends Feature<DefaultFeatureConfig> {
    public ReplaceOresFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        if (context.getGenerator() instanceof FlatChunkGenerator) {
            return false;
        }
        /*

        StructureWorldAccess level = context.getWorld();
        ChunkPos cp = new ChunkPos(context.getOrigin());
        IDepositCapability deposits = level.getLevel().getCapability(DepositCapability.CAPABILITY)
                .orElseThrow(() -> new RuntimeException(
                        "Geolosys detected a null Pluton capability somehow. Are any invasive world gen mods active?"));
        IChunkGennedCapability chunks = level.getLevel().getCapability(ChunkGennedCapability.CAPABILITY)
                .orElseThrow(() -> new RuntimeException(
                        "Geolosys detected a null Pluton capability somehow. Are any invasive world gen mods active?"));

        for (int x = cp.getStartX(); x <= cp.getEndX(); x++) {
            for (int z = cp.getStartZ(); z <= cp.getEndZ(); z++) {
                for (int y = level.getBottomY(); y < level.getTopY(); y++) {
                    BlockPos p = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(p);
                    if (UNACCEPTABLE.contains(state.getBlock())) {
                        BlockState properReplacement = oreReplacementMap.get(state.getBlock()).defaultBlockState();
                        FeatureUtils.enqueueBlockPlacement(level, cp, p, properReplacement, deposits, chunks);
                    }
                }
            }
        }

         */

        return true;
    }
}
