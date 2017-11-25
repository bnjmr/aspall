package ir.jahanmir.aspa.events;

/**
 * Created by Microsoft on 4/2/2016.
 */
public class EventOnGetClubScoreResponse {
    int result;
    int score;
    public EventOnGetClubScoreResponse(int result, int score) {
        this.result = result;
        this.score = score;
    }
    public int isResult() {
        return result;
    }
    public int getScore() {
        return score;
    }
}
