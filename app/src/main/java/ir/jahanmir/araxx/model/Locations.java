package ir.jahanmir.araxx.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by HaMiD on 3/4/2017.
 */
@Table(name = "Locations ")
public class Locations extends Model {

    @Column(name = "Latitude")
    double Latitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code")
    int code;

    @Column(name = "Longitude")
    double Longitude;

    @Column(name = "Des")
    String Des;

    @Column(name = "name")
    String name;


    @Column(name = "startDate")
    String startDate;

    @Column(name = "endDate")
    String endDate;

    @Column(name = "scoreTypeCode")
    int scoreTypeCode;

    @Column(name = "hasConditions")
    boolean hasConditions;

    @Column(name = "isShowInOffline")
    boolean isShowInOffline;

    @Column(name = "scoreInDay")
    int scoreInDay;

    @Column(name = "scoreInMonth")
    int scoreInMonth;

    @Column(name = "lastDate")
    String lastDate;

    @Column(name = "score")
    int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScoreInDay() {
        return scoreInDay;
    }

    public void setScoreInDay(int scoreInDay) {
        this.scoreInDay = scoreInDay;
    }

    public int getScoreInMonth() {
        return scoreInMonth;
    }

    public void setScoreInMonth(int scoreInMonth) {
        this.scoreInMonth = scoreInMonth;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isHasConditions() {
        return hasConditions;
    }

    public void setHasConditions(boolean hasConditions) {
        this.hasConditions = hasConditions;
    }

    public int getScoreTypeCode() {
        return scoreTypeCode;
    }

    public void setScoreTypeCode(int scoreTypeCode) {
        this.scoreTypeCode = scoreTypeCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public boolean isShowInOffline() {
        return isShowInOffline;
    }

    public void setShowInOffline(boolean showInOffline) {
        isShowInOffline = showInOffline;
    }
}
