package dev.sterner.geocluster.common.utils;

import dev.sterner.geocluster.Geocluster;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;

import java.util.HashSet;

public class PacketUtils {
    public static NbtCompound fromBlockSet(HashSet<BlockState> blocks) {
        NbtCompound comp = new NbtCompound();
        NbtList list = new NbtList();
        for (BlockState b : blocks) {
            list.add(NbtHelper.fromBlockState(b));
        }
        comp.put("Blocks", list);
        return comp;
    }

    public static HashSet<BlockState> toBlockSet(NbtCompound comp) {
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
}
