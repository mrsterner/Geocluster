package dev.sterner.geocluster;

import dev.sterner.geocluster.api.GeoclusterAPI;
import dev.sterner.geocluster.common.data.WorldGenDataReloadListener;
import dev.sterner.geocluster.common.registry.GeoclusterObjects;
import dev.sterner.geocluster.common.registry.GeoclusterWorldgenRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Geocluster implements ModInitializer {
    public static final String MODID = "geocluster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(GeoclusterObjects.ZINC_INGOT));


    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }

    @Override
    public void onInitialize() {
        GeoclusterAPI.init();
        GeoclusterObjects.init();
        GeoclusterWorldgenRegistry.init();
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new WorldGenDataReloadListener());
    }
}