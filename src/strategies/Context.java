package strategies;

import singletonfactoryobserver.Distributor;
import singletonfactoryobserver.PowerGrid;

import java.util.List;

public class Context {
    private final ProducerStrategy strategy;

    public Context(ProducerStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * method that applies the given strategy
     * @param distributor represents distributors' list
     * @param producers represents producers' list
     */
    public void executeStrategy(Distributor distributor, List<PowerGrid> producers) {
        strategy.applyStrategy(distributor, producers);
    }
}
