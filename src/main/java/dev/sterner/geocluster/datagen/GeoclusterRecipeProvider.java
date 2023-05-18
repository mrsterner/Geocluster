package dev.sterner.geocluster.datagen;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

import java.util.List;
import java.util.function.Consumer;

import static dev.sterner.geocluster.common.registry.GeoclusterObjects.*;

public class GeoclusterRecipeProvider extends FabricRecipeProvider {

    private static final ImmutableList<ItemConvertible> ZINC_ORES = ImmutableList.of(ZINC_ORE, DEEPSLATE_ZINC_ORE, RAW_ZINC);
    private static final ImmutableList<ItemConvertible> SILVER_ORES = ImmutableList.of(SILVER_ORE, DEEPSLATE_SILVER_ORE, RAW_SILVER);
    private static final ImmutableList<ItemConvertible> LEAD_ORES = ImmutableList.of(LEAD_ORE, DEEPSLATE_LEAD_ORE, RAW_LEAD);
    private static final ImmutableList<ItemConvertible> ALUMINIUM_ORES = ImmutableList.of(ALUMINIUM_ORE, DEEPSLATE_ALUMINIUM_ORE, RAW_ALUMINIUM);
    private static final ImmutableList<ItemConvertible> URANIUM_ORES = ImmutableList.of(URANIUM_ORE, DEEPSLATE_URANIUM_ORE, RAW_URANIUM);
    private static final ImmutableList<ItemConvertible> TIN_ORES = ImmutableList.of(TIN_ORE, DEEPSLATE_TIN_ORE, RAW_TIN);
    private static final ImmutableList<ItemConvertible> PLATINUM_ORES = ImmutableList.of(PLATINUM_ORE, DEEPSLATE_PLATINUM_ORE, RAW_PLATINUM);
    private static final ImmutableList<ItemConvertible> TITANIUM_ORES = ImmutableList.of(TITANIUM_ORE, DEEPSLATE_TITANIUM_ORE, RAW_TITANIUM);
    private static final ImmutableList<ItemConvertible> NICKEL_ORES = ImmutableList.of(NICKEL_ORE, DEEPSLATE_NICKEL_ORE, RAW_NICKEL);

    public GeoclusterRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, COPPER_NUGGET, Items.COPPER_INGOT, "copper_ingot_from_nuggets", "copper_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, ZINC_NUGGET, ZINC_INGOT, "zinc_ingot_from_nuggets", "zinc_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, SILVER_NUGGET, SILVER_INGOT, "silver_ingot_from_nuggets", "silver_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, LEAD_NUGGET, LEAD_INGOT, "lead_ingot_from_nuggets", "lead_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, ALUMINIUM_NUGGET, ALUMINIUM_INGOT, "aluminium_ingot_from_nuggets", "aluminium_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, URANIUM_NUGGET, URANIUM_INGOT, "uranium_ingot_from_nuggets", "uranium_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, TIN_NUGGET, TIN_INGOT, "tin_ingot_from_nuggets", "tin_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, PLATINUM_NUGGET, PLATINUM_INGOT, "platinum_ingot_from_nuggets", "platinum_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, TITANIUM_NUGGET, TITANIUM_INGOT, "titanium_ingot_from_nuggets", "titanium_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, NICKEL_NUGGET, NICKEL_INGOT, "nickel_ingot_from_nuggets", "nickel_ingot");

        offerSmelting(exporter, ZINC_ORES, ZINC_INGOT, 1.0F, 200, "zinc_ingot");
        offerSmelting(exporter, SILVER_ORES, SILVER_INGOT, 1.0F, 200, "silver_ingot");
        offerSmelting(exporter, LEAD_ORES, LEAD_INGOT, 1.0F, 200, "lead_ingot");
        offerSmelting(exporter, ALUMINIUM_ORES, ALUMINIUM_INGOT, 1.0F, 200, "aluminium_ingot");
        offerSmelting(exporter, URANIUM_ORES, URANIUM_INGOT, 1.0F, 200, "uranium_ingot");
        offerSmelting(exporter, TIN_ORES, TIN_INGOT, 1.0F, 200, "tin_ingot");
        offerSmelting(exporter, PLATINUM_ORES, PLATINUM_INGOT, 1.0F, 200, "platinum_ingot");
        offerSmelting(exporter, TITANIUM_ORES, TITANIUM_INGOT, 1.0F, 200, "titanium_ingot");
        offerSmelting(exporter, NICKEL_ORES, NICKEL_INGOT, 1.0F, 200, "nickel_ingot");
        offerSmelting(exporter, List.of(RAW_ANCIENT_DEBRIS), Items.NETHERITE_SCRAP, 1.0F, 200, "netherite_scrap");

        offerBlasting(exporter, ZINC_ORES, ZINC_INGOT, 1.0F, 100, "zinc_ingot");
        offerBlasting(exporter, SILVER_ORES, SILVER_INGOT, 1.0F, 100, "silver_ingot");
        offerBlasting(exporter, LEAD_ORES, LEAD_INGOT, 1.0F, 100, "lead_ingot");
        offerBlasting(exporter, ALUMINIUM_ORES, ALUMINIUM_INGOT, 1.0F, 100, "aluminium_ingot");
        offerBlasting(exporter, URANIUM_ORES, URANIUM_INGOT, 1.0F, 100, "uranium_ingot");
        offerBlasting(exporter, TIN_ORES, TIN_INGOT, 1.0F, 100, "tin_ingot");
        offerBlasting(exporter, PLATINUM_ORES, PLATINUM_INGOT, 1.0F, 100, "platinum_ingot");
        offerBlasting(exporter, TITANIUM_ORES, TITANIUM_INGOT, 1.0F, 100, "titanium_ingot");
        offerBlasting(exporter, NICKEL_ORES, NICKEL_INGOT, 1.0F, 100, "nickel_ingot");
        offerBlasting(exporter, List.of(RAW_ANCIENT_DEBRIS), Items.NETHERITE_SCRAP, 1.0F, 100, "netherite_scrap");

        ShapedRecipeJsonBuilder.create(PROSPECTORS_PICK).input('I', Items.IRON_INGOT).input('N', Items.IRON_NUGGET).input('S', Items.STICK)
                .pattern("NI")
                .pattern(" S")
                .criterion("has_raw_iron", conditionsFromItem(Items.RAW_IRON))
                .offerTo(exporter);

    }
}
