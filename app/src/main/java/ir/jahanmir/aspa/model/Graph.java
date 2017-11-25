package ir.jahanmir.aspa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Microsoft on 4/3/2016.
 */
@Table(name = "Graph")
public class Graph extends Model {

    @Column(name = "UserId")
    public long userId;
    @Column(name = "XData")
    public String xData = "";
    @Column(name = "YData")
    public String yData = "";
}
