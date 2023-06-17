package dev.sterner.geocluster.common.data;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.GeoclusterConfig;
import dev.sterner.geocluster.api.DepositCache;
import dev.sterner.geocluster.api.deposits.*;
import dev.sterner.geocluster.common.data.serializer.*;
import dev.sterner.geocluster.common.utils.ProspectingUtils;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.List;
import java.util.Map;

public class WorldGenDataReloadListener extends JsonDataLoader implements IdentifiableResourceReloadListener {
    private final List<String> MODDED = Lists.newArrayList();
    private static final Gson GSON = (new GsonBuilder()).create();

    private final DenseDepositSerializer DenseSerializer = new DenseDepositSerializer();
    private final LayerDepositSerializer LayerSerializer = new LayerDepositSerializer();
    private final TopLayerDepositSerializer TopLayerSerializer = new TopLayerDepositSerializer();
    private final DikeDepositSerializer DikeSerializer = new DikeDepositSerializer();
    private final SparseDepositSerializer SparseSerializer = new SparseDepositSerializer();

    public WorldGenDataReloadListener() {
        super(GSON, "deposits");
        MODDED.add("aluminium");
        MODDED.add("lead_silver");
        MODDED.add("platinum");
        MODDED.add("tin");
        MODDED.add("titanium");
        MODDED.add("uranium");
        MODDED.add("zinc");
        MODDED.add("iron_nickel");
    }

    @Override
    public Identifier getFabricId() {
        return Geocluster.id("deposits");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        DepositCache cache = DepositCache.getCache();
        cache.clear();
        prepared.forEach((identifier, json) -> {
            if (GeoclusterConfig.ONLY_VANILLA_ORES) {
                if (MODDED.contains(identifier.getPath())) {
                    return;
                }
            }
            try {
                JsonObject jsonobject = json.getAsJsonObject();
                JsonObject config = jsonobject.get("config").getAsJsonObject();
                Geocluster.LOGGER.info("Preparing to load deposit datafile {}", identifier.toString());

                switch (jsonobject.get("type").getAsString()) {
                    case "geocluster:deposit_dense" -> {
                        DenseDeposit denseDeposit = DenseSerializer.deserialize(config);
                        if (denseDeposit != null) {
                            Geocluster.LOGGER.info(denseDeposit.toString());
                            cache.addDeposit(denseDeposit);
                        }
                    }
                    case "geocluster:deposit_layer" -> {
                        LayerDeposit layerDeposit = LayerSerializer.deserialize(config);
                        if (layerDeposit != null) {
                            Geocluster.LOGGER.info(layerDeposit.toString());
                            cache.addDeposit(layerDeposit);
                        }
                    }
                    case "geocluster:deposit_top_layer" -> {
                        TopLayerDeposit topLayerDeposit = TopLayerSerializer.deserialize(config);
                        if (topLayerDeposit != null) {
                            Geocluster.LOGGER.info(topLayerDeposit.toString());
                            cache.addDeposit(topLayerDeposit);
                        }
                    }
                    case "geocluster:deposit_dike" -> {
                        DikeDeposit dikeDeposit = DikeSerializer.deserialize(config);
                        if (dikeDeposit != null) {
                            Geocluster.LOGGER.info(dikeDeposit.toString());
                            cache.addDeposit(dikeDeposit);
                        }
                    }
                    case "geocluster:deposit_sparse" -> {
                        SparseDeposit sparseDeposit = SparseSerializer.deserialize(config);
                        if (sparseDeposit != null) {
                            Geocluster.LOGGER.info(sparseDeposit.toString());
                            cache.addDeposit(sparseDeposit);
                        }
                    }
                    default -> {
                        Geocluster.LOGGER.warn("Unknown JSON type. Received JSON {}", json);
                    }
                }
            } catch (NullPointerException ex) {
                Geocluster.LOGGER.info("Skipping registration of ore {}", identifier);
            }
        });

        ProspectingUtils.populateDepositBlocks();
    }
}
