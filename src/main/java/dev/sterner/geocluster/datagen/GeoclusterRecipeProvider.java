package dev.sterner.geocluster.datagen;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;

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
    private static final ImmutableList<ItemConvertible> QUARTZ_ORES = ImmutableList.of(QUARTZ_ORE, DEEPSLATE_QUARTZ_ORE);

    public GeoclusterRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, RecipeCategory.MISC, COPPER_NUGGET, RecipeCategory.MISC, Items.COPPER_INGOT, "copper_ingot_from_nuggets", "copper_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, RecipeCategory.MISC, ZINC_NUGGET, RecipeCategory.MISC, ZINC_INGOT, "zinc_ingot_from_nuggets", "zinc_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, RecipeCategory.MISC, SILVER_NUGGET, RecipeCategory.MISC, SILVER_INGOT, "silver_ingot_from_nuggets", "silver_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, RecipeCategory.MISC, LEAD_NUGGET, RecipeCategory.MISC, LEAD_INGOT, "lead_ingot_from_nuggets", "lead_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, RecipeCategory.MISC, ALUMINIUM_NUGGET, RecipeCategory.MISC, ALUMINIUM_INGOT, "aluminium_ingot_from_nuggets", "aluminium_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, RecipeCategory.MISC, URANIUM_NUGGET, RecipeCategory.MISC, URANIUM_INGOT, "uranium_ingot_from_nuggets", "uranium_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, RecipeCategory.MISC, TIN_NUGGET, RecipeCategory.MISC, TIN_INGOT, "tin_ingot_from_nuggets", "tin_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, RecipeCategory.MISC, PLATINUM_NUGGET, RecipeCategory.MISC, PLATINUM_INGOT, "platinum_ingot_from_nuggets", "platinum_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, RecipeCategory.MISC, TITANIUM_NUGGET, RecipeCategory.MISC, TITANIUM_INGOT, "titanium_ingot_from_nuggets", "titanium_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, RecipeCategory.MISC, NICKEL_NUGGET, RecipeCategory.MISC, NICKEL_INGOT, "nickel_ingot_from_nuggets", "nickel_ingot");

        offerSmelting(exporter, ZINC_ORES, RecipeCategory.MISC, ZINC_INGOT, 1.0F, 200, "zinc_ingot");
        offerSmelting(exporter, SILVER_ORES, RecipeCategory.MISC, SILVER_INGOT, 1.0F, 200, "silver_ingot");
        offerSmelting(exporter, LEAD_ORES, RecipeCategory.MISC, LEAD_INGOT, 1.0F, 200, "lead_ingot");
        offerSmelting(exporter, ALUMINIUM_ORES, RecipeCategory.MISC, ALUMINIUM_INGOT, 1.0F, 200, "aluminium_ingot");
        offerSmelting(exporter, URANIUM_ORES, RecipeCategory.MISC, URANIUM_INGOT, 1.0F, 200, "uranium_ingot");
        offerSmelting(exporter, TIN_ORES, RecipeCategory.MISC, TIN_INGOT, 1.0F, 200, "tin_ingot");
        offerSmelting(exporter, PLATINUM_ORES, RecipeCategory.MISC, PLATINUM_INGOT, 1.0F, 200, "platinum_ingot");
        offerSmelting(exporter, TITANIUM_ORES, RecipeCategory.MISC, TITANIUM_INGOT, 1.0F, 200, "titanium_ingot");
        offerSmelting(exporter, NICKEL_ORES, RecipeCategory.MISC, NICKEL_INGOT, 1.0F, 200, "nickel_ingot");
        offerSmelting(exporter, List.of(RAW_ANCIENT_DEBRIS), RecipeCategory.MISC, Items.NETHERITE_SCRAP, 1.0F, 200, "netherite_scrap");
        offerSmelting(exporter, QUARTZ_ORES, RecipeCategory.MISC, Items.QUARTZ, 1.0F, 200, "quartz");

        offerBlasting(exporter, ZINC_ORES, RecipeCategory.MISC, ZINC_INGOT, 1.0F, 100, "zinc_ingot");
        offerBlasting(exporter, SILVER_ORES, RecipeCategory.MISC, SILVER_INGOT, 1.0F, 100, "silver_ingot");
        offerBlasting(exporter, LEAD_ORES, RecipeCategory.MISC, LEAD_INGOT, 1.0F, 100, "lead_ingot");
        offerBlasting(exporter, ALUMINIUM_ORES, RecipeCategory.MISC, ALUMINIUM_INGOT, 1.0F, 100, "aluminium_ingot");
        offerBlasting(exporter, URANIUM_ORES, RecipeCategory.MISC, URANIUM_INGOT, 1.0F, 100, "uranium_ingot");
        offerBlasting(exporter, TIN_ORES, RecipeCategory.MISC, TIN_INGOT, 1.0F, 100, "tin_ingot");
        offerBlasting(exporter, PLATINUM_ORES, RecipeCategory.MISC, PLATINUM_INGOT, 1.0F, 100, "platinum_ingot");
        offerBlasting(exporter, TITANIUM_ORES, RecipeCategory.MISC, TITANIUM_INGOT, 1.0F, 100, "titanium_ingot");
        offerBlasting(exporter, NICKEL_ORES, RecipeCategory.MISC, NICKEL_INGOT, 1.0F, 100, "nickel_ingot");
        offerBlasting(exporter, List.of(RAW_ANCIENT_DEBRIS), RecipeCategory.MISC, Items.NETHERITE_SCRAP, 1.0F, 100, "netherite_scrap");
        offerBlasting(exporter, QUARTZ_ORES, RecipeCategory.MISC, Items.QUARTZ, 1.0F, 100, "quartz");

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, IRON_PROSPECTORS_PICK).input('I', Items.IRON_INGOT).input('N', Items.IRON_NUGGET).input('S', Items.STICK)
                .pattern("NI")
                .pattern(" S")
                .criterion("has_raw_iron", conditionsFromItem(Items.RAW_IRON))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, STONE_PROSPECTORS_PICK).input('I', Items.COBBLESTONE).input('N', STONE_CHUNK).input('S', Items.STICK)
                .pattern("NI")
                .pattern(" S")
                .criterion("has_wood", conditionsFromTag(ItemTags.LOGS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, COPPER_PROSPECTORS_PICK).input('I', Items.COPPER_INGOT).input('N', COPPER_NUGGET).input('S', Items.STICK)
                .pattern("NI")
                .pattern(" S")
                .criterion("has_copper", conditionsFromItem(Items.RAW_COPPER))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.COBBLESTONE).input('S', STONE_CHUNK)
                .pattern("SS")
                .pattern("SS")
                .criterion("has_chunk", conditionsFromItem(STONE_CHUNK))
                .offerTo(exporter);
    }
}
