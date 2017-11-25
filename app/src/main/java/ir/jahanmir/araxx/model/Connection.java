package ir.jahanmir.araxx.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 4/3/2016.
 */
@Table(name = "Connection")
public class Connection extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "StartTime")
    public String startTime = "";
    @Column(name = "EndTime")
    public String endTime = "";
    @Column(name = "Duration")
    public String Duration = "";
    @Column(name = "Send")
    public String Send;
    @Column(name = "Recieve")
    public String Recieve;
    @Column(name = "TrafficUsed")
    public String TrafficUsed = "";
    @Column(name = "Reason")
    public String Reason;
    @Column(name = "TrafficAll")
    public String TrafficAll;

}
