package ir.jahanmir.araxx.events;

import java.util.List;

import ir.jahanmir.araxx.model.Feshfeshe;


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
