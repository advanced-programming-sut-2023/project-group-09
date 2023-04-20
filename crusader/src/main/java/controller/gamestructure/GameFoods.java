package controller.gamestructure;

import model.goods.Goods;

public class GameFoods extends GameGoods {
    protected static void createMeat() {
        Goods food = new Goods("meat", "food", 40);
        food.addRequired("cow", 1);
        GameFoods.foods.put("meat",food);
        GameFoods.goods.put("meat",food);
    }

    protected static void createCheese() {
        Goods food = new Goods("cheese", "food", 40);
        food.addRequired("cow", 1);
        GameFoods.foods.put("cheese",food);
        GameFoods.goods.put("cheese",food);
    }

    protected static void createApple() {
        Goods food = new Goods("apple", "food", 40);
        GameFoods.foods.put("apple",food);
        GameFoods.goods.put("apple",food);
    }

    protected static void createWheat() {
        Goods food = new Goods("wheat", "food", 115);
        GameFoods.foods.put("wheat",food);
        GameFoods.goods.put("wheat",food);
    }

    protected static void createFlour() {
        Goods food = new Goods("flour", "food", 160);
        food.addRequired("wheat", 1);
        GameFoods.foods.put("flour",food);
        GameFoods.goods.put("flour",food);
    }

    protected static void createBread() {
    }

    protected static void createHops() {
    }

    protected static void createAle() {
    }

    protected static void addFoods() {
        createMeat();
        createWheat();
        createFlour();
        createAle();
        createBread();
        createHops();
        createApple();
        createCheese();
    }
}
