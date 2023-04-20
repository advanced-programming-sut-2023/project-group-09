package controller.gamestructure;

import model.goods.Goods;

public class GameResources extends GameGoods {
    protected static void createStone() {
        Goods resource = new Goods("stone" , "resource" , 70);
    }

    protected static void createIron() {
        Goods resource = new Goods("iron" , "resource" , 225);
    }

    protected static void createWood() {
        Goods resource = new Goods("wood" , "resource" , 20);
    }

    protected static void createPitch() {
        Goods resource = new Goods("pitch" , "resource" , 100);
    }

    protected static void addResources() {
        createIron();
        createPitch();
        createStone();
        createWood();
    }
}
