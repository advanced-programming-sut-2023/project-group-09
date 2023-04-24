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

    private Textures texture = Textures.EARTH;
    private Building building = null;
    private ArrayList<Human> humans = new ArrayList<>();
    private boolean isMoat;
    private boolean isPit;

    private boolean passable = true;
    private boolean canPutBuilding = true;

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public void setHumans(ArrayList<Human> humans) {
        this.humans = humans;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public boolean isCanPutBuilding() {
        return canPutBuilding;
    }

    public void setCanPutBuilding(boolean canPutBuilding) {
        this.canPutBuilding = canPutBuilding;
    }

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
