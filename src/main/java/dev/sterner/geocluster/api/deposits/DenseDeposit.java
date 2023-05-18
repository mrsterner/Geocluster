package dev.sterner.geocluster.api.deposits;

import dev.sterner.geocluster.api.IDeposit;
import net.minecraft.block.BlockState;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DenseDeposit implements IDeposit {

    public static final String JSON_TYPE = "geocluster:deposit_dense";

    private final HashMap<String, HashMap<BlockState, Float>> oreToWtMap;
    private final HashMap<BlockState, Float> sampleToWtMap;
    private final int yMin;
    private final int yMax;
    private final int size;
    private final int genWt;
    private final HashSet<BlockState> blockStateMatchers;
    private final TagKey<Biome> biomeTag;
    /* Hashmap of blockMatcher.getRegistryName(): sumWt */
    private final HashMap<String, Float> cumulOreWtMap = new HashMap<>();
    private float sumWtSamples = 0.0F;

    public DenseDeposit(HashMap<String, HashMap<BlockState, Float>> oreBlocks, HashMap<BlockState, Float> sampleBlocks, int yMin, int yMax, int size, int genWt, TagKey<Biome> biomeTag, HashSet<BlockState> blockStateMatchers) {
        this.oreToWtMap = oreBlocks;
        this.sampleToWtMap = sampleBlocks;
        this.yMin = yMin;
        this.yMax = yMax;
        this.size = size;
        this.genWt = genWt;
        this.biomeTag = biomeTag;
        this.blockStateMatchers = blockStateMatchers;

        // Verify that blocks.default exists.
        if (!this.oreToWtMap.containsKey("default")) {
            throw new RuntimeException("Pluton blocks should always have a default key");
        }

        for (Map.Entry<String, HashMap<BlockState, Float>> i : this.oreToWtMap.entrySet()) {
            if (!this.cumulOreWtMap.containsKey(i.getKey())) {
                this.cumulOreWtMap.put(i.getKey(), 0.0F);
            }

            for (Map.Entry<BlockState, Float> j : i.getValue().entrySet()) {
                float v = this.cumulOreWtMap.get(i.getKey());
                this.cumulOreWtMap.put(i.getKey(), v + j.getValue());
            }

            if (!DepositUtils.nearlyEquals(this.cumulOreWtMap.get(i.getKey()), 1.0F)) {
                throw new RuntimeException("Sum of weights for pluton blocks should equal 1.0");
            }
        }

        for (Map.Entry<BlockState, Float> e : this.sampleToWtMap.entrySet()) {
            this.sumWtSamples += e.getValue();
        }

        if (!DepositUtils.nearlyEquals(sumWtSamples, 1.0F)) {
            throw new RuntimeException("Sum of weights for pluton samples should equal 1.0");
        }
    }

    @Override
    public int generate(StructureWorldAccess level, BlockPos pos, IDepositCapability deposits, IChunkGennedCapability chunksGenerated) {
        return 0;
    }

    @Override
    public void afterGen(StructureWorldAccess level, BlockPos pos, IDepositCapability deposits, IChunkGennedCapability chunksGenerated) {

    }

    @Override
    public HashSet<BlockState> getAllOres() {
        return null;
    }

    @Override
    public int getGenWt() {
        return 0;
    }

    @Override
    public boolean canPlaceInBiome(RegistryEntry<Biome> biome) {
        return false;
    }

    @Override
    public HashSet<BlockState> getBlockStateMatchers() {
        return null;
    }
}
