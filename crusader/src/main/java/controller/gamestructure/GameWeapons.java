package controller.gamestructure;

import model.goods.Goods;

public class GameWeapons extends GameGoods {
    protected static void createMace() {
        Goods weapon = new Goods("mace", "weapon", 290);
        weapon.addRequired("iron", 1);
        GameGoods.goods.put("mace",weapon);
        GameGoods.weapons.put("mace",weapon);
    }

    protected static void createSwords() {
        Goods weapon = new Goods("sword", "weapon", 290);
        weapon.addRequired("iron", 1);
        GameGoods.goods.put("sword",weapon);
        GameGoods.weapons.put("sword",weapon);
    }

    protected static void createBow() {
        Goods weapon = new Goods("bow", "weapon", 155);
        weapon.addRequired("wood", 2);
        GameGoods.goods.put("bow",weapon);
        GameGoods.weapons.put("bow",weapon);
    }

    protected static void createSpear() {
        Goods weapon = new Goods("spear", "weapon", 100);
        weapon.addRequired("wood", 1);
        GameGoods.goods.put("spear",weapon);
        GameGoods.weapons.put("spear",weapon);
    }

    protected static void createPike() {
        Goods weapon = new Goods("pike", "weapon", 180);
        weapon.addRequired("wood", 2);
        GameGoods.goods.put("pike",weapon);
        GameGoods.weapons.put("pike",weapon);
    }

    protected static void createLeatherArmour() {
        Goods weapon = new Goods("leatherArmour", "weapon", 125);
        weapon.addRequired("cow", 1);
        GameGoods.goods.put("leatherArmour",weapon);
        GameGoods.weapons.put("leatherArmour",weapon);
    }

    protected static void createMetalArmour() {
        Goods weapon = new Goods("metalArmour", "weapon", 290);
        weapon.addRequired("iron", 1);
        GameGoods.goods.put("metalArmour",weapon);
        GameGoods.weapons.put("metalArmour",weapon);
    }

    protected static void createCrossbow() {
        Goods weapon = new Goods("crossbow", "weapon", 290);
        weapon.addRequired("wood", 3);
        GameGoods.goods.put("crossBow",weapon);
        GameGoods.weapons.put("crossBow",weapon);
    }

    protected static void addWeapons() {
        createBow();
        createMace();
        createPike();
        createSpear();
        createSwords();
        createLeatherArmour();
        createMetalArmour();
        createCrossbow();
    }
}
