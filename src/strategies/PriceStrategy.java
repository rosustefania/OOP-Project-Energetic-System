package strategies;

import singletonfactory.Distributor;
import singletonfactory.PowerGrid;
import singletonfactory.Producer;

import java.util.Comparator;
import java.util.List;

public class PriceStrategy implements ProducerStrategy {
    /**
     * method that applies the PRICE strategy
     */
    @Override
    @SuppressWarnings("unchecked")
    public void applyStrategy(Distributor distributor, List<PowerGrid> producers) {
        int neededEnergy = distributor.getEnergyNeededKW();
        List<Producer> sortedProducers = (List<Producer>) (List<?>) producers;

        //sorts producers by the price per KW and then by the amount of energy per distributor;
        sortedProducers.sort(Comparator.comparing(Producer::getPriceKW)
                .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder()));

        // chooses producers based on their price;
        for (Producer sortedProducer : sortedProducers) {
            if (neededEnergy > 0) {
                if (sortedProducer.getDistributorsList().size() < sortedProducer
                        .getMaxDistributors()) {
                    distributor.getChosenProducers().add(sortedProducer);
                    neededEnergy -= sortedProducer.getEnergyPerDistributor();

                    //add distributor to producer's distributors list;
                    sortedProducer.getDistributorsList().add(distributor);
                }
            } else {
                break;
            }
        }
    }
}
