package controller.gamestructure;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import enumeration.Speed;
import model.human.Human;
import model.human.military.EuropeanTroop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GameHumans {
    public static HashMap<String, Human> humans = new HashMap<String, Human>();

    public static void addHumans() {
    }

    public static void addEuropeanTroops() {
    }

    public static void addArabianMercenaries() {
    }

    public static void addLord() {
    }

    public static void createArcher() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        EuropeanTroop archer = new EuropeanTroop(speed, defenseRating, 10, attackRating);
        archer.setWeapon("bow");
        archer.addArmour("bow");
    }

    public static void createCrossbowman() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();
        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        crossbowman.setWeapon("crossbow");
        crossbowman.addArmour("crossbow");
        crossbowman.addArmour("leatherArmour");
    }

    public static void createSpearman() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop spearman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        spearman.setWeapon("spear");
        spearman.addArmour("spear");
    }

    public static void createPikeman() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop pikeman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        pikeman.setWeapon("pike");
        pikeman.addArmour("pike");
        pikeman.addArmour("metalArmour");
    }

    public static void createMaceman() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        EuropeanTroop maceman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        maceman.setWeapon("mace");
        maceman.addArmour("mace");
        maceman.addArmour("leatherArmour");
    }

    public static void createSwordsman() {
        int speed = Speed.EXTREMELY_SLOW.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.VERY_HIGH.getRate();
        EuropeanTroop swordsman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        swordsman.setWeapon("sword");
        swordsman.addArmour("sword");
        swordsman.addArmour("metalArmour");
    }

    public static void createKnight() {
    }

    public static void createTunneler() {
    }

    public static void createLadderman() {
    }

    public static void createEngineer() {
    }

    public static void createBlackMonk() {
    }

    public static void createArcherBow() {
    }

    public static void createSlave() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.EXTREMELY_LOW.getRate();
        int attackRating = AttackRating.EXTREMELY_LOW.getRate();
        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        crossbowman.setWeapon("torch");
    }

    public static void createSlinger() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        crossbowman.setWeapon("sling");
    }

    public static void createAssassin() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        crossbowman.setWeapon("scimitar");
    }

    public static void createHorseArcher() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();
        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        crossbowman.setWeapon("bow");
        crossbowman.addArmour("horse");
    }

    public static void createArabianSwordsman() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        crossbowman.setWeapon("scimitar");
        crossbowman.addArmour("metalArmour");
    }

    public static void createFireThrower() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
        crossbowman.setWeapon("greekFire");
    }
}
