package simulation;

import input.UpdateData;
import singletonfactory.Consumer;
import singletonfactory.Distributor;
import singletonfactory.PowerGrid;
import strategies.Context;
import strategies.GreenStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;

import java.util.ArrayList;
import java.util.List;

import static common.Constants.MAX_PRICE;

public class Simulation {
    /** simulation's number of turns **/
    private final long numberOfTurns;
    /** consumer's list **/
    private final List<PowerGrid> consumers;
    /** distributors' list **/
    private final List<PowerGrid> distributors;
    /** producers' list **/
    private final List<PowerGrid> producers;
    /** monthly updates' list **/
    private final List<UpdateData> monthlyUpdates;

    public Simulation(final long numberOfTurns, final List<PowerGrid> consumers,
                      final List<PowerGrid> distributors, final List<PowerGrid> producers,
                      final List<UpdateData> monthlyUpdates) {
        this.numberOfTurns = numberOfTurns;
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
        this.monthlyUpdates = monthlyUpdates;
    }

    public final long getNumberOfTurns() {
        return numberOfTurns;
    }

    public final List<PowerGrid> getConsumers() {
        return consumers;
    }

    public final List<PowerGrid> getDistributors() {
        return distributors;
    }

    public final List<UpdateData> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    /**
     * method that calculates the contract's price for every distributor
     */
    public void calculateContractsPrices() {
        for (PowerGrid distributor : distributors) {
            ((Distributor) distributor).setContractPrice();
        }
    }

    /**
     * method that calculates the production cost for every distributor
     */
    public void calculateProductionCosts() {
        for (PowerGrid distributor : distributors) {
            ((Distributor) distributor).setProductionCost();
        }
    }

    /**
     * method by which the given consumer choose the best contract
     * @param consumer is the given consumer
     */
    public void chooseContract(final Consumer consumer) {
        double min = MAX_PRICE;
        PowerGrid chosenDistributor = null;

        /* finds the best price */
        for (PowerGrid distributor : distributors) {
            if (!((Distributor) distributor).isBankrupt()) {
                if (((Distributor) distributor).getContractPrice() < min) {
                    min = ((Distributor) distributor).getContractPrice();
                    chosenDistributor = distributor;
                    consumer.setPrice(((Distributor) distributor).getContractPrice());
                    consumer.setDistributor((Distributor) chosenDistributor);
                    consumer.setNumberOfMonths(((Distributor) distributor).getContractLength());
                }
            }
        }

        /* add the given consumer on the chosen distributor's contracts' list */
        assert chosenDistributor != null;
        ((Distributor) chosenDistributor).getContracts().add(new Contract(consumer.getId(),
                ((Distributor) chosenDistributor).getContractPrice(),
                ((Distributor) chosenDistributor).getContractLength()));

    }

    /**
     * method by which the given distributor choose his producers based on a strategy
     * @param distributor is the given distributor
     */
    public void chooseProducers(final Distributor distributor) {
        if (distributor.getProducerStrategy().equalsIgnoreCase("GREEN")) {
            Context context = new Context(new GreenStrategy());
            context.executeStrategy(distributor, producers);
        }

        if (distributor.getProducerStrategy().equalsIgnoreCase("PRICE")) {
            Context context = new Context(new PriceStrategy());
            context.executeStrategy(distributor, producers);
        }

        if (distributor.getProducerStrategy().equalsIgnoreCase("QUANTITY")) {
            Context context = new Context(new QuantityStrategy());
            context.executeStrategy(distributor, producers);
        }
    }

    /**
     * method that does the initial actions based on the initial data
     */
    public void setInitialActions() {
        for (PowerGrid distributor : distributors) {
            chooseProducers((Distributor) distributor);
        }

      /* for (PowerGrid distributor : distributors) {
            //System.out.println(((Distributor) distributor).getChosenProducers());
           System.out.println(((Distributor) distributor).getProductionCost());
        }

       */

        calculateProductionCosts();
        calculateContractsPrices();

        for (PowerGrid consumer : consumers) {
            ((Consumer) consumer).getSalary();
            chooseContract((Consumer) consumer);
            ((Consumer) consumer).payContractPrice();
        }

        for (PowerGrid distributor : distributors) {
            ((Distributor) distributor).payCosts();
        }
    }

    /**
     * method that updates costs for the given distributor
     * @param costsChange represents the given distributor and his costs' updates
     */
    public void doCostsUpdate(final PowerGrid costsChange) {
        for (PowerGrid distributor : distributors) {
            if (distributor.getId() == costsChange.getId()) {
                ((Distributor) distributor)
                        .setInfrastructureCost(((Distributor) costsChange).getInfrastructureCost());
            }
        }
    }

    /**
     * method that does updates for the given month
     * @param update represent given month's updates
     */
    public void doUpdate(final UpdateData update) {
        /* does costs' changes for every distributor */
        for (PowerGrid costsChange : update.getDistributorChanges()) {
            doCostsUpdate(costsChange);
        }

        /* calculates the new contracts' prices */
        calculateContractsPrices();

        /* adds the consumers to the consumers' list */
        consumers.addAll(update.getNewConsumers());

        for (PowerGrid consumer : consumers) {
            if (!((Consumer) consumer).isBankrupt()) {
                /* receives salary */
                ((Consumer) consumer).getSalary();

                /* if the consumer doesn't have an active contract, he chooses one */
                if (((Consumer) consumer).getNumberOfMonths() == 0) {
                    /* if the consumer had another distributor, change him and delete the client
                    from distributor's contracts */
                    if (((Consumer) consumer).getDistributor() != null) {
                        ((Consumer) consumer)
                                .getDistributor().getContracts()
                                .removeIf(contract -> contract.getConsumerId() == consumer.getId());
                        ((Consumer) consumer).setDistributor(null);
                    }
                    chooseContract((Consumer) consumer);
                }

                /* if consumer's distributor got bankrupt, choose another distributor */
                if (((Consumer) consumer).getDistributor().isBankrupt()) {
                    ((Consumer) consumer).setDistributor(null);
                }

                /* pays contract's price */
                ((Consumer) consumer).payContractPrice();
            }
        }

        /* distributors pay month's costs */
        for (PowerGrid distributor : distributors) {
            ((Distributor) distributor).payCosts();

            List<Contract> bankruptConsumers = new ArrayList<>();
            for (Contract contract : ((Distributor) distributor).getContracts()) {
                for (PowerGrid consumer : consumers) {
                    if (consumer.getId() == contract.getConsumerId()) {
                        if (((Consumer) consumer).isBankrupt()) {
                            bankruptConsumers.add(contract);
                        }
                    }
                }
            }
            ((Distributor) distributor).getContracts().removeAll(bankruptConsumers);
        }
    }

    /**
     * method that does the simulation
     */
    public void doSimulation() {
        setInitialActions();

        for (UpdateData monthlyUpdate : monthlyUpdates) {
            doUpdate(monthlyUpdate);
        }
    }
}
