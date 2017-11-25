package ir.jahanmir.aspa.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by HaMiD on 3/11/2017.
 */
@Table(name = "TicketCodes")
public class TicketCodes extends Model {

    @Column(name = "Name")
    String name;

    @Column(name = "code")
    int code;

    @Column(name = "parent")
    int parent;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
}
