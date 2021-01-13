package output;

import simulation.Contract;

import java.util.List;

public class DistributorOut {
    private final int id;

    private final int budget;

    private final boolean isBankrupt;

    private final List<Contract> contracts;

    public DistributorOut(final int id, final int budget, final boolean isBankrupt,
                          final List<Contract> contracts) {
        this.id = id;
        this.budget = budget;
        this.isBankrupt = isBankrupt;
        this.contracts = contracts;
    }

    public final int getId() {
        return id;
    }

    public final int getBudget() {
        return budget;
    }

    public final boolean getIsBankrupt() {
        return isBankrupt;
    }

    public final List<Contract> getContracts() {
        return contracts;
    }
}
