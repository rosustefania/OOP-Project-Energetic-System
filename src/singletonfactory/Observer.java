package singletonfactory;

public interface Observer {
    /**
     * method that updates a distributor when one of his producers changes data
     */
    void update();
}
