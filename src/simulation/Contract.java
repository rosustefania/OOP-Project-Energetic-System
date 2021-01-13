package simulation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contract {
    /** consumer's id **/
    private final int consumerId;
    /** contract's price **/
    private final int price;
    /** number of remained months **/
    private int remainedMonths;

    public Contract(final int consumerId, final int price, final int remainedMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedMonths = remainedMonths;
    }

    public final int getConsumerId() {
        return consumerId;
    }

    public final int getPrice() {
        return price;
    }

    @JsonProperty("remainedContractMonths")
    public final int getRemainedMonths() {
      return remainedMonths;
   }

    public final void setRemainedMonths(final int remainedMonths) {
        this.remainedMonths = remainedMonths;
    }

}
