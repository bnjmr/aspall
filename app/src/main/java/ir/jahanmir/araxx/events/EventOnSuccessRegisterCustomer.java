package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.model.ModelRegisterCustomerResponse;

/**
 * Created by FCC on 11/7/2017.
 */

public class EventOnSuccessRegisterCustomer {

    ModelRegisterCustomerResponse customerResponse;

    public ModelRegisterCustomerResponse getCustomerResponse() {
        return customerResponse;
    }

    public void setCustomerResponse(ModelRegisterCustomerResponse customerResponse) {
        this.customerResponse = customerResponse;
    }

    public EventOnSuccessRegisterCustomer(ModelRegisterCustomerResponse customerResponse) {

        this.customerResponse = customerResponse;
    }
}
