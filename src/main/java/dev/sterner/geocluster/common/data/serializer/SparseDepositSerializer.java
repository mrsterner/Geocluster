package dev.sterner.geocluster.common.data.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sterner.geocluster.api.deposits.SparseDeposit;

public class SparseDepositSerializer {
    public SparseDeposit deserialize(JsonObject json) {
        return SparseDeposit.deserialize(json);
    }

    public JsonElement serialize(SparseDeposit dep) {
        return dep.serialize();
    }
}
