package controller.gamestructure;

import model.goods.Goods;

public class GameResources extends GameGoods {
    protected static void createStone() {
        Goods resource = new Goods("stone" , "resource" ,"stockPile", 70);
        GameGoods.goods.put("stone",resource);
        GameGoods.resources.put("stone",resource);
    }

    protected static void createIron() {
        Goods resource = new Goods("iron" , "resource" ,"stockPile", 225);
        GameGoods.goods.put("iron",resource);
        GameGoods.resources.put("iron",resource);
    }

    protected static void createWood() {
        Goods resource = new Goods("wood" , "resource" ,"stockPile", 20);
        GameGoods.goods.put("wood",resource);
        GameGoods.resources.put("wood",resource);
    }

    protected static void createPitch() {
        Goods resource = new Goods("pitch" , "resource" ,"stockPile", 100);
        GameGoods.goods.put("pitch",resource);
        GameGoods.resources.put("pitch",resource);
    }


    protected static void createWheat() {
        Goods food = new Goods("wheat", "resource", "stockPile",115);
        GameFoods.resources.put("wheat",food);
        GameFoods.goods.put("wheat",food);
    }

    protected static void createFlour() {
        Goods food = new Goods("flour", "resource", "stockPile",160);
        food.addRequired("wheat", 1);
        GameFoods.resources.put("flour",food);
        GameFoods.goods.put("flour",food);
    }

    protected static void createHops() {
        Goods food = new Goods("hops", "resource", "stockPile",75);
        GameFoods.resources.put("hops",food);
        GameFoods.goods.put("hops",food);
    }

    protected static void createAle() {
        Goods food = new Goods("ale", "resource", "stockPile",100);
        food.addRequired("hops", 1);
        GameFoods.resources.put("ale",food);
        GameFoods.goods.put("ale",food);
    }


    protected static void addResources() {
        createIron();
        createPitch();
        createStone();
        createWood();
        createWheat();
        createFlour();
        createAle();
        createHops();
    }
}
