package model.game;

import enumeration.Textures;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import javafx.scene.paint.Color;
import model.Government;
import model.building.Building;
import model.building.castlebuildings.CastleBuilding;
import model.building.castlebuildings.Gatehouse;
import model.building.castlebuildings.MainCastle;
import model.building.castlebuildings.Wall;
import model.human.Human;
import model.human.civilian.Civilian;
import model.human.military.Military;
import model.tools.Tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Tile implements Serializable {

    private Textures texture;
    private Trees tree;
    private RockDirections rockDirection;
    private transient Tool tool;
    private boolean isDefaultCastle;
    private transient Building building = null;
    private boolean isMoat;
    private boolean isPit;
    private int textureNum;
    private transient Color color;

    private transient Government pitGovernment;
    private boolean passable = true;
    private boolean canPutBuilding = true;

    public int x;
    public int y;

    public Tile() {
        this.isMoat = false;
        this.isPit = false;
        tree = null;
        rockDirection = null;
        setTexture(Textures.EARTH);
    }


    public boolean isDefaultCastle() {
        return isDefaultCastle;
    }

    public void setDefaultCastle(boolean defaultCastle) {
        isDefaultCastle = defaultCastle;
    }

    public Tile(Textures texture) {
        setTexture(texture);
    }

    public Trees getTree() {
        return tree;
    }

    public void setTree(Trees tree) {
        if (tree != null) {
            canPutBuilding = false;
        }
        this.tree = tree;

    }

    public boolean isCanPutBuilding() {
        return canPutBuilding;
    }


    private transient ArrayList<Civilian> civilians = new ArrayList<>();

    public ArrayList<Military> getMilitaries() {
        if (militaries == null) {
            militaries = new ArrayList<>();
        }
        return militaries;
    }

    public ArrayList<Human> getHumans() {
        ArrayList<Human> humans = new ArrayList<Human>(militaries);
        humans.addAll(civilians);
        return humans;
    }

    public void setMilitaries(ArrayList<Military> militaries) {
        this.militaries = militaries;
    }

    public RockDirections getRockDirection() {
        return rockDirection;
    }

    public void setRockDirection(RockDirections rockDirection) {
        if (rockDirection != null) {
            canPutBuilding = false;
            passable = false;
        }
        this.rockDirection = rockDirection;
    }

    private transient ArrayList<Military> militaries = new ArrayList<>();


    public ArrayList<Civilian> getCivilians() {
        if (civilians == null) {
            civilians = new ArrayList<>();
        }
        return civilians;
    }

    public void setCivilians(ArrayList<Civilian> civilian) {
        this.civilians = civilian;
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean isPassable(Human human, boolean isOverhead) {
        if (human instanceof Military military && military.getName().equals("assassin")) {
            return building instanceof Wall || passable;
        }

        if (!isOverhead) {
            if (building instanceof Wall wall && wall.getHeight() == 1) {
                return true;
            }
            if (building instanceof Gatehouse gatehouse && gatehouse.isOpen()) {
                return true;
            }
            if (building instanceof MainCastle) {
                return true;
            }
            if (tool != null && tool.getName().equals("siegeTower") && !tool.isCanMove()) {
                return true;
            }
            if (human instanceof Military military && military.isUsesLadder()) {
                for (Military troop : militaries) {
                    if (troop.getGovernment().equals(military.getGovernment()) && troop.getName().equals("ladderman")) {
                        return true;
                    }
                }
            }
            return passable;
        } else {
            if (building instanceof CastleBuilding castleBuilding && !castleBuilding.getName().equals("drawBridge")) {
                return true;
            }
            if (tool != null && tool.getName().equals("siegeTower") && !tool.isCanMove()) {
                return true;
            }
            if (human instanceof Military military && military.isUsesLadder() && !military.getName().equals("ladderman")) {
                for (Military troop : militaries) {
                    if (troop.getGovernment().equals(military.getGovernment()) && troop.getName().equals("ladderman")) {
                        return true;
                    }
                }
            }
            return false;
        }
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
        if (building == null && (civilians == null || civilians.size() == 0) && (
                militaries == null || militaries.size() == 0)
                && rockDirection == null && tree == null) {
            this.texture = texture;
            passable = texture.isPassable();
            canPutBuilding = texture.getCanPutBuilding();
            setColor();
        }
    }

    public void setColor(){
        Color prmColor = texture.getColor();
        Color tmpColor = texture.getTempColor();
        Random random = new Random();
        textureNum = random.nextInt(texture.getCount());
        int whichColor = random.nextInt(texture.getRatio());
        if (whichColor == 0) {
            color = tmpColor;
        } else {
            color = prmColor;
        }
    }
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        canPutBuilding = false;
        this.building = building;
        isPit = false;
        pitGovernment = null;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Civilian> getCivilian() {
        if (civilians == null) {
            civilians = new ArrayList<>();
        }
        return civilians;
    }

    public void setCivilian(ArrayList<Civilian> human) {
        this.civilians = human;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void addMilitary(Military military) {
        if (militaries == null) {
            militaries = new ArrayList<>();
        }
        militaries.add(military);
    }

    public void removeMilitary(Military military) {
        if (militaries == null) {
            militaries = new ArrayList<>();
        }
        militaries.remove(military);
    }

    public void addHuman(Civilian civilian) {
        if (civilians == null) {
            civilians = new ArrayList<>();
        }
        civilians.add(civilian);
    }

    public void removeHuman(Civilian civilian) {
        if (civilians == null) {
            civilians = new ArrayList<>();
        }
        civilians.remove(civilian);
    }

    public boolean isMoat() {
        return isMoat;
    }

    public void setMoat(boolean moat) {
        passable = !moat;
        isMoat = moat;
    }

    public int getTextureNum() {
        return textureNum;
    }

    public boolean isPit() {
        return isPit;
    }

    public void setPit(boolean pit) {
        isPit = pit;
    }

    public void clearCivilian() {
        if (civilians == null) {
            civilians = new ArrayList<>();
        }
        civilians.clear();
    }

    public void clearMilitary() {
        if (militaries == null) {
            militaries = new ArrayList<>();
        }
        militaries.clear();
    }

    public Government getPitGovernment() {
        return pitGovernment;
    }

    public void setPitGovernment(Government pitGovernment) {
        this.pitGovernment = pitGovernment;
    }
}
