package output;

public class ConsumerOut {
    private final int id;

    private final boolean isBankrupt;

    private final int budget;

    public ConsumerOut(final int id, final boolean isBankrupt, final int budget) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
    }

    public final int getId() {
        return id;
    }

    public final boolean getIsBankrupt() {
        return isBankrupt;
    }

    public final int getBudget() {
        return budget;
    }
}
