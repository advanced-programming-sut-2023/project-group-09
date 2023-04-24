package model.goods;

import java.util.ArrayList;
import java.util.HashMap;

public class Goods {
    private String name;

    private final String nameOfStorage;
    private final String type;
    private final HashMap<String, Integer> required = new HashMap<>();
    private final int price;

    public String getNameOfStorage() {
        return nameOfStorage;
    }

    public Goods(String name, String type, String nameOfStorage, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.nameOfStorage = nameOfStorage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getRequired() {
        return required;
    }

    public void addRequired(String required, int count) {
        this.required.put(required, count);
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

}
