package controller;

import controller.gamestructure.GameGoods;
import model.Government;
import model.goods.Goods;

import java.util.HashMap;

public class MarketController {
    public static String showPriceList() {
        HashMap<String, Goods> goodsHashMap = GameGoods.goods;
        String output = "";
        Government government = GameController.getGame().getCurrentGovernment();
        for (String key : goodsHashMap.keySet().stream().sorted().toList()) {
            Goods product = goodsHashMap.get(key);
            output += key + " ==> type: " + product.getType() + " | sell price: " + product.getPrice() + " | buy price: " +
                    (int) Math.ceil(product.getPrice() * 0.5) + " | count: " + government.getPropertyAmount(key) + "\n";
        }
        return output.substring(0, output.length() - 1);
    }

    public static String buyItem(String name, int amount) {
        String message = validateItems(name, amount);
        if (message != null) {
            return message;
        }
        Goods product = GameGoods.getProduct(name);
        int cost = product.getPrice() * amount;
        Government government = GameController.getGame().getCurrentGovernment();
        if (cost > government.getGold()) return "you don't have enough gold!";
        int addedCount = GovernmentController.generateProduct(government, name, amount);

        cost = product.getPrice() * addedCount;
        if (addedCount == 0)
            return "storage is full!";
        if (addedCount == -1)
            return "no " + product.getNameOfStorage() + " to store this product!";

        government.addGold(-cost);
        if (addedCount != amount)
            return "storage is full!\n" + addedCount + " " + name + " bought successfully!";
        return addedCount + " " + name + " bought successfully!";
    }

    public static String sellItem(String name, int amount) {
        String message = validateItems(name, amount);
        if (message != null) {
            return message;
        }
        Goods product = GameGoods.getProduct(name);
        int cost = (int) Math.ceil(product.getPrice() * 0.5) * amount;
        Government government = GameController.getGame().getCurrentGovernment();

        boolean check = GovernmentController.consumeProduct(government, name, amount);
        if (!check) {
            return "your resource is not enough!";
        }
        government.addGold(cost);
        return amount + " " + name + " sold successfully!";
    }

    private static String validateItems(String name, int amount) {
        if (checkNullFields(name)) {
            return "name field is required!";
        }

        if (checkNullFields(amount)) {
            return "amount field is required!";
        }

        if (amount == 0) {
            return "amount value can not be 0!";
        }

        if (GameGoods.getProduct(name) == null) {
            return "product name is invalid!";
        }
        return null;
    }

    private static boolean checkNullFields(String input) {
        return input == null || input.length() == 0;
    }

    private static boolean checkNullFields(int input) {
        return input == -1;
    }
}
