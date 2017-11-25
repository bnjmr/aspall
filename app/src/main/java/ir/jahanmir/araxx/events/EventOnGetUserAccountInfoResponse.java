package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.model.Account;

public class EventOnGetUserAccountInfoResponse {
    Account accountInfo;
    public EventOnGetUserAccountInfoResponse(Account accountInfo) {
        this.accountInfo = accountInfo;
    }
    public Account getAccountInfo() {
        return accountInfo;
    }
}
