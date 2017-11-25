package ir.jahanmir.aspa.events;

import java.util.List;

import ir.jahanmir.aspa.model.Feshfeshe;


/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetLoadFeshFeshesResponse {
    List<Feshfeshe> feshfesheList;
    public EventOnGetLoadFeshFeshesResponse(List<Feshfeshe> feshfesheList) {
        this.feshfesheList = feshfesheList;
    }
    public List<Feshfeshe> getFeshfesheList() {
        return feshfesheList;
    }
}
