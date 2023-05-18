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
    int generate(StructureWorldAccess level, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated);

    void generatePost(StructureWorldAccess level, BlockPos pos, IWorldDepositComponent deposits, IWorldChunkComponent chunksGenerated);

    HashSet<BlockState> getAllOres();

    int getGenWeight();

    boolean canPlaceInBiome(RegistryEntry<Biome> biome);

    HashSet<BlockState> getBlockStateMatchers();
}
