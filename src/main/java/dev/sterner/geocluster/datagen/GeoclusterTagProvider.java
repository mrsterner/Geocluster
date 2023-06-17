package dev.sterner.geocluster.datagen;

import dev.sterner.geocluster.common.registry.GeoclusterTagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.concurrent.CompletableFuture;

import static dev.sterner.geocluster.common.registry.GeoclusterObjects.*;

public class GeoclusterTagProvider {
    public static class GeoclusterBlockTags extends FabricTagProvider.BlockTagProvider {

        public GeoclusterBlockTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            for (Block block : BLOCKS.keySet()) {
                getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
                getOrCreateTagBuilder(GeoclusterTagRegistry.ORES).add(block);
                if (!block.equals(ANCIENT_DEBRIS_ORE)) {
                    getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(block);
                } else {
                    getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
                }
            }

            getOrCreateTagBuilder(BlockTags.DIRT);
            getOrCreateTagBuilder(BlockTags.SAND);
            getOrCreateTagBuilder(GeoclusterTagRegistry.STONE)
                    .add(Blocks.STONE)
                    .add(Blocks.ANDESITE)
                    .add(Blocks.GRANITE)
                    .add(Blocks.DIORITE)
                    .add(Blocks.DEEPSLATE)
                    .add(Blocks.NETHERRACK);
            getOrCreateTagBuilder(GeoclusterTagRegistry.SUPPORTS_SAMPLE)
                    .addTag(BlockTags.DIRT)
                    .addTag(BlockTags.SAND)
                    .add(Blocks.GRAVEL)
                    .addTag(GeoclusterTagRegistry.STONE)
                    .add(Blocks.SOUL_SOIL)
                    .add(Blocks.SOUL_SAND)
                    .add(Blocks.BASALT)
                    .add(Blocks.CRIMSON_NYLIUM)
                    .add(Blocks.WARPED_NYLIUM);

