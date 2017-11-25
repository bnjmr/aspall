package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 5/23/2016.
 */
public class EventOnGetCheckTarazResponse {
    int taraz;
    boolean CanPayment;

    public void setTaraz(int taraz) {
        this.taraz = taraz;
    }

    public boolean isCanPayment() {
        return CanPayment;
    }

    public void setCanPayment(boolean canPayment) {
        CanPayment = canPayment;
    }

    public EventOnGetCheckTarazResponse(int taraz,boolean CanPayment) {
        this.taraz = taraz;
        this.CanPayment = CanPayment;
    }
    public int getTaraz() {
        return taraz;
    }
}
