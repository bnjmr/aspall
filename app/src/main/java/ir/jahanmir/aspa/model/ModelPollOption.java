package ir.jahanmir.aspa.model;

/**
 * Created by FCC on 11/12/2017.
 */

public class ModelPollOption {

    String OptionID;
    String OptionText;
    boolean isSelected = false;
    String pollCode;

    public String getPollCode() {
        return pollCode;
    }

    public void setPollCode(String pollCode) {
        this.pollCode = pollCode;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
}
