package controller.gamestructure;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import enumeration.Speed;
import model.human.military.ArabianMercenary;
import model.human.military.EuropeanTroop;
import model.human.military.Military;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GameHumans {
    public static HashMap<String, Military> militaries = new HashMap<>();

    public static void addHumans() {
    }

    public static void addEuropeanTroops() {
        createArcher();
        createCrossbowman();
        createSpearman();
        createPikeman();
        createMaceman();
        createSwordsman();
        createKnight();
    }

    public static void addArabianMercenaries() {
        createArcherBow();
        createSlave();
        createSlinger();
        createAssassin();
        createHorseArcher();
        createArabianSwordsman();
        createFireThrower();
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
        militaries.put("archer",archer);
    }

    public static void createCrossbowman() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();
        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 10, attackRating, 20);
        crossbowman.setWeapon("crossbow");
        crossbowman.addArmour("leatherArmour");
        militaries.put("crossbowman",crossbowman);
    }

    public static void createSpearman() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop spearman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 8);
        spearman.setWeapon("spear");
        spearman.enableUsesLadder();
        spearman.enableDigsMoat();
        militaries.put("spearman",spearman);
    }

    public static void createPikeman() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop pikeman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 20);
        pikeman.setWeapon("pike");
        pikeman.addArmour("metalArmour");
        pikeman.enableDigsMoat();
        militaries.put("pikeman",pikeman);
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
        militaries.put("maceman",maceman);
    }

    public static void createSwordsman() {
        int speed = Speed.EXTREMELY_SLOW.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.VERY_HIGH.getRate();
        EuropeanTroop swordsman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 40);
        swordsman.setWeapon("sword");
        swordsman.addArmour("metalArmour");
        militaries.put("swordsman",swordsman);
    }

    public static void createKnight() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.VERY_HIGH.getRate();
        EuropeanTroop knight = new EuropeanTroop(speed, defenseRating, 1, attackRating, 40);
        knight.setWeapon("sword");
        knight.addArmour("metalArmour");
        militaries.put("knight",knight);
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
        militaries.put("blackmonk",blackmonk);
    }

    public static void createArcherBow() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary archerBow = new ArabianMercenary(speed, defenseRating, 10, attackRating, 75);
        archerBow.setWeapon("bow");
        archerBow.enableDigsMoat();
        militaries.put("archerBow",archerBow);
    }

    public static void createSlave() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.EXTREMELY_LOW.getRate();
        int attackRating = AttackRating.EXTREMELY_LOW.getRate();
        ArabianMercenary slave = new ArabianMercenary(speed, defenseRating, 1, attackRating, 5);
        slave.enableDigsMoat();
        militaries.put("slave",slave);
    }

    public static void createSlinger() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary slinger = new ArabianMercenary(speed, defenseRating, 5, attackRating, 12);
        militaries.put("slinger ",slinger );
    }

    public static void createAssassin() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        ArabianMercenary assassin = new ArabianMercenary(speed, defenseRating, 1, attackRating, 60);
        militaries.put("assassin",assassin);
        assassin.enableInvisible();
    }

    public static void createHorseArcher() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary horseArcher = new ArabianMercenary(speed, defenseRating, 10, attackRating, 80);
        horseArcher.setWeapon("bow");
        militaries.put("horseArcher",horseArcher);
    }

    public static void createArabianSwordsman() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        ArabianMercenary arabianSwordsman = new ArabianMercenary(speed, defenseRating, 1, attackRating, 80);
        arabianSwordsman.addArmour("metalArmour");
        militaries.put("arabianSwordsman",arabianSwordsman);
    }

    public static void createFireThrower() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        ArabianMercenary fireThrower = new ArabianMercenary(speed, defenseRating, 5, attackRating, 100);
        militaries.put("fireThrower",fireThrower);
    }
}
