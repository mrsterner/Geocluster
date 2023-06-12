package dev.sterner.geocluster.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class GeoclusterDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        var pack = dataGenerator.createPack();
        pack.addProvider(GeoclusterLanguageProvider::new);
        pack.addProvider(GeoclusterLootTableProvider::new);
        pack.addProvider(GeoclusterModelProvider::new);
        pack.addProvider(GeoclusterRecipeProvider::new);
        pack.addProvider(GeoclusterTagProvider.GeoclusterBlockTags::new);
        pack.addProvider(GeoclusterTagProvider.GeoclusterItemTags::new);
        pack.addProvider(GeoclusterTagProvider.GeoclusterBiomeTags::new);
    }
}
