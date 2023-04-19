package model.goods;

import java.util.ArrayList;

public class Goods {

    private String name;
    private ArrayList<String> required = new ArrayList<>();

    public Goods(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getRequired() {
        return required;
    }

    public void setRequired(ArrayList<String> required) {
        this.required = required;
    }
}
