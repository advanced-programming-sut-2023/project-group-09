package model.game;

import enumeration.Textures;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import model.building.Building;
import model.human.Human;
import model.human.civilian.Civilian;
import model.human.civilian.Civilian;
import model.human.military.Military;
import model.tools.AttackingAndDefendingTool;

import java.util.ArrayList;

public class Tile {
    public Tile() {
        this.isMoat = false;
        this.isPit = false;
        tree = null;
        rockDirection = null;
    }

    private Textures texture = Textures.EARTH;
    private Trees tree;
    private RockDirections rockDirection;
    private AttackingAndDefendingTool tool;

    public Tile(Textures texture) {
        this.texture = texture;
    }

    public Trees getTree() {
        return tree;
    }

    public void setTree(Trees tree) {
        this.tree = tree;
    }

    public boolean isCanPutBuilding() {
        return canPutBuilding;
    }

    private Building building = null;
    private ArrayList<Civilian> civilians = new ArrayList<>();

    public ArrayList<Military> getMilitaries() {
        return militaries;
    }

    public void setMilitaries(ArrayList<Military> militaries) {
        this.militaries = militaries;
    }

    public RockDirections getRockDirection() {
        return rockDirection;
    }

    public void setRockDirection(RockDirections rockDirection) {
        this.rockDirection = rockDirection;
    }

    private ArrayList<Military> militaries = new ArrayList<>();
    private boolean isMoat;
    private boolean isPit;

    private boolean passable = true;
    private boolean canPutBuilding = true;

    public ArrayList<Civilian> getCivilians() {
        return civilians;
    }

    public void setCivilians(ArrayList<Civilian> civilian) {
        this.civilians = civilian;
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean isPassable(Human human) {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public boolean getCanPutBuilding() {
        return canPutBuilding;
    }

    public void setCanPutBuilding(boolean canPutBuilding) {
        this.canPutBuilding = canPutBuilding;
    }

    public Textures getTexture() {
        return texture;
    }

    public void setTexture(Textures texture) {
        if (building == null) {
            this.texture = texture;
            passable = texture.isPassable();
            if (!texture.isPassable()) {
                civilians.clear();
            }
            canPutBuilding = texture.getCanPutBuilding();
        }
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public ArrayList<Civilian> getCivilian() {
        return civilians;
    }

    public void setCivilian(ArrayList<Civilian> human) {
        this.civilians = human;
    }

    public AttackingAndDefendingTool getTool() {
        return tool;
    }

    public void setTool(AttackingAndDefendingTool tool) {
        this.tool = tool;
    }

    public void addMilitary(Military military) {
        militaries.add(military);
    }

    public void removeMilitary(Military military) {
        militaries.remove(military);
    }

    public void addHuman(Civilian civilian) {
        civilians.add(civilian);
    }

    public void removeHuman(Civilian civilian) {
        civilians.remove(civilian);
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

    public void clearCivilian() {
        civilians.clear();
    }

    public void clearMilitary() {
        militaries.clear();
    }
}
