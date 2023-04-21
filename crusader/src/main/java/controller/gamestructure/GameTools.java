package controller.gamestructure;

import enumeration.AttackRating;
import enumeration.Speed;
import model.tools.AttackingAndDefendingTool;

public class GameTools {
    public static void createPortableShield() {
        int speed = Speed.VERY_FAST.getRate();
        int damage = AttackRating.NONE.getRate();
        AttackingAndDefendingTool portableShield = new AttackingAndDefendingTool(1, speed, 0, damage);
    }

    public static void createBatteringRam() {
        int speed = Speed.AVERAGE.getRate();
//        TODO: correct damage
        int damage = AttackRating.NONE.getRate();
        AttackingAndDefendingTool batteringRam = new AttackingAndDefendingTool(4, speed, 0, damage);
    }

    public static void createSiegeTower() {
        int speed = Speed.FAST.getRate();
        int damage = AttackRating.NONE.getRate();
        AttackingAndDefendingTool siegeTower = new AttackingAndDefendingTool(4, speed, 0, damage);
    }

    public static void createCatapult() {
        int speed = Speed.AVERAGE.getRate();
//        TODO: correct damage
        int damage = AttackRating.HIGH.getRate();
        AttackingAndDefendingTool catapult = new AttackingAndDefendingTool(2, speed, 10, damage);
    }

    public static void createTrebuchet() {
        int speed = Speed.STATIONARY.getRate();
//        TODO: correct damage
        int damage = AttackRating.VERY_HIGH.getRate();
        AttackingAndDefendingTool trebuchet = new AttackingAndDefendingTool(3, speed, 15, damage);
    }

    public static void createFireBallista() {
        int speed = Speed.SLOW.getRate();
//        TODO: correct damage
        int damage = AttackRating.VERY_HIGH.getRate();
        AttackingAndDefendingTool fireBallista = new AttackingAndDefendingTool(2, speed, 10, damage);
    }
}
