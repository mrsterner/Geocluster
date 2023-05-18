package dev.sterner.geocluster.common.registry;

import dev.sterner.geocluster.Geocluster;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.PlacedFeature;

public interface GeoclusterTagRegistry {
    TagKey<PlacedFeature> ORES_TO_REMOVE = TagKey.of(Registry.PLACED_FEATURE_KEY, Geocluster.id("ores_to_remove"));

    TagKey<Block> SUPPORTS_SAMPLE = TagKey.of(Registry.BLOCK_KEY, Geocluster.id("supports_sample"));

    TagKey<Block> ORES = TagKey.of(Registry.BLOCK_KEY, new Identifier("c", "ores"));
    TagKey<Item> COPPER_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "copper_nuggets"));

    TagKey<Item> ZINC_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "zinc_ingots"));
    TagKey<Item> ZINC_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "zinc_nuggets"));

    TagKey<Item> SILVER_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "silver_ingots"));
    TagKey<Item> SILVER_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "silver_nuggets"));

    TagKey<Item> LEAD_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "lead_ingots"));
    TagKey<Item> LEAD_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "lead_nuggets"));

    TagKey<Item> ALUMINIUM_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "aluminium_ingots"));
    TagKey<Item> ALUMINIUM_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "aluminium_nuggets"));

    TagKey<Item> URANIUM_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "uranium_ingots"));
    TagKey<Item> URANIUM_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "uranium_nuggets"));

    TagKey<Item> TIN_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "tin_ingots"));
    TagKey<Item> TIN_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "tin_nuggets"));

    TagKey<Item> PLATINUM_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "platinum_ingots"));
    TagKey<Item> PLATINUM_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "platinum_nuggets"));

    TagKey<Item> TITANIUM_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "titanium_ingots"));
    TagKey<Item> TITANIUM_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "titanium_nuggets"));

    TagKey<Item> NICKEL_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "nickel_ingots"));
    TagKey<Item> NICKEL_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "nickel_nuggets"));
}
