package controller;

import controller.gamestructure.GameGoods;
import model.Government;
import model.building.Building;
import model.building.storagebuildings.StorageBuilding;
import model.goods.Goods;

import java.util.ArrayList;

public class GovernmentController {
    public static Government currentGovernment;

    public static String showPopularityFactors() {
        return "";
    }

    public static String showPopularity() {
        return "";
    }

    public static String changeFoodRate(int rate) {
        return "";
    }

    public static String showFoodList() {
        return "";
    }

    public static String showFoodRate() {
        return "";
    }

    public static String showTaxRate() {
        return "";
    }

    public static String changeTaxRate(int rate) {
        return "";
    }

    public static String changeFearRate(int rate) {
        return "";
    }

    public static String showFearRate() {
        return "";
    }

    public static boolean consumeProduct(Government government,String product,int amount){
        Goods property = GameGoods.getProduct(product);
        if(government.getPropertyAmount(product) < amount){
            return false;
        }

        if (property != null) {
            String storageName = property.getNameOfStorage();
            government.addAmountToProperties(product, property.getType(), -amount);
            ArrayList<Building> storages = government.getBuildings().get(storageName).getBuildings();
            for (Building building : storages) {
                if (amount == 0) {
                    break;
                }
                StorageBuilding storage = (StorageBuilding) building;

                int entity = Math.min(amount, storage.getItemAmount(product));
                amount -= entity;
                storage.addAmount(-entity);
                storage.addItem(product, -entity);
            }
        } else {
            government.addAmountToProperties(product, null, -amount);
        }
        return true;
    }

    public static int generateProduct(Government government,String name,int amount){
        Goods product = GameGoods.getProduct(name);
        int rate = amount;
        ArrayList<Building> storages = government.getBuildings().get(product.getNameOfStorage()).getBuildings();
        for (Building building : storages) {
            if (amount == 0) {
                break;
            }
            StorageBuilding storage = (StorageBuilding) building;
            int saved = Math.min(amount, storage.remained());
            amount -= saved;
            storage.addAmount(saved);
            storage.addItem(name, saved);
        }
        government.addAmountToProperties(product.getName(), product.getType(), rate - amount);
        return rate - amount;
    }

    /*public static boolean checkRate(int rate , int lowerbound , int upperbound) {
        return false;
    }*/

}
