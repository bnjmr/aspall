package ir.jahanmir.aspa.events;

public class EventOnGetErrorGetFactorDetail {
    int errorType;
    long factorCode;
    public EventOnGetErrorGetFactorDetail(int errorType,long factorCode) {
        this.errorType = errorType;
        this.factorCode = factorCode;
    }
    public long getFactorCode() {
        return factorCode;
    }
    public int getErrorType() {
        return errorType;
    }
}
