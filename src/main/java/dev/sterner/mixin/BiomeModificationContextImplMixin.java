package dev.sterner.mixin;

import net.fabricmc.fabric.impl.biome.modification.BiomeModificationContextImpl;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BiomeModificationContextImpl.class)
public interface BiomeModificationContextImplMixin {

    @Accessor("registries")
    DynamicRegistryManager getRegistries();
}