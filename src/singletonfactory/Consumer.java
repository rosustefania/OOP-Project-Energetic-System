package singletonfactory;

import common.Constants;
import simulation.Contract;

public class Consumer extends PowerGrid {
    /** consumer's id **/
    private final int id;
    /** consumer's budget **/
    private int budget;
    /** consumer's monthly income **/
    private final int monthlyIncome;
    /** consumer's chosen distributor **/
    private Distributor distributor;
    /** consumer's penalty **/
    private double penalty;
    /** for verifying if the consumer is bankrupt **/
    private boolean isBankrupt;
    /** the number of months remaining in the current contract **/
    private int numberOfMonths;
    /** consumer's monthly rate **/
    private double price;

    public Consumer(final int id, final int budget, final int monthlyIncome) {
        this.id = id;
        this.budget = budget;
        this.monthlyIncome = monthlyIncome;
        this.distributor = null;
        this.penalty = 0.0;
        this.isBankrupt = false;
        this.numberOfMonths = 0;
        this.price = 0.0;
    }

    @Override
    public final int getId() {
        return this.id;
    }

    public final int getBudget() {
        return this.budget;
    }

    public final void setBudget(final int budget) {
        this.budget = budget;
    }

    public final int getMonthlyIncome() {
        return monthlyIncome;
    }

    public final Distributor getDistributor() {
        return distributor;
    }

    public final void setDistributor(final Distributor distributor) {
        this.distributor = distributor;
    }

    public final boolean isBankrupt() {
        return isBankrupt;
    }

    public final void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public final int getNumberOfMonths() {
        return numberOfMonths;
    }

    public final void setNumberOfMonths(final int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }

    public final double getPrice() {
        return price;
    }

    public final void setPrice(final double price) {
        this.price = price;
    }

    /**
     * method by which the consumer receives his monthly income
     */
    public final void getSalary() {
        budget += monthlyIncome;
    }

    /**
     * method by which the consumer pays the contract's monthly price
     */
    public final void payContractPrice() {
        /* verifies if the consumer already has a penalty */
        if (penalty == 0.0) {
            /* if not, then verifies if he has enough money to pay this month **/
            if (budget < price) {
                penalty = price;
            } else {
                budget -= price;
                distributor.setBudget((int) (distributor.getBudget() + price));
            }
            for (Contract contract : distributor.getContracts()) {
                if (contract.getConsumerId() == id) {
                    contract.setRemainedMonths(contract.getRemainedMonths() - 1);
                    numberOfMonths = contract.getRemainedMonths();
                }
            }
        } else {
            double bill;
            boolean newDistributor = false;

            /* if the consumer has a penalty then verifies if he has changed his distributor */
            if (penalty != price) {
                bill = Math.round(Math.floor(Constants.PENALTY_PERCENTAGE * penalty));
                newDistributor = true;
            } else {
                bill = Math.round(Math.floor(Constants.PENALTY_PERCENTAGE * penalty)) + price;
            }

            /* verifies if he has enough money to pay the bill */
            if (budget < bill) {
                isBankrupt = true;
            } else {
                budget -= bill;

                if (newDistributor) {
                    penalty = price;
                } else {
                    penalty = 0.0;
                }

                for (Contract contract : distributor.getContracts()) {
                    if (contract.getConsumerId() == id) {
                        contract.setRemainedMonths(contract.getRemainedMonths() - 1);
                        numberOfMonths = contract.getRemainedMonths();
                    }
                }
            }
        }
    }

    @Override
    public final String toString() {
        return "Consumer{"
                + "id=" + id
                + ", budget=" + budget
                + ", monthlyIncome=" + monthlyIncome
                + "}";
    }
}
