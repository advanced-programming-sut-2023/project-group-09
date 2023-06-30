package controllers;

import controllers.gamestructure.GameGoods;
import model.Government;
import model.goods.Goods;
import model.menugui.MenuPopUp;
import model.menugui.MenuTextField;
import view.menus.GameMenu;

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

    public static boolean buyItem(String name, MenuTextField buyAmount) {
        if (buyAmount.getText().isEmpty() || buyAmount.getText() == null) {
            buyAmount.handlingError("amount is required!");
            return false;
        }
        int amount = Integer.parseInt(buyAmount.getText());
        Goods product = GameGoods.getProduct(name);
        int cost = product.getPrice() * amount;
        Government government = GameController.getGame().getCurrentGovernment();
        if (cost > government.getGold()) buyAmount.handlingError("not enough gold!");
        int addedCount = GovernmentController.generateProduct(government, name, amount);

        cost = product.getPrice() * addedCount;
        if (addedCount == 0) {
            MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400, "error", "storage is full!");
            GameMenu.root.getChildren().add(popUp);
            return false;
        }
        if (addedCount == -1) {
            MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400, "error",
                    "no " + product.getNameOfStorage() + " to store this product!");
            GameMenu.root.getChildren().add(popUp);
            return false;
        }

        government.addGold(-cost);
        return true;
    }

    public static boolean sellItem(String name, MenuTextField sellAmount) {
        if (sellAmount.getText().isEmpty() || sellAmount.getText() == null) {
            sellAmount.handlingError("amount is required!");
            return false;
        }
        int amount = Integer.parseInt(sellAmount.getText());
        Goods product = GameGoods.getProduct(name);
        int cost = (int) Math.ceil(product.getPrice() * 0.5) * amount;
        Government government = GameController.getGame().getCurrentGovernment();

        boolean check = GovernmentController.consumeProduct(government, name, amount);
        if (!check) {
            MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400, "error",
                    "not enough resource!");
            GameMenu.root.getChildren().add(popUp);
            return false;
        }
        government.addGold(cost);
        return true;
    }

    public static boolean checkNullFields(String input) {
        return input == null || input.length() == 0;
    }

    private static boolean checkNullFields(int input) {
        return input == -1;
    }
}
