package ir.jahanmir.araxx.events;

/**
 * Created by Microsoft on 5/23/2016.
 */
public class EventOnBankSelected {
    int code;
    String bankName;

    public EventOnBankSelected(int code, String bankName) {
        this.code = code;
        this.bankName = bankName;
    }

    public int getCode() {
        return code;
    }

    public String getBankName() {
        return bankName;
    }

}
