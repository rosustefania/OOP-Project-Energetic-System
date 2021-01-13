package strategies;

import singletonfactory.Distributor;
import singletonfactory.PowerGrid;

import java.util.List;

public class Context {
    private ProducerStrategy strategy;

    public Context(ProducerStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(Distributor distributor, List<PowerGrid> producers) {
        strategy.applyStrategy(distributor, producers);
    }
}
