package ir.jahanmir.aspa.events;

import java.util.List;

import ir.jahanmir.aspa.model.ClubScore;


public class EventOnGetClubScoresResponse {
    List<ClubScore> clubScoresResponse;
    public EventOnGetClubScoresResponse(List<ClubScore> clubScoresResponse) {
        this.clubScoresResponse = clubScoresResponse;
    }
    public List<ClubScore> getClubScoresResponse() {
        return clubScoresResponse;
    }
}
