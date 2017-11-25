package ir.jahanmir.aspa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import ir.jahanmir.aspa.enums.EnumTicketPriority;


/**
 * Created by Microsoft on 3/26/2016.
 */
@Table(name = "Ticket")
public class    Ticket extends Model {
    @Column(name = "code")
    public long code;
    @Column(name = "Date")
    public String date = "";
    @Column(name = "Title")
    public String title = "";
    @Column(name = "State")
    public String state = "";
    @Column(name = "Priority")
    public String priority = EnumTicketPriority.KAM;
    @Column(name = "Open")
    public int open = 0;
    @Column(name = "PriorityName")
    public String PriorityName = "";
    @Column(name = "CountUnread")
    public int CountUnread = 0;
}
