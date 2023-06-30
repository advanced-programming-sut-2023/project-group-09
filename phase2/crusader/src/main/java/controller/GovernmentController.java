package controllers;

import controllers.gamestructure.GameGoods;
import model.Government;
import model.building.Building;
import model.building.storagebuildings.StorageBuilding;
import model.goods.Goods;

import java.util.ArrayList;

public class GovernmentController {
    private static Government currentGovernment;

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        GovernmentController.currentGovernment = currentGovernment;
    }

    public static String showPopularityFactors() {
        StringBuilder output = new StringBuilder();
        int foodRate = currentGovernment.getVarietyOfFood() + getFoodPopularity(currentGovernment.getFoodRate()) - 1;
        output.append("food : ").append(foodRate).append("\n");
        output.append("tax : ").append(currentGovernment.getFearRate()).append("\n");
        output.append("fear : ").append(getTaxPopularity(currentGovernment.getTaxRate())).append("\n");
        output.append("religion : ").append(currentGovernment.getPopularityOfReligion()).append("\n");
        output.append("ale coverage : ").append(currentGovernment.getPopularityOfAleCoverage()).append("\n");
        return output.toString();
    }

    public static String showPopularity() {
        int popularityOfGovernment = 0;
        popularityOfGovernment += currentGovernment.getVarietyOfFood() + getFoodPopularity(currentGovernment.getFoodRate()) - 1;
        popularityOfGovernment += currentGovernment.getFearRate();
        popularityOfGovernment += getTaxPopularity(currentGovernment.getTaxRate());
        popularityOfGovernment += currentGovernment.getPopularityOfReligion();
        popularityOfGovernment += currentGovernment.getPopularityOfAleCoverage();
        return "The popularity of your government is " + popularityOfGovernment;
    }

    public static String changeFoodRate(int rate) {
        if (-2 <= rate && rate <= 2) {
            currentGovernment.setFoodRate(rate);
            return "food rate changed successfully!";
        }
        return "invalid food rate!";
    }

    public static String showFoodList() {
        String output = "Foods:\n";
        for (String food : GameGoods.foods.keySet()) {
            output += food + " : " + currentGovernment.getPropertyAmount(food) + "\n";
        }
        return output;
    }

    public static String showFoodRate() {
        return "food rate: " + currentGovernment.getFoodRate();
    }

    public static String showTaxRate() {
        return "tax rate: " + currentGovernment.getTaxRate();
    }

    public static String changeTaxRate(int rate) {
        if (-3 <= rate && rate <= 8) {
            currentGovernment.setTaxRate(rate);
            return "tax rate changed successfully!";
        }
        return "invalid tax rate!";
    }

    public static String changeFearRate(int rate) {
        if (-5 <= rate && rate <= 5) {
            currentGovernment.setFearRate(rate);
            return "fear rate changed successfully!";
        }
        return "invalid fear rate!";
    }

    public static String showFearRate() {
        return "fear rate: " + currentGovernment.getFearRate();
    }

    public static boolean consumeProduct(Government government, String product, int amount) {
        Goods property = GameGoods.getProduct(product);
        if (government.getPropertyAmount(product) < amount) {
            return false;
        }

        if (property != null) {
            String storageName = property.getNameOfStorage();
            government.addAmountToProperties(product, property.getType(), -amount);
            ArrayList<Building> storages = government.getBuildings().get(storageName).getBuildings();
            if (storages.size() == 0) {
                return false;
            }
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

    public static int generateProduct(Government government, String name, int amount) {
        Goods product = GameGoods.getProduct(name);
        int rate = amount;
        ArrayList<Building> storages = government.getBuildings().get(product.getNameOfStorage()).getBuildings();
        if (storages.size() == 0) {
            return -1;
        }
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

    public static boolean canAddProduct(Government government, String name, int amount) {
        Goods product = GameGoods.getProduct(name);
        ArrayList<Building> storages = government.getBuildings().get(product.getNameOfStorage()).getBuildings();
        for (Building building : storages) {
            if (amount == 0) {
                break;
            }
            StorageBuilding storage = (StorageBuilding) building;
            int saved = Math.min(amount, storage.remained());
            amount -= saved;
        }
        if (amount == 0) return true;
        return false;
    }

    public static int getFoodPopularity(int num) {
        return switch (num) {
            case -2 -> -8;
            case -1 -> -4;
            case 1 -> 4;
            case 2 -> 8;
            default -> 0;
        };
    }

    public static double getFoodRate(int num) {
        return switch (num) {
            case -2 -> 0;
            case -1 -> 0.5;
            case 1 -> 1.5;
            case 2 -> 2;
            default -> 1;
        };
    }

    public static int getTaxPopularity(int num) {
        return switch (num) {
            case -3 -> 7;
            case -2 -> 5;
            case -1 -> 3;
            case 1 -> -2;
            case 2 -> -4;
            case 3 -> -6;
            case 4 -> -8;
            case 5 -> -12;
            case 6 -> -16;
            case 7 -> -20;
            case 8 -> -24;
            default -> 1;
        };
    }

    public static double getTaxRate(int num) {
        return switch (num) {
            case -3 -> -1;
            case -2 -> -0.8;
            case -1 -> -0.6;
            case 1 -> 0.6;
            case 2 -> 0.8;
            case 3 -> 1;
            case 4 -> 1.2;
            case 5 -> 1.4;
            case 6 -> 1.6;
            case 7 -> 1.8;
            case 8 -> 2;
            default -> 0;
        };
    }

    public static String showProperties() {
        String result = "";
        int counter = 1;
        for (String itemName : currentGovernment.getProperties().keySet()) {
            result += counter + ". " + itemName.toUpperCase() + " : " + currentGovernment.getPropertyAmount(itemName) + "\n";
            counter++;
        }
        result += counter + ". GOLD : " + currentGovernment.getGold();
        return result;
    }
}
