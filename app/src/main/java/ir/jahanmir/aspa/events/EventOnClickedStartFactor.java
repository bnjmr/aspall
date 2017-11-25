package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 4/12/2016.
 */
public class EventOnClickedStartFactor {
    long factorId;
    public EventOnClickedStartFactor(long factorId) {
        this.factorId = factorId;
    }
    public long getFactorId() {
        return factorId;
    }
}
