package controller.gamestructure;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import enumeration.Speed;
import model.Government;
import model.building.Building;
import model.human.Human;
import model.human.military.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;

public class GameHumans {
    public static HashMap<String, Military> militaries = new HashMap<>();

    public static Military getUnit(String name, Government government, int x, int y) {
        Military military= getClone(militaries.get(name));
        military.setGovernment(government);
        military.setX(x);
        military.setY(y);
        return military;
    }

    public static Military getUnit(String name) {
        return militaries.get(name);
    }
    public static void addEuropeanTroops() {
        createArcher();
        createCrossbowman();
        createSpearman();
        createPikeman();
        createMaceman();
        createSwordsman();
        createKnight();
        createLadderman();
        addLord();
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
        int speed = Speed.EXTREMELY_SLOW.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        Lord lord = new Lord(speed , defenseRating , 1 , attackRating , 0);
        lord.setName("lord");
        militaries.put("lord" , lord);
    }

    public static void createArcher() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        EuropeanTroop archer = new EuropeanTroop(speed, defenseRating, 10, attackRating, 12);
        archer.setName("archer");
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
        crossbowman.setName("crossbowman");
        crossbowman.setWeapon("crossbow");
        crossbowman.addArmour("leatherArmour");
        militaries.put("crossbowman",crossbowman);
    }

    public static void createSpearman() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop spearman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 8);
        spearman.setName("spearman");
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
        pikeman.setName("pikeman");
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
        maceman.setName("maceman");
        maceman.setWeapon("mace");
        maceman.addArmour("leatherArmour");
        maceman.enableDigsMoat();
        militaries.put("maceman",maceman);
    }

    public static void createSwordsman() {
        int speed = Speed.EXTREMELY_SLOW.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.VERY_HIGH.getRate();
        EuropeanTroop swordsman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 40);
        swordsman.setName("swordsman");
        swordsman.setWeapon("sword");
        swordsman.addArmour("metalArmour");
        militaries.put("swordsman",swordsman);
    }

    public static void createKnight() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.VERY_HIGH.getRate();
        EuropeanTroop knight = new EuropeanTroop(speed, defenseRating, 1, attackRating, 40);
        knight.setName("knight");
        knight.setWeapon("sword");
        knight.addArmour("metalArmour");
        militaries.put("knight",knight);
    }

    public static void createTunneler() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        Tunneler tunneler = new Tunneler(speed, defenseRating, 1, attackRating, 30);
        tunneler.setName("tunneler");
        militaries.put("tunneler",tunneler);
    }

    public static void createLadderman() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.NONE.getRate();
        EuropeanTroop ladderman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 4);
        ladderman.setName("ladderman");
        militaries.put("ladderman" , ladderman);
    }

    public static void createEngineer() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.NONE.getRate();
        Engineer engineer = new Engineer(speed, defenseRating, 1, attackRating, 30);
        engineer.setName("engineer");
        engineer.enableDigsMoat();
        militaries.put("engineer" , engineer);
    }

    public static void createBlackMonk() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop blackmonk = new EuropeanTroop(speed, defenseRating, 1, attackRating, 10);
        blackmonk.setName("blackmonk");
        militaries.put("blackmonk",blackmonk);
    }

    public static void createArcherBow() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary archerBow = new ArabianMercenary(speed, defenseRating, 10, attackRating, 75);
        archerBow.setName("archerbow");
        archerBow.setWeapon("bow");
        archerBow.enableDigsMoat();
        militaries.put("archerBow",archerBow);
    }

    public static void createSlave() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.EXTREMELY_LOW.getRate();
        int attackRating = AttackRating.EXTREMELY_LOW.getRate();
        ArabianMercenary slave = new ArabianMercenary(speed, defenseRating, 1, attackRating, 5);
        slave.setName("slave");
        slave.enableDigsMoat();
        militaries.put("slave",slave);
    }

    public static void createSlinger() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary slinger = new ArabianMercenary(speed, defenseRating, 5, attackRating, 12);
        slinger.setName("slinger");
        militaries.put("slinger ",slinger );
    }

    public static void createAssassin() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        ArabianMercenary assassin = new ArabianMercenary(speed, defenseRating, 1, attackRating, 60);
        assassin.setName("assassin");
        militaries.put("assassin",assassin);
        assassin.enableInvisible();
    }

    public static void createHorseArcher() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary horseArcher = new ArabianMercenary(speed, defenseRating, 10, attackRating, 80);
        horseArcher.setName("horseArcher");
        horseArcher.setWeapon("bow");
        militaries.put("horseArcher",horseArcher);
    }

    public static void createArabianSwordsman() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        ArabianMercenary arabianSwordsman = new ArabianMercenary(speed, defenseRating, 1, attackRating, 80);
        arabianSwordsman.setName("arabianSwordsman");
        arabianSwordsman.addArmour("metalArmour");
        militaries.put("arabianSwordsman",arabianSwordsman);
    }

    public static void createFireThrower() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        ArabianMercenary fireThrower = new ArabianMercenary(speed, defenseRating, 5, attackRating, 100);
        fireThrower.setName("fireThrower");
        militaries.put("fireThrower",fireThrower);
    }


    public static Military getClone(Military military){
        if(military instanceof Engineer){
            return new Engineer((Engineer) military);
        }

        if(military instanceof  Tunneler){
            return new Tunneler((Tunneler) military);
        }

        if(military instanceof  EuropeanTroop){
            return new EuropeanTroop((EuropeanTroop) military);
        }
        if(military instanceof  ArabianMercenary){
            return new ArabianMercenary((ArabianMercenary) military);
        }
        if(military instanceof Lord){
            return new Lord((Lord) military);
        }
        return null;
    }
}
