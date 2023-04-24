package controller;

import controller.gamestructure.GameGoods;
import model.Government;
import model.goods.Goods;

import java.util.HashMap;

public class ShopController {
    public static String showPriceList() {
        HashMap<String, Goods> goodsHashMap = GameGoods.goods;
        StringBuilder output = new StringBuilder();
        Government government = GameController.getGame().getCurrentGovernment();
        for (String key : goodsHashMap.keySet().stream().sorted().toList()) {
            Goods product = goodsHashMap.get(key);
            output.append(key).append(" type: ").append(product.getType()).append(" sell price: ").append(product.getPrice());
            output.append(" buy price: ").append((int)Math.ceil(product.getPrice() * 0.5));
            output.append(" count: ").append(government.getPropertyAmount(key)).append("\n");
        }
        return output.toString();
    }

    public static String buyItem(String name, int amount) {
        String message = validateItems(name, amount);
        if (message != null) {
            return message;
        }
        Goods product = GameGoods.getProduct(name);
        int cost = product.getPrice() * amount;
        Government government = GameController.getGame().getCurrentGovernment();
        if (cost > government.getGold()) {
            return "your money is not enough!";
        }
        int addedCount = GovernmentController.generateProduct(government, name, amount);
        government.addGold(-cost);
        if (addedCount != amount) {
            return "storage is full!\n" + addedCount + " " + name + " bought successfully!";
        }
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
        if(!check){
            return "your resource is not enough";
        }
        government.addGold(cost);
        return amount + " " + name + "sold successfully!";
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
