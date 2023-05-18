package dev.sterner.geocluster.common.data.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sterner.geocluster.api.deposits.DenseDeposit;

public class DenseDepositSerializer {
    public DenseDeposit deserialize(JsonObject json) {
        return DenseDeposit.deserialize(json);
    }

    public JsonElement serialize(DenseDeposit denseDeposit) {
        return denseDeposit.serialize();
    }
}
