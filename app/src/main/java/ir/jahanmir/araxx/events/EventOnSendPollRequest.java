package ir.jahanmir.araxx.events;

public class EventOnSendPollRequest {
    long pollId;
    String des = "";
    String optionId = "0";
    public EventOnSendPollRequest(long pollId, String des, String optionId) {
        this.pollId = pollId;
        this.des = des;
        this.optionId = optionId;
    }
    public long getPollId() {
        return pollId;
    }
    public String getDes() {
        return des;
    }
    public String getOptionId() {
        return optionId;
    }
}
