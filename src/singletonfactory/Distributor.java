package singletonfactory;

import common.Constants;
import simulation.Contract;

import java.util.ArrayList;
import java.util.List;

public class Distributor extends PowerGrid {
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
    private List<PowerGrid> chosenProducers;

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

    public void setInfrastructureCost(int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public long getProductionCost() {
        return productionCost;
    }

    public void setProductionCost() {
        double sum = 0.00;
        for (PowerGrid chosenProducer : chosenProducers) {
            sum += ((Producer) chosenProducer).getEnergyPerDistributor() *
                    ((Producer) chosenProducer).getPriceKW();
        }
        productionCost = Math.round(Math.floor(sum/10));
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
                    + Math.round(Math.floor(Constants.PRICE_PERCENTAGE ));
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

    public final void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public List<PowerGrid> getChosenProducers() {
        return chosenProducers;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public String getProducerStrategy() {
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
     * removes the given contract from the contracts' list
     * @param givenContract represents the given contract that need to be removed
     */
    public void removeContract(final Contract givenContract) {
        for (Contract contract : contracts) {
            if (contract.equals(givenContract)) {
                contracts.remove(givenContract);
            }
        }
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "id=" + id +
                ", contractLength=" + contractLength +
                ", budget=" + budget +
                ", infrastructureCost=" + infrastructureCost +
                ", energyNeededKW=" + energyNeededKW +
                ", producerStrategy='" + producerStrategy + '\'' +
                "} ";
    }
}
