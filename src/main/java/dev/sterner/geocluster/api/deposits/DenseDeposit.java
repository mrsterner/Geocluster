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

import java.util.HashMap;
import java.util.HashSet;

public class DenseDeposit extends Deposit implements IDeposit {

    public static final String JSON_TYPE = "geocluster:deposit_dense";

    private final int yMin;
    private final int yMax;
    private final int size;
    private final int weight;
    private final HashSet<BlockState> blockStateMatchers;
    private final TagKey<Biome> biomeTag;

    public DenseDeposit(HashMap<String, HashMap<BlockState, Float>> oreBlocks, HashMap<BlockState, Float> sampleBlocks, int yMin, int yMax, int size, int weight, TagKey<Biome> biomeTag, HashSet<BlockState> blockStateMatchers) {
        super(oreBlocks, sampleBlocks);
        this.yMin = yMin;
        this.yMax = yMax;
        this.size = size;
        this.weight = weight;
        this.biomeTag = biomeTag;
        this.blockStateMatchers = blockStateMatchers;

        Deposit.checkDefault(oreToWeightMap, sampleToWeightMap, cumulativeOreWeightMap, sumWeightSamples);
    }

    @Nullable
    public BlockState getOre(BlockState currentState, Random rand) {
        String res = this.oreToWeightMap.containsKey(GeoclusterUtils.getRegistryName(currentState)) ? GeoclusterUtils.getRegistryName(currentState) : "default";
        return DepositUtils.pick(this.oreToWeightMap.get(res), rand);
    }

    @Nullable
    public BlockState getSample(Random rand) {
        return DepositUtils.pick(this.sampleToWeightMap, rand);
    }

