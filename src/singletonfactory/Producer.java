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
    /** stores distributors' ids for every month **/
     private List<MonthlyStat> monthlyStats;
     /** list of distributors that have chose the producer **/
     private List<PowerGrid> distributorsList;

    public Producer(final int id, final String energyType, final int maxDistributors,
                    final double priceKW, int energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = new ArrayList<>();
        this.distributorsList = new ArrayList<>();
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

    public final List<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }

    public final void setMonthlyStats(final List<MonthlyStat> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    public final List<PowerGrid> getDistributorsList() {
        return distributorsList;
    }

    public final void setDistributorsList(final List<PowerGrid> distributorsList) {
        this.distributorsList = distributorsList;
    }

    /**
     * method that notifies all of the distributors if any of the producers has changed his amount
     * of monthly given energy per distributor
     * @param distributors represents the objects that will be notified
     */
    public void notifyDistributors(List<PowerGrid> distributors) {
        for (PowerGrid distributor : distributors) {
            // verifies if the modified producer is chosen by the distributor
            for (PowerGrid chosenProducer : ((Distributor) distributor).getChosenProducers()) {
                if (chosenProducer.getId() == this.id) {
                    ((Distributor) distributor).update();
                    break;
                }
            }
        }
    }

    @Override
    public final String toString() {
        return "Producer{"
                + "id=" + id
                + ", energyType='" + energyType + '\''
                + ", maxDistributors=" + maxDistributors
                + ", priceKW=" + priceKW
                + ", energyPerDistributor=" + energyPerDistributor
                + ", monthlyStats=" + monthlyStats
                + "} ";
    }
}
