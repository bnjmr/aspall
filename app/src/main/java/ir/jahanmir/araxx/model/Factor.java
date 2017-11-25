package ir.jahanmir.araxx.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table (name = "Factor")
public class Factor extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "FactorId")
    public long factorId;
    @Column(name = "Date")
    public String date;
    @Column(name = "StartDate")
    public String startDate;
    @Column(name = "ExpireDate")
    public String expireDate;
    @Column(name = "Price")
    public String price;
    @Column(name = "Des")
    public String des;
    @Column(name = "Status")
    public int status;
    @Column(name = "Discount")
    public String discount;
    @Column(name = "Tax")
    public String tax;
    @Column(name = "Amount")
    public String amount;
}
