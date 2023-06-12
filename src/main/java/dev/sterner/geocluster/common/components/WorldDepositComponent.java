package dev.sterner.geocluster.common.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class WorldDepositComponent implements AutoSyncedComponent, IWorldDepositComponent {
    private final ConcurrentHashMap<ChunkPos, ConcurrentLinkedQueue<PendingBlock>> pendingBlocks;

    public World world;

    public WorldDepositComponent(World world) {
        this.world = world;
        this.pendingBlocks = new ConcurrentHashMap<>();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        tag.getKeys().forEach(chunkPosAsString -> {
            String[] parts = chunkPosAsString.split("_");
            ChunkPos cp = new ChunkPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            NbtList pending = tag.getList(chunkPosAsString, 10);
            ConcurrentLinkedQueue<PendingBlock> lq = new ConcurrentLinkedQueue<>();
            pending.forEach(x -> {
                PendingBlock pendingBlock = PendingBlock.readNbt(x);
                if (pendingBlock != null) {
                    lq.add(pendingBlock);
                }
            });
            this.pendingBlocks.put(cp, lq);
        });
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        NbtCompound compound = new NbtCompound();
        this.pendingBlocks.forEach((pos, pending) -> {
            NbtList nbtList = new NbtList();
            String key = pos.x + "_" + pos.z;
            pending.forEach(pendingBlock -> nbtList.add(pendingBlock.writeNbt()));
            compound.put(key, nbtList);
        });
    }

    @Override
    public void putPendingBlock(BlockPos pos, BlockState state) {
        PendingBlock p = new PendingBlock(pos, state);
        ChunkPos cp = new ChunkPos(pos);
        this.pendingBlocks.putIfAbsent(cp, new ConcurrentLinkedQueue<>());
        this.pendingBlocks.get(cp).add(p);
    }

    @Override
    public void removePendingBlocksForChunk(ChunkPos p) {
        this.pendingBlocks.remove(p);
    }

    @Override
    public int getPendingBlockCount() {
        return (int) this.pendingBlocks.values().stream().collect(Collectors.summarizingInt(ConcurrentLinkedQueue::size)).getSum();
    }

    @Override
    public ConcurrentLinkedQueue<PendingBlock> getPendingBlocks(ChunkPos chunkPos) {
        return this.pendingBlocks.getOrDefault(chunkPos, new ConcurrentLinkedQueue<>());
    }

    public record PendingBlock(BlockPos pos, BlockState state) {

        public NbtCompound writeNbt() {
            NbtCompound tmp = new NbtCompound();
            NbtCompound posTag = NbtHelper.fromBlockPos(this.pos);
            NbtCompound stateTag = NbtHelper.fromBlockState(this.state);
            tmp.put("pos", posTag);
            tmp.put("state", stateTag);
            return tmp;
        }

        @Nullable
        public static PendingBlock readNbt(NbtElement element) {
            if (element instanceof NbtCompound tag) {
                BlockPos pos = NbtHelper.toBlockPos(tag.getCompound("pos"));
                BlockState state = NbtHelper.toBlockState(Registries.BLOCK.getReadOnlyWrapper(), tag.getCompound("state"));
                return new PendingBlock(pos, state);
            }
            return null;
        }

        @Override
        public String toString() {
            return "[" + this.pos.getX() + " " + this.pos.getY() + " " + this.pos.getZ() + "]: " + Registries.BLOCK.getId(this.state.getBlock());
        }
    }
}
