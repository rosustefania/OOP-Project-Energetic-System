package input;

import common.Constants;
import singletonfactoryobserver.Consumer;
import singletonfactoryobserver.Distributor;
import singletonfactoryobserver.EntityFactory;
import singletonfactoryobserver.PowerGrid;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import singletonfactoryobserver.Producer;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputLoader {
    /** input path **/
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * reads initial data
     * @return initial data
     */
    public Input readData() {
        JSONParser jsonParser = new JSONParser();

        EntityFactory factory = EntityFactory.getEntityInstance();

        long numberOfTurns = 0;
        List<PowerGrid> consumers = new ArrayList<>();
        List<PowerGrid> distributors = new ArrayList<>();
        List<PowerGrid> producers = new ArrayList<>();
        List<UpdateData> updates = null;

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(inputPath));
            JSONObject initialData = (JSONObject) jsonObject.get(Constants.INITIALDATA);
            numberOfTurns = (long) jsonObject.get(Constants.NUMBEROFTURNS);
            JSONArray jsonConsumers = (JSONArray) initialData.get(Constants.CONSUMERS);
            JSONArray jsonDistributors = (JSONArray) initialData.get(Constants.DISTRIBUTORS);
            JSONArray jsonProducers = (JSONArray) initialData.get(Constants.PRODUCERS);

            /* reads consumers' list */
            if (jsonConsumers != null) {
                for (Object jsonConsumer : jsonConsumers) {
                    Consumer consumer = (Consumer) factory.createEntity("consumer",
                            ((Long) ((JSONObject) jsonConsumer).get(Constants.ID))
                                    .intValue(), 0,
                            ((Long) ((JSONObject) jsonConsumer).get(Constants.INITIALBUDGET))
                                    .intValue(),
                            ((Long) ((JSONObject) jsonConsumer).get(Constants.MONTHLYINCOME))
                                    .intValue(), 0, 0,
                            null,  null, 0, 0.00,
                            0);

                    consumers.add(consumer);
                }
            }

            /* reads distributors' list */
            if (jsonDistributors != null) {
                for (Object jsonDistributor : jsonDistributors) {
                    Distributor distributor = (Distributor) factory.createEntity("distributor",
                            ((Long) ((JSONObject) jsonDistributor).get(Constants.ID))
                                    .intValue(),
                            ((Long) ((JSONObject) jsonDistributor).get(Constants.CONTRACTLENGTH))
                                    .intValue(),
                            ((Long) ((JSONObject) jsonDistributor).get(Constants.INITIALBUDGET))
                                    .intValue(), 0,
                            ((Long) ((JSONObject) jsonDistributor)
                                    .get(Constants.INITIALINFRASTRUCTURECOST)).intValue(),
                            ((Long) ((JSONObject) jsonDistributor)
                                    .get(Constants.ENERGYNEEDEDKW)).intValue(),
                            (((JSONObject) jsonDistributor).get(Constants.PRODUCERSTRATEGY))
                                    .toString(),
                            null, 0, 0.00, 0);

                    distributors.add(distributor);
                }
            }

            /* reads producers' list */
            if (jsonProducers != null) {
                for (Object jsonProducer : jsonProducers) {
                    Producer producer = (Producer) factory.createEntity("producer",
                            ((Long) ((JSONObject) jsonProducer).get(Constants.ID))
                                    .intValue(), 0, 0, 0,
                            0, 0, null,
                            (((JSONObject) jsonProducer).get(Constants.ENERGYTYPE)).toString(),
                            ((Long) ((JSONObject) jsonProducer).get(Constants.MAXDISTRIBUTORS))
                                    .intValue(),
                            (Double) ((JSONObject) jsonProducer).get(Constants.PRICEKW),
                            ((Long) ((JSONObject) jsonProducer).get(Constants.ENERGYPERDISTRIBUTOR))
                                    .intValue());

                    producers.add(producer);
                }
            }

            if (jsonConsumers == null) {
                consumers = null;
            }

            if (jsonDistributors == null) {
                distributors = null;
            }

            if (jsonProducers == null) {
                producers = null;
            }

            updates = readUpdates(jsonObject, factory);


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return new Input(numberOfTurns, consumers, distributors, producers, updates);

    }

    /**
     * The method reads the actions from input file
     * @param jsonObject given object
     * @return A list of actions
     */
    public List<UpdateData> readUpdates(final JSONObject jsonObject, final EntityFactory factory) {
        List<UpdateData> monthlyUpdates = new ArrayList<>();
        JSONArray jsonMonthlyUpdates = (JSONArray) jsonObject.get(Constants.MONTHLYUPDATES);

        /* reads updates */
        if (jsonMonthlyUpdates != null) {
            for (Object jsonUpdate : jsonMonthlyUpdates) {
                JSONArray jsonNewConsumers = (JSONArray) ((JSONObject) jsonUpdate)
                        .get(Constants.NEWCONSUMERS);
                JSONArray jsonDistributorChanges = (JSONArray) ((JSONObject) jsonUpdate)
                        .get(Constants.DISTRIBUTORCHANGES);
                JSONArray jsonProducerChanges = (JSONArray) ((JSONObject) jsonUpdate)
                        .get(Constants.PRODUCERCHANGES);
                List<PowerGrid> newConsumers = new ArrayList<>();
                List<PowerGrid> distributorChanges = new ArrayList<>();
                List<PowerGrid> producerChanges = new ArrayList<>();

                /* reads new consumers' list */
                if (jsonNewConsumers != null) {
                    for (Object jsonNewConsumer : jsonNewConsumers) {
                        Consumer newConsumer = (Consumer) factory.createEntity("consumer",
                                ((Long) ((JSONObject) jsonNewConsumer).get(Constants.ID))
                                        .intValue(), 0,
                                ((Long) ((JSONObject) jsonNewConsumer).get(Constants.INITIALBUDGET))
                                        .intValue(),
                                ((Long) ((JSONObject) jsonNewConsumer).get(Constants.MONTHLYINCOME))
                                        .intValue(), 0, 0,
                                null, null, 0, 0.00,
                                0);

                        newConsumers.add(newConsumer);
                    }

                }

                /* reads distributor changes' list */
                if (jsonDistributorChanges != null) {
                    for (Object jsonDistributorChange : jsonDistributorChanges) {
                        Distributor distributorChange = (Distributor) factory
                                .createEntity("distributor",
                                        ((Long) ((JSONObject) jsonDistributorChange)
                                                .get(Constants.ID)).intValue(), 0,
                                        0, 0,
                                        ((Long) ((JSONObject) jsonDistributorChange)
                                                .get(Constants.INFRASTRUCTURECOST)).intValue(),
                                        0, null, null,
                                        0, 0.00, 0);

                        distributorChanges.add(distributorChange);

                    }
                }

                /* reads producer changes' list */
                if (jsonProducerChanges != null) {
                    for (Object jsonProducerChange : jsonProducerChanges) {
                        Producer producerChange = (Producer) factory
                                .createEntity("producer",
                                        ((Long) ((JSONObject) jsonProducerChange)
                                                .get(Constants.ID)).intValue(), 0,
                                        0, 0, 0,
                                        0, null, null,
                                        0, 0.00,
                                        ((Long) ((JSONObject) jsonProducerChange)
                                                .get(Constants.ENERGYPERDISTRIBUTOR)).intValue());

                        producerChanges.add(producerChange);

                    }
                }


                if (jsonNewConsumers == null) {
                    newConsumers = null;
                }

                if (jsonDistributorChanges == null) {
                    distributorChanges = null;
                }

                if (jsonProducerChanges == null) {
                    producerChanges = null;
                }

                UpdateData monthlyUpdate = new UpdateData(newConsumers, distributorChanges,
                        producerChanges);

                monthlyUpdates.add(monthlyUpdate);
            }
        }
        return monthlyUpdates;
    }

}
