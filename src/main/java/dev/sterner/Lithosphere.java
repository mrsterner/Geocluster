package dev.sterner;

import dev.sterner.common.registry.LithosphereWorldgenRegistry;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Lithosphere implements ModInitializer {
    public static final String MODID = "lithosphere";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static Identifier id(String id) {
       return new Identifier(MODID, id);
    }

    @Override
    public void onInitialize() {

        LithosphereWorldgenRegistry.init();
    }
}