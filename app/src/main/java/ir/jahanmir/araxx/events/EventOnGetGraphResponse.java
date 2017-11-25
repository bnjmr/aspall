package ir.jahanmir.araxx.events;


import ir.jahanmir.araxx.model.Graph;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetGraphResponse {
    Graph graph;
    public EventOnGetGraphResponse(Graph graph) {
        this.graph = graph;
    }
    public Graph getGraph() {
        return graph;
    }
}
