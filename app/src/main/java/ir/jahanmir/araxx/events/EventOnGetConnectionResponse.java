package ir.jahanmir.araxx.events;

import java.util.List;

import ir.jahanmir.araxx.model.Connection;


/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetConnectionResponse {
    List<Connection> getConnectionsResponses;
    public EventOnGetConnectionResponse(List<Connection> getConnectionsResponses) {
        this.getConnectionsResponses = getConnectionsResponses;
    }
    public List<Connection> getGetConnectionsResponses() {
        return getConnectionsResponses;
    }
}
