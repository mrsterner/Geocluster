package dev.sterner.geocluster.api.deposits;

import dev.sterner.geocluster.api.DepositUtils;
import dev.sterner.geocluster.common.components.IWorldChunkComponent;
import dev.sterner.geocluster.common.components.IWorldDepositComponent;
import dev.sterner.geocluster.common.utils.FeatureUtils;
import dev.sterner.geocluster.common.utils.SampleUtils;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.HashMap;
import java.util.Map;

public abstract class Deposit {
    public final HashMap<String, HashMap<BlockState, Float>> oreToWeightMap;
    public final HashMap<BlockState, Float> sampleToWeightMap;
    public final HashMap<String, Float> cumulativeOreWeightMap = new HashMap<>();
    public float sumWeightSamples = 0.0F;

    public Deposit(HashMap<String, HashMap<BlockState, Float>> oreBlocks, HashMap<BlockState, Float> sampleBlocks) {
        this.oreToWeightMap = oreBlocks;
        this.sampleToWeightMap = sampleBlocks;
    }

    public static void checkDefault(HashMap<String, HashMap<BlockState, Float>> oreToWeightMap, HashMap<BlockState, Float> sampleToWeightMap, HashMap<String, Float> cumulativeOreWeightMap, float sumWeightSamples) {
        if (!oreToWeightMap.containsKey("default")) {
            throw new RuntimeException("Cluster blocks should always have a default key");
        }

        for (Map.Entry<String, HashMap<BlockState, Float>> i : oreToWeightMap.entrySet()) {
            if (!cumulativeOreWeightMap.containsKey(i.getKey())) {
                cumulativeOreWeightMap.put(i.getKey(), 0.0F);
            }

            for (Map.Entry<BlockState, Float> j : i.getValue().entrySet()) {
                float v = cumulativeOreWeightMap.get(i.getKey());
                cumulativeOreWeightMap.put(i.getKey(), v + j.getValue());
            }

            if (!DepositUtils.nearlyEquals(cumulativeOreWeightMap.get(i.getKey()), 1.0F)) {
                throw new RuntimeException("Sum of weights for cluster blocks should equal 1.0");
            }
        }

        for (Map.Entry<BlockState, Float> e : sampleToWeightMap.entrySet()) {
            sumWeightSamples += e.getValue();
        }

        if (!DepositUtils.nearlyEquals(sumWeightSamples, 1.0F)) {
            throw new RuntimeException("Sum of weights for cluster samples should equal 1.0");
        }
    }

    public static void findAndPlaceSample(int maxSampleCnt, BlockState sampleState, StructureWorldAccess world, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
        for (int i = 0; i < maxSampleCnt; i++) {
            BlockState tmp = sampleState;
            if (tmp == null) {
                continue;
            }

            BlockPos samplePos = SampleUtils.getSamplePosition(world, new ChunkPos(pos));

            if (samplePos == null || SampleUtils.inNonWaterFluid(world, samplePos)) {
                continue;
            }

            if (SampleUtils.isInWater(world, samplePos) && tmp.contains(Properties.WATERLOGGED)) {
                tmp = tmp.with(Properties.WATERLOGGED, Boolean.TRUE);
            }

            FeatureUtils.enqueueBlockPlacement(world, samplePos, tmp, deposits, chunksGenerated);
            FeatureUtils.fixSnowyBlock(world, samplePos);
        }
    }
}
