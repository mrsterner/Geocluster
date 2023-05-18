package dev.sterner.geocluster.api.deposits;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sterner.geocluster.api.DepositUtils;
import dev.sterner.geocluster.api.IDeposit;
import dev.sterner.geocluster.common.utils.GeoclusterUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DikeDeposit implements IDeposit {
    public static final String JSON_TYPE = "geocluster:deposit_dike";

    private final HashMap<String, HashMap<BlockState, Float>> oreToWtMap;
    private final HashMap<BlockState, Float> sampleToWtMap;
    private final int yMin;
    private final int yMax;
    private final int baseRadius;
    private final int genWt;
    private final HashSet<BlockState> blockStateMatchers;
    private final TagKey<Biome> biomeTag;

    /* Hashmap of blockMatcher.getRegistryName(): sumWt */
    private final HashMap<String, Float> cumulOreWtMap = new HashMap<>();
    private float sumWtSamples = 0.0F;

    public DikeDeposit(HashMap<String, HashMap<BlockState, Float>> oreBlocks, HashMap<BlockState, Float> sampleBlocks, int yMin, int yMax, int baseRadius, int genWt, TagKey<Biome> biomeTag, HashSet<BlockState> blockStateMatchers) {
        this.oreToWtMap = oreBlocks;
        this.sampleToWtMap = sampleBlocks;
        this.yMin = yMin;
        this.yMax = yMax;
        this.baseRadius = baseRadius;
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


    @Nullable
    public BlockState getOre(BlockState currentState, Random rand) {
        String res = GeoclusterUtils.getRegistryName(currentState);
        if (this.oreToWtMap.containsKey(res)) {
            // Return a choice from a specialized set here
            HashMap<BlockState, Float> mp = this.oreToWtMap.get(res);
            return DepositUtils.pick(mp, this.cumulOreWtMap.get(res), rand);
        }
        return DepositUtils.pick(this.oreToWtMap.get("default"), this.cumulOreWtMap.get("default"), rand);
    }

    @Nullable
    public BlockState getSample(Random rand) {
        return DepositUtils.pick(this.sampleToWtMap, this.sumWtSamples, rand);
    }

    @Override
    @Nullable
    public HashSet<BlockState> getAllOres() {
        HashSet<BlockState> ret = new HashSet<BlockState>();
        this.oreToWtMap.values().forEach(x -> ret.addAll(x.keySet()));
        ret.remove(Blocks.AIR.getDefaultState());
        return ret.isEmpty() ? null : ret;
    }

    @Override
    public boolean canPlaceInBiome(RegistryEntry<Biome> b) {
        return b.isIn(this.biomeTag);
    }

    @Override
    public int getGenWt() {
        return this.genWt;
    }


    @Override
    public String toString() {
        return "Dike deposit with Blocks=" + this.getAllOres() + ", Samples=" + Arrays.toString(this.sampleToWtMap.keySet().toArray()) + ", Y Range=[" + this.yMin + "," + this.yMax + "], Radius of Base=" + this.baseRadius;
    }

    /**
     * Handles full-on generation of this type of pluton. Requires 0 arguments as
     * everything is self-contained in this class
     *
     * @return (int) the number of pluton resource blocks placed. If 0 -- this
     * should be evaluted as a false for use of Mojang's sort-of sketchy
     * generation code in
     */
    @Override
    public int generate(WorldGenLevel level, BlockPos pos, IDepositCapability deposits, IChunkGennedCapability chunksGenerated) {
        /* Dimension checking is done in PlutonRegistry#pick */
        /* Check biome allowance */
        if (!this.canPlaceInBiome(level.getBiome(pos))) {
            return 0;
        }

        ChunkPos thisChunk = new ChunkPos(pos);
        int height = Math.abs((this.yMax - this.yMin));
        int x = thisChunk.getMinBlockX() + level.getRandom().nextInt(16);
        int z = thisChunk.getMinBlockZ() + level.getRandom().nextInt(16);
        int yMin = this.yMin + level.getRandom().nextInt(height / 4);
        int yMax = this.yMax - level.getRandom().nextInt(height / 4);
        int max = Utils.getTopSolidBlock(level, pos).getY();
        if (yMin > max) {
            yMin = Math.max(yMin, max);
        } else if (yMin == yMax) {
            yMax = this.yMax;
        }
        BlockPos basePos = new BlockPos(x, yMin, z);

        int totlPlaced = 0;
        int htRnd = Math.abs((yMax - yMin));
        int rad = this.baseRadius / 2;
        boolean shouldSub = false;

        for (int dY = yMin; dY <= yMax; dY++) {
            for (int dX = -rad; dX <= rad; dX++) {
                for (int dZ = -rad; dZ <= rad; dZ++) {
                    float dist = (dX * dX) + (dZ * dZ);
                    if (dist > rad) {
                        continue;
                    }

                    BlockPos placePos = new BlockPos(basePos.getX() + dX, dY, basePos.getZ() + dZ);
                    BlockState current = level.getBlockState(placePos);
                    BlockState tmp = this.getOre(current, level.getRandom());
                    if (tmp == null) {
                        continue;
                    }

                    // Skip this block if it can't replace the target block or doesn't have a
                    // manually-configured replacer in the blocks object
                    if (!(this.getBlockStateMatchers().contains(current) || this.oreToWtMap.containsKey(Utils.getRegistryName(current)))) {
                        continue;
                    }

                    if (FeatureUtils.enqueueBlockPlacement(level, new ChunkPos(pos), placePos, tmp, deposits, chunksGenerated)) {
                        totlPlaced++;
                    }
                }
            }

            // flip at around the halfway point.
            if (yMin + (htRnd / 2) <= dY) {
                shouldSub = true;
            }
            if (level.getRandom().nextInt(3) == 0) {
                rad += shouldSub ? -1 : 1;
                if (rad <= 0) {
                    return totlPlaced;
                }
            }
        }

        return totlPlaced;
    }

    /**
     * Handles what to do after the world has generated
     */
    @Override
    public void afterGen(WorldGenLevel level, BlockPos pos, IDepositCapability deposits, IChunkGennedCapability chunksGenerated) {
        // Debug the pluton
        if (CommonConfig.DEBUG_WORLD_GEN.get()) {
            Geolosys.getInstance().LOGGER.info("Generated {} in Chunk {} (Pos [{} {} {}])", this.toString(), new ChunkPos(pos), pos.getX(), pos.getY(), pos.getZ());
        }

        ChunkPos thisChunk = new ChunkPos(pos);
        int maxSampleCnt = Math.min(CommonConfig.MAX_SAMPLES_PER_CHUNK.get(), (this.baseRadius / CommonConfig.MAX_SAMPLES_PER_CHUNK.get()) + (this.baseRadius % CommonConfig.MAX_SAMPLES_PER_CHUNK.get()));
        maxSampleCnt = Math.max(maxSampleCnt, 1);
        for (int i = 0; i < maxSampleCnt; i++) {
            BlockState tmp = this.getSample(level.getRandom());
            if (tmp == null) {
                continue;
            }

            BlockPos samplePos = SampleUtils.getSamplePosition(level, new ChunkPos(pos));
            if (samplePos == null || SampleUtils.inNonWaterFluid(level, samplePos)) {
                continue;
            }

            if (SampleUtils.isInWater(level, samplePos) && tmp.hasProperty(BlockStateProperties.WATERLOGGED)) {
                tmp = tmp.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE);
            }

            FeatureUtils.enqueueBlockPlacement(level, thisChunk, samplePos, tmp, deposits, chunksGenerated);
            FeatureUtils.fixSnowyBlock(level, samplePos);
        }
    }

    @Override
    public HashSet<BlockState> getBlockStateMatchers() {
        return this.blockStateMatchers == null ? DepositUtils.getDefaultMatchers() : this.blockStateMatchers;
    }

    public static DikeDeposit deserialize(JsonObject json) {
        if (json == null) {
            return null;
        }

        try {
            // Plutons 101 -- basics and intro to getting one gen'd
            HashMap<String, HashMap<BlockState, Float>> oreBlocks = SerializerUtils.buildMultiBlockMatcherMap(json.get("blocks").getAsJsonObject());
            HashMap<BlockState, Float> sampleBlocks = SerializerUtils.buildMultiBlockMap(json.get("samples").getAsJsonArray());
            int yMin = json.get("yMin").getAsInt();
            int yMax = json.get("yMax").getAsInt();
            int baseRadius = json.get("baseRadius").getAsInt();
            int genWt = json.get("generationWeight").getAsInt();
            TagKey<Biome> biomeTag = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(json.get("biomeTag").getAsString().replace("#", "")));

            // Block State Matchers
            HashSet<BlockState> blockStateMatchers = DepositUtils.getDefaultMatchers();
            if (json.has("blockStateMatchers")) {
                blockStateMatchers = SerializerUtils.toBlockStateList(json.get("blockStateMatchers").getAsJsonArray());
            }

            return new DikeDeposit(oreBlocks, sampleBlocks, yMin, yMax, baseRadius, genWt, biomeTag, blockStateMatchers);
        } catch (Exception e) {
            Geolosys.getInstance().LOGGER.error("Failed to parse: {}", e.getMessage());
            return null;
        }
    }

    public JsonElement serialize() {
        JsonObject json = new JsonObject();
        JsonObject config = new JsonObject();

        // Add basics of Plutons
        config.add("blocks", SerializerUtils.deconstructMultiBlockMatcherMap(this.oreToWtMap));
        config.add("samples", SerializerUtils.deconstructMultiBlockMap(this.sampleToWtMap));
        config.addProperty("yMin", this.yMin);
        config.addProperty("yMax", this.yMax);
        config.addProperty("baseRadius", this.baseRadius);
        config.addProperty("generationWeight", this.genWt);
        config.addProperty("biomeTag", this.biomeTag.location().toString());
        // Glue the two parts of this together.
        json.addProperty("type", JSON_TYPE);
        json.add("config", config);
        return json;
    }
}