package ir.jahanmir.araxx.events;

/**
 * Created by FCC on 11/12/2017.
 */

public class EventOnRegisterPoll {

    String pollCode;

    String optionId;

    String des;

    public EventOnRegisterPoll(String pollCode, String optionId, String des) {
        this.pollCode = pollCode;
        this.optionId = optionId;
        this.des = des;
    }

    public String getPollCode() {
        return pollCode;
    }

    public void setPollCode(String pollCode) {
        this.pollCode = pollCode;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
