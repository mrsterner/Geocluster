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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TopLayerDeposit extends Deposit implements IDeposit {
    public static final String JSON_TYPE = "geocluster:deposit_top_layer";
    public final HashMap<String, HashMap<BlockState, Float>> oreToWeightMap;
    public final HashMap<BlockState, Float> sampleToWeightMap;
    public final HashMap<String, Float> cumulativeOreWeightMap = new HashMap<>();
    public float sumWeightSamples = 0.0F;
    private final int radius;
    private final int depth;
    private final float sampleChance;
    private final int weight;
    private final HashSet<BlockState> blockStateMatchers;
    private final TagKey<Biome> biomeTag;

    public TopLayerDeposit(HashMap<String, HashMap<BlockState, Float>> oreBlocks, HashMap<BlockState, Float> sampleBlocks, int radius, int depth, float sampleChance, int weight, TagKey<Biome> biomeTag, HashSet<BlockState> blockStateMatchers) {
        this.oreToWeightMap = oreBlocks;
        this.sampleToWeightMap = sampleBlocks;
        this.radius = radius;
        this.depth = depth;
        this.sampleChance = sampleChance;
        this.weight = weight;
        this.biomeTag = biomeTag;
        this.blockStateMatchers = blockStateMatchers;

        validateFormat(oreToWeightMap, cumulativeOreWeightMap, sampleToWeightMap, sumWeightSamples);
    }

    @Nullable
    public BlockState getOre(BlockState currentState, Random rand) {
        String res = GeoclusterUtils.getRegistryName(currentState);
        if (this.oreToWeightMap.containsKey(res)) {
            HashMap<BlockState, Float> mp = this.oreToWeightMap.get(res);
            return DepositUtils.pick(mp, rand);
        }
        return DepositUtils.pick(this.oreToWeightMap.get("default"), rand);
    }

    @Nullable
    public BlockState getSample(Random rand) {
        return DepositUtils.pick(this.sampleToWeightMap, rand);
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
    public int getWeight() {
        return this.weight;
    }


    @Override
    public String toString() {
        return "Top Layer deposit with Blocks=" + this.getAllOres() + ", Samples=" + Arrays.toString(this.sampleToWeightMap.keySet().toArray()) + ", Radius=" + this.radius + ", Depth=" + this.depth;
    }

    @Override
    public int generate(StructureWorldAccess level, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
        /* Dimension checking is done in DepositCache#pick */
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
                    if (!(this.getBlockStateMatchers().contains(current) || this.oreToWeightMap.containsKey(GeoclusterUtils.getRegistryName(current)))) {
                        continue;
                    }

                    if (FeatureUtils.enqueueBlockPlacement(level, placePos, tmp, deposits, chunksGenerated)) {
                        totlPlaced++;
                        if (isTop && level.getRandom().nextFloat() <= this.sampleChance) {
                            BlockState smpl = this.getSample(level.getRandom());
                            if (smpl != null) {
                                FeatureUtils.enqueueBlockPlacement(level, placePos.up(), smpl, deposits, chunksGenerated);
                                FeatureUtils.fixSnowyBlock(level, placePos);
                            }
                        }
                    }
                }
            }
        }

        return totlPlaced;
    }

    @Override
    public void generatePost(StructureWorldAccess level, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
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
            HashMap<String, HashMap<BlockState, Float>> oreBlocks = SerializerUtils.buildMultiBlockMatcherMap(json.get("blocks").getAsJsonObject());
            HashMap<BlockState, Float> sampleBlocks = SerializerUtils.buildMultiBlockMap(json.get("samples").getAsJsonArray());
            int radius = json.get("radius").getAsInt();
            int depth = json.get("depth").getAsInt();
            float sampleChance = json.get("chanceForSample").getAsFloat();
            int genWt = json.get("generationWeight").getAsInt();
            TagKey<Biome> biomeTag = TagKey.of(RegistryKeys.BIOME, new Identifier(json.get("biomeTag").getAsString().replace("#", "")));

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

        config.add("blocks", SerializerUtils.deconstructMultiBlockMatcherMap(this.oreToWeightMap));
        config.add("samples", SerializerUtils.deconstructMultiBlockMap(this.sampleToWeightMap));
        config.addProperty("radius", this.radius);
        config.addProperty("depth", this.depth);
        config.addProperty("chanceForSample", this.sampleChance);
        config.addProperty("generationWeight", this.getWeight());
        config.addProperty("biomeTag", this.biomeTag.id().toString());
        json.addProperty("type", JSON_TYPE);
        json.add("config", config);
        return json;
    }
}