package output;

import java.util.List;

public class ProducerOut {
    private final int id;

    private final int maxDistributors;

    private final double priceKW;

    private final String energyType;

    private final int energyPerDistributor;

    private final List<MonthlyStat> monthlyStats;

    public ProducerOut(int id, int maxDistributors, double priceKW, String energyType,
                       int energyPerDistributor,
                       List<MonthlyStat> monthlyStats) {
        this.id = id;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyType = energyType;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = monthlyStats;
    }

    public final int getId() {
        return id;
    }

    public final int getMaxDistributors() {
        return maxDistributors;
    }

    public final double getPriceKW() {
        return priceKW;
    }

    public final String getEnergyType() {
        return energyType;
    }

    public final int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public final List<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }
}
