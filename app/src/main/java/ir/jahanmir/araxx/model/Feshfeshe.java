package ir.jahanmir.araxx.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Feshfeshe")
public class Feshfeshe extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "code")
    public long code;
    @Column(name = "Package")
    public String packageName = "";
    @Column(name = "Des")
    public String des = "";
    @Column(name = "Started")
    public int started;
    @Column(name = "hour")
    public int hour;
    @Column(name = "day")
    public int day;
    @Column(name = "traffic")
    public int traffic;

}
