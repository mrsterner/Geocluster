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
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
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

public class DikeDeposit extends Deposit implements IDeposit {
    public static final String JSON_TYPE = "geocluster:deposit_dike";
    public final HashMap<String, HashMap<BlockState, Float>> oreToWeightMap;
    public final HashMap<BlockState, Float> sampleToWeightMap;
    public final HashMap<String, Float> cumulativeOreWeightMap = new HashMap<>();
    public float sumWeightSamples = 0.0F;
    private final int yMin;
    private final int yMax;
    private final int maxHeight;
    private final int baseRadius;
    private final int weight;
    private final HashSet<BlockState> blockStateMatchers;
    private final TagKey<Biome> biomeTag;

    public DikeDeposit(HashMap<String, HashMap<BlockState, Float>> oreBlocks, HashMap<BlockState, Float> sampleBlocks, int yMin, int yMax, int baseRadius, int maxHeight, int weight, TagKey<Biome> biomeTag, HashSet<BlockState> blockStateMatchers) {
        this.oreToWeightMap = oreBlocks;
        this.sampleToWeightMap = sampleBlocks;
        this.yMin = yMin;
        this.yMax = yMax;
        this.maxHeight = maxHeight;
        this.baseRadius = baseRadius;
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
        return "Dike deposit with Blocks=" + this.getAllOres() + ", Samples=" + Arrays.toString(this.sampleToWeightMap.keySet().toArray()) + ", Y Range=[" + this.yMin + "," + this.yMax + "], Radius of Base=" + this.baseRadius;
    }

    @Override
    public int generate(StructureWorldAccess world, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
        /* Dimension checking is done in DepositCache#pick */
        /* Check biome allowance */
        if (!this.canPlaceInBiome(world.getBiome(pos))) {
            return 0;
        }

        ChunkPos thisChunk = new ChunkPos(pos);

        int height = Math.abs((this.yMax - this.yMin));
        int x = thisChunk.getStartX() + world.getRandom().nextInt(16);
        int z = thisChunk.getStartZ() + world.getRandom().nextInt(16);
        int yMin = this.yMin + world.getRandom().nextInt(height / 4);
        int yMax = this.yMax - world.getRandom().nextInt(height / 4);

        int cmaxHeight = maxHeight - world.getRandom().nextBetween(0, maxHeight / 4);
        int maxStart = MathHelper.abs(yMax - cmaxHeight - yMin);
        yMin = world.getRandom().nextInt(maxStart + 1) + yMin;
        yMax = yMin + cmaxHeight;

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

            // flip at around the halfway point.
            if (yMin + (htRnd / 2) <= dY) {
                shouldSub = true;
            }
            if (world.getRandom().nextInt(3) == 0) {
                rad += shouldSub ? -1 : 1;
                if (rad <= 0) {
                    return totlPlaced;
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

        int maxSampleCnt = Math.min(GeoclusterConfig.MAX_SAMPLES_PER_CHUNK, (this.baseRadius / GeoclusterConfig.MAX_SAMPLES_PER_CHUNK) + (this.baseRadius % GeoclusterConfig.MAX_SAMPLES_PER_CHUNK));
        Deposit.findAndPlaceSample(maxSampleCnt, this.getSample(world.getRandom()), world, pos, deposits, chunksGenerated);
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
            HashMap<String, HashMap<BlockState, Float>> oreBlocks = SerializerUtils.buildMultiBlockMatcherMap(json.get("blocks").getAsJsonObject());
            HashMap<BlockState, Float> sampleBlocks = SerializerUtils.buildMultiBlockMap(json.get("samples").getAsJsonArray());
            int yMin = json.get("yMin").getAsInt();
            int yMax = json.get("yMax").getAsInt();
            int baseRadius = json.get("baseRadius").getAsInt();
            int maxHeight = json.get("maxHeight").getAsInt();
            int genWt = json.get("generationWeight").getAsInt();
            TagKey<Biome> biomeTag = TagKey.of(Registry.BIOME_KEY, new Identifier(json.get("biomeTag").getAsString().replace("#", "")));

            HashSet<BlockState> blockStateMatchers = DepositUtils.getDefaultMatchers();
            if (json.has("blockStateMatchers")) {
                blockStateMatchers = SerializerUtils.toBlockStateList(json.get("blockStateMatchers").getAsJsonArray());
            }

            return new DikeDeposit(oreBlocks, sampleBlocks, yMin, yMax, baseRadius, maxHeight, genWt, biomeTag, blockStateMatchers);
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
        config.addProperty("baseRadius", this.baseRadius);
        config.addProperty("maxHeight", this.maxHeight);
        config.addProperty("generationWeight", this.getWeight());
        config.addProperty("biomeTag", this.biomeTag.id().toString());
        json.addProperty("type", JSON_TYPE);
        json.add("config", config);
        return json;
    }
}