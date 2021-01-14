package singletonfactory;

import java.util.ArrayList;
import java.util.List;

public class Producer extends PowerGrid {
    /** producer's id **/
    private final int id;
    /** energy type **/
    private final String energyType;
    /** maximum number of distributors **/
    private final int maxDistributors;
    /** KW's price **/
    private final double priceKW;
    /** monthly amount of energy for a distributor **/
    private int energyPerDistributor;
    /** left number of distributors **/
    private int remainedDistributors;
    /** stores distributors' ids for every month **/
     private List<MonthlyStat> monthlyStats;

    public Producer(final int id, final String energyType, final int maxDistributors,
                    final double priceKW, int energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        this.remainedDistributors = maxDistributors;
        this.monthlyStats = new ArrayList<>();
    }

    @Override
    public final int getId() {
        return id;
    }

    public final String getEnergyType() {
        return energyType;
    }

    public final int getMaxDistributors() {
        return maxDistributors;
    }

    public final double getPriceKW() {
        return priceKW;
    }

    public final int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public final void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getRemainedDistributors() {
        return remainedDistributors;
    }

    public void setRemainedDistributors(int remainedDistributors) {
        this.remainedDistributors = remainedDistributors;
    }

    public List<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(List<MonthlyStat> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    /**
     * method that notifies all of the distributors if any of the producers has changed his amount
     * of monthly given energy per distributor
     * @param distributors represents the objects that will be notified
     */
    public void notifyDistributors(List<PowerGrid> distributors) {
        /* every distributor will choose again */
        for (PowerGrid distributor : distributors) {
            ((Distributor) distributor).update();
        }
        /* the producer will have maximum of distributors left again */
        remainedDistributors = maxDistributors;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", energyType='" + energyType + '\'' +
                ", maxDistributors=" + maxDistributors +
                ", priceKW=" + priceKW +
                ", energyPerDistributor=" + energyPerDistributor +
                "} " + super.toString();
    }
}
