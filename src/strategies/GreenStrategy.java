package strategies;

import singletonfactory.Distributor;
import singletonfactory.PowerGrid;
import singletonfactory.Producer;

import java.util.Comparator;
import java.util.List;

public class GreenStrategy implements ProducerStrategy {
    /**
     * method that applies the GREEN strategy
     */
    @Override
    @SuppressWarnings("unchecked")
    public void applyStrategy(final Distributor distributor, final List<PowerGrid> producers) {
        int neededEnergy = distributor.getEnergyNeededKW();
        List<Producer> sortedProducers = (List<Producer>) (List<?>) producers;

        //sorts producers by the price per KW and then by the amount of energy per distributor;
        sortedProducers.sort(Comparator.comparing(Producer::getPriceKW)
                .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder()));

        // chooses producers based on their energy type;
        // first looks for renewable energy;
        for (Producer sortedProducer : sortedProducers) {
            if (neededEnergy > 0) {
                if ((sortedProducer.getDistributorsList().size() < sortedProducer
                        .getMaxDistributors())
                         && (sortedProducer.getEnergyType().equalsIgnoreCase("WIND")
                         || sortedProducer.getEnergyType().equalsIgnoreCase("SOLAR")
                         || sortedProducer.getEnergyType().equalsIgnoreCase("HYDRO"))) {

                    distributor.getChosenProducers().add(sortedProducer);
                    neededEnergy -= sortedProducer.getEnergyPerDistributor();

                    //add distributor to producer's distributors list;
                    sortedProducer.getDistributorsList().add(distributor);
                }
            } else {
                break;
            }
        }

        // then looks for the other types of energy;
        for (Producer sortedProducer : sortedProducers) {
            if (neededEnergy > 0) {
                if ((sortedProducer.getDistributorsList().size() < sortedProducer
                        .getMaxDistributors())
                        && (sortedProducer.getEnergyType().equalsIgnoreCase("COAL")
                        || sortedProducer.getEnergyType().equalsIgnoreCase("NUCLEAR"))) {

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

