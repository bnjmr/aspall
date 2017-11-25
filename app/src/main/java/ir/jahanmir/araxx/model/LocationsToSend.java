package ir.jahanmir.araxx.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by HaMiD on 4/20/2017.
 */
@Table(name = "LocationsToSend")
public class LocationsToSend extends Model {

    @Column(name = "Date")
    String Date;

    @Column(name = "code")
    int Code;

    @Column(name = "ScoreInDay")
    int ScoreInDay;

    @Column(name = "ScoreInMonth")
    int ScoreInMonth;

    @Column(name = "Offline")
    boolean Offline;

    @Column(name = "scoreTypeCode")
    int scoreTypeCode;

    public int getScoreTypeCode() {
        return scoreTypeCode;
    }

    public void setScoreTypeCode(int scoreTypeCode) {
        this.scoreTypeCode = scoreTypeCode;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public int getScoreInDay() {
        return ScoreInDay;
    }

    public void setScoreInDay(int scoreInDay) {
        ScoreInDay = scoreInDay;
    }

    public int getScoreInMonth() {
        return ScoreInMonth;
    }

    public void setScoreInMonth(int scoreInMonth) {
        ScoreInMonth = scoreInMonth;
    }

    public boolean isOffline() {
        return Offline;
    }

    public void setOffline(boolean offline) {
        Offline = offline;
    }
}
