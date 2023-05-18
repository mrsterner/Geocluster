package dev.sterner.geocluster.api;

import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.GeoclusterConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DepositUtils {

    private static HashSet<BlockState> defaultMatchersCached = null;

    @Nullable
    public static BlockState pick(HashMap<BlockState, Float> map, Random random) {
        float rng = random.nextFloat();
        for (Map.Entry<BlockState, Float> entry : map.entrySet()) {
            float weight = entry.getValue();
            if (rng < weight) {
                return entry.getKey();
            }
            rng -= weight;
        }

        Geocluster.LOGGER.error("Could not reach decision on block to place at Utils#pick");
        return null;
    }

    @SuppressWarnings("unchecked")
    public static HashSet<BlockState> getDefaultMatchers() {
        if (defaultMatchersCached == null) {
            defaultMatchersCached = new HashSet<>();
            GeoclusterConfig.DEFAULT_REPLACEMENT_MATS.forEach(s -> {
                Block block = Registry.BLOCK.get(new Identifier(s));
                if (!addDefaultMatcher(block)) {
                    Geocluster.LOGGER.warn(String.format(s + "&s is not a valid block. Please verify.", s));
                }
            });
        }

        return (HashSet<BlockState>) defaultMatchersCached.clone();
    }

    public static boolean addDefaultMatcher(Block block) {
        BlockState defaultState = block.getDefaultState();
        if (!defaultState.isAir()) {
            defaultMatchersCached.add(defaultState);
            return true;
        }
        return false;
    }

    public static boolean nearlyEquals(float a, float b) {
        return Math.abs(a - b) <= Float.MIN_VALUE;
    }
}
