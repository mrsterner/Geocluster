package dev.sterner.geocluster.common.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import dev.sterner.geocluster.Geocluster;

public class GeoclusterComponents implements WorldComponentInitializer {
    public static final ComponentKey<WorldChunkComponent> CHUNK = ComponentRegistry.getOrCreate(Geocluster.id("chunk"), WorldChunkComponent.class);
    public static final ComponentKey<WorldDepositComponent> DEPOSIT = ComponentRegistry.getOrCreate(Geocluster.id("deposit"), WorldDepositComponent.class);

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(CHUNK, WorldChunkComponent::new);
        registry.register(DEPOSIT, WorldDepositComponent::new);
    }
}
