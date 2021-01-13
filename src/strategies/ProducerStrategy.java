package strategies;

import singletonfactory.Distributor;
import singletonfactory.PowerGrid;

import java.util.List;

public interface ProducerStrategy {
    void applyStrategy(Distributor distributor, List<PowerGrid> producers);
}
