package dev.sterner.geocluster.api;

import dev.sterner.geocluster.Geocluster;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class DepositCache {
    private ArrayList<IDeposit> deposits;

    public DepositCache() {
        this.deposits = new ArrayList<>();
    }

    public void clear() {
        this.deposits = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<IDeposit> getOres() {
        return (ArrayList<IDeposit>) this.deposits.clone();
    }

    public void addDeposit(IDeposit ore) {
        this.deposits.add(ore);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public IDeposit pick(StructureWorldAccess level, BlockPos pos) {
        ArrayList<IDeposit> choices = (ArrayList<IDeposit>) this.deposits.clone();
        choices.removeIf((dep) -> !dep.canPlaceInBiome(level.getBiome(pos)));

        if (choices.size() == 0) {
            return null;
        }

        int totalWt = 0;
        for (IDeposit d : choices) {
            totalWt += d.getGenWeight();
        }

        int rng = level.getRandom().nextInt(totalWt);
        for (IDeposit d : choices) {
            int wt = d.getGenWeight();
            if (rng < wt) {
                return d;
            }
            rng -= wt;
        }

        Geocluster.LOGGER.error("Could not reach decision on deposit to generate at DepositCache#pick");
        return null;
    }
}
