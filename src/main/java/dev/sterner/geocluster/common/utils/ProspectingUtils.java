package dev.sterner.geocluster.common.utils;

import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.GeoclusterConfig;
import dev.sterner.geocluster.api.DepositCache;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;

public class ProspectingUtils {
    private static HashSet<BlockState> depositBlocks;
    private static HashSet<BlockState> detectionBlacklist;

    public static HashSet<BlockState> getDepositBlocks() {
        if (depositBlocks == null) {
            depositBlocks = new HashSet<>();
            populateDepositBlocks();
        }
        return depositBlocks;
    }

    public static void populateDepositBlocks() {
        depositBlocks = new HashSet<>();
        DepositCache cache = DepositCache.getCache();
        cache.getOres().forEach((cluster) -> {
            HashSet<BlockState> ores = cluster.getAllOres();
            if (ores != null) {
                depositBlocks.addAll(ores);
            }
        });
    }

    public static HashSet<BlockState> getDetectionBlacklist() {
        if (detectionBlacklist == null) {
            detectionBlacklist = new HashSet<>();
            populateDetectionBlacklist();
        }
        return detectionBlacklist;
    }

    public static void populateDetectionBlacklist() {
        detectionBlacklist = new HashSet<>();

        GeoclusterConfig.PROSPECTORS_PICK_DETECTION_BLACKLIST.forEach(s -> {
            Block block = Registry.BLOCK.get(new Identifier(s));
            if (block != null) {
                detectionBlacklist.add(block.getDefaultState());
            } else {
                Geocluster.LOGGER.warn("The item {} in the proPickDetectionBlacklist config option was not valid", s);
            }
        });
    }

    public static boolean canDetect(BlockState test) {
        return !getDetectionBlacklist().contains(test.getBlock().getDefaultState());
    }
}
