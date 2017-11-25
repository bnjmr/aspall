package ir.jahanmir.araxx.events;

import java.util.List;

import ir.jahanmir.araxx.gson.NewsResponse;


/**
 * Created by Microsoft on 3/15/2016.
 */
public class EventOnGetNewsResponse {
    List<NewsResponse> newses ;
    public EventOnGetNewsResponse(List<NewsResponse> newses) {
        this.newses = newses;
    }
    public List<NewsResponse> getNewses() {
        return newses;
    }
}
