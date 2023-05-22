package dev.sterner.geocluster.api;

import dev.sterner.geocluster.common.components.IWorldChunkComponent;
import dev.sterner.geocluster.common.components.IWorldDepositComponent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;

import java.util.HashSet;

public interface IDeposit {
    /**
     * Handles full-on generation of this type of cluster. Requires 0 arguments as
     * everything is self-contained in this class
     *
     * @return (int) the number of cluster resource blocks placed. If 0 -- this
     * should be evaluted as a false for use of Mojang's sort-of sketchy
     * generation code in
     */
    int generate(StructureWorldAccess level, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated);

    /**
     * Handles what to do after the world has generated
     */
    void generatePost(StructureWorldAccess level, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated);

    HashSet<BlockState> getAllOres();

    int getWeight();

    boolean canPlaceInBiome(RegistryEntry<Biome> biome);

    HashSet<BlockState> getBlockStateMatchers();
}
