package dev.sterner.geocluster;

import dev.sterner.geocluster.common.registry.GeoclusterObjects;
import dev.sterner.geocluster.common.registry.GeoclusterWorldgenRegistry;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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
        GeoclusterObjects.init();
        GeoclusterWorldgenRegistry.init();
    }
}