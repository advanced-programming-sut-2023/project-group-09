package controller.gamestructure;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import enumeration.Speed;
import model.Government;
import model.human.military.*;

import java.util.HashMap;

public class GameHumans {
    public static HashMap<String, Military> militaries = new HashMap<>();

    public static Military getUnit(String name, Government government, int x, int y) {
        Military military = getClone(militaries.get(name));
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
        createBlackMonk();
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
        EuropeanTroop lord = new EuropeanTroop(speed, defenseRating, 1, attackRating, 0);
        lord.setName("lord");
        lord.setDefenseRange(3);
        lord.setAggressiveRange(6);
        militaries.put("lord", lord);
    }

    public static void createArcher() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        EuropeanTroop archer = new EuropeanTroop(speed, defenseRating, 5, attackRating, 12);
        archer.setName("archer");
        archer.setWeapon("bow");
        archer.enableDigsMoat();
        archer.enableUsesLadder();
        archer.setDefenseRange(8);
        archer.setAggressiveRange(11);
        militaries.put("archer", archer);
    }

    public static void createCrossbowman() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();
        EuropeanTroop crossbowman = new EuropeanTroop(speed, defenseRating, 3, attackRating, 20);
        crossbowman.setName("crossbowman");
        crossbowman.setWeapon("crossbow");
        crossbowman.addArmour("leatherArmour");
        crossbowman.setDefenseRange(6);
        crossbowman.setAggressiveRange(9);
        militaries.put("crossbowman", crossbowman);
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
        spearman.setDefenseRange(4);
        spearman.setAggressiveRange(7);
        militaries.put("spearman", spearman);
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
        pikeman.setDefenseRange(4);
        pikeman.setAggressiveRange(7);
        militaries.put("pikeman", pikeman);
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
        maceman.setDefenseRange(4);
        maceman.setAggressiveRange(7);
        maceman.setUsesLadder(true);
        militaries.put("maceman", maceman);
    }

    public static void createSwordsman() {
        int speed = Speed.EXTREMELY_SLOW.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.VERY_HIGH.getRate();
        EuropeanTroop swordsman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 40);
        swordsman.setName("swordsman");
        swordsman.setWeapon("sword");
        swordsman.addArmour("metalArmour");
        swordsman.setDefenseRange(4);
        swordsman.setAggressiveRange(7);
        militaries.put("swordsman", swordsman);
    }

    public static void createKnight() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.VERY_HIGH.getRate();
        EuropeanTroop knight = new EuropeanTroop(speed, defenseRating, 1, attackRating, 40);
        knight.setName("knight");
        knight.setWeapon("sword");
        knight.addArmour("metalArmour");
        knight.setDefenseRange(4);
        knight.setAggressiveRange(7);
        militaries.put("knight", knight);
    }

    public static void createTunneler() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        Tunneler tunneler = new Tunneler(speed, defenseRating, 1, attackRating, 30);
        tunneler.setName("tunneler");
        tunneler.setDefenseRange(4);
        tunneler.setAggressiveRange(7);
        militaries.put("tunneler", tunneler);
    }

    public static void createLadderman() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.NONE.getRate();
        EuropeanTroop ladderman = new EuropeanTroop(speed, defenseRating, 1, attackRating, 4);
        ladderman.setName("ladderman");
        militaries.put("ladderman", ladderman);
    }

    public static void createEngineer() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.NONE.getRate();
        Engineer engineer = new Engineer(speed, defenseRating, 1, attackRating, 30);
        engineer.setName("engineer");
        engineer.enableDigsMoat();
        engineer.setDefenseRange(5);
        engineer.setAggressiveRange(5);
        militaries.put("engineer", engineer);
    }

    public static void createBlackMonk() {
        int speed = Speed.SLOW.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        EuropeanTroop blackmonk = new EuropeanTroop(speed, defenseRating, 1, attackRating, 10);
        blackmonk.setName("blackmonk");
        blackmonk.setDefenseRange(4);
        blackmonk.setAggressiveRange(7);
        militaries.put("blackmonk", blackmonk);
    }

    public static void createArcherBow() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary archerBow = new ArabianMercenary(speed, defenseRating, 5, attackRating, 75);
        archerBow.setName("archerbow");
        archerBow.setWeapon("bow");
        archerBow.enableDigsMoat();
        archerBow.setDefenseRange(8);
        archerBow.setAggressiveRange(11);
        militaries.put("archerBow", archerBow);
    }

    public static void createSlave() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.EXTREMELY_LOW.getRate();
        int attackRating = AttackRating.EXTREMELY_LOW.getRate();
        ArabianMercenary slave = new ArabianMercenary(speed, defenseRating, 1, attackRating, 5);
        slave.setName("slave");
        slave.enableDigsMoat();
        militaries.put("slave", slave);
    }

    public static void createSlinger() {
        int speed = Speed.FAST.getRate();
        int defenseRating = DefenseRating.VERY_LOW.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary slinger = new ArabianMercenary(speed, defenseRating, 5, attackRating, 12);
        slinger.setName("slinger");
        slinger.setDefenseRange(8);
        slinger.setAggressiveRange(11);
        militaries.put("slinger", slinger);
    }

    public static void createAssassin() {
        int speed = Speed.AVERAGE.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.MEDIUM.getRate();
        ArabianMercenary assassin = new ArabianMercenary(speed, defenseRating, 1, attackRating, 60);
        assassin.setName("assassin");
        assassin.setDefenseRange(4);
        assassin.setAggressiveRange(7);
        assassin.enableInvisible();
        militaries.put("assassin", assassin);

    }

    public static void createHorseArcher() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.MEDIUM.getRate();
        int attackRating = AttackRating.LOW.getRate();
        ArabianMercenary horseArcher = new ArabianMercenary(speed, defenseRating, 10, attackRating, 80);
        horseArcher.setName("horseArcher");
        horseArcher.setWeapon("bow");
        horseArcher.setDefenseRange(13);
        horseArcher.setAggressiveRange(16);
        militaries.put("horseArcher", horseArcher);
    }

    public static void createArabianSwordsman() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.HIGH.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        ArabianMercenary arabianSwordsman = new ArabianMercenary(speed, defenseRating, 1, attackRating, 80);
        arabianSwordsman.setName("arabianSwordsman");
        arabianSwordsman.addArmour("metalArmour");
        arabianSwordsman.setDefenseRange(4);
        arabianSwordsman.setAggressiveRange(7);
        militaries.put("arabianSwordsman", arabianSwordsman);
    }

    public static void createFireThrower() {
        int speed = Speed.VERY_FAST.getRate();
        int defenseRating = DefenseRating.LOW.getRate();
        int attackRating = AttackRating.HIGH.getRate();
        ArabianMercenary fireThrower = new ArabianMercenary(speed, defenseRating, 5, attackRating, 100);
        fireThrower.setName("fireThrower");
        fireThrower.setDefenseRange(4);
        fireThrower.setAggressiveRange(7);
        militaries.put("fireThrower", fireThrower);
    }


    public static Military getClone(Military military) {
        if (military instanceof Engineer) {
            return new Engineer((Engineer) military);
        }

        if (military instanceof Tunneler) {
            return new Tunneler((Tunneler) military);
        }

        if (military instanceof EuropeanTroop) {
            return new EuropeanTroop((EuropeanTroop) military);
        }
        if (military instanceof ArabianMercenary) {
            return new ArabianMercenary((ArabianMercenary) military);
        }
        return null;
    }
}
