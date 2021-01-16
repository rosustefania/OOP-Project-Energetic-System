package strategies;

import singletonfactoryobserver.Distributor;
import singletonfactoryobserver.PowerGrid;

import java.util.List;

public interface ProducerStrategy {
    /**
     * method that applies the given strategy
     */
    void applyStrategy(Distributor distributor, List<PowerGrid> producers);
}
