package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.gson.LoginResponse;

/**
 * Created by Microsoft on 3/5/2016.
 */
public class EventOnGetLoginResponse {
    LoginResponse login;
    long ispId;
    String ispUrl = "";

    public EventOnGetLoginResponse(LoginResponse login, long ispId, String ispUrl) {
        this.login = login;
        this.ispId = ispId;
        this.ispUrl = ispUrl;
    }
    public LoginResponse getLogin() {
        return login;
    }
    public long getIspId() { return ispId; }
    public String getIspUrl() { return ispUrl; }

}
