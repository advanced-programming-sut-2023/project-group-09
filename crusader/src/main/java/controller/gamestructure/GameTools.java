package controller.gamestructure;

import enumeration.AttackRating;
import enumeration.Speed;
import model.Government;
import model.tools.Tool;

import java.util.HashMap;

public class GameTools {
    public static HashMap<String, Tool> tools = new HashMap<>();

    public static void createTools() {
        createTrebuchet();
        createSiegeTower();
        createFireBallista();
        createCatapult();
        createBatteringRam();
        createPortableShield();
    }

    public static Tool getTool(String type, Government government, int x, int y) {
        Tool tool = getClone(tools.get(type));
        if (tool == null) {
            return null;
        }
        tool.setX(x);
        tool.setY(y);
        tool.setGovernment(government);
        return tool;
    }

    public static Tool getTool(String type) {
        return tools.get(type);
    }


    public static void createPortableShield() {
        int speed = Speed.VERY_FAST.getRate();
        int damage = AttackRating.NONE.getRate();
        Tool portableShield = new Tool("portableShield", 1, speed,
                0, damage);
        portableShield.setCanAttack(false);
        portableShield.setCanAirAttack(false);
        portableShield.setCanMove(true);
        portableShield.setUseStone(false);
        portableShield.setAttackToBuilding(false);
        portableShield.setAttackToHuman(false);
        tools.put("portableShield", portableShield);
        portableShield.setName("portableShield");
    }

    public static void createBatteringRam() {
        int speed = Speed.AVERAGE.getRate();
        int damage = AttackRating.NONE.getRate();
        Tool batteringRam = new Tool("batteringRam", 4, speed,
                0, damage);
        batteringRam.setCanAttack(true);
        batteringRam.setCanAirAttack(false);
        batteringRam.setCanMove(true);
        batteringRam.setUseStone(false);
        batteringRam.setAttackToBuilding(true);
        batteringRam.setAttackToHuman(false);
        tools.put("batteringRam", batteringRam);
        batteringRam.setName("batteringRam");
    }

    public static void createSiegeTower() {
        int speed = Speed.FAST.getRate();
        int damage = AttackRating.NONE.getRate();
        Tool siegeTower = new Tool("siegeTower", 4, speed,
                0, damage);
        siegeTower.setCanAttack(true);
        siegeTower.setCanAirAttack(false);
        siegeTower.setCanMove(true);
        siegeTower.setUseStone(false);
        siegeTower.setAttackToBuilding(true);
        siegeTower.setAttackToHuman(false);
        tools.put("siegeTower", siegeTower);
        siegeTower.setName("siegeTower");
    }

    public static void createCatapult() {
        int speed = Speed.AVERAGE.getRate();
        int damage = AttackRating.HIGH.getRate();
        Tool catapult = new Tool("catapult", 2, speed,
                10, damage);
        catapult.setCanAttack(true);
        catapult.setCanAirAttack(true);
        catapult.setCanMove(true);
        catapult.setUseStone(true);
        catapult.setAttackToBuilding(true);
        catapult.setAttackToHuman(true);
        tools.put("catapult", catapult);
        catapult.setName("catapult");
    }

    public static void createTrebuchet() {
        int speed = Speed.STATIONARY.getRate();
        int damage = AttackRating.VERY_HIGH.getRate();
        Tool trebuchet = new Tool("trebuchet", 3, speed,
                15, damage);
        trebuchet.setCanAttack(true);
        trebuchet.setCanAirAttack(true);
        trebuchet.setCanMove(false);
        trebuchet.setUseStone(true);
        trebuchet.setAttackToBuilding(true);
        trebuchet.setAttackToHuman(true);
        tools.put("trebuchet", trebuchet);
        trebuchet.setName("trebuchet");
    }

    public static void createFireBallista() {
        int speed = Speed.SLOW.getRate();
        int damage = AttackRating.VERY_HIGH.getRate();
        Tool fireBallista = new Tool("fireBallista", 2, speed,
                10, damage);
        fireBallista.setCanAttack(true);
        fireBallista.setCanAirAttack(true);
        fireBallista.setCanMove(true);
        fireBallista.setUseStone(true);
        fireBallista.setAttackToBuilding(false);
        fireBallista.setAttackToHuman(true);
        tools.put("fireBallista", fireBallista);
        fireBallista.setName("fireBallista");
    }

    public static Tool getClone(Tool tool) {
        if (tool != null) {
            return new Tool(tool);
        }
        return null;
    }
}
