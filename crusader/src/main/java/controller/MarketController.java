package controller;

import controller.gamestructure.GameGoods;
import model.Government;
import model.goods.Goods;
import model.menugui.MenuTextField;

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

    public static void buyItem(String name, MenuTextField buyAmount) {
        int amount = validateAmount(buyAmount);
        if (amount == 0) return;
        Goods product = GameGoods.getProduct(name);
        int cost = product.getPrice() * amount;
        Government government = GameController.getGame().getCurrentGovernment();
        if (cost > government.getGold()) buyAmount.handlingError("not enough gold!");
        int addedCount = GovernmentController.generateProduct(government, name, amount);

        cost = product.getPrice() * addedCount;
        if (addedCount == 0) {
            buyAmount.handlingError("storage is full!");
            return;
        }
        if (addedCount == -1) {
            buyAmount.handlingError("no " + product.getNameOfStorage() + " to store this product!");
            return;
        }

        government.addGold(-cost);
        buyAmount.handlingCorrect(addedCount + " " + name + " bought successfully!");
    }

    public static void sellItem(String name, MenuTextField sellAmount) {
        int amount = validateAmount(sellAmount);
        if (amount == 0) return;
        Goods product = GameGoods.getProduct(name);
        int cost = (int) Math.ceil(product.getPrice() * 0.5) * amount;
        Government government = GameController.getGame().getCurrentGovernment();

        boolean check = GovernmentController.consumeProduct(government, name, amount);
        if (!check) {
            sellAmount.handlingError("not enough resource!");
            return;
        }
        government.addGold(cost);
        sellAmount.handlingCorrect(amount + " " + name + " sold successfully!");
    }

    public static int validateAmount(MenuTextField amount) {
        amount.clearErrorOrMessage();
        if (MarketController.checkNullFields(amount.getText())) {
            amount.handlingError("amount is required!");
            return 0;
        }

        int amountOfGoods = 0;
        try {
            amountOfGoods = Integer.parseInt(amount.getText());
        } catch (Exception e) {
            amount.handlingError("invalid amount!");
            return 0;
        }

        if (amountOfGoods <= 0) {
            amount.handlingError("amount should be positive!");
            return 0;
        }
        return amountOfGoods;
    }

    public static boolean checkNullFields(String input) {
        return input == null || input.length() == 0;
    }

    private static boolean checkNullFields(int input) {
        return input == -1;
    }
}
