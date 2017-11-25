package ir.jahanmir.aspa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "TicketDetail")
public class TicketDetail extends Model {

    @Column(name = "ParentCode")
    public long parentCode = 0;

    @Column(name = "Date")
    public String Date = "";

    @Column(name = "UnitName")
    public String UnitName = "";

    @Column(name = "User")
    public String User = "";

    @Column(name = "State")
    public String State = "";

    @Column(name = "Des")
    public String Des = "";

    @Column(name = "UnitCode")
    public int UnitCode;
}
