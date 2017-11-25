package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 5/23/2016.
 */
public class EventOnGetBankPageResponse {
    String bankUrl = "";
    public EventOnGetBankPageResponse(String url) {
        this.bankUrl = url;
    }
    public String getBankUrl() {
        return bankUrl;
    }
}
