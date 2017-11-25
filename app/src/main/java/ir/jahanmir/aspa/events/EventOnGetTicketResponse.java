package ir.jahanmir.aspa.events;

import java.util.List;

import ir.jahanmir.aspa.gson.TicketResponse;


/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetTicketResponse {
    List<TicketResponse> ticketResponses;
    public EventOnGetTicketResponse(List<TicketResponse> ticketResponses) {
        this.ticketResponses = ticketResponses;
    }
    public List<TicketResponse> getTicketResponses() {
        return ticketResponses;
    }
}
