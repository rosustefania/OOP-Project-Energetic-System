package strategies;

import singletonfactoryobserver.Distributor;
import singletonfactoryobserver.PowerGrid;
import singletonfactoryobserver.Producer;

import java.util.Comparator;
import java.util.List;

public class QuantityStrategy implements ProducerStrategy {
    /**
     * method that applies the QUANTITY strategy
     */
    @Override
    @SuppressWarnings("unchecked")
    public void applyStrategy(Distributor distributor, List<PowerGrid> producers) {
        int neededEnergy = distributor.getEnergyNeededKW();
        List<Producer> sortedProducers = (List<Producer>) (List<?>) producers;

        //sorts producers by the monthly amount of energy per distributor;
        sortedProducers.sort(Comparator.comparing(Producer::getEnergyPerDistributor,
                Comparator.reverseOrder()));

        // chooses producers based on their amount of energy;
        for (Producer sortedProducer : sortedProducers) {
            if (neededEnergy > 0) {
                if (sortedProducer.getDistributorsList().size() < sortedProducer
                        .getMaxDistributors()) {

                    distributor.getChosenProducers().add(sortedProducer);
                    neededEnergy -= sortedProducer.getEnergyPerDistributor();
                    sortedProducer.getDistributorsList().add(distributor);
                }
            } else {
                break;
            }
        }
    }
}
