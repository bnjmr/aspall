package ir.jahanmir.aspa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 3/7/2016.
 */
@Table(name = "Info")
public class Info extends Model {
    //اطلاعات مشترکی که لاگین کرده است
    @Column(name = "userId")
    public long userId;
    @Column(name = "username")
    public String username = "";
    @Column(name = "fullName")
    public String fullName = "";
    @Column(name = "firstCon")
    public String firstCon = "";
    @Column(name = "lastCon")
    public String lastCon = "";
    @Column(name = "mobileNo")
    public String mobileNo = "";
    @Column(name = "serviceType")
    public String serviceType = "";
    @Column(name = "status")
    public String status = "";
    @Column(name = "resellerName")
    public String resellerName = "";
    @Column(name = "UnitName")
    public String unit = "";
    @Column(name = "Encoded64ImageString")
    public String Encoded64ImageString = "";

}
