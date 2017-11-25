package ir.jahanmir.aspa.model;


import ir.jahanmir.aspa.enums.EnumYesNoKind;

/**
 * Created by FCC on 10/26/2017.
 */

public class ModelYesNoDialog {

    String title;
    String body;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ModelYesNoDialog(String title, String body, String data, EnumYesNoKind yesNoKind) {

        this.title = title;
        this.body = body;
        this.data = data;
        this.yesNoKind = yesNoKind;
    }

    String data;
    EnumYesNoKind yesNoKind;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EnumYesNoKind getYesNoKind() {
        return yesNoKind;
    }

    public void setYesNoKind(EnumYesNoKind yesNoKind) {
        this.yesNoKind = yesNoKind;
    }

}
