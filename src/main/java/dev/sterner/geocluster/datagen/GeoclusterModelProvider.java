package dev.sterner.geocluster.datagen;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import static dev.sterner.geocluster.common.registry.GeoclusterObjects.*;

public class GeoclusterModelProvider extends FabricModelProvider {

    public GeoclusterModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerSimpleCubeAll(ZINC_ORE);
        generator.registerSimpleCubeAll(DEEPSLATE_ZINC_ORE);

        generator.registerSimpleCubeAll(SILVER_ORE);
        generator.registerSimpleCubeAll(DEEPSLATE_SILVER_ORE);

        generator.registerSimpleCubeAll(LEAD_ORE);
        generator.registerSimpleCubeAll(DEEPSLATE_LEAD_ORE);

        generator.registerSimpleCubeAll(ALUMINIUM_ORE);
        generator.registerSimpleCubeAll(DEEPSLATE_ALUMINIUM_ORE);

        generator.registerSimpleCubeAll(URANIUM_ORE);
        generator.registerSimpleCubeAll(DEEPSLATE_URANIUM_ORE);

        generator.registerSimpleCubeAll(TIN_ORE);
        generator.registerSimpleCubeAll(DEEPSLATE_TIN_ORE);

        generator.registerSimpleCubeAll(PLATINUM_ORE);
        generator.registerSimpleCubeAll(DEEPSLATE_PLATINUM_ORE);

        generator.registerSimpleCubeAll(TITANIUM_ORE);
        generator.registerSimpleCubeAll(DEEPSLATE_TITANIUM_ORE);

        generator.registerSimpleCubeAll(NICKEL_ORE);
        generator.registerSimpleCubeAll(DEEPSLATE_NICKEL_ORE);

        generator.registerSimpleCubeAll(QUARTZ_ORE);
        generator.registerSimpleCubeAll(DEEPSLATE_QUARTZ_ORE);

        generator.registerSimpleCubeAll(ANCIENT_DEBRIS_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(ZINC_INGOT, Models.GENERATED);
        generator.register(SILVER_INGOT, Models.GENERATED);
        generator.register(LEAD_INGOT, Models.GENERATED);
        generator.register(ALUMINIUM_INGOT, Models.GENERATED);
        generator.register(URANIUM_INGOT, Models.GENERATED);
        generator.register(TIN_INGOT, Models.GENERATED);
        generator.register(PLATINUM_INGOT, Models.GENERATED);
        generator.register(TITANIUM_INGOT, Models.GENERATED);
        generator.register(NICKEL_INGOT, Models.GENERATED);

        generator.register(RAW_ZINC, Models.GENERATED);
        generator.register(RAW_LEAD, Models.GENERATED);
        generator.register(RAW_ALUMINIUM, Models.GENERATED);
        generator.register(RAW_URANIUM, Models.GENERATED);
        generator.register(RAW_TIN, Models.GENERATED);
        generator.register(RAW_PLATINUM, Models.GENERATED);
        generator.register(RAW_TITANIUM, Models.GENERATED);
        generator.register(RAW_NICKEL, Models.GENERATED);
        generator.register(RAW_SILVER, Models.GENERATED);
        generator.register(RAW_ANCIENT_DEBRIS, Models.GENERATED);

        generator.register(COPPER_NUGGET, Models.GENERATED);
        generator.register(ZINC_NUGGET, Models.GENERATED);
        generator.register(SILVER_NUGGET, Models.GENERATED);
        generator.register(LEAD_NUGGET, Models.GENERATED);
        generator.register(ALUMINIUM_NUGGET, Models.GENERATED);
        generator.register(URANIUM_NUGGET, Models.GENERATED);
        generator.register(TIN_NUGGET, Models.GENERATED);
        generator.register(PLATINUM_NUGGET, Models.GENERATED);
        generator.register(TITANIUM_NUGGET, Models.GENERATED);
        generator.register(NICKEL_NUGGET, Models.GENERATED);
        generator.register(IRON_PROSPECTORS_PICK, Models.GENERATED);
        generator.register(COPPER_PROSPECTORS_PICK, Models.GENERATED);
        generator.register(STONE_PROSPECTORS_PICK, Models.GENERATED);
        generator.register(STONE_CHUNK, Models.GENERATED);
    }
}
