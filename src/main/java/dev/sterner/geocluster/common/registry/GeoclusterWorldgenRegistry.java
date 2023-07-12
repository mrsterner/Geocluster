package dev.sterner.geocluster.common.registry;

import com.google.common.collect.Lists;
import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.common.world.feature.DepositFeature;
import dev.sterner.geocluster.mixin.BiomeModificationContextImplMixin;
import net.fabricmc.fabric.api.biome.v1.*;
import net.fabricmc.fabric.api.event.registry.DynamicRegistryView;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;


public class GeoclusterWorldgenRegistry {

    public static Feature<DefaultFeatureConfig> DEPOSIT_FEATURE = Registry.register(Registries.FEATURE, Geocluster.id("deposits"), new DepositFeature(DefaultFeatureConfig.CODEC));
    public static ConfiguredFeature<DefaultFeatureConfig, Feature<DefaultFeatureConfig>> CONFIGURED_DEPOSIT_FEATURE;
    public static RegistryKey<ConfiguredFeature<?, ?>> CONFIGURED_DEPOSIT_FEATURE_KEY;

    public static PlacedFeature PLACED_DEPOSIT_FEATURE;
    public static RegistryKey<PlacedFeature> PLACED_DEPOSIT_FEATURE_KEY;

    public static void init() {
        PLACED_DEPOSIT_FEATURE_KEY = PlacedFeatures.of(Geocluster.id("deposits_placed").toString());

        BiomeModification modifications = BiomeModifications.create(Geocluster.id("worldgen"));
        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.all(), ctx -> {
            ctx.getGenerationSettings().addFeature(GenerationStep.Feature.UNDERGROUND_ORES, PLACED_DEPOSIT_FEATURE_KEY);
        });

        modifications.add(ModificationPhase.REMOVALS, BiomeSelectors.all(), ctx -> {
            Iterable<RegistryEntry<PlacedFeature>> registryEntries = getPlacedFeaturesByTag(ctx, GeoclusterTagRegistry.ORES_TO_REMOVE);
            for (RegistryEntry<PlacedFeature> placedFeatureHolder : registryEntries) {
                if (placedFeatureHolder.getKey().isPresent()) {
                    ctx.getGenerationSettings().removeFeature(placedFeatureHolder.getKey().get());
                }
            }
        });
    }

    public static void init(DynamicRegistryView registryView, Registry<ConfiguredFeature<?, ?>> configuredFeatures) {
        CONFIGURED_DEPOSIT_FEATURE = Registry.register(configuredFeatures, Geocluster.id("deposits_configured"), new ConfiguredFeature<>(DEPOSIT_FEATURE, DefaultFeatureConfig.INSTANCE));
        CONFIGURED_DEPOSIT_FEATURE_KEY = ConfiguredFeatures.of(Geocluster.id("deposits_configured").toString());

        registryView.getOptional(RegistryKeys.PLACED_FEATURE).ifPresent(registry -> {
            RegistryEntryLookup<ConfiguredFeature<?, ?>> entry = registryView.asDynamicRegistryManager().createRegistryLookup().getOrThrow(RegistryKeys.CONFIGURED_FEATURE);
            PLACED_DEPOSIT_FEATURE = new PlacedFeature(entry.getOrThrow(CONFIGURED_DEPOSIT_FEATURE_KEY), Lists.newArrayList(HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(320))));
            Registry.register(registry, Geocluster.id("deposits_placed"), PLACED_DEPOSIT_FEATURE);
        });
    }

    private static Iterable<RegistryEntry<PlacedFeature>> getPlacedFeaturesByTag(BiomeModificationContext ctx, TagKey<PlacedFeature> placedFeatureTagKey) {
        DynamicRegistryManager dynamicRegistryManager = ((BiomeModificationContextImplMixin) ctx).getRegistries();
        Registry<PlacedFeature> placedFeatureRegistry = dynamicRegistryManager.get(RegistryKeys.PLACED_FEATURE);
        return placedFeatureRegistry.iterateEntries(placedFeatureTagKey);
    }
}
