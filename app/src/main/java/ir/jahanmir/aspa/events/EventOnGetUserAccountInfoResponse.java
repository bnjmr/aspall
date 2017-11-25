package ir.jahanmir.aspa.events;


import ir.jahanmir.aspa.model.Account;

public class EventOnGetUserAccountInfoResponse {
    Account accountInfo;
    public EventOnGetUserAccountInfoResponse(Account accountInfo) {
        this.accountInfo = accountInfo;
    }
    public Account getAccountInfo() {
        return accountInfo;
    }
}
