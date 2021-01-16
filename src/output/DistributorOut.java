package output;

import simulation.Contract;

import java.util.List;

public class DistributorOut {
    private final int id;

    private final int energyNeededKW;

    private final int contractCost;

    private final int budget;

    private final String producerStrategy;

    private final boolean isBankrupt;

    private final List<Contract> contracts;

    public DistributorOut(final int id, final int energyNeededKW, final int contractCost,
                          final int budget, final String producerStrategy, final boolean isBankrupt,
                          final List<Contract> contracts) {
        this.id = id;
        this.energyNeededKW = energyNeededKW;
        this.contractCost = contractCost;
        this.budget = budget;
        this.producerStrategy = producerStrategy;
        this.isBankrupt = isBankrupt;
        this.contracts = contracts;
    }

    public final int getId() {
        return id;
    }

    public final int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public final int getContractCost() {
        return contractCost;
    }

    public final int getBudget() {
        return budget;
    }

    public final String getProducerStrategy() {
        return producerStrategy;
    }

    public final boolean getIsBankrupt() {
        return isBankrupt;
    }

    public final List<Contract> getContracts() {
        return contracts;
    }
}
