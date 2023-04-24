package controller;

import enumeration.answers.BuildingAnswers;
import model.Government;
import model.building.Building;
import model.building.castlebuildings.Gatehouse;
import model.building.producerbuildings.Barrack;
import model.building.storagebuildings.StorageBuilding;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.ArrayList;
import java.util.HashMap;

public class BuildingController {
    private static Building building;
    private static Government government;

    private static Government getGovernment() {
        return government;
    }

    public static void setGovernment() {
        if (building != null)
            BuildingController.government = BuildingController.getBuilding().getGovernment();
    }

    public static Building getBuilding() {
        return building;
    }

    private static void setBuilding(Building b) {
        building = b;
    }

    public static String changeTaxRate(String rateNumberString){
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
        ((Gatehouse)building).openOrCloseGatehouse(openIt);
        return BuildingAnswers.getMessage(BuildingAnswers.OPEN_CLOSE_SUCCESSFULLY_DONE);
    }

    public static String repair() {
        //TODO: how to repair?
        return "";
    }

    public static String showStateOfGate() {
        return ((Gatehouse)building).isOpen() ? "Open" : "Close";
    }

    public static String showItems() {
        String result = "";
        HashMap<String , Integer> items = ((StorageBuilding)building).getItems();
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

    public static String buyUnit(String unitName) {
        Barrack barrack = (Barrack) building;
        if (!barrack.getUnits().contains(unitName)) {
            return BuildingAnswers.getMessage(BuildingAnswers.INVALID_UNIT_NAME);
        }
        //TODO: how to buy? --check he has enough money to pay or not
        return "";
    }

    public static String shop() {
        // TODO: I don't know :)
        return "";
    }

    public static String showSavedGoods() {
        String result = "";
        StorageBuilding stockpile = (StorageBuilding) building;
        for (String itemName : stockpile.getItems().keySet()) {
            result += itemName + " : " + stockpile.getItemAmount(itemName) + "\n";
        }
        return result;
    }

    public static String howManyHorses() {
        //TODO: handle it!
        return "";
    }
}
