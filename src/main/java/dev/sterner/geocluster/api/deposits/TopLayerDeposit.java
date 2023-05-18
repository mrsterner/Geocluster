package dev.sterner.geocluster.api.deposits;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.GeoclusterConfig;
import dev.sterner.geocluster.api.DepositUtils;
import dev.sterner.geocluster.api.IDeposit;
import dev.sterner.geocluster.common.components.IWorldChunkComponent;
import dev.sterner.geocluster.common.components.IWorldDepositComponent;
import dev.sterner.geocluster.common.utils.FeatureUtils;
import dev.sterner.geocluster.common.utils.GeoclusterUtils;
import dev.sterner.geocluster.common.utils.SerializerUtils;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TopLayerDeposit implements IDeposit {
    public static final String JSON_TYPE = "geocluster:deposit_top_layer";

    private final HashMap<String, HashMap<BlockState, Float>> oreToWtMap;
    private final HashMap<BlockState, Float> sampleToWtMap;
    private final int radius;
    private final int depth;
    private final float sampleChance;
    private final int genWt;
    private final HashSet<BlockState> blockStateMatchers;
    private final TagKey<Biome> biomeTag;
    // Optional biome stuff!
    /* Hashmap of blockMatcher.getRegistryName(): sumWt */
    private final HashMap<String, Float> cumulOreWtMap = new HashMap<>();
    private float sumWtSamples = 0.0F;

    public TopLayerDeposit(HashMap<String, HashMap<BlockState, Float>> oreBlocks, HashMap<BlockState, Float> sampleBlocks, int radius, int depth, float sampleChance, int genWt, TagKey<Biome> biomeTag, HashSet<BlockState> blockStateMatchers) {
        this.oreToWtMap = oreBlocks;
        this.sampleToWtMap = sampleBlocks;
        this.radius = radius;
        this.depth = depth;
        this.sampleChance = sampleChance;
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
        return null;
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
        return "Top Layer deposit with Blocks=" + this.getAllOres() + ", Samples=" + Arrays.toString(this.sampleToWtMap.keySet().toArray()) + ", Radius=" + this.radius + ", Depth=" + this.depth;
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
    public int generate(StructureWorldAccess level, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
        /* Dimension checking is done in PlutonRegistry#pick */
        /* Check biome allowance */
        if (!this.canPlaceInBiome(level.getBiome(pos))) {
            return 0;
        }

        int totlPlaced = 0;
        ChunkPos thisChunk = new ChunkPos(pos);

        int x = ((thisChunk.getStartX() + thisChunk.getEndX()) / 2) - level.getRandom().nextInt(8) + level.getRandom().nextInt(16);
        int z = ((thisChunk.getStartZ() + thisChunk.getEndZ()) / 2) - level.getRandom().nextInt(8) + level.getRandom().nextInt(16);
        int radX = (this.radius / 2) + level.getRandom().nextInt(this.radius / 2);
        int radZ = (this.radius / 2) + level.getRandom().nextInt(this.radius / 2);

        BlockPos basePos = new BlockPos(x, 0, z);

        for (int dX = -radX; dX <= radX; dX++) {
            for (int dZ = -radZ; dZ <= radZ; dZ++) {
                if (((dX * dX) + (dZ * dZ)) > this.radius + level.getRandom().nextInt(Math.max(1, this.radius / 2))) {
                    continue;
                }

                BlockPos baseForXZ = GeoclusterUtils.getTopSolidBlock(level, basePos.add(dX, 0, dZ));

                for (int i = 0; i < this.depth; i++) {
                    BlockPos placePos = baseForXZ.down(i);
                    BlockState current = level.getBlockState(placePos);
                    BlockState tmp = this.getOre(current, level.getRandom());
                    boolean isTop = i == 0;

                    if (tmp == null) {
                        continue;
                    } else if (tmp.contains(Properties.BOTTOM)) {
                        tmp = tmp.with(Properties.BOTTOM, !isTop);
                    }

                    // Skip this block if it can't replace the target block or doesn't have a
                    // manually-configured replacer in the blocks object
                    if (!(this.getBlockStateMatchers().contains(current) || this.oreToWtMap.containsKey(GeoclusterUtils.getRegistryName(current)))) {
                        continue;
                    }

                    if (FeatureUtils.enqueueBlockPlacement(level, thisChunk, placePos, tmp, deposits, chunksGenerated)) {
                        totlPlaced++;
                        if (isTop && level.getRandom().nextFloat() <= this.sampleChance) {
                            BlockState smpl = this.getSample(level.getRandom());
                            if (smpl != null) {
                                FeatureUtils.enqueueBlockPlacement(level, thisChunk, placePos.up(), smpl, deposits, chunksGenerated);
                                FeatureUtils.fixSnowyBlock(level, placePos);
                            }
                        }
                    }
                }
            }
        }

        return totlPlaced;
    }

    /**
     * Handles what to do after the world has generated
     */
    @Override
    public void afterGen(StructureWorldAccess level, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
        // Debug the pluton
        if (GeoclusterConfig.DEBUG_WORLD_GEN) {
            Geocluster.LOGGER.info("Generated {} in Chunk {} (Pos [{} {} {}])", this, new ChunkPos(pos), pos.getX(), pos.getY(), pos.getZ());
        }
    }

    @Override
    public HashSet<BlockState> getBlockStateMatchers() {
        return this.blockStateMatchers == null ? DepositUtils.getDefaultMatchers() : this.blockStateMatchers;
    }

    public static TopLayerDeposit deserialize(JsonObject json) {
        if (json == null) {
            return null;
        }

        try {
            // Plutons 101 -- basics and intro to getting one gen'd
            HashMap<String, HashMap<BlockState, Float>> oreBlocks = SerializerUtils.buildMultiBlockMatcherMap(json.get("blocks").getAsJsonObject());
            HashMap<BlockState, Float> sampleBlocks = SerializerUtils.buildMultiBlockMap(json.get("samples").getAsJsonArray());
            int radius = json.get("radius").getAsInt();
            int depth = json.get("depth").getAsInt();
            float sampleChance = json.get("chanceForSample").getAsFloat();
            int genWt = json.get("generationWeight").getAsInt();
            TagKey<Biome> biomeTag = TagKey.of(Registry.BIOME_KEY, new Identifier(json.get("biomeTag").getAsString().replace("#", "")));

            // Block State Matchers
            HashSet<BlockState> blockStateMatchers = DepositUtils.getDefaultMatchers();
            if (json.has("blockStateMatchers")) {
                blockStateMatchers = SerializerUtils.toBlockStateList(json.get("blockStateMatchers").getAsJsonArray());
            }

            return new TopLayerDeposit(oreBlocks, sampleBlocks, radius, depth, sampleChance, genWt, biomeTag, blockStateMatchers);
        } catch (Exception e) {
            Geocluster.LOGGER.error("Failed to parse: {}", e.getMessage());
            return null;
        }
    }

    public JsonElement serialize() {
        JsonObject json = new JsonObject();
        JsonObject config = new JsonObject();

        // Add basics of Plutons
        config.add("blocks", SerializerUtils.deconstructMultiBlockMatcherMap(this.oreToWtMap));
        config.add("samples", SerializerUtils.deconstructMultiBlockMap(this.sampleToWtMap));
        config.addProperty("radius", this.radius);
        config.addProperty("depth", this.depth);
        config.addProperty("chanceForSample", this.sampleChance);
        config.addProperty("generationWeight", this.genWt);
        config.addProperty("biomeTag", this.biomeTag.id().toString());
        // Glue the two parts of this together.
        json.addProperty("type", JSON_TYPE);
        json.add("config", config);
        return json;
    }
}