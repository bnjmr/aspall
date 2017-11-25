package ir.jahanmir.aspa.events;


import ir.jahanmir.aspa.model.ModelYesNoDialog;

/**
 * Created by FCC on 10/26/2017.
 */

public class EventOnYesNoDialogShow {

    ModelYesNoDialog modelYesNoDialog;

    public EventOnYesNoDialogShow(ModelYesNoDialog modelYesNoDialog) {
        this.modelYesNoDialog = modelYesNoDialog;
    }

    public ModelYesNoDialog getModelYesNoDialog() {

        return modelYesNoDialog;
    }

    public void setModelYesNoDialog(ModelYesNoDialog modelYesNoDialog) {
        this.modelYesNoDialog = modelYesNoDialog;
    }
}
