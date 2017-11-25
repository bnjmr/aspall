package ir.jahanmir.aspa.events;


import ir.jahanmir.aspa.gson.UserInfoResponse;

/**
 * Created by Microsoft on 3/7/2016.
 */
public class EventOnGetUserInfoResponse {
    UserInfoResponse userInfo;
    public EventOnGetUserInfoResponse(UserInfoResponse userInfo) {
        this.userInfo = userInfo;
    }
    public UserInfoResponse getUserInfo() {
        return userInfo;
    }
}
