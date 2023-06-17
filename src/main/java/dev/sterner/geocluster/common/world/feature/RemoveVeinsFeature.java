package dev.sterner.geocluster.common.world.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import dev.sterner.geocluster.common.components.GeoclusterComponents;
import dev.sterner.geocluster.common.components.WorldChunkComponent;
import dev.sterner.geocluster.common.components.WorldDepositComponent;
import dev.sterner.geocluster.common.utils.FeatureUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.HashMap;

@Deprecated
public class RemoveVeinsFeature extends Feature<DefaultFeatureConfig> {
    private final ArrayList<Block> UNACCEPTABLE = Lists.newArrayList(
            Blocks.RAW_IRON_BLOCK,
            Blocks.RAW_COPPER_BLOCK,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.COPPER_ORE
    );

    private final HashMap<Block, Block> oreReplacementMap;

    public RemoveVeinsFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
        oreReplacementMap = initOreReplacementMap();
    }

    private HashMap<Block, Block> initOreReplacementMap() {
        return new HashMap<>() {{
            put(Blocks.COPPER_ORE, Blocks.STONE);
            put(Blocks.RAW_COPPER_BLOCK, Blocks.STONE);
            put(Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE);
            put(Blocks.RAW_IRON_BLOCK, Blocks.DEEPSLATE);
        }};
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        if (context.getGenerator() instanceof FlatChunkGenerator) {
            return false;
        }

        StructureWorldAccess world = context.getWorld();
        ChunkPos cp = new ChunkPos(context.getOrigin());
        WorldDepositComponent depositComponent = GeoclusterComponents.DEPOSIT.get(world.toServerWorld());
        WorldChunkComponent chunkComponent = GeoclusterComponents.CHUNK.get(world.toServerWorld());

        for (int x = cp.getStartX(); x <= cp.getEndX(); x++) {
            for (int z = cp.getStartZ(); z <= cp.getEndZ(); z++) {
                for (int y = world.getBottomY(); y < world.getHeight(); y++) {
                    BlockPos p = new BlockPos(x, y, z);
                    BlockState state = world.getBlockState(p);
                    if (UNACCEPTABLE.contains(state.getBlock())) {
                        BlockState properReplacement = oreReplacementMap.get(state.getBlock()).getDefaultState();
                        FeatureUtils.enqueueBlockPlacement(world, p, properReplacement, depositComponent, chunkComponent);
                    }
                }
            }
        }

        return true;
    }
}
