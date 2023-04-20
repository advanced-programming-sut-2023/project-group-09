package controller.gamestructure;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import enumeration.Speed;
import model.human.Human;
import model.human.military.ArabianMercenary;
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
        EuropeanTroop archer = new EuropeanTroop(speed, defenseRating, 10, attackRating, 12);
        archer.setWeapon("bow");
        archer.enableDigsMoat();
        archer.enableUsesLadder();
    }

    public static void createCrossbowman() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();
        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 10, attackRating, 20);
        crossbowman.setWeapon("crossbow");
        crossbowman.addArmour("leatherArmour");
    }

    public static void createSpearman() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop spearman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 8);
        spearman.setWeapon("spear");
        spearman.enableUsesLadder();
        spearman.enableDigsMoat();
    }

    public static void createPikeman() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop pikeman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 20);
        pikeman.setWeapon("pike");
        pikeman.addArmour("metalArmour");
        pikeman.enableDigsMoat();
    }

    public static void createMaceman() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        EuropeanTroop maceman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 20);
        maceman.setWeapon("mace");
        maceman.addArmour("leatherArmour");
        maceman.enableDigsMoat();
        maceman.enableDigsMoat();
    }

    public static void createSwordsman() {
        int speed = Speed.EXTREMELY_SLOW.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.VERY_HIGH.getRate();
        EuropeanTroop swordsman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 40);
        swordsman.setWeapon("sword");
        swordsman.addArmour("metalArmour");
    }

    public static void createKnight() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.VERY_HIGH.getRate();
        EuropeanTroop knight = new EuropeanTroop(speed, defenseRating, 1, attackRating, 40);
        knight.setWeapon("sword");
        knight.addArmour("metalArmour");
    }

    public static void createTunneler() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop tunneler = new EuropeanTroop(speed, defenseRating, 1, attackRating, 30);
    }

    public static void createLadderman() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.NONE.getRate();
        EuropeanTroop ladderman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 4);
    }

    public static void createEngineer() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.NONE.getRate();
        EuropeanTroop engineer = new EuropeanTroop(speed, defenseRating, 1, attackRating, 30);
        engineer.enableDigsMoat();
    }

    public static void createBlackMonk() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop blackmonk = new EuropeanTroop(speed, defenseRating, 1, attackRating, 10);
    }

    public static void createArcherBow() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary archerBow = new ArabianMercenary(speed, defenseRating, 10, attackRating, 75);
        archerBow.setWeapon("bow");
        archerBow.enableDigsMoat();
    }

    public static void createSlave() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.EXTREMELY_LOW.getRate();
        int attackRating = AttackRating.EXTREMELY_LOW.getRate();
        ArabianMercenary slave = new ArabianMercenary(speed, defenseRating, 1, attackRating, 5);
        slave.enableDigsMoat();
    }

    public static void createSlinger() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary slinger = new ArabianMercenary(speed, defenseRating, 5, attackRating, 12);

    }

    public static void createAssassin() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        ArabianMercenary assassin = new ArabianMercenary(speed, defenseRating, 1, attackRating, 60);
    }

    public static void createHorseArcher() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary horseArcher = new ArabianMercenary(speed, defenseRating, 10, attackRating, 80);
        horseArcher.setWeapon("bow");
    }

    public static void createArabianSwordsman() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        ArabianMercenary arabianSwordsman = new ArabianMercenary(speed, defenseRating, 1, attackRating, 80);
        arabianSwordsman.addArmour("metalArmour");
    }

    public static void createFireThrower() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        ArabianMercenary fireThrower = new ArabianMercenary(speed, defenseRating, 5, attackRating, 100);
    }
}
