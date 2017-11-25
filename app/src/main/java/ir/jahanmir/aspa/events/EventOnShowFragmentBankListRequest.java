package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 5/23/2016.
 */
public class EventOnShowFragmentBankListRequest {
    long factorCode;
    int money;
    public EventOnShowFragmentBankListRequest(long factorCode,int money) {
        this.factorCode = factorCode;
        this.money = money;
    }
    public int getMoney() {
        return money;
    }
    public long getFactorCode() {
        return factorCode;
    }
}
