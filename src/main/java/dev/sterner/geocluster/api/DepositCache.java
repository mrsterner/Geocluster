package dev.sterner.geocluster.api;

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

    @Nullable
    public IDeposit pick(WorldGenLevel level, BlockPos pos) {
        @SuppressWarnings("unchecked")
        ArrayList<IDeposit> choices = (ArrayList<IDeposit>) this.deposits.clone();
        // Dimension Filtering done here!
        choices.removeIf((dep) -> !dep.canPlaceInBiome(level.getBiome(pos)));

        if (choices.size() == 0) {
            return null;
        }

        int totalWt = 0;
        for (IDeposit d : choices) {
            totalWt += d.getGenWt();
        }

        int rng = level.getRandom().nextInt(totalWt);
        for (IDeposit d : choices) {
            int wt = d.getGenWt();
            if (rng < wt) {
                return d;
            }
            rng -= wt;
        }

        Geolosys.getInstance().LOGGER.error("Could not reach decision on pluton to generate at PlutonRegistry#pick");
        return null;
    }
}
