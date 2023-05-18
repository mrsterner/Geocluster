package dev.sterner.geocluster.common.utils;

import dev.sterner.geocluster.Geocluster;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;

import java.util.HashSet;

public class PacketUtils {
    public static NbtCompound encodeBlocks(HashSet<BlockState> blocks) {
        NbtCompound comp = new NbtCompound();
        NbtList list = new NbtList();
        for (BlockState b : blocks) {
            list.add(NbtHelper.fromBlockState(b));
        }
        comp.put("Blocks", list);
        return comp;
    }

    public static HashSet<BlockState> decodeBlocks(NbtCompound comp) {
        HashSet<BlockState> ret = new HashSet<BlockState>();
        NbtList list = comp.getList("Blocks", 10);
        list.forEach((c) -> {
            if (c instanceof NbtCompound) {
                ret.add(NbtHelper.toBlockState((NbtCompound) c));
            } else {
                Geocluster.LOGGER.error("The following compound appears to be broken: {}", c);
            }
        });

        return ret;
    }

    public static String messagify(HashSet<BlockState> blocks) {
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        for (BlockState b : blocks) {
            sb.append(new ItemStack(b.getBlock()).getName().getString().replace("[", "").replace("]", ""));
            if ((idx + 2) == blocks.size()) {
                sb.append(" & ");
            } else if ((idx + 1) != blocks.size()) {
                sb.append(", ");
            }
            idx++;
        }
        return sb.toString();
    }
}
