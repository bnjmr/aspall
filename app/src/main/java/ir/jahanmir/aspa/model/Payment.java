package ir.jahanmir.aspa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 3/28/2016.
 */
@Table(name = "Payment")
public class Payment extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "Date")
    public String date = "";
    @Column(name = "Type")
    public String type = "";
    @Column(name = "Price")
    public String price = "";
    @Column(name = "Des")
    public String des = "";
}
