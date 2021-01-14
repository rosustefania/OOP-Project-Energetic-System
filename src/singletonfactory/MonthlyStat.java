package singletonfactory;

import java.util.ArrayList;
import java.util.List;

public class MonthlyStat {
    private final int monthNumber;

    private List<Integer> distributorsIds;

    public MonthlyStat(int monthNumber) {
        this.monthNumber = monthNumber;
        this.distributorsIds = new ArrayList<>();
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    @Override
    public String toString() {
        return "MonthlyStat{" +
                "monthNumber=" + monthNumber +
                ", distributorsIds=" + distributorsIds +
                '}';
    }
}
