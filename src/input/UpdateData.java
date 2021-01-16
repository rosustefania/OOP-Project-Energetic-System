package input;

import singletonfactoryobserver.PowerGrid;

import java.util.List;

public class UpdateData {
    /** new consumers' list **/
    private final List<PowerGrid> newConsumers;
    /** costs changes' list **/
    private final List<PowerGrid> distributorChanges;
    /** producer changes' list **/
    private final List<PowerGrid> producerChanges;

    public UpdateData(final List<PowerGrid> newConsumers, final List<PowerGrid> distributorChanges,
                      final List<PowerGrid> producerChanges) {
        this.newConsumers = newConsumers;
        this.distributorChanges = distributorChanges;
        this.producerChanges = producerChanges;
    }

    public final List<PowerGrid> getNewConsumers() {
        return newConsumers;
    }

    public final List<PowerGrid> getDistributorChanges() {
        return distributorChanges;
    }

    public final List<PowerGrid> getProducerChanges() {
        return producerChanges;
    }

    @Override
    public final String toString() {
        return "UpdateData{"
                + "newConsumers=" + newConsumers
                + ", distributorChanges=" + distributorChanges
                + ", producerChanges=" + producerChanges
                + '}';
    }
}
