package dev.sterner.geocluster.common.registry;

import dev.sterner.geocluster.Geocluster;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.PlacedFeature;

public interface GeoclusterTagRegistry {
    TagKey<PlacedFeature> ORES_TO_REMOVE = TagKey.of(RegistryKeys.PLACED_FEATURE, Geocluster.id("ores_to_remove"));

    /*
    TagKey<Biome> IS_DRY = TagKey.of(RegistryKeys.BIOME, Identifier.of("c", "is_dry/overworld"));
    TagKey<Biome> IS_HOT = TagKey.of(RegistryKeys.BIOME, Identifier.of("c", "is_hot/overworld"));

    TagKey<Biome> IS_SANDY = TagKey.of(RegistryKeys.BIOME, Identifier.of("c", "is_sandy"));
    TagKey<Biome> IS_SWAMP = TagKey.of(RegistryKeys.BIOME, Identifier.of("c", "is_swamp"));
    TagKey<Biome> IS_MUSHROOM = TagKey.of(RegistryKeys.BIOME, Identifier.of("c", "is_mushroom"));
    TagKey<Biome> IS_MARSHY = TagKey.of(RegistryKeys.BIOME, Geocluster.id("is_marshy"));

    TagKey<Biome> IS_PLAINS = TagKey.of(RegistryKeys.BIOME, Geocluster.id("is_plains"));


     */
    TagKey<Block> STONE = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "stone"));
    TagKey<Block> SUPPORTS_SAMPLE = TagKey.of(RegistryKeys.BLOCK, Geocluster.id("supports_sample"));

    TagKey<Block> ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "ores"));
    TagKey<Item> COPPER_NUGGETS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "copper_nuggets"));

    TagKey<Block> ZINC_ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "zinc_ores"));
    TagKey<Item> ZINC_INGOTS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "zinc_ingots"));
    TagKey<Item> ZINC_NUGGETS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "zinc_nuggets"));

    TagKey<Block> SILVER_ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "silver_ores"));
    TagKey<Item> SILVER_INGOTS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "silver_ingots"));
    TagKey<Item> SILVER_NUGGETS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "silver_nuggets"));

    TagKey<Block> LEAD_ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "lead_ores"));
    TagKey<Item> LEAD_INGOTS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "lead_ingots"));
    TagKey<Item> LEAD_NUGGETS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "lead_nuggets"));

    TagKey<Block> ALUMINIUM_ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "aluminum_ores"));
    TagKey<Item> ALUMINIUM_INGOTS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "aluminium_ingots"));
    TagKey<Item> ALUMINIUM_NUGGETS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "aluminium_nuggets"));

    TagKey<Block> URANIUM_ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "uranium_ores"));
    TagKey<Item> URANIUM_INGOTS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "uranium_ingots"));
    TagKey<Item> URANIUM_NUGGETS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "uranium_nuggets"));

    TagKey<Block> TIN_ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "tin_ores"));
    TagKey<Item> TIN_INGOTS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "tin_ingots"));
    TagKey<Item> TIN_NUGGETS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "tin_nuggets"));

    TagKey<Block> PLATINUM_ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "platinum_ores"));
    TagKey<Item> PLATINUM_INGOTS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "platinum_ingots"));
    TagKey<Item> PLATINUM_NUGGETS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "platinum_nuggets"));

    TagKey<Block> TITANIUM_ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "titanium_ores"));
    TagKey<Item> TITANIUM_INGOTS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "titanium_ingots"));
    TagKey<Item> TITANIUM_NUGGETS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "titanium_nuggets"));

    TagKey<Block> NICKEL_ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "nickel_ores"));
    TagKey<Item> NICKEL_INGOTS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "nickel_ingots"));
    TagKey<Item> NICKEL_NUGGETS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "nickel_nuggets"));
}
