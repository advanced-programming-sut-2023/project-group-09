package controller.gamestructure;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import enumeration.Speed;
import model.human.Human;
import model.human.military.EuropeanTroop;

import java.util.HashMap;

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
    }

    public static void createCrossbowman() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();

        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
    }

    public static void createSpearman() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop spearman = new EuropeanTroop(speed, defenseRating, 1, attackRating);

    }

    public static void createPikeman() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop pikeman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
    }

    public static void createMaceman() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        EuropeanTroop maceman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
    }

    public static void createSwordsman() {
        int speed = Speed.EXTREMELY_SLOW.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.VERY_HIGH.getRate();
        EuropeanTroop swordsman = new EuropeanTroop(speed, defenseRating, 1, attackRating);
//        swordsman.setWeapon();
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
    }

    public static void createSlinger() {
    }

    public static void createAssassin() {
    }

    public static void createHorseArcher() {
    }

    public static void createArabianSwordsman() {
    }

    public static void createFireThrower() {
    }
}
