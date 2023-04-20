package model.goods;

import java.util.ArrayList;
import java.util.HashMap;

public class Goods {
    private String name;
    private String type;
    private HashMap<String, Integer> required = new HashMap<>();
    private int price;

    public Goods(String name, String type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
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
}
