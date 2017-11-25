package ir.jahanmir.aspa.events;

public class EventOnGetErrorPayFactorFromCredit {
    int errorType;
    public EventOnGetErrorPayFactorFromCredit(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
