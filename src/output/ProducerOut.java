package output;

import singletonfactory.MonthlyStat;

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

    public int getId() {
        return id;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public String getEnergyType() {
        return energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public List<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }
}
