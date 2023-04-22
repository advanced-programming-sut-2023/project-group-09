package model.game;

import enumeration.Textures;
import model.building.Building;
import model.human.Human;

import java.util.ArrayList;

public class Tile {
    public Tile() {
        this.isMoat = false;
        this.isPit = false;
    }

    private Textures texture;
    private Building building = null;
    private ArrayList<Human> humans = new ArrayList<>();
    private boolean isMoat;
    private boolean isPit;

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

    public boolean isMoat() {
        return isMoat;
    }

    public void setMoat(boolean moat) {
        isMoat = moat;
    }

    public boolean isPit() {
        return isPit;
    }

    public void setPit(boolean pit) {
        isPit = pit;
    }
}