            getOrCreateTagBuilder(GeoclusterTagRegistry.ZINC_ORES).add(ZINC_ORE).add(DEEPSLATE_ZINC_ORE);
            getOrCreateTagBuilder(GeoclusterTagRegistry.SILVER_ORES).add(SILVER_ORE).add(DEEPSLATE_SILVER_ORE);
            getOrCreateTagBuilder(GeoclusterTagRegistry.LEAD_ORES).add(LEAD_ORE).add(DEEPSLATE_LEAD_ORE);
            getOrCreateTagBuilder(GeoclusterTagRegistry.ALUMINIUM_ORES).add(ALUMINIUM_ORE).add(DEEPSLATE_ALUMINIUM_ORE);
            getOrCreateTagBuilder(GeoclusterTagRegistry.URANIUM_ORES).add(URANIUM_ORE).add(DEEPSLATE_URANIUM_ORE);
            getOrCreateTagBuilder(GeoclusterTagRegistry.TIN_ORES).add(TIN_ORE).add(DEEPSLATE_TIN_ORE);
            getOrCreateTagBuilder(GeoclusterTagRegistry.TITANIUM_ORES).add(TITANIUM_ORE).add(DEEPSLATE_TITANIUM_ORE);
            getOrCreateTagBuilder(GeoclusterTagRegistry.PLATINUM_ORES).add(PLATINUM_ORE).add(DEEPSLATE_PLATINUM_ORE);
            getOrCreateTagBuilder(GeoclusterTagRegistry.NICKEL_ORES).add(NICKEL_ORE).add(DEEPSLATE_NICKEL_ORE);
        }
    }

    public static class GeoclusterItemTags extends FabricTagProvider.ItemTagProvider {


        public GeoclusterItemTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(GeoclusterTagRegistry.ZINC_INGOTS).add(ZINC_INGOT);
            getOrCreateTagBuilder(GeoclusterTagRegistry.ZINC_NUGGETS).add(ZINC_NUGGET);
            getOrCreateTagBuilder(GeoclusterTagRegistry.SILVER_INGOTS).add(SILVER_INGOT);
            getOrCreateTagBuilder(GeoclusterTagRegistry.SILVER_NUGGETS).add(SILVER_NUGGET);
            getOrCreateTagBuilder(GeoclusterTagRegistry.LEAD_INGOTS).add(LEAD_INGOT);
            getOrCreateTagBuilder(GeoclusterTagRegistry.LEAD_NUGGETS).add(LEAD_NUGGET);
            getOrCreateTagBuilder(GeoclusterTagRegistry.ALUMINIUM_INGOTS).add(ALUMINIUM_INGOT);
            getOrCreateTagBuilder(GeoclusterTagRegistry.ALUMINIUM_NUGGETS).add(ALUMINIUM_NUGGET);
            getOrCreateTagBuilder(GeoclusterTagRegistry.URANIUM_INGOTS).add(URANIUM_INGOT);
            getOrCreateTagBuilder(GeoclusterTagRegistry.URANIUM_NUGGETS).add(URANIUM_NUGGET);
            getOrCreateTagBuilder(GeoclusterTagRegistry.TIN_INGOTS).add(TIN_INGOT);
            getOrCreateTagBuilder(GeoclusterTagRegistry.TIN_NUGGETS).add(TIN_NUGGET);
            getOrCreateTagBuilder(GeoclusterTagRegistry.PLATINUM_INGOTS).add(PLATINUM_INGOT);
            getOrCreateTagBuilder(GeoclusterTagRegistry.PLATINUM_NUGGETS).add(PLATINUM_NUGGET);
            getOrCreateTagBuilder(GeoclusterTagRegistry.TITANIUM_INGOTS).add(TITANIUM_INGOT);
            getOrCreateTagBuilder(GeoclusterTagRegistry.TITANIUM_NUGGETS).add(TITANIUM_NUGGET);
            getOrCreateTagBuilder(GeoclusterTagRegistry.NICKEL_INGOTS).add(NICKEL_INGOT);
            getOrCreateTagBuilder(GeoclusterTagRegistry.NICKEL_NUGGETS).add(NICKEL_NUGGET);
            getOrCreateTagBuilder(GeoclusterTagRegistry.COPPER_NUGGETS).add(COPPER_NUGGET);
        }
    }

    public static class GeoclusterBiomeTags extends FabricTagProvider<Biome> {


        public GeoclusterBiomeTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.BIOME, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(GeoclusterTagRegistry.IS_DRY).add(BiomeKeys.DESERT).add(BiomeKeys.ERODED_BADLANDS).add(BiomeKeys.WINDSWEPT_SAVANNA).add(BiomeKeys.BADLANDS).add(BiomeKeys.WOODED_BADLANDS);
            getOrCreateTagBuilder(GeoclusterTagRegistry.IS_HOT).addTag(GeoclusterTagRegistry.IS_DRY).add(BiomeKeys.JUNGLE).add(BiomeKeys.SPARSE_JUNGLE).add(BiomeKeys.SAVANNA).add(BiomeKeys.SAVANNA_PLATEAU).add(BiomeKeys.STONY_PEAKS)
                    .add(BiomeKeys.WARM_OCEAN).add(BiomeKeys.BAMBOO_JUNGLE).add(BiomeKeys.MANGROVE_SWAMP);
            getOrCreateTagBuilder(GeoclusterTagRegistry.IS_SANDY).add(BiomeKeys.DESERT).add(BiomeKeys.BEACH).add(BiomeKeys.BADLANDS).add(BiomeKeys.WOODED_BADLANDS);

            getOrCreateTagBuilder(GeoclusterTagRegistry.IS_SWAMP).add(BiomeKeys.SWAMP).add(BiomeKeys.MANGROVE_SWAMP);
            getOrCreateTagBuilder(GeoclusterTagRegistry.IS_MUSHROOM).add(BiomeKeys.MUSHROOM_FIELDS);
            getOrCreateTagBuilder(GeoclusterTagRegistry.IS_MARSHY).addTag(GeoclusterTagRegistry.IS_SWAMP).addTag(GeoclusterTagRegistry.IS_MUSHROOM);

            getOrCreateTagBuilder(GeoclusterTagRegistry.IS_PLAINS).add(BiomeKeys.PLAINS).add(BiomeKeys.SNOWY_PLAINS).add(BiomeKeys.MEADOW).add(BiomeKeys.SUNFLOWER_PLAINS);
        }
    }
}
