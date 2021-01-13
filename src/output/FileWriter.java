package output;

import com.fasterxml.jackson.databind.ObjectMapper;
import simulation.Contract;
import singletonfactory.Consumer;
import singletonfactory.Distributor;
import singletonfactory.PowerGrid;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileWriter {
    /** final consumers' list **/
    private final List<PowerGrid> consumers;
    /** final distributors' list **/
    private final List<PowerGrid> distributors;
    /** output file path **/
    private final String filePath;

    public FileWriter(final List<PowerGrid> consumers, final List<PowerGrid> distributors,
                  final String filePath) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.filePath = filePath;
    }

    /**
     * writes the result to the output file
     * @throws IOException n case of exceptions to writing
     */
    public void write() throws IOException {
        List<ConsumerOut> consumerOuts = new ArrayList<>();
        List<DistributorOut> distributorOuts = new ArrayList<>();

        for (PowerGrid consumer : consumers) {
            consumerOuts.add(new ConsumerOut(((Consumer) consumer).getId(),
                    ((Consumer) consumer).isBankrupt(), ((Consumer) consumer).getBudget()));
        }

        for (PowerGrid distributor : distributors) {

            List<Contract> contracts = new ArrayList<>(((Distributor) distributor).getContracts());

            distributorOuts.add(new DistributorOut(((Distributor) distributor).getId(),
                    ((Distributor) distributor).getBudget(),
                    ((Distributor) distributor).isBankrupt(),
                    contracts));
        }

        Result result = new Result(consumerOuts, distributorOuts);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), result);
    }



}
