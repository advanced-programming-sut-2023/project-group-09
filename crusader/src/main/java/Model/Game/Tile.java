package model.game;

import enumeration.Textures;
import model.building.Building;
import model.human.Human;

import java.util.ArrayList;

public class Tile {
    private Textures texture;
    private Building building = null;
    private ArrayList<Human> humans = new ArrayList<>();

    public Tile(Textures texture) {
        this.texture = texture;
    }

    public Textures getTexture() {
        return texture;
    }

    public void setTexture(Textures texture) {
        this.texture = texture;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public ArrayList<Human> getHuman() {
        return humans;
    }

    public void setHuman(ArrayList<Human> human) {
        this.humans = human;
    }
}
