package ir.jahanmir.aspa.model;

/**
 * Created by FCC on 10/25/2017.
 */

public class getUpdate {

    float Ver;
    String Url;
    boolean Force;
    String Des;
    String Message;
    int Result;


    public float getVer() {
        return Ver;
    }

    public void setVer(float ver) {
        Ver = ver;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public boolean isForce() {
        return Force;
    }

    public void setForce(boolean force) {
        Force = force;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
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
