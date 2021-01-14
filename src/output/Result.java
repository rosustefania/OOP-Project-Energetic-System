package output;

import java.util.List;

public class Result {
    private final List<ConsumerOut> consumers;

    private final List<DistributorOut> distributors;

    private final List<ProducerOut> producers;

    public Result(final List<ConsumerOut> consumers, final List<DistributorOut> distributors,
                  final List<ProducerOut> producers) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
    }

    public final List<ConsumerOut> getConsumers() {
        return consumers;
    }

    public final List<DistributorOut> getDistributors() {
        return distributors;
    }

    public List<ProducerOut> getProducers() {
        return producers;
    }
}
