package ir.jahanmir.araxx.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 3/27/2016.
 */
@Table(name = "Notify")
public class Notify extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "NotifyCode")
    public long notifyCode;
    @Column(name = "Message")
    public String message = "";
    @Column(name = "Title")
    public String Title = "";
    @Column(name = "IsSeen")
    public boolean isSeen = false;
    @Column(name = "Date")
    public String Date = "0";

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(long notifyCode) {
        this.notifyCode = notifyCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
