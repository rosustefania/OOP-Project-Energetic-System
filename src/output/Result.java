package output;

import java.util.List;

public class Result {
    private final List<ConsumerOut> consumers;

    private final List<DistributorOut> distributors;

    private final List<ProducerOut> energyProducers;

    public Result(final List<ConsumerOut> consumers, final List<DistributorOut> distributors,
                  final List<ProducerOut> energyProducers) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.energyProducers = energyProducers;
    }

    public final List<ConsumerOut> getConsumers() {
        return consumers;
    }

    public final List<DistributorOut> getDistributors() {
        return distributors;
    }

    public final List<ProducerOut> getEnergyProducers() {
        return energyProducers;
    }
}
