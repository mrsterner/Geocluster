package dev.sterner.geocluster.common.data.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sterner.geocluster.api.deposits.LayerDeposit;

public class LayerDepositSerializer {
    public LayerDeposit deserialize(JsonObject json) {
        return LayerDeposit.deserialize(json);
    }

    public JsonElement serialize(LayerDeposit layerDeposit) {
        return layerDeposit.serialize();
    }
}
