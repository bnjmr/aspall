package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 4/11/2016.
 */
public class EventOnGetCheckOrderIdResultResponse {
    long factorCode;
    String message = "";
    boolean result;

    public EventOnGetCheckOrderIdResultResponse(boolean result, String message, long factorCode) {
        this.message = message;
        this.factorCode = factorCode;
        this.result = result;
    }
    public long getFactorCode() {
        return factorCode;
    }
    public String getMessage() {
        return message;
    }
    public boolean isResult() {
        return result;
    }
}
