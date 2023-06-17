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
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
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

public class LayerDeposit extends Deposit implements IDeposit {
    public static final String JSON_TYPE = "geocluster:deposit_layer";
    public final HashMap<String, HashMap<BlockState, Float>> oreToWeightMap;
    public final HashMap<BlockState, Float> sampleToWeightMap;
    public final HashMap<String, Float> cumulativeOreWeightMap = new HashMap<>();
    public float sumWeightSamples = 0.0F;
    private final int yMin;
    private final int yMax;
    private final int radius;
    private final int depth;
    private final int weight;
    private final HashSet<BlockState> blockStateMatchers;

    private final TagKey<Biome> biomeTag;

    public LayerDeposit(HashMap<String, HashMap<BlockState, Float>> oreBlocks, HashMap<BlockState, Float> sampleBlocks, int yMin, int yMax, int radius, int depth, int weight, TagKey<Biome> biomeTag, HashSet<BlockState> blockStateMatchers) {
        this.oreToWeightMap = oreBlocks;
        this.sampleToWeightMap = sampleBlocks;
        this.yMin = yMin;
        this.yMax = yMax;
        this.radius = radius;
        this.depth = depth;
        this.weight = weight;
        this.biomeTag = biomeTag;
        this.blockStateMatchers = blockStateMatchers;

        validateFormat(oreToWeightMap, cumulativeOreWeightMap, sampleToWeightMap, sumWeightSamples);
    }


    @Nullable
    public BlockState getOre(BlockState currentState, Random rand) {
        String res = GeoclusterUtils.getRegistryName(currentState);
        if (this.oreToWeightMap.containsKey(res)) {
            // Return a choice from a specialized set here
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
        HashSet<BlockState> ret = new HashSet<BlockState>();
        this.oreToWeightMap.values().forEach(x -> ret.addAll(x.keySet()));
        ret.remove(Blocks.AIR.getDefaultState());
        return ret.isEmpty() ? null : ret;
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
        return "Layer deposit with Blocks=" + this.getAllOres() + ", Samples=" + Arrays.toString(this.sampleToWeightMap.keySet().toArray()) + ", Y Range=[" + this.yMin + "," + this.yMax + "], Radius=" + this.radius + ", Depth=" + this.depth;
    }

    @Override
    public int generate(StructureWorldAccess world, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
        /* Dimension checking is done in DepositCache#pick */
        /* Check biome allowance */
        if (!this.canPlaceInBiome(world.getBiome(pos))) {
            return 0;
        }

        int totlPlaced = 0;

        ChunkPos thisChunk = new ChunkPos(pos);

        int x = ((thisChunk.getStartX() + thisChunk.getEndX()) / 2) - world.getRandom().nextInt(8) + world.getRandom().nextInt(16);
        int y = this.yMin + world.getRandom().nextInt(Math.abs(this.yMax - this.yMin));
        int z = ((thisChunk.getStartZ() + thisChunk.getEndZ()) / 2) - world.getRandom().nextInt(8) + world.getRandom().nextInt(16);
        int max = GeoclusterUtils.getTopSolidBlock(world, pos).getY();
        if (y > max) {
            y = Math.max(yMin, max);
        }

        BlockPos basePos = new BlockPos(x, y, z);

        for (int dX = -this.radius; dX <= this.radius; dX++) {
            for (int dZ = -this.radius; dZ <= this.radius; dZ++) {
                for (int dY = 0; dY < depth; dY++) {
                    float dist = dX * dX + dZ * dZ;
                    if (dist > this.radius * 2) {
                        continue;
                    }

                    BlockPos placePos = basePos.add(dX, dY, dZ);
                    BlockState current = world.getBlockState(placePos);
                    BlockState tmp = this.getOre(current, world.getRandom());
                    if (tmp == null) {
                        continue;
                    }

                    // Skip this block if it can't replace the target block or doesn't have a
                    // manually-configured replacer in the blocks object
                    if (!(this.getBlockStateMatchers().contains(current) || this.oreToWeightMap.containsKey(GeoclusterUtils.getRegistryName(current)))) {
                        continue;
                    }

                    if (FeatureUtils.enqueueBlockPlacement(world, placePos, tmp, deposits, chunksGenerated)) {
                        totlPlaced++;
                    }
                }
            }
        }
        return totlPlaced;
    }

    @Override
    public void generatePost(StructureWorldAccess world, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
        if (GeoclusterConfig.DEBUG_WORLD_GEN) {
            Geocluster.LOGGER.info("Generated {} in Chunk {} (Pos [{} {} {}])", this, new ChunkPos(pos), pos.getX(), pos.getY(), pos.getZ());
        }

        int maxSampleCnt = Math.min(GeoclusterConfig.MAX_SAMPLES_PER_CHUNK, (this.radius / GeoclusterConfig.MAX_SAMPLES_PER_CHUNK) + (this.radius % GeoclusterConfig.MAX_SAMPLES_PER_CHUNK));
        Deposit.findAndPlaceSample(maxSampleCnt, this.getSample(world.getRandom()), world, pos, deposits, chunksGenerated);
    }

    @Override
    public HashSet<BlockState> getBlockStateMatchers() {
        return this.blockStateMatchers == null ? DepositUtils.getDefaultMatchers() : this.blockStateMatchers;
    }

    public static LayerDeposit deserialize(JsonObject json) {
        if (json == null) {
            return null;
        }

        try {
            HashMap<String, HashMap<BlockState, Float>> oreBlocks = SerializerUtils.buildMultiBlockMatcherMap(json.get("blocks").getAsJsonObject());
            HashMap<BlockState, Float> sampleBlocks = SerializerUtils.buildMultiBlockMap(json.get("samples").getAsJsonArray());
            int yMin = json.get("yMin").getAsInt();
            int yMax = json.get("yMax").getAsInt();
            int radius = json.get("radius").getAsInt();
            int depth = json.get("depth").getAsInt();
            int genWt = json.get("generationWeight").getAsInt();
            TagKey<Biome> biomeTag = TagKey.of(RegistryKeys.BIOME, new Identifier(json.get("biomeTag").getAsString().replace("#", "")));
            HashSet<BlockState> blockStateMatchers = DepositUtils.getDefaultMatchers();
            if (json.has("blockStateMatchers")) {
                blockStateMatchers = SerializerUtils.toBlockStateList(json.get("blockStateMatchers").getAsJsonArray());
            }

            return new LayerDeposit(oreBlocks, sampleBlocks, yMin, yMax, radius, depth, genWt, biomeTag, blockStateMatchers);
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
        config.addProperty("yMin", this.yMin);
        config.addProperty("yMax", this.yMax);
        config.addProperty("radius", this.radius);
        config.addProperty("depth", this.depth);
        config.addProperty("generationWeight", this.getWeight());
        config.addProperty("biomeTag", this.biomeTag.id().toString());

        json.addProperty("type", JSON_TYPE);
        json.add("config", config);
        return json;
    }
}