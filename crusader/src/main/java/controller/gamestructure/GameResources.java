package controller.gamestructure;

import model.goods.Goods;

public class GameResources extends GameGoods {
    protected static void createStone() {
        Goods resource = new Goods("stone" , "resource" , 70);
        GameGoods.goods.put("stone",resource);
        GameGoods.resources.put("stone",resource);
    }

    protected static void createIron() {
        Goods resource = new Goods("iron" , "resource" , 225);
        GameGoods.goods.put("iron",resource);
        GameGoods.resources.put("iron",resource);
    }

    protected static void createWood() {
        Goods resource = new Goods("wood" , "resource" , 20);
        GameGoods.goods.put("wood",resource);
        GameGoods.resources.put("wood",resource);
    }

    protected static void createPitch() {
        Goods resource = new Goods("pitch" , "resource" , 100);
        GameGoods.goods.put("pitch",resource);
        GameGoods.resources.put("pitch",resource);
    }

    protected static void addResources() {
        createIron();
        createPitch();
        createStone();
        createWood();
    }
}
