import input.Input;
import input.InputLoader;
import input.UpdateData;
import output.FileWriter;
import simulation.Simulation;
import singletonfactoryobserver.PowerGrid;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class Main {
    private Main() { }

    /**
     * @param args input and output paths
     * @throws Exception in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws Exception {
        File out = new File(args[1]);
        boolean isCreated = out.createNewFile();
        if (isCreated) {
            update(args[0], args[1]);
        }
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void update(final String filePath1, final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        long numberOfTurns = input.getNumberOfTurns();
        List<PowerGrid> consumers = input.getConsumers();
        List<PowerGrid> distributors = input.getDistributors();
        List<PowerGrid> producers = input.getProducers();
        List<UpdateData> monthlyUpdates = input.getMonthlyUpdates();

        Simulation simulation = new Simulation(numberOfTurns, consumers, distributors, producers,
                monthlyUpdates);
        simulation.doSimulation();

        FileWriter writer = new FileWriter(consumers, distributors, producers, filePath2);
        writer.write();

    }
}
