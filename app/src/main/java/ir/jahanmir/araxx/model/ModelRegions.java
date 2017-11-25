package ir.jahanmir.araxx.model;

/**
 * Created by FCC on 11/6/2017.
 */

public class ModelRegions  {

    int code;
    String name;
    int preTel;

    public ModelRegions(int code, String name, int preTel) {
        this.code = code;
        this.name = name;
        this.preTel = preTel;
    }


    public ModelRegions() {

    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPreTel() {
        return preTel;
    }

    public void setPreTel(int preTel) {
        this.preTel = preTel;
    }
}
