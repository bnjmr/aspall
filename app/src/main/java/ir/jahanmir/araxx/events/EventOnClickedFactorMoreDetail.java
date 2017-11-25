package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 3/16/2016.
 */
public class EventOnClickedFactorMoreDetail {
    long factorCode;
    public EventOnClickedFactorMoreDetail(long factorCode) {
        this.factorCode = factorCode;
    }
    public long getFactorCode() {
        return factorCode;
    }
}
