package ir.jahanmir.aspa.gson;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class GetPollResponse {
    public int Result;
    public long code;
    public String Question = "";
    public String OptionID  = "";
    public String OptionText = "";
    public String Message = "";

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getOptionID() {
        return OptionID;
    }

    public void setOptionID(String optionID) {
        OptionID = optionID;
    }

    public String getOptionText() {
        return OptionText;
    }

    public void setOptionText(String optionText) {
        OptionText = optionText;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
