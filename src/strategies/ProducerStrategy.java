package strategies;

import singletonfactory.Distributor;
import singletonfactory.PowerGrid;

import java.util.List;

public interface ProducerStrategy {
    /**
     * method that applies the given strategy
     */
    void applyStrategy(Distributor distributor, List<PowerGrid> producers);
}
