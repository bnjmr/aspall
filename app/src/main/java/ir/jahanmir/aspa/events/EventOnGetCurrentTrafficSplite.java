package ir.jahanmir.aspa.events;


import ir.jahanmir.aspa.gson.CurrentTrraficSplite;

/**
 * Created by FCC on 10/10/2017.
 */

public class EventOnGetCurrentTrafficSplite {

    CurrentTrraficSplite currentTrraficSplite;

    public CurrentTrraficSplite getCurrentTrraficSplite() {
        return currentTrraficSplite;
    }

    public void setCurrentTrraficSplite(CurrentTrraficSplite currentTrraficSplite) {
        this.currentTrraficSplite = currentTrraficSplite;
    }

    public EventOnGetCurrentTrafficSplite(CurrentTrraficSplite currentTrraficSplite) {

        this.currentTrraficSplite = currentTrraficSplite;
    }
}
