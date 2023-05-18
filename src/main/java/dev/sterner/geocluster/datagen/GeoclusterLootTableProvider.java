package dev.sterner.geocluster.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

import static dev.sterner.geocluster.common.registry.GeoclusterObjects.*;

public class GeoclusterLootTableProvider extends FabricBlockLootTableProvider {
    public GeoclusterLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        this.addDrop(ZINC_ORE, b -> oreDrops(b, RAW_ZINC));
        this.addDrop(DEEPSLATE_ZINC_ORE, b -> oreDrops(b, RAW_ZINC));

        this.addDrop(SILVER_ORE, b -> oreDrops(b, RAW_SILVER));
        this.addDrop(DEEPSLATE_SILVER_ORE, b -> oreDrops(b, RAW_SILVER));

        this.addDrop(LEAD_ORE, b -> oreDrops(b, RAW_LEAD));
        this.addDrop(DEEPSLATE_LEAD_ORE, b -> oreDrops(b, RAW_LEAD));

        this.addDrop(ALUMINIUM_ORE, b -> oreDrops(b, RAW_ALUMINIUM));
        this.addDrop(DEEPSLATE_ALUMINIUM_ORE, b -> oreDrops(b, RAW_ALUMINIUM));

        this.addDrop(URANIUM_ORE, b -> oreDrops(b, RAW_URANIUM));
        this.addDrop(DEEPSLATE_URANIUM_ORE, b -> oreDrops(b, RAW_URANIUM));

        this.addDrop(TIN_ORE, b -> oreDrops(b, RAW_TIN));
        this.addDrop(DEEPSLATE_TIN_ORE, b -> oreDrops(b, RAW_TIN));

        this.addDrop(PLATINUM_ORE, b -> oreDrops(b, RAW_PLATINUM));
        this.addDrop(DEEPSLATE_PLATINUM_ORE, b -> oreDrops(b, RAW_PLATINUM));

        this.addDrop(TITANIUM_ORE, b -> oreDrops(b, RAW_TITANIUM));
        this.addDrop(DEEPSLATE_TITANIUM_ORE, b -> oreDrops(b, RAW_TITANIUM));

        this.addDrop(NICKEL_ORE, b -> oreDrops(b, RAW_NICKEL));
        this.addDrop(DEEPSLATE_NICKEL_ORE, b -> oreDrops(b, RAW_NICKEL));
    }
}
