package dev.sterner.geocluster.common.components;

import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;

public class GeoclusterComponents implements WorldComponentInitializer {
    //public static final ComponentKey<IChunkGennedCapability> CHUNK = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("geolosys", "chunk"), IChunkGennedCapability.class);



    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        //registry.register(CHUNK, ChunkGennedCapability::new);
    }
}
