package dev.sterner.geocluster.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static dev.sterner.geocluster.common.registry.GeoclusterObjects.*;

public class GeoclusterLanguageProvider extends FabricLanguageProvider {
    public GeoclusterLanguageProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add("itemGroup.geocluster.geocluster", "Geocluster");
        builder.add("geocluster.pro_pick.tooltip.nonefound_surface", "Nothing found in this area");
        builder.add("geocluster.pro_pick.tooltip.found_surface", "%s found in this area");
        builder.add("geocluster.pro_pick.tooltip.found", "Found %s %s from you");

        builder.add(PROSPECTORS_PICK, "Prospectors Pick");
        builder.add(COPPER_SAMPLE, "Copper Sample");
        builder.add(IRON_SAMPLE, "Iron Sample");
        builder.add(GOLD_SAMPLE, "Gold Sample");
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


        builder.add(ZINC_INGOT, "Zinc Ingot");
        builder.add(SILVER_INGOT, "Silver Ingot");
        builder.add(LEAD_INGOT, "Lead Ingot");
        builder.add(ALUMINIUM_INGOT, "Aluminium Ingot");
        builder.add(URANIUM_INGOT, "Uranium Ingot");
        builder.add(TIN_INGOT, "Tin Ingot");
        builder.add(PLATINUM_INGOT, "Platinum Ingot");
        builder.add(TITANIUM_INGOT, "Titanium Ingot");
        builder.add(NICKEL_INGOT, "Nickel Ingot");

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
        builder.add(SILVER_ORE, "Nickel Ore");
        builder.add(LEAD_ORE, "Lead Ore");
        builder.add(ALUMINIUM_ORE, "Aluminium Ore");
        builder.add(URANIUM_ORE, "Uranium Ore");
        builder.add(TIN_ORE, "Tin Ore");
        builder.add(PLATINUM_ORE, "Platinum Ore");
        builder.add(TITANIUM_ORE, "Titanium Ore");
        builder.add(NICKEL_ORE, "Nickel Ore");

        builder.add(DEEPSLATE_ZINC_ORE, "Deepslate Zinc Ore");
        builder.add(DEEPSLATE_SILVER_ORE, "Deepslate Silver Ore");
        builder.add(DEEPSLATE_LEAD_ORE, "Deepslate Lead Ore");
        builder.add(DEEPSLATE_ALUMINIUM_ORE, "Deepslate Aluminium Ore");
        builder.add(DEEPSLATE_TIN_ORE, "Deepslate Tin Ore");
        builder.add(DEEPSLATE_URANIUM_ORE, "Deepslate Uranium Ore");
        builder.add(DEEPSLATE_PLATINUM_ORE, "Deepslate Platinum Ore");
        builder.add(DEEPSLATE_TITANIUM_ORE, "Deepslate Titanium Ore");
        builder.add(DEEPSLATE_NICKEL_ORE, "Deepslate Nickel Ore");

        builder.add(ANCIENT_DEBRIS_ORE, "Ancient Debris Ore");
        builder.add(RAW_ANCIENT_DEBRIS, "Raw Ancient Debris");
    }
}
