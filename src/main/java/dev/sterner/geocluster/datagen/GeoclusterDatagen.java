package dev.sterner.geocluster.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class GeoclusterDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        GeoclusterTagProvider.GeoclusterBlockTags blockTagsProvider = new GeoclusterTagProvider.GeoclusterBlockTags(dataGenerator);
        dataGenerator.addProvider(blockTagsProvider);
        dataGenerator.addProvider(GeoclusterTagProvider.GeoclusterBiomeTags::new);
        dataGenerator.addProvider((p) -> new GeoclusterTagProvider.GeoclusterItemTags(p, blockTagsProvider));

        dataGenerator.addProvider(GeoclusterLanguageProvider::new);
        dataGenerator.addProvider(GeoclusterLootTableProvider::new);
        dataGenerator.addProvider(GeoclusterModelProvider::new);
        dataGenerator.addProvider(GeoclusterRecipeProvider::new);
    }
}
