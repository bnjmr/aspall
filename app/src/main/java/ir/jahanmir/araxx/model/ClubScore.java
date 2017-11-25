package ir.jahanmir.araxx.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 4/2/2016.
 */
@Table(name = "ClubScore")
public class ClubScore extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "Title")
    public String title = "";
    @Column(name = "Score")
    public int score;
    @Column(name = "Des")
    public String des = "";
    @Column(name = "Date")
    public String date = "";
}
