package ir.jahanmir.aspa.events;


import ir.jahanmir.aspa.gson.LoadBanksResponse;

/**
 * Created by Microsoft on 5/23/2016.
 */
public class EventOnGetBankListResponse {
    LoadBanksResponse[] bankList;
    public EventOnGetBankListResponse(LoadBanksResponse[] response) {
        bankList = response;
    }
    public LoadBanksResponse[] getBankList() {
        return bankList;
    }
}
