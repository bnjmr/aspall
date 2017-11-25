package ir.jahanmir.aspa.model;

/**
 * Created by FCC on 11/6/2017.
 */

public class ModelCities {

    int code;
    String name;

    public ModelCities() {
    }

    int region;

    public ModelCities(int code, String name, int region) {
        this.code = code;
        this.name = name;
        this.region = region;
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

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }
}
