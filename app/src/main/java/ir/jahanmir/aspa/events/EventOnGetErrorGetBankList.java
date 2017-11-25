package ir.jahanmir.aspa.events;

public class EventOnGetErrorGetBankList {
    int errorType;
    public EventOnGetErrorGetBankList(int errorType) {
        this.errorType = errorType;
    }
    public int getErrorType() {
        return errorType;
    }
}
