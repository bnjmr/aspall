package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.enums.EnumYesNoKind;

/**
 * Created by FCC on 10/26/2017.
 */

public class EventOnClickedYesOnYesNoDialog {

    EnumYesNoKind yesNoKind;
    String date;

    public EnumYesNoKind getYesNoKind() {
        return yesNoKind;
    }

    public void setYesNoKind(EnumYesNoKind yesNoKind) {
        this.yesNoKind = yesNoKind;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public EventOnClickedYesOnYesNoDialog(EnumYesNoKind yesNoKind, String date) {

        this.date = date;
        this.yesNoKind = yesNoKind;
    }
}
