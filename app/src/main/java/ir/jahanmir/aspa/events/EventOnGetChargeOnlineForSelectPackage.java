package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 3/29/2016.
 */
public class EventOnGetChargeOnlineForSelectPackage {
    int result;
    String message = "";
    long factorCode;
    public EventOnGetChargeOnlineForSelectPackage(int result, String message, long factorCode) {
        this.result = result;
        this.message = message;
        this.factorCode = factorCode;
    }
    public long getFactorCode() {
        return factorCode;
    }
    public String getMessage() {
        return message;
    }
    public int isResult() {
        return result;
    }
}
