package singletonfactoryobserver;

public final class EntityFactory {
    /** singleton factory instance **/
    private static EntityFactory entityInstance;

    private EntityFactory() { }

    /**
     * gets only one entity instance (Singleton)
     * @return an entity instance
     */
    public static EntityFactory getEntityInstance() {
        if (entityInstance == null) {
            entityInstance = new EntityFactory();
        }
        return entityInstance;
    }

    /**
     * factory method
     * @return consumer/distributor object
     */
    public PowerGrid createEntity(final String type, final int id, final int contractLength,
                                  final int initialBudget, final int monthlyIncome,
                                  final int initialInfrastructureCost,
                                  final int energyNeededKW, final String producerStrategy,
                                  final String energyType, final int maxDistributors,
                                  final double priceKW, int energyPerDistributor) {
        if (type.equalsIgnoreCase("consumer")) {
            return new Consumer(id, initialBudget, monthlyIncome);
        }

        if (type.equalsIgnoreCase("distributor")) {
            return new Distributor(id, contractLength, initialBudget, initialInfrastructureCost,
                    energyNeededKW, producerStrategy);
        }

        if (type.equalsIgnoreCase("producer")) {
            return new Producer(id, energyType, maxDistributors, priceKW, energyPerDistributor);
        }
        return null;

    }

}
