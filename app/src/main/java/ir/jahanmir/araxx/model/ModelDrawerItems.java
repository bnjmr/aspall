package ir.jahanmir.araxx.model;


import ir.jahanmir.araxx.enums.EnumDrawerItemsTag;

/**
 * Created by FCC on 10/28/2017.
 */

public class ModelDrawerItems {

    String name;
    EnumDrawerItemsTag tag;
    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ModelDrawerItems(String name, EnumDrawerItemsTag tag, int image) {

        this.name = name;
        this.tag = tag;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumDrawerItemsTag getTag() {
        return tag;
    }

    public void setTag(EnumDrawerItemsTag tag) {
        this.tag = tag;
    }

    public ModelDrawerItems() {

    }

    public ModelDrawerItems(String name, EnumDrawerItemsTag tag) {

        this.name = name;
        this.tag = tag;
    }
}
