package ir.jahanmir.araxx.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 3/26/2016.
 */
@Table(name = "Unit")
public class Unit extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "code")
    public long code = 0 ;
    @Column(name = "Name")
    public String name = "";
}
