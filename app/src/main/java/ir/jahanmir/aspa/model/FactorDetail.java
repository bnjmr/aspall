package ir.jahanmir.aspa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "FactorDetail")
public class FactorDetail extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "ParentId")
    public long ParentId;
    @Column(name = "Name")
    public String Name = "";
    @Column(name = "Des")
    public String Des = "";
    @Column(name = "Price")
    public String Price = "";
}
