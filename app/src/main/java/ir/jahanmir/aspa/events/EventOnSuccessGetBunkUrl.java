package ir.jahanmir.aspa.events;

/**
 * Created by FCC on 11/4/2017.
 */

public class EventOnSuccessGetBunkUrl {
    String URL;
    String bankName;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public EventOnSuccessGetBunkUrl(String URL, String bankName) {

        this.URL = URL;
        this.bankName = bankName;
    }
}
