package controller.gamestructure;

import model.goods.Goods;

public class GameFoods extends GameGoods {
    protected static void createMeat() {
        Goods food = new Goods("meat", "food","granary", 40);
        food.addRequired("cow", 1);
        GameFoods.foods.put("meat",food);
        GameFoods.goods.put("meat",food);
    }

    protected static void createCheese() {
        Goods food = new Goods("cheese", "food","granary",40);
        food.addRequired("cow", 1);
        GameFoods.foods.put("cheese",food);
        GameFoods.goods.put("cheese",food);
    }

    protected static void createApple() {
        Goods food = new Goods("apple", "food","granary", 40);
        GameFoods.foods.put("apple",food);
        GameFoods.goods.put("apple",food);
    }

    protected static void createBread() {
        Goods food = new Goods("bread", "food","granary", 40);
        food.addRequired("flour", 1);
        GameFoods.foods.put("bread",food);
        GameFoods.goods.put("bread",food);
    }

    protected static void addFoods() {
        createMeat();
        createBread();
        createApple();
        createCheese();
    }
}
