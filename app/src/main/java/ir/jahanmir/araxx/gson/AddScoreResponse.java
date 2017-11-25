package ir.jahanmir.araxx.gson;

/**
 * Created by HaMiD on 3/6/2017.
 */

public class AddScoreResponse {
    int Result;
    int Err;
    String Name;

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public void setErr(int err) {
        Err = err;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    String Message;

    public int isResult() {
        return Result;
    }

    public int getErr() {
        return Err;
    }

    public String getName() {
        return Name;
    }
}
