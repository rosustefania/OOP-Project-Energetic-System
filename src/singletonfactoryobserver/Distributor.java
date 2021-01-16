package singletonfactoryobserver;

import common.Constants;
import simulation.Contract;

import java.util.ArrayList;
import java.util.List;

public class Distributor extends PowerGrid implements Observer {
    /** distributor's id **/
    private final int id;
    /** distributor's contract length **/
    private final int contractLength;
    /** distributor's budget **/
    private int budget;
    /** distributor's infrastructure cost **/
    private int infrastructureCost;
    /** distributor's production cost **/
    private long productionCost;
    /** monthly amount of energy **/
    private final int energyNeededKW;
    /** producer's strategy **/
    private final String producerStrategy;
    /** distributor's contracts list **/
    private List<Contract> contracts = new ArrayList<>();
    /** distributor's contract's price **/
    private int contractPrice;
    /** for verifying if the distributor is bankrupt **/
    private boolean isBankrupt;
    /** list of chosen producers **/
    private final List<PowerGrid> chosenProducers;

    public Distributor(final int id, final int contractLength, final int budget,
                       final int infrastructureCost, final int energyNeededKW,
                       final String producerStrategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = budget;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = 0;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = producerStrategy;
        this.contractPrice = 0;
        this.isBankrupt = false;
        this.chosenProducers = new ArrayList<>();
    }

    @Override
    public final int getId() {
        return id;
    }

    public final int getContractLength() {
        return contractLength;
    }

    public final int getBudget() {
        return budget;
    }

    public final void setBudget(final int budget) {
        this.budget = budget;
    }

    public final int getInfrastructureCost() {
        return infrastructureCost;
    }

    public final void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    /**
     * method that sets production cost by the given formulas
     */
    public final void setProductionCost() {
        double sum = 0.00;
        for (PowerGrid chosenProducer : chosenProducers) {
            sum += ((Producer) chosenProducer).getEnergyPerDistributor()
                    * ((Producer) chosenProducer).getPriceKW();
        }
        productionCost = Math.round(Math.floor(sum / Constants.PRODUCTION_COST_CONSTANT));
    }

    public final List<Contract> getContracts() {
        return contracts;
    }

    public final void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }

    public final int getContractPrice() {
        return contractPrice;
    }

    /**
     * method that sets contract's price by the given formulas
     */
    public final void setContractPrice() {
        long price;
        if (contracts.size() == 0) {
            price = infrastructureCost + productionCost
                    + Math.round(Math.floor(Constants.PRICE_PERCENTAGE * productionCost));
        } else {
            price = Math.round(Math.floor(infrastructureCost / (double) contracts.size())
                    + productionCost + Math.round(Math.floor(Constants.PRICE_PERCENTAGE
                    * productionCost)));
        }
        contractPrice = (int) price;
    }

    public final boolean isBankrupt() {
        return isBankrupt;
    }

    public final List<PowerGrid> getChosenProducers() {
        return chosenProducers;
    }

    public final int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public final String getProducerStrategy() {
        return producerStrategy;
    }

    /**
     * method by which the distributor pays his monthly costs
     */
    public void payCosts() {
        if (!isBankrupt) {
            if (budget < infrastructureCost + productionCost * contracts.size()) {
                isBankrupt = true;
                contracts.clear();
            }
            budget -= infrastructureCost + productionCost * contracts.size();
        }

    }

    /**
     * method that updates the chosen producers' list if any of the producers has changed his
     * given energy amount
     */
    public void update() {
        /* removes the distributor from all producers he chose, if one of his producers updated */
        for (PowerGrid chosenProducer : chosenProducers) {
            List<PowerGrid> toBeRemovedDistributors = new ArrayList<>();
            for (PowerGrid distributor : ((Producer) chosenProducer).getDistributorsList()) {
                if (distributor.getId() == this.id) {
                    toBeRemovedDistributors.add(distributor);
                }
            }
            ((Producer) chosenProducer).getDistributorsList().removeAll(toBeRemovedDistributors);
        }

        /* removes all producers he chose, so he can choose again */
        chosenProducers.clear();
    }

    @Override
    public final String toString() {
        return "Distributor{"
                + "id=" + id
                + ", contractLength=" + contractLength
                + ", budget=" + budget
                + ", infrastructureCost=" + infrastructureCost
                + ", energyNeededKW=" + energyNeededKW
                + ", producerStrategy='" + producerStrategy + '\''
                + "} ";
    }
}
