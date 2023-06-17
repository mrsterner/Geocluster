package dev.sterner.geocluster.common.registry;

import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.common.blocks.SampleBlock;
import dev.sterner.geocluster.common.items.ProspectorsPickItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface GeoclusterObjects {
    Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    Item STONE_PROSPECTORS_PICK = register("stone_prospectors_pick", new ProspectorsPickItem(ProspectorsPickItem.Type.STONE));
    Item COPPER_PROSPECTORS_PICK = register("copper_prospectors_pick", new ProspectorsPickItem(ProspectorsPickItem.Type.COPPER));
    Item IRON_PROSPECTORS_PICK = register("iron_prospectors_pick", new ProspectorsPickItem(ProspectorsPickItem.Type.IRON));

    Item ZINC_INGOT = register("zinc_ingot", new Item(settings()));
    Item SILVER_INGOT = register("silver_ingot", new Item(settings()));
    Item LEAD_INGOT = register("lead_ingot", new Item(settings()));
    Item ALUMINIUM_INGOT = register("aluminium_ingot", new Item(settings()));
    Item URANIUM_INGOT = register("uranium_ingot", new Item(settings()));
    Item TIN_INGOT = register("tin_ingot", new Item(settings()));
    Item PLATINUM_INGOT = register("platinum_ingot", new Item(settings()));
    Item TITANIUM_INGOT = register("titanium_ingot", new Item(settings()));
    Item NICKEL_INGOT = register("nickel_ingot", new Item(settings()));

    Item STONE_CHUNK = register("stone_chunk", new Item(settings()));
    Item RAW_ZINC = register("raw_zinc", new Item(settings()));
    Item RAW_SILVER = register("raw_silver", new Item(settings()));
    Item RAW_LEAD = register("raw_lead", new Item(settings()));
    Item RAW_ALUMINIUM = register("raw_aluminium", new Item(settings()));
    Item RAW_URANIUM = register("raw_uranium", new Item(settings()));
    Item RAW_TIN = register("raw_tin", new Item(settings()));
    Item RAW_PLATINUM = register("raw_platinum", new Item(settings()));
    Item RAW_TITANIUM = register("raw_titanium", new Item(settings()));
    Item RAW_NICKEL = register("raw_nickel", new Item(settings()));
    Item RAW_ANCIENT_DEBRIS = register("raw_ancient_debris", new Item(settings()));

    Item COPPER_NUGGET = register("copper_nugget", new Item(settings()));
    Item ZINC_NUGGET = register("zinc_nugget", new Item(settings()));
    Item SILVER_NUGGET = register("silver_nugget", new Item(settings()));
    Item LEAD_NUGGET = register("lead_nugget", new Item(settings()));
    Item ALUMINIUM_NUGGET = register("aluminium_nugget", new Item(settings()));
    Item URANIUM_NUGGET = register("uranium_nugget", new Item(settings()));
    Item TIN_NUGGET = register("tin_nugget", new Item(settings()));
    Item PLATINUM_NUGGET = register("platinum_nugget", new Item(settings()));
    Item TITANIUM_NUGGET = register("titanium_nugget", new Item(settings()));
    Item NICKEL_NUGGET = register("nickel_nugget", new Item(settings()));

    Block ZINC_ORE = register("zinc_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());
    Block DEEPSLATE_ZINC_ORE = register("deepslate_zinc_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(ZINC_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)), settings());

    Block SILVER_ORE = register("silver_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());
    Block DEEPSLATE_SILVER_ORE = register("deepslate_silver_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(SILVER_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)), settings());

    Block LEAD_ORE = register("lead_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());
    Block DEEPSLATE_LEAD_ORE = register("deepslate_lead_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(LEAD_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)), settings());

    Block ALUMINIUM_ORE = register("aluminium_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());
    Block DEEPSLATE_ALUMINIUM_ORE = register("deepslate_aluminium_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(ALUMINIUM_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)), settings());

    Block URANIUM_ORE = register("uranium_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());
    Block DEEPSLATE_URANIUM_ORE = register("deepslate_uranium_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(URANIUM_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)), settings());

    Block TIN_ORE = register("tin_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());
    Block DEEPSLATE_TIN_ORE = register("deepslate_tin_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(TIN_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)), settings());

    Block PLATINUM_ORE = register("platinum_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());
    Block DEEPSLATE_PLATINUM_ORE = register("deepslate_platinum_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(PLATINUM_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)), settings());

    Block TITANIUM_ORE = register("titanium_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());
    Block DEEPSLATE_TITANIUM_ORE = register("deepslate_titanium_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(TITANIUM_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)), settings());

    Block NICKEL_ORE = register("nickel_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());
    Block DEEPSLATE_NICKEL_ORE = register("deepslate_nickel_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(NICKEL_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)), settings());

    Block QUARTZ_ORE = register("quartz_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());
    Block DEEPSLATE_QUARTZ_ORE = register("deepslate_quartz_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(NICKEL_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5F, 3.0F).sounds(BlockSoundGroup.DEEPSLATE)), settings());


    Block ANCIENT_DEBRIS_ORE = register("ancient_debris_ore", new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().strength(3.0F, 3.0F)), settings());

    Block STONE_SAMPLE = register("stone_sample", new SampleBlock(), settings());

    Block COPPER_SAMPLE = register("copper_ore_sample", new SampleBlock(), settings());
    Block IRON_SAMPLE = register("iron_ore_sample", new SampleBlock(), settings());
    Block GOLD_SAMPLE = register("gold_ore_sample", new SampleBlock(), settings());
    Block NETHER_GOLD_SAMPLE = register("nether_gold_ore_sample", new SampleBlock(), settings());
    Block ZINC_SAMPLE = register("zinc_ore_sample", new SampleBlock(), settings());
    Block SILVER_SAMPLE = register("silver_ore_sample", new SampleBlock(), settings());
    Block LEAD_SAMPLE = register("lead_ore_sample", new SampleBlock(), settings());
    Block ALUMINIUM_SAMPLE = register("aluminium_ore_sample", new SampleBlock(), settings());
    Block URANIUM_SAMPLE = register("uranium_ore_sample", new SampleBlock(), settings());
    Block TIN_SAMPLE = register("tin_ore_sample", new SampleBlock(), settings());
    Block PLATINUM_SAMPLE = register("platinum_ore_sample", new SampleBlock(), settings());
    Block TITANIUM_SAMPLE = register("titanium_ore_sample", new SampleBlock(), settings());
    Block NICKEL_SAMPLE = register("nickel_ore_sample", new SampleBlock(), settings());

    Block COAL_SAMPLE = register("coal_ore_sample", new SampleBlock(), settings());
    Block REDSTONE_SAMPLE = register("redstone_ore_sample", new SampleBlock(), settings());
    Block LAPIS_LAZULI_SAMPLE = register("lapis_ore_sample", new SampleBlock(), settings());
    Block EMERALD_SAMPLE = register("emerald_ore_sample", new SampleBlock(), settings());
    Block DIAMOND_SAMPLE = register("diamond_ore_sample", new SampleBlock(), settings());
    Block ANCIENT_DEBRIS_SAMPLE = register("ancient_debris_ore_sample", new SampleBlock(), settings());
    Block QUARTZ_SAMPLE = register("quartz_ore_sample", new SampleBlock(), settings());
    Block NETHER_QUARTZ_SAMPLE = register("nether_quartz_ore_sample", new SampleBlock(), settings());

    //COMPAT
    Block AMBER_SAMPLE = register("amber_ore_sample", new SampleBlock(), settings());
    Block ENDER_SAMPLE = register("ender_ore_sample", new SampleBlock(), settings());
    Block CINCINNASITE_SAMPLE = register("cincinnasite_ore_sample", new SampleBlock(), settings());
    Block NETHER_LAPIS_SAMPLE = register("nether_lapis_ore_sample", new SampleBlock(), settings());
    Block NETHER_REDSTONE_SAMPLE = register("nether_redstone_ore_sample", new SampleBlock(), settings());
    Block NETHER_RUBY_SAMPLE = register("nether_ruby_ore_sample", new SampleBlock(), settings());
    Block SALT_SAMPLE = register("salt_ore_sample", new SampleBlock(), settings());

    Block SULFUR_SAMPLE = register("sulfur_ore_sample", new SampleBlock(), settings());
    Block POTASSIUM_SAMPLE = register("potassium_ore_sample", new SampleBlock(), settings());
    Block PURPEILLE_SAMPLE = register("purpur_remnants_ore_sample", new SampleBlock(), settings());

    Block NEBULITE_SAMPLE = register("nebulite_ore_sample", new SampleBlock(), settings());
    Block SHADOW_QUARTZ_SAMPLE = register("shadow_quartz_ore_sample", new SampleBlock(), settings());

    static Item.Settings settings() {
        return new Item.Settings();
    }

    static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, Geocluster.id(name));
        return item;
    }

    static <T extends Block> T register(String name, T block, Item.Settings settings) {
        BLOCKS.put(block, Geocluster.id(name));
        ITEMS.put(new BlockItem(block, settings), BLOCKS.get(block));
        return block;
    }

    static void init() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registries.BLOCK, BLOCKS.get(block), block));
        ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));
        ItemGroupEvents.modifyEntriesEvent(Geocluster.GEOCLUSTER_ITEM_GROUP).register(entries -> ITEMS.keySet().forEach(entries::add));
    }
}
