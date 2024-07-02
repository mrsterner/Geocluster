package dev.sterner.geocluster.common.components;

import dev.sterner.geocluster.Geocluster;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class GeoclusterComponents implements WorldComponentInitializer {
    public static final ComponentKey<WorldChunkComponent> CHUNK = ComponentRegistry.getOrCreate(Geocluster.id("chunk"), WorldChunkComponent.class);
    public static final ComponentKey<WorldDepositComponent> DEPOSIT = ComponentRegistry.getOrCreate(Geocluster.id("deposit"), WorldDepositComponent.class);

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(CHUNK, WorldChunkComponent::new);
        registry.register(DEPOSIT, WorldDepositComponent::new);
    }
}
