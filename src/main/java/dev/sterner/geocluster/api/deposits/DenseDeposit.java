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
    private final int genWt;
    private final HashSet<BlockState> blockStateMatchers;
    private final TagKey<Biome> biomeTag;

    public DenseDeposit(HashMap<String, HashMap<BlockState, Float>> oreBlocks, HashMap<BlockState, Float> sampleBlocks, int yMin, int yMax, int size, int genWt, TagKey<Biome> biomeTag, HashSet<BlockState> blockStateMatchers) {
        super(oreBlocks, sampleBlocks);
        this.yMin = yMin;
        this.yMax = yMax;
        this.size = size;
        this.genWt = genWt;
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
        if (!this.canPlaceInBiome(level.getBiome(pos))) {
            return 0;
        }

        int totlPlaced = 0;
        int randY = this.yMin + level.getRandom().nextInt(this.yMax - this.yMin);
        int max = GeoclusterUtils.getTopSolidBlock(level, pos).getY();
        if (randY > max) {
            randY = Math.max(yMin, max);
        }

        float ranFlt = level.getRandom().nextFloat() * (float) Math.PI;
        double x1 = (float) (pos.getX() + 8) + MathHelper.sin(ranFlt) * (float) this.size / 8.0F;
        double x2 = (float) (pos.getX() + 8) - MathHelper.sin(ranFlt) * (float) this.size / 8.0F;
        double z1 = (float) (pos.getZ() + 8) + MathHelper.cos(ranFlt) * (float) this.size / 8.0F;
        double z2 = (float) (pos.getZ() + 8) - MathHelper.cos(ranFlt) * (float) this.size / 8.0F;
        double y1 = randY + level.getRandom().nextInt(3) - 2;
        double y2 = randY + level.getRandom().nextInt(3) - 2;

        for (int i = 0; i < this.size; ++i) {
            float radScl = (float) i / (float) this.size;
            double xn = x1 + (x2 - x1) * (double) radScl;
            double yn = y1 + (y2 - y1) * (double) radScl;
            double zn = z1 + (z2 - z1) * (double) radScl;
            double noise = level.getRandom().nextDouble() * (double) this.size / 16.0D;
            double radius = (double) (MathHelper.sin((float) Math.PI * radScl) + 1.0F) * noise + 1.0D;
            int xmin = MathHelper.floor(xn - radius / 2.0D);
            int ymin = MathHelper.floor(yn - radius / 2.0D);
            int zmin = MathHelper.floor(zn - radius / 2.0D);
            int xmax = MathHelper.floor(xn + radius / 2.0D);
            int ymax = MathHelper.floor(yn + radius / 2.0D);
            int zmax = MathHelper.floor(zn + radius / 2.0D);

            for (int x = xmin; x <= xmax; ++x) {
                double layerRadX = ((double) x + 0.5D - xn) / (radius / 2.0D);

                if (layerRadX * layerRadX < 1.0D) {
                    for (int y = ymin; y <= ymax; ++y) {
                        double layerRadY = ((double) y + 0.5D - yn) / (radius / 2.0D);

                        if (layerRadX * layerRadX + layerRadY * layerRadY < 1.0D) {
                            for (int z = zmin; z <= zmax; ++z) {
                                double layerRadZ = ((double) z + 0.5D - zn) / (radius / 2.0D);

                                if (layerRadX * layerRadX + layerRadY * layerRadY + layerRadZ * layerRadZ < 1.0D) {
                                    BlockPos placePos = new BlockPos(x, y, z);
                                    BlockState current = level.getBlockState(placePos);
                                    BlockState tmp = this.getOre(current, level.getRandom());
                                    if (tmp == null) {
                                        continue;
                                    }

                                    if (!(this.getBlockStateMatchers().contains(current) || this.oreToWeightMap.containsKey(GeoclusterUtils.getRegistryName(current)))) {
                                        continue;
                                    }

                                    if (FeatureUtils.enqueueBlockPlacement(level, placePos, tmp, deposits, chunksGenerated)) {
                                        totlPlaced++;
                                    }
                                }
                            }
                        }
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
    public int getGenWeight() {
        return this.genWt;
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
        config.addProperty("generationWeight", this.genWt);
        config.addProperty("biomeTag", this.biomeTag.id().toString());
        json.addProperty("type", JSON_TYPE);
        json.add("config", config);
        return json;
    }
}
