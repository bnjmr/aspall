package ir.jahanmir.aspa.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Microsoft on 3/2/2016.
 */
public class SearchISPResponse {
    @SerializedName("Id")
    public long ispId;
    @SerializedName("Name")
    public String name = "";
    @SerializedName("MyLink")
    public String url = "";
}
