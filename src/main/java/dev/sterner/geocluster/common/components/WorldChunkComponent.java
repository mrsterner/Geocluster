package dev.sterner.geocluster.common.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.concurrent.ConcurrentHashMap;

public class WorldChunkComponent implements AutoSyncedComponent, IWorldChunkComponent {

    private final ConcurrentHashMap.KeySetView<ChunkPos, Boolean> generatedChunks;
    private final World world;

    public WorldChunkComponent(World world) {
        this.world = world;
        this.generatedChunks = ConcurrentHashMap.newKeySet();
    }

    @Override
    public boolean hasChunkGenerated(ChunkPos pos) {
        return this.generatedChunks.contains(pos);
    }

    @Override
    public void setChunkGenerated(ChunkPos pos) {
        this.generatedChunks.add(pos);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        NbtList chunks = tag.getList("chunks", 10);
        chunks.forEach(x -> {
            NbtCompound comp = (NbtCompound) x;
            ChunkPos chunkPos = new ChunkPos(comp.getInt("x"), comp.getInt("z"));
            this.generatedChunks.add(chunkPos);
        });
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        NbtCompound compound = new NbtCompound();
        NbtList chunks = new NbtList();
        this.generatedChunks.forEach(chunkPos -> {
            NbtCompound t = new NbtCompound();
            t.putInt("x", chunkPos.x);
            t.putInt("z", chunkPos.z);
            chunks.add(t);
        });
        compound.put("chunks", chunks);
    }
}
