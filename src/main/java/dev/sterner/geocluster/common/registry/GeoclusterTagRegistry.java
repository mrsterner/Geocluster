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


    TagKey<Block> STONE = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "stones"));
    TagKey<Block> SUPPORTS_SAMPLE = TagKey.of(RegistryKeys.BLOCK, Geocluster.id("supports_sample"));

    TagKey<Block> ORES = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "ores"));
    TagKey<Item> COPPER_NUGGETS = registerNuggetTag("copper_nuggets");

    TagKey<Block> ZINC_ORES = registerOreTag("zinc_ores");
    TagKey<Item> ZINC_INGOTS = registerIngotTag("zinc_ingots");
    TagKey<Item> ZINC_NUGGETS = registerNuggetTag("zinc_nuggets");

    TagKey<Block> SILVER_ORES = registerOreTag("silver_ores");
    TagKey<Item> SILVER_INGOTS = registerIngotTag("silver_ingots");
    TagKey<Item> SILVER_NUGGETS = registerNuggetTag("silver_nuggets");

    TagKey<Block> LEAD_ORES = registerOreTag("lead_ores");
    TagKey<Item> LEAD_INGOTS = registerIngotTag("lead_ingots");
    TagKey<Item> LEAD_NUGGETS = registerNuggetTag("lead_nuggets");

    TagKey<Block> ALUMINIUM_ORES = registerOreTag( "aluminum_ores");
    TagKey<Item> ALUMINIUM_INGOTS = registerIngotTag("aluminium_ingots");
    TagKey<Item> ALUMINIUM_NUGGETS = registerNuggetTag("aluminium_nuggets");

    TagKey<Block> URANIUM_ORES = registerOreTag("uranium_ores");
    TagKey<Item> URANIUM_INGOTS = registerIngotTag("uranium_ingots");
    TagKey<Item> URANIUM_NUGGETS = registerNuggetTag("uranium_nuggets");

    TagKey<Block> TIN_ORES = registerOreTag("tin_ores");
    TagKey<Item> TIN_INGOTS = registerIngotTag("tin_ingots");
    TagKey<Item> TIN_NUGGETS = registerNuggetTag("tin_nuggets");

    TagKey<Block> PLATINUM_ORES = registerOreTag("platinum_ores");
    TagKey<Item> PLATINUM_INGOTS = registerIngotTag("platinum_ingots");
    TagKey<Item> PLATINUM_NUGGETS = registerNuggetTag("platinum_nuggets");

    TagKey<Block> TITANIUM_ORES = registerOreTag("titanium_ores");
    TagKey<Item> TITANIUM_INGOTS = registerIngotTag("titanium_ingots");
    TagKey<Item> TITANIUM_NUGGETS = registerNuggetTag("titanium_nuggets");

    TagKey<Block> NICKEL_ORES = registerOreTag("nickel_ores");
    TagKey<Item> NICKEL_INGOTS = registerIngotTag("nickel_ingots");
    TagKey<Item> NICKEL_NUGGETS = registerNuggetTag("nickel_nuggets");

    static TagKey<Block> registerOreTag(String name){
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "ores/" + name));
    }

    static TagKey<Item> registerIngotTag(String name){
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "ingots/" + name));
    }

    static TagKey<Item> registerNuggetTag(String name){
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "nuggets/" + name));
    }
}
