package dev.sterner.geocluster.common.data.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sterner.geocluster.api.deposits.TopLayerDeposit;

public class TopLayerDepositSerializer {
    public TopLayerDeposit deserialize(JsonObject json) {
        return TopLayerDeposit.deserialize(json);
    }

    public JsonElement serialize(TopLayerDeposit topLayerDeposit) {
        return topLayerDeposit.serialize();
    }
}
