package ir.jahanmir.araxx.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User")
public class User extends Model {
//    @Column(Name = "userId")
//    public long userId;
    @Column(name = "Token")
    public String Token = "";
    @Column(name = "isLogin")
    public boolean isLogin = false;
    @Column(name = "ispUrl")
    public String ispUrl = "";
    @Column(name = "ispId")
    public long ispId;
    @Column(name = "isLastLogin")
    public boolean isLastLogin = false;
}
