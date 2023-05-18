package dev.sterner.geocluster;

import com.google.common.collect.Lists;
import eu.midnightdust.lib.config.MidnightConfig;

import java.util.ArrayList;
import java.util.List;

public class GeoclusterConfig extends MidnightConfig {


    private static final List<String> stoneIshMaterials = Lists.newArrayList("minecraft:stone", "minecraft:andesite", "minecraft:diorite", "minecraft:granite", "minecraft:netherrack", "minecraft:sandstone", "minecraft:deepslate", "minecraft:tuff", "minecraft:calcite", "minecraft:dripstone_block");


    public static final double CHUNK_SKIP_CHANCE = 0.9;
    public static final int NUMBER_PLUTONS_PER_CHUNK = 2;
    public static final boolean DEBUG_WORLD_GEN = false;
    public static final int MAX_SAMPLES_PER_CHUNK = 2;
    public static final List<String> DEFAULT_REPLACEMENT_MATS = stoneIshMaterials;
    public static final List<String> PRO_PICK_DETECTION_BLACKLIST = new ArrayList<>();
    public static final int PRO_PICK_RANGE = 5;
    public static final int PRO_PICK_DIAMETER = 5;
}
