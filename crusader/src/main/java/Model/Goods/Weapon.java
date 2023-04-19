package model.goods;

import model.Permission;

import java.util.ArrayList;

public class Weapon extends Goods {
    private ArrayList<Permission> permissions = new ArrayList<>();

    public Weapon(String name) {
        super(name);
    }

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(ArrayList<Permission> permissions) {
        this.permissions = permissions;
    }
}
