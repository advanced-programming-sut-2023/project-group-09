package controller.gamestructure;

import model.goods.Goods;

public class GameFoods extends GameGoods {
    protected static void createMeat() {
    }

    protected static void createCheese() {
    }

    protected static void createApple() {
    }

    protected static void createWheat() {
    }

    protected static void createFlour() {
    }

    protected static void createBread() {
        Goods weapon = new Goods("bread", "food", 40);
        weapon.addRequired("iron", 1);
    }

    protected static void createHops() {
    }

    protected static void createAle() {
    }

    protected static void addFoods() {
    }
}