    @Override
    public int generate(StructureWorldAccess level, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
        if (!canPlaceInBiome(level.getBiome(pos))) {
            return 0;
        }

        int totalPlaced = 0;
        int max = GeoclusterUtils.getTopSolidBlock(level, pos).getY();
        int randY = Math.max(yMin, max);

        Random random = level.getRandom();
        float ranFlt = random.nextFloat() * (float) Math.PI;
        double x1 = (pos.getX() + 8) + MathHelper.sin(ranFlt) * size / 8.0F;
        double x2 = (pos.getX() + 8) - MathHelper.sin(ranFlt) * size / 8.0F;
        double z1 = (pos.getZ() + 8) + MathHelper.cos(ranFlt) * size / 8.0F;
        double z2 = (pos.getZ() + 8) - MathHelper.cos(ranFlt) * size / 8.0F;
        double y1 = randY + random.nextInt(3) - 2;
        double y2 = randY + random.nextInt(3) - 2;

        double radiusDiv2 = size / 16.0D;
        double radiusDiv8 = size / 8.0D;

        for (int i = 0; i < size; ++i) {
            float radScl = (float) i / (float) size;
            double xNoise = x1 + (x2 - x1) * radScl;
            double yNoise = y1 + (y2 - y1) * radScl;
            double zNoise = z1 + (z2 - z1) * radScl;
            double noise = random.nextDouble() * radiusDiv8;
            double radius = (MathHelper.sin((float) Math.PI * radScl) + 1.0F) * noise + 1.0D;
            int xmin = MathHelper.floor(xNoise - radius / 2.0D);
            int ymin = MathHelper.floor(yNoise - radius / 2.0D);
            int zmin = MathHelper.floor(zNoise - radius / 2.0D);
            int xmax = MathHelper.floor(xNoise + radius / 2.0D);
            int ymax = MathHelper.floor(yNoise + radius / 2.0D);
            int zmax = MathHelper.floor(zNoise + radius / 2.0D);

            for (int x = xmin; x <= xmax; ++x) {
                double layerRadX = (x + 0.5D - xNoise) / radiusDiv2;

                if (Math.pow(layerRadX, 2) < 1.0D) {
                    for (int y = ymin; y <= ymax; ++y) {
                        double layerRadY = (y + 0.5D - yNoise) / radiusDiv2;

                        if (Math.pow(layerRadX, 2) + Math.pow(layerRadY, 2) < 1.0D) {
                            for (int z = zmin; z <= zmax; ++z) {
                                double layerRadZ = (z + 0.5D - zNoise) / radiusDiv2;

                                if (Math.pow(layerRadX, 2) + Math.pow(layerRadY, 2) + Math.pow(layerRadZ, 2) < 1.0D) {
                                    BlockPos placePos = new BlockPos(x, y, z);
                                    BlockState current = level.getBlockState(placePos);
                                    BlockState tmp = getOre(current, random);
                                    if (tmp == null) {
                                        continue;
                                    }

                                    if (!(getBlockStateMatchers().contains(current) || oreToWeightMap.containsKey(GeoclusterUtils.getRegistryName(current)))) {
                                        continue;
                                    }

                                    if (FeatureUtils.enqueueBlockPlacement(level, placePos, tmp, deposits, chunksGenerated)) {
                                        totalPlaced++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return totalPlaced;
    }

    @Override
    public void generatePost(StructureWorldAccess world, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated) {
        if (GeoclusterConfig.DEBUG_WORLD_GEN) {
            Geocluster.LOGGER.info("Generated {} in Chunk {} (Pos [{} {} {}])", this, new ChunkPos(pos), pos.getX(), pos.getY(), pos.getZ());
        }

        int maxSampleCnt = Math.min(GeoclusterConfig.MAX_SAMPLES_PER_CHUNK, (this.size / GeoclusterConfig.MAX_SAMPLES_PER_CHUNK) + (this.size % GeoclusterConfig.MAX_SAMPLES_PER_CHUNK));
        Deposit.findAndPlaceSample(maxSampleCnt, this.getSample(world.getRandom()), world, pos, deposits, chunksGenerated);
    }

    @Override
    public HashSet<BlockState> getAllOres() {
        HashSet<BlockState> ret = new HashSet<>();
        this.oreToWeightMap.values().forEach(x -> ret.addAll(x.keySet()));
        ret.remove(Blocks.AIR.getDefaultState());
        return ret.isEmpty() ? null : ret;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public boolean canPlaceInBiome(RegistryEntry<Biome> biome) {
        return biome.isIn(this.biomeTag);
    }

    @Override
    public HashSet<BlockState> getBlockStateMatchers() {
        return this.blockStateMatchers == null ? DepositUtils.getDefaultMatchers() : this.blockStateMatchers;
    }

    public static DenseDeposit deserialize(JsonObject json) {
        if (json == null) {
            return null;
        }

        try {
            HashMap<String, HashMap<BlockState, Float>> oreBlocks = SerializerUtils.buildMultiBlockMatcherMap(json.get("blocks").getAsJsonObject());
            HashMap<BlockState, Float> sampleBlocks = SerializerUtils.buildMultiBlockMap(json.get("samples").getAsJsonArray());
            int yMin = json.get("yMin").getAsInt();
            int yMax = json.get("yMax").getAsInt();
            int size = json.get("size").getAsInt();
            int genWt = json.get("generationWeight").getAsInt();
            TagKey<Biome> biomeTag = TagKey.of(Registry.BIOME_KEY, new Identifier(json.get("biomeTag").getAsString().replace("#", "")));


            HashSet<BlockState> blockStateMatchers = DepositUtils.getDefaultMatchers();
            if (json.has("blockStateMatchers")) {
                blockStateMatchers = SerializerUtils.toBlockStateList(json.get("blockStateMatchers").getAsJsonArray());
            }

            return new DenseDeposit(oreBlocks, sampleBlocks, yMin, yMax, size, genWt, biomeTag, blockStateMatchers);
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
        config.addProperty("size", this.size);
        config.addProperty("generationWeight", this.getWeight());
        config.addProperty("biomeTag", this.biomeTag.id().toString());
        json.addProperty("type", JSON_TYPE);
        json.add("config", config);
        return json;
    }
}
