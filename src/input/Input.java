package input;

import singletonfactoryobserver.PowerGrid;

import java.util.List;

public class Input {
    /** number of rounds **/
    private final long numberOfTurns;
    /** consumers' list **/
    private final List<PowerGrid> consumers;
    /** distributors' list **/
    private final List<PowerGrid> distributors;
    /** priducers' list **/
    private final List<PowerGrid> producers;
    /** updates' list **/
    private final List<UpdateData> monthlyUpdates;

    public Input(final long numberOfTurns, final List<PowerGrid> consumers,
                 final List<PowerGrid> distributors, final List<PowerGrid> producers,
                 final List<UpdateData> monthlyUpdates) {
        this.numberOfTurns = numberOfTurns;
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
        this.monthlyUpdates = monthlyUpdates;
    }

    public final long getNumberOfTurns() {
        return numberOfTurns;
    }

    public final List<PowerGrid> getConsumers() {
        return consumers;
    }

    public final List<PowerGrid> getDistributors() {
        return distributors;
    }

    public final List<PowerGrid> getProducers() {
        return producers; }

    public final List<UpdateData> getMonthlyUpdates() {
        return monthlyUpdates;
    }
}
