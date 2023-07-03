package controller;

import client.Packet;
import controller.human.HumanController;
import controller.human.MoveController;
import enumeration.Pair;
import enumeration.answers.BuildingAnswers;
import model.Government;
import model.building.Building;
import model.building.castlebuildings.Gatehouse;
import model.building.producerbuildings.Barrack;
import model.building.producerbuildings.WeaponProducer;
import model.building.storagebuildings.StorageBuilding;
import model.game.Map;
import model.human.civilian.Civilian;
import model.human.military.Military;
import model.menugui.game.GameMap;
import view.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BuildingController {
    private static Building building;
    private static Government government;

    public static void setGovernment() {
        if (building != null)
            BuildingController.government = BuildingController.getBuilding().getGovernment();
    }

    public static Building getBuilding() {
        return building;
    }

    public static void setBuilding(Building b) {
        building = b;
        government = building.getGovernment();
    }

    public static String changeTaxRate(String rateNumberString) {
        int rateNumber;
        try {
            rateNumber = Integer.parseInt(rateNumberString);
        } catch (Exception e) {
            return BuildingAnswers.getMessage(BuildingAnswers.INVALID_NUMBER_INPUT);
        }
        if (rateNumber < -3 || rateNumber > 8) {
            return BuildingAnswers.getMessage(BuildingAnswers.INVALID_RANGE_FOR_TAX_RATE);
        }
        government.setTaxRate(rateNumber);
        return BuildingAnswers.getMessage(BuildingAnswers.TAX_RATE_SUCCESSFULLY_CHANGED);
    }

    public static String showTaxRate() {
        return BuildingAnswers.getMessage(BuildingAnswers.TAX_RATE_SHOWING) + government.getTaxRate();
    }

    public static String openOrCloseGatehouse(String order) {
        boolean openIt = order.equals("open");
        ((Gatehouse) building).openOrCloseGatehouse(openIt);
        return BuildingAnswers.getMessage(BuildingAnswers.OPEN_CLOSE_SUCCESSFULLY_DONE);
    }

    public static String resourcesNeededForRepair() {
        double rateOfRepairNeeded = ((double) building.getHp()) / building.getMaxHp();
        String result = "Items in need for repair : \n";
        for (String item : building.getCost().keySet()) {
            result += item + " : " + (int) (rateOfRepairNeeded * building.getCost().get(item)) + "\n";
        }
        return result;
    }

    public static String repair() {
        String itemNeeded = "";
        boolean canRepaired = true;
        double rateOfRepairNeeded = ((double) building.getHp()) / building.getMaxHp();
        for (String item : building.getCost().keySet()) {
            if (government.getPropertyAmount(item) < (int) (rateOfRepairNeeded * building.getCost().get(item))) {
                itemNeeded = item;
                canRepaired = false;
                break;
            }
        }
        if (!canRepaired) {
            return "You don't have enough " + itemNeeded + " for repair!";
        }

        ArrayList<Military> enemies = getEnemyOfRange(building.getStartX(), building.getStartY(), 5, building.getGovernment());
        if (enemies.size() != 0) {
            return "there are some enemies around here!";
        }


        for (String item : building.getCost().keySet()) {
            GovernmentController.consumeProduct(government, item, (int) (rateOfRepairNeeded * building.getCost().get(item)));
        }
        building.setHp(building.getMaxHp());
        return "Successfully repaired!";
    }

    public static ArrayList<Military> getEnemyOfRange(int x, int y, int range, Government government) {
        int endX = x + range;
        int endY = y + range;

        Map map = GameController.getGame().getMap();
        if (endX + 1 >= map.getWidth()) {
            endX = map.getWidth() - 1;
        }

        if (endY + 1 >= map.getLength()) {
            endY = map.getLength() - 1;
        }

        int startX = x - range;
        int startY = y - range;
        if (x - range < 0) {
            startX = 0;
        }

        if (y - range < 0) {
            startY = 0;
        }
        return HumanController.getEnemiesOfArea(startX, startY, endX, endY, government);
    }

    public static String showStateOfGate() {
        return ((Gatehouse) building).isOpen() ? "Open" : "Close";
    }

    public static String showItems() {
        String result = "";
        HashMap<String, Integer> items = ((StorageBuilding) building).getItems();
        for (String name : items.keySet()) {
            result += name + " : " + items.get(name) + "\n";
        }
        return result;
    }

    public static String showUnitsList() {
        Barrack barrack = (Barrack) building;
        String result = "";
        int counter = 0;
        for (String name : barrack.getUnits()) {
            counter++;
            result += "unit " + counter + ": " + name + "\n";
        }
        return result;
    }

    public static String buyUnit(String unitName) throws IOException {
        Barrack barrack = (Barrack) building;
        if (!barrack.getUnits().contains(unitName)) {
            return BuildingAnswers.getMessage(BuildingAnswers.INVALID_UNIT_NAME);
        }
        if (!barrack.checkGold(unitName)) {
            return BuildingAnswers.getMessage(BuildingAnswers.INSUFFICIENT_MONEY);
        }

        if (!barrack.checkRequired(unitName)) {
            return BuildingAnswers.getMessage(BuildingAnswers.INSUFFICIENT_RESOURCE);
        }

        barrack.makeUnit(unitName);
        return unitName + " added successfully!";
    }


    public static void changeWeapon(String name) {
        if (building instanceof WeaponProducer weaponProducer) {
            weaponProducer.changeItemName(name);
        }
    }

    public static String showSavedGoods() {
        StringBuilder result = new StringBuilder();
        StorageBuilding stockpile = (StorageBuilding) building;
        for (String itemName : stockpile.getItems().keySet()) {
            result.append(itemName).append(" : ").append(stockpile.getItemAmount(itemName)).append("\n");
        }
        return result.toString();
    }

    public static String howManyHorses() {
        int availableHorses = government.getPropertyAmount("horse");
        int horsesInUse = 0;
        for (Military military : government.getTroops()) {
            if (military.getName().equals("knight")) {
                horsesInUse++;
            }
        }
        return "Available horses: " + availableHorses + "\nUsed horses: " + horsesInUse;
    }

    public static boolean unleashWarDogs() {
        return attackEnemyOfRange(building.getStartX(), building.getStartY(), 10);
    }

    public static boolean attackEnemyOfRange(int x, int y, int range) {
        int startX = x - range;
        int startY = y - range;
        Map map = GameController.getGame().getMap();
        if (x - range < 0) {
            startX = 0;
        }

        if (y - range < 0) {
            startY = 0;
        }
        int endX = x + range;
        int endY = y + range;
        if (endX + 1 >= map.getWidth()) {
            endX = map.getWidth() - 1;
        }

        if (endY + 1 >= map.getLength()) {
            endY = map.getLength() - 1;
        }
        return getEnemiesOfArea(x, y, startX, startY, endX, endY);
    }

    public static boolean getEnemiesOfArea(int x, int y, int startX, int startY, int endX, int endY) {
        double minDistance = 10000;
        Civilian targetCivilian = null;
        Military targetMilitary = null;
        double distance;

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                ArrayList<Civilian> civilians = MapController.getCiviliansOfOtherGovernment(i, j, building.getGovernment());
                if (civilians.size() != 0) {
                    Civilian civilian = civilians.get(0);
                    distance = MoveController.getDistance(x, y, i, j);
                    if (minDistance > distance) {
                        minDistance = distance;
                        targetCivilian = civilian;
                    }
                }

                ArrayList<Military> enemies = MapController.getMilitariesOfOtherGovernment(i, j, building.getGovernment());
                if (enemies.size() != 0) {
                    Military military = enemies.get(0);
                    distance = MoveController.getDistance(military.getX(), military.getY(), i, j);
                    if (minDistance > distance) {
                        minDistance = distance;
                        targetCivilian = null;
                        targetMilitary = military;
                    }
                }
            }
        }


        if (targetCivilian != null) {
            MapController.deleteHuman(targetCivilian.getX(), targetCivilian.getY(), targetCivilian);
            targetCivilian.setGovernment(null);
            MapController.deleteBuilding(building);
            return true;
        }

        if (targetMilitary != null) {
            int hp = targetMilitary.takeDamage(5);
            if (hp <= 0) {
                MapController.deleteMilitary(targetMilitary.getX(), targetMilitary.getY(), targetMilitary);
                targetMilitary.setGovernment(null);
            }
            MapController.deleteBuilding(building);
            return true;
        }
        return false;

    }

    public static void sendRepair() throws IOException {
        Packet packet = new Packet("repair" , "Game");
        packet.addAttribute("tileX" , building.getEndX());
        packet.addAttribute("tileY" , building.getEndY());
        packet.addAttribute("government" , building.getGovernment().getColor());
        packet.sendPacket();
        Main.connection.getObjectOutputStream().writeObject(GameController.getFakeGame());
    }

    public static void repairOnline(int tileX , int tileY , Government government) {
        Building building = GameMap.getGameTile(tileX , tileY).getTile().getBuilding();
        String itemNeeded = "";
        boolean canRepaired = true;
        double rateOfRepairNeeded = ((double) building.getHp()) / building.getMaxHp();
        for (String item : building.getCost().keySet()) {
            if (government.getPropertyAmount(item) < (int) (rateOfRepairNeeded * building.getCost().get(item))) {
                itemNeeded = item;
                canRepaired = false;
                break;
            }
        }
        if (!canRepaired) {
            return;
        }

        ArrayList<Military> enemies = getEnemyOfRange(building.getStartX(), building.getStartY(), 5, government);
        if (enemies.size() != 0) {
            return;
        }


        for (String item : building.getCost().keySet()) {
            GovernmentController.consumeProduct(government, item, (int) (rateOfRepairNeeded * building.getCost().get(item)));
        }
        building.setHp(building.getMaxHp());
        return;
    }
}
