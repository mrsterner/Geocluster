package dev.sterner.geocluster.mixin;

import net.minecraft.util.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(targets = {
        "net.fabricmc.fabric.impl.biome.modification.BiomeModificationContextImpl",
        "org.quiltmc.qsl.worldgen.biome.impl.modification.BiomeModificationContextImpl"
})
public interface BiomeModificationContextImplMixin {

    @Accessor("registries")
    DynamicRegistryManager getRegistries();
}