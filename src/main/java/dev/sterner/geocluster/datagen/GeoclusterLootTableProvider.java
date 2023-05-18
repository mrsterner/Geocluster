package dev.sterner.geocluster.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
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

        this.addDrop(COPPER_SAMPLE, Items.RAW_COPPER);
        this.addDrop(IRON_SAMPLE, Items.RAW_IRON);
        this.addDrop(GOLD_SAMPLE, Items.RAW_GOLD);
        this.addDrop(ZINC_SAMPLE, RAW_ZINC);
        this.addDrop(SILVER_SAMPLE, RAW_SILVER);
        this.addDrop(LEAD_SAMPLE, RAW_LEAD);
        this.addDrop(ALUMINIUM_SAMPLE, RAW_ALUMINIUM);
        this.addDrop(URANIUM_SAMPLE, RAW_URANIUM);
        this.addDrop(TIN_SAMPLE, RAW_TIN);
        this.addDrop(PLATINUM_SAMPLE, RAW_PLATINUM);
        this.addDrop(TITANIUM_SAMPLE, RAW_TITANIUM);
        this.addDrop(NICKEL_SAMPLE, RAW_NICKEL);

        this.addDrop(COAL_SAMPLE, Items.COAL);
        this.addDrop(REDSTONE_SAMPLE, Items.REDSTONE);
        this.addDrop(LAPIS_LAZULI_SAMPLE, Items.LAPIS_LAZULI);
        this.addDrop(EMERALD_SAMPLE, Items.EMERALD);
        this.addDrop(DIAMOND_SAMPLE, Items.DIAMOND);
        this.addDrop(ANCIENT_DEBRIS_SAMPLE, RAW_ANCIENT_DEBRIS);
    }
}
