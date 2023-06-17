package dev.sterner.geocluster.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static dev.sterner.geocluster.common.registry.GeoclusterObjects.*;

public class GeoclusterLanguageProvider extends FabricLanguageProvider {

    public GeoclusterLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add("geocluster.group.main", "Geocluster");
        builder.add("geocluster.pro_pick.tooltip.nonefound_surface", "Nothing found in this area");
        builder.add("geocluster.pro_pick.tooltip.found_surface", "Found in this area");
        builder.add("geocluster.pro_pick.tooltip.found", "Found %s from you");

        builder.add(COPPER_PROSPECTORS_PICK, "Copper Prospectors Pick");
        builder.add(STONE_PROSPECTORS_PICK, "Stone Prospectors Pick");
        builder.add(IRON_PROSPECTORS_PICK, "Iron Prospectors Pick");
        builder.add(COPPER_SAMPLE, "Copper Sample");
        builder.add(IRON_SAMPLE, "Iron Sample");
        builder.add(GOLD_SAMPLE, "Gold Sample");
        builder.add(NETHER_GOLD_SAMPLE, "Gold Sample");
        builder.add(ZINC_SAMPLE, "Zinc Sample");
        builder.add(LEAD_SAMPLE, "Lead Sample");
        builder.add(SILVER_SAMPLE, "Silver Sample");
        builder.add(ALUMINIUM_SAMPLE, "Aluminium Sample");
        builder.add(URANIUM_SAMPLE, "Uranium Sample");
        builder.add(TIN_SAMPLE, "Tin Sample");
        builder.add(PLATINUM_SAMPLE, "Platinum Sample");
        builder.add(TITANIUM_SAMPLE, "Titanium Sample");
        builder.add(NICKEL_SAMPLE, "Nickel Sample");

        builder.add(COAL_SAMPLE, "Coal Sample");
        builder.add(REDSTONE_SAMPLE, "Redstone Sample");
        builder.add(LAPIS_LAZULI_SAMPLE, "Lapis Lazuli Sample");
        builder.add(EMERALD_SAMPLE, "Emerald Sample");
        builder.add(DIAMOND_SAMPLE, "Diamond Sample");
        builder.add(ANCIENT_DEBRIS_SAMPLE, "Ancient Debris Sample");
        builder.add(QUARTZ_SAMPLE, "Quartz Sample");
        builder.add(NETHER_QUARTZ_SAMPLE, "Quartz Sample");


        builder.add(ZINC_INGOT, "Zinc Ingot");
        builder.add(SILVER_INGOT, "Silver Ingot");
        builder.add(LEAD_INGOT, "Lead Ingot");
        builder.add(ALUMINIUM_INGOT, "Aluminium Ingot");
        builder.add(URANIUM_INGOT, "Uranium Ingot");
        builder.add(TIN_INGOT, "Tin Ingot");
        builder.add(PLATINUM_INGOT, "Platinum Ingot");
        builder.add(TITANIUM_INGOT, "Titanium Ingot");
        builder.add(NICKEL_INGOT, "Nickel Ingot");

        builder.add(STONE_CHUNK, "Stone Chunk");
        builder.add(RAW_ZINC, "Raw Zinc");
        builder.add(RAW_SILVER, "Raw Silver");
        builder.add(RAW_LEAD, "Raw Lead");
        builder.add(RAW_ALUMINIUM, "Raw Aluminium");
        builder.add(RAW_URANIUM, "Raw Uranium");
        builder.add(RAW_TIN, "Raw Tin");
        builder.add(RAW_PLATINUM, "Raw Platinum");
        builder.add(RAW_TITANIUM, "Raw Titanium");
        builder.add(RAW_NICKEL, "Raw Nickel");

        builder.add(COPPER_NUGGET, "Copper Nugget");
        builder.add(ZINC_NUGGET, "Zinc Nugget");
        builder.add(SILVER_NUGGET, "Silver Nugget");
        builder.add(LEAD_NUGGET, "Lead Nugget");
        builder.add(ALUMINIUM_NUGGET, "Aluminium Nugget");
        builder.add(URANIUM_NUGGET, "Uranium Nugget");
        builder.add(TIN_NUGGET, "Tin Nugget");
        builder.add(PLATINUM_NUGGET, "Platinum Nugget");
        builder.add(TITANIUM_NUGGET, "Titanium Nugget");
        builder.add(NICKEL_NUGGET, "Nickel Nugget");

        builder.add(ZINC_ORE, "Zinc Ore");
        builder.add(SILVER_ORE, "Silver Ore");
        builder.add(LEAD_ORE, "Lead Ore");
        builder.add(ALUMINIUM_ORE, "Aluminium Ore");
        builder.add(URANIUM_ORE, "Uranium Ore");
        builder.add(TIN_ORE, "Tin Ore");
        builder.add(PLATINUM_ORE, "Platinum Ore");
        builder.add(TITANIUM_ORE, "Titanium Ore");
        builder.add(NICKEL_ORE, "Nickel Ore");
        builder.add(QUARTZ_ORE, "Quartz Ore");

