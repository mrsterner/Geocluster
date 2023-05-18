package dev.sterner.geocluster.common.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class SerializerUtils {
    public static @NotNull BlockState fromString(@Nullable String string) {
        if (string == null) {
            return Blocks.AIR.getDefaultState();
        }
        Identifier r = new Identifier(string);
        return Objects.requireNonNull(Registry.BLOCK.get(r)).getDefaultState();
    }

    public static String[] toStringArray(JsonArray arr) {
        String[] ret = new String[arr.size()];

        for (int i = 0; i < arr.size(); i++) {
            ret[i] = arr.get(i).getAsString();
        }

        return ret;
    }

    public static HashSet<BlockState> toBlockStateList(JsonArray arr) {
        HashSet<BlockState> ret = new HashSet<>();

        for (String s : toStringArray(arr)) {
            ret.add(fromString(s));
        }

        return ret;
    }

    public static HashMap<BlockState, Float> buildMultiBlockMap(JsonArray arr) {
        HashMap<BlockState, Float> ret = new HashMap<>();

        for (JsonElement j : arr) {
            JsonObject pair = j.getAsJsonObject();
            if (pair.get("block").isJsonNull()) {
                ret.put(null, pair.get("chance").getAsFloat());
            } else {
                ret.put(fromString(pair.get("block").getAsString()), pair.get("chance").getAsFloat());
            }
        }

        return ret;
    }

    public static JsonArray deconstructMultiBlockMap(HashMap<BlockState, Float> in) {
        JsonArray ret = new JsonArray();

        for (Map.Entry<BlockState, Float> e : in.entrySet()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("block", GeoclusterUtils.getRegistryName(e.getKey().getBlock()));
            obj.addProperty("chance", e.getValue());
            ret.add(obj);
        }

        return ret;
    }

    public static HashMap<String, HashMap<BlockState, Float>> buildMultiBlockMatcherMap(JsonObject jsonObject) {
        HashMap<String, HashMap<BlockState, Float>> map = new HashMap<>();

        jsonObject.keySet().forEach((key) -> {
            HashMap<BlockState, Float> value = buildMultiBlockMap(jsonObject.get(key).getAsJsonArray());
            map.put(key, value);
        });

        return map;
    }

    public static JsonObject deconstructMultiBlockMatcherMap(HashMap<String, HashMap<BlockState, Float>> in) {
        JsonObject ret = new JsonObject();

        for (Map.Entry<String, HashMap<BlockState, Float>> i : in.entrySet()) {
            String key = i.getKey();
            JsonArray value = deconstructMultiBlockMap(i.getValue());
            ret.add(key, value);
        }

        return ret;
    }
}
