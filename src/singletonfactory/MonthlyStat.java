package singletonfactory;

import java.util.ArrayList;
import java.util.List;

public class MonthlyStat {
    private final int month;

    private List<Integer> distributorsIds;

    public MonthlyStat(int month) {
        this.month = month;
        this.distributorsIds = new ArrayList<>();
    }

    public final List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public final void setDistributorsIds(final List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }

    public final int getMonth() {
        return month;
    }

    @Override
    public final String toString() {
        return "MonthlyStat{"
                + "monthNumber=" + month
                + ", distributorsIds=" + distributorsIds
                + '}';
    }
}
