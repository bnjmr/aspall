package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 4/2/2016.
 */
public class EventOnClickedStartFeshfeshe {
    long feshfesheCode;
    public EventOnClickedStartFeshfeshe(long code) {
        this.feshfesheCode = code;
    }
    public long getFeshfesheCode() {
        return feshfesheCode;
    }
}
