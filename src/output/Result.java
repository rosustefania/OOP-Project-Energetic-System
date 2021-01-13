package output;

import java.util.List;

public class Result {
    private final List<ConsumerOut> consumers;

    private final List<DistributorOut> distributors;

    public Result(final List<ConsumerOut> consumers,
                  final List<DistributorOut> distributors) {
        this.consumers = consumers;
        this.distributors = distributors;
    }

    public final List<ConsumerOut> getConsumers() {
        return consumers;
    }

    public final List<DistributorOut> getDistributors() {
        return distributors;
    }
}