        builder.add(DEEPSLATE_ZINC_ORE, "Deepslate Zinc Ore");
        builder.add(DEEPSLATE_SILVER_ORE, "Deepslate Silver Ore");
        builder.add(DEEPSLATE_LEAD_ORE, "Deepslate Lead Ore");
        builder.add(DEEPSLATE_ALUMINIUM_ORE, "Deepslate Aluminium Ore");
        builder.add(DEEPSLATE_TIN_ORE, "Deepslate Tin Ore");
        builder.add(DEEPSLATE_URANIUM_ORE, "Deepslate Uranium Ore");
        builder.add(DEEPSLATE_PLATINUM_ORE, "Deepslate Platinum Ore");
        builder.add(DEEPSLATE_TITANIUM_ORE, "Deepslate Titanium Ore");
        builder.add(DEEPSLATE_NICKEL_ORE, "Deepslate Nickel Ore");
        builder.add(DEEPSLATE_QUARTZ_ORE, "Deepslate Quartz Ore");

        builder.add(ANCIENT_DEBRIS_ORE, "Ancient Debris Ore");
        builder.add(RAW_ANCIENT_DEBRIS, "Raw Ancient Debris");

        builder.add("geocluster.midnightconfig.CHUNK_SKIP_CHANCE", "Chance To Skip A Chunk");
        builder.add("geocluster.midnightconfig.CHUNK_SKIP_CHANCE.tooltip", "The upper limit of RNG for generating any cluster in a given chunk.\nLarger values indicate further distance between clusters.");

        builder.add("geocluster.midnightconfig.NUMBER_CLUSTERS_PER_CHUNK", "Number of Clusters per Chunk");
        builder.add("geocluster.midnightconfig.NUMBER_CLUSTERS_PER_CHUNK.tooltip", "The number of times Geocluster will attempt to place clusters in a given chunk");

        builder.add("geocluster.midnightconfig.DEBUG_WORLD_GEN", "WorldGen Debug");
        builder.add("geocluster.midnightconfig.DEBUG_WORLD_GEN.tooltip", "Output info into the logs when generating Geolosys deposits");

        builder.add("geocluster.midnightconfig.MAX_SAMPLES_PER_CHUNK", "Max samples per chunk");
        builder.add("geocluster.midnightconfig.MAX_SAMPLES_PER_CHUNK.tooltip", "Maximum samples that can generate with each cluster within a chunk");

        builder.add("geocluster.midnightconfig.DEFAULT_REPLACEMENT_MATERIALS", "Default Replacement Materials");
        builder.add("geocluster.midnightconfig.DEFAULT_REPLACEMENT_MATERIALS.tooltip", "The fallback materials which a Deposit can replace if they're not specified by the deposit itself\\n\" + \"Format: Comma-delimited set of <modid:block> (see default for example)\"");

        builder.add("geocluster.midnightconfig.PROSPECTORS_PICK_DETECTION_BLACKLIST", "Prospectors Pick Detection Blacklist");
        builder.add("geocluster.midnightconfig.PROSPECTORS_PICK_DETECTION_BLACKLIST.tooltip", "A list of blocks to always ignore when prospecting, even if they're in a deposit.");

        builder.add("geocluster.midnightconfig.PROSPECTORS_PICK_RANGE", "Prospectors Pick Range");
        builder.add("geocluster.midnightconfig.PROSPECTORS_PICK_RANGE.tooltip", "The range (depth) of the prospector's pick prospecting cycle");

        builder.add("geocluster.midnightconfig.PROSPECTORS_PICK_DIAMETER", "Prospectors Pick Diameter");
        builder.add("geocluster.midnightconfig.PROSPECTORS_PICK_DIAMETER.tooltip", "The diameter of the prospector's pick prospecting cycle");

        builder.add("geocluster.midnightconfig.PROSPECTORS_POPUP_RIGHT", "Prospectors Info Popup Right-side");
        builder.add("geocluster.midnightconfig.PROSPECTORS_POPUP_RIGHT.tooltip", "If the prospectors pick should popup info on the right side of the screen");

        builder.add("geocluster.midnightconfig.FORCE_DEEPSLATE_SAMPLE_CAVEGEN", "Deepslate Ores Samples");
        builder.add("geocluster.midnightconfig.FORCE_DEEPSLATE_SAMPLE_CAVEGEN.tooltip", "Will try to spawn deepslate ores samples at first avalible pos above deposit");

        builder.add("geocluster.midnightconfig.DISABLE_IN_AREA_MESSAGE", "Disable In Area Message");

        builder.add("geocluster.midnightconfig.REMOVE_VEINS", "Remove Vanilla Veins of Gold/Iron");

        builder.add("geocluster.midnightconfig.ONLY_VANILLA_ORES", "Only Spawn Vanilla Ores");
        builder.add("geocluster.midnightconfig.ONLY_VANILLA_ORES.tooltip", "If enabled, dont spawn any modded ores added by this mod");


        builder.add(STONE_SAMPLE, "Stone Sample");
        builder.add(AMBER_SAMPLE, "Amber Sample");
        builder.add(ENDER_SAMPLE, "Ender Sample");
        builder.add(CINCINNASITE_SAMPLE, "Cincinnasite Sample");
        builder.add(NEBULITE_SAMPLE, "Nebulite Sample");
        builder.add(NETHER_LAPIS_SAMPLE, "Lapis Sample");
        builder.add(NETHER_REDSTONE_SAMPLE, "Redstone Sample");
        builder.add(NETHER_RUBY_SAMPLE, "Ruby Sample");
        builder.add(POTASSIUM_SAMPLE, "Potassium Sample");
        builder.add(PURPEILLE_SAMPLE, "Purpur Remnants Sample");
        builder.add(SALT_SAMPLE, "Salt Sample");
        builder.add(SHADOW_QUARTZ_SAMPLE, "Shadow Quartz Sample");
        builder.add(SULFUR_SAMPLE, "Sulfur Sample");
    }
}
