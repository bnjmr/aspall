package ir.jahanmir.aspa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "News")
public class News extends Model {
    @Column(name = "UserId")
    public long userId;
    @Column(name = "code")
    public long newsID;
    @Column(name = "NewsDate")
    public String newsDate = "";
    @Column(name = "Title")
    public String title = "";
    @Column(name = "BodyText")
    public String bodyText = "";
    @Column(name = "Important")
    public boolean important;
    @Column(name = "IsSeen")
    public boolean isSeen = false;
}
