package dev.sterner.common.registry;

import dev.sterner.Lithosphere;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.PlacedFeature;

public interface LithosphereTagRegistry {
    TagKey<PlacedFeature> ORES_TO_REMOVE = TagKey.of(Registry.PLACED_FEATURE_KEY, Lithosphere.id("vanilla_ores_to_remove"));
}
