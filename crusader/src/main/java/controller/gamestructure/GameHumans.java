package controller.gamestructure;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import enumeration.Speed;
import enumeration.dictionary.Weapons;
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

    public static void createArcher(){
        int speed = Speed.FAST.getRate();
        int defenceRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();

        EuropeanTroop archer = new EuropeanTroop(speed,defenceRating,1,attackRating);
    }

    public static void createCrossbowman() {
        int speed = Speed.SLOW.getRate();
        int defenceRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();

        EuropeanTroop crossbowman = new EuropeanTroop(speed,defenceRating,1,attackRating);
    }

    public static void createSpearman() {
        int speed = Speed.AVERAGE.getRate();
        int defenceRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop spearman = new EuropeanTroop(speed,defenceRating,1,attackRating);

    }

    public static void createPikeman() {
        int speed = Speed.SLOW.getRate();
        int defenceRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        EuropeanTroop pikeman = new EuropeanTroop(speed,defenceRating,1,attackRating);
    }

    public static void createMaceman() {
        int speed = Speed.AVERAGE.getRate();
        int defenceRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop maceman = new EuropeanTroop(speed,defenceRating,1,attackRating);
    }

    public static void createSwordsman() {
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
