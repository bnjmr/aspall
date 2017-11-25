package ir.jahanmir.araxx.events;

import java.util.List;

import ir.jahanmir.araxx.model.Payment;


/**
 * Created by Microsoft on 3/10/2016.
 */
public class EventOnGetPaymentResponse {
    List<Payment> payments;
    public EventOnGetPaymentResponse(List<Payment> payments) {
        this.payments = payments;
    }
    public List<Payment> getPayments() {
        return payments;
    }
}
