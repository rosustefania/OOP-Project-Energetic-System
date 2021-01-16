package output;

import com.fasterxml.jackson.databind.ObjectMapper;
import simulation.Contract;
import singletonfactoryobserver.Consumer;
import singletonfactoryobserver.Distributor;
import singletonfactoryobserver.PowerGrid;
import singletonfactoryobserver.Producer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileWriter {
    /** final consumers' list **/
    private final List<PowerGrid> consumers;
    /** final distributors' list **/
    private final List<PowerGrid> distributors;
    /** final producers' list **/
    private final List<PowerGrid> producers;
    /** output file path **/
    private final String filePath;

    public FileWriter(final List<PowerGrid> consumers, final List<PowerGrid> distributors,
                      final List<PowerGrid> producers, final String filePath) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
        this.filePath = filePath;
    }

    /**
     * writes the result to the output file
     * @throws IOException n case of exceptions to writing
     */
    public void write() throws IOException {
        List<ConsumerOut> consumerOuts = new ArrayList<>();
        List<DistributorOut> distributorOuts = new ArrayList<>();
        List<ProducerOut> producersOut = new ArrayList<>();

        for (PowerGrid consumer : consumers) {
            consumerOuts.add(new ConsumerOut(consumer.getId(),
                    ((Consumer) consumer).isBankrupt(), ((Consumer) consumer).getBudget()));
        }

        for (PowerGrid distributor : distributors) {
            List<Contract> contracts = new ArrayList<>(((Distributor) distributor).getContracts());

            distributorOuts.add(new DistributorOut(distributor.getId(),
                    ((Distributor) distributor).getEnergyNeededKW(),
                    ((Distributor) distributor).getContractPrice(),
                    ((Distributor) distributor).getBudget(),
                    ((Distributor) distributor).getProducerStrategy(),
                    ((Distributor) distributor).isBankrupt(),
                    contracts));
        }

        for (PowerGrid producer : producers) {
            List<MonthlyStat> monthlyStats;
            monthlyStats = new ArrayList<>(((Producer) producer)
                    .getMonthlyStats());

            producersOut.add(new ProducerOut(producer.getId(),
                    ((Producer) producer).getMaxDistributors(), ((Producer) producer).getPriceKW(),
                    ((Producer) producer).getEnergyType(),
                    ((Producer) producer).getEnergyPerDistributor(), monthlyStats));

        }

        Result result = new Result(consumerOuts, distributorOuts, producersOut);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), result);
    }



}
