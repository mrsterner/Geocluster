package dev.sterner.geocluster.common.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sterner.geocluster.Geocluster;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class WorldGenDataReloadListener extends JsonDataLoader implements IdentifiableResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).create();

    private final DenseDepositSerializer DenseSerializer = new DenseDepositSerializer();
    private final LayerDepositSerializer LayerSerializer = new LayerDepositSerializer();
    private final TopLayerDepositSerializer TopLayerSerializer = new TopLayerDepositSerializer();
    private final DikeDepositSerializer DikeSerializer = new DikeDepositSerializer();
    private final SparseDepositSerializer SparseSerializer = new SparseDepositSerializer();

    public WorldGenDataReloadListener() {
        super(GSON, "deposits");
    }

    @Override
    public Identifier getFabricId() {
        return null;
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        GeoclusterAPI.depositRegistry.clear();
        prepared.forEach((identifier, json) -> {

            try {
                JsonObject jsonobject = json.getAsJsonObject();
                JsonObject config = jsonobject.get("config").getAsJsonObject();
                Geocluster.LOGGER.info("Preparing to load deposit datafile {}", identifier.toString());

                switch (jsonobject.get("type").getAsString()) {
                    case "geolosys:deposit_dense" -> {
                        DenseDeposit denseDeposit = DenseSerializer.deserialize(config);
                        if (denseDeposit != null) {
                            Geocluster.LOGGER.info(denseDeposit.toString());
                            GeoclusterAPI.depositRegistry.addDeposit(denseDeposit);
                        }
                    }
                    case "geolosys:deposit_layer" -> {
                        LayerDeposit layerDeposit = LayerSerializer.deserialize(config);
                        if (layerDeposit != null) {
                            Geocluster.LOGGER.info(layerDeposit.toString());
                            GeoclusterAPI.depositRegistry.addDeposit(layerDeposit);
                        }
                    }
                    case "geolosys:deposit_top_layer" -> {
                        TopLayerDeposit topLayerDeposit = TopLayerSerializer.deserialize(config);
                        if (topLayerDeposit != null) {
                            Geocluster.LOGGER.info(topLayerDeposit.toString());
                            GeoclusterAPI.depositRegistry.addDeposit(topLayerDeposit);
                        }
                    }
                    case "geolosys:deposit_dike" -> {
                        DikeDeposit dikeDeposit = DikeSerializer.deserialize(config);
                        if (dikeDeposit != null) {
                            Geocluster.LOGGER.info(dikeDeposit.toString());
                            GeoclusterAPI.depositRegistry.addDeposit(dikeDeposit);
                        }
                    }
                    case "geolosys:deposit_sparse" -> {
                        SparseDeposit sparseDeposit = SparseSerializer.deserialize(config);
                        if (sparseDeposit != null) {
                            Geocluster.LOGGER.info(sparseDeposit.toString());
                            GeoclusterAPI.depositRegistry.addDeposit(sparseDeposit);
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

        Prospecting.populateDepositBlocks();
    }
}
