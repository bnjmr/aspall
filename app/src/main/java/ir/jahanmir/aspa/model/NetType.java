package ir.jahanmir.aspa.model;

/**
 * Created by FCC on 10/24/2017.
 */

public class NetType {

    String key;
    int value;

    public NetType() {
    }

    public NetType(String key, int value) {

        this.key = key;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
