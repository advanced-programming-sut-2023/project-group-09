package model.buildinghandler;

import model.building.Building;

import java.util.ArrayList;

public class BuildingCounter {
    private int number = 0;
    private ArrayList<Building> buildings = new ArrayList<>();

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }
    public void addBuilding(Building building) {
        this.number++;
        this.buildings.add(building);
    }
    public void deleteBuilding(Building building){
        this.buildings.remove(building);
        this.number--;
    }
}
