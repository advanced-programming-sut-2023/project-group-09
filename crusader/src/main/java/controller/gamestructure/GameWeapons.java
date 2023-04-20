package controller.gamestructure;

import model.goods.Goods;

public class GameWeapons extends GameGoods {
    protected static void createMace() {
        Goods weapon = new Goods("mace", "weapon", 290);
        weapon.addRequired("iron", 1);
    }

    protected static void createSwords() {
        Goods weapon = new Goods("sword", "weapon", 290);
        weapon.addRequired("iron", 1);
    }

    protected static void createBow() {
        Goods weapon = new Goods("bow", "weapon", 155);
        weapon.addRequired("wood", 2);
    }

    protected static void createSpear() {
        Goods weapon = new Goods("spear", "weapon", 100);
        weapon.addRequired("wood", 1);
    }

    protected static void createPike() {
        Goods weapon = new Goods("pike", "weapon", 180);
        weapon.addRequired("wood", 2);
    }

    protected static void createLeatherArmour() {
        Goods weapon = new Goods("leatherArmour", "weapon", 125);
        weapon.addRequired("cow", 1);
    }

    protected static void createMetalArmour() {
        Goods weapon = new Goods("metalArmour", "weapon", 290);
        weapon.addRequired("iron", 1);
    }

    protected static void createCrossBow() {
        Goods weapon = new Goods("crossBow", "weapon", 290);
        weapon.addRequired("wood", 3);
    }

    protected static void addWeapons() {
        createBow();
        createMace();
        createPike();
        createSpear();
        createSwords();
        createLeatherArmour();
        createMetalArmour();
        createCrossBow();
    }
}
