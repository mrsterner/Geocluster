package dev.sterner.geocluster.api;

import dev.sterner.geocluster.Geocluster;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.List;

public class DepositCache {
    private static final DepositCache cache = new DepositCache();
    private ArrayList<IDeposit> deposits;

    public DepositCache() {
        this.deposits = new ArrayList<>();
    }

    public static DepositCache getCache() {
        return cache;
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

    public IDeposit pick(StructureWorldAccess level, BlockPos pos) {
        List<IDeposit> choices = new ArrayList<>(deposits);
        choices.removeIf(dep -> !dep.canPlaceInBiome(level.getBiome(pos)));

        if (choices.isEmpty()) {
            return null;
        }

        int totalWeight = choices.stream().mapToInt(IDeposit::getWeight).sum();
        int rng = level.getRandom().nextInt(totalWeight);

        for (IDeposit d : choices) {
            int weight = d.getWeight();
            if (rng < weight) {
                return d;
            }
            rng -= weight;
        }

        Geocluster.LOGGER.error("Could not reach decision on deposit to generate at DepositCache#pick");
        return null;
    }
}
