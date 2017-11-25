package ir.jahanmir.araxx.gson;

/**
 * Created by FCC on 10/10/2017.
 */

public class CurrentTrraficSplite {

    boolean Exist;
    boolean EndPackage;
    String EndDateTrafficSplit;
    boolean GoToMainTraffic;
    int CurrentRemain;
    String Message;
    int Result;
    float SumRemainTraffic;

    public float getSumRemainTraffic() {
        return SumRemainTraffic;
    }

    public void setSumRemainTraffic(float sumRemainTraffic) {
        SumRemainTraffic = sumRemainTraffic;
    }

    public boolean isExist() {
        return Exist;
    }

    public void setExist(boolean exist) {
        Exist = exist;
    }

    public boolean isEndPackage() {
        return EndPackage;
    }

    public void setEndPackage(boolean endPackage) {
        EndPackage = endPackage;
    }

    public String getEndDateTrafficSplit() {
        return EndDateTrafficSplit;
    }

    public void setEndDateTrafficSplit(String endDateTrafficSplit) {
        EndDateTrafficSplit = endDateTrafficSplit;
    }

    public boolean isGoToMainTraffic() {
        return GoToMainTraffic;
    }

    public void setGoToMainTraffic(boolean goToMainTraffic) {
        GoToMainTraffic = goToMainTraffic;
    }

    public int getCurrentRemain() {
        return CurrentRemain;
    }

    public void setCurrentRemain(int currentRemain) {
        CurrentRemain = currentRemain;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }
}
