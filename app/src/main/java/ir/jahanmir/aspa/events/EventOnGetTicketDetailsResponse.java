package ir.jahanmir.aspa.events;

import java.util.List;

import ir.jahanmir.aspa.gson.TicketDetailsResponse;


/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetTicketDetailsResponse {
    List<TicketDetailsResponse> ticketDetails;
    public EventOnGetTicketDetailsResponse(List<TicketDetailsResponse> ticketDetails) {
        this.ticketDetails = ticketDetails;
    }
    public List<TicketDetailsResponse> getTicketDetails() {
        return ticketDetails;
    }
}
