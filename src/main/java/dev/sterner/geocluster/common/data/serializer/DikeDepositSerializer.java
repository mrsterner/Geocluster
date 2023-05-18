package dev.sterner.geocluster.common.data.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sterner.geocluster.api.deposits.DikeDeposit;

public class DikeDepositSerializer {
    public DikeDeposit deserialize(JsonObject json) {
        return DikeDeposit.deserialize(json);
    }

    public JsonElement serialize(DikeDeposit dep) {
        return dep.serialize();
    }
}
