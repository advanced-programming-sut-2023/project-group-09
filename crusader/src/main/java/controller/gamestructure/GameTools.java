package controller.gamestructure;

import enumeration.AttackRating;
import enumeration.Speed;
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

    public static void createPortableShield() {
        int speed = Speed.VERY_FAST.getRate();
        int damage = AttackRating.NONE.getRate();
        Tool portableShield = new Tool(1, speed,
                0, damage);
        tools.put("portableShield",portableShield);
        portableShield.setName("portableShield");
    }

    public static void createBatteringRam() {
        int speed = Speed.AVERAGE.getRate();
//        TODO: correct damage
        int damage = AttackRating.NONE.getRate();
        Tool batteringRam = new Tool(4, speed,
                0, damage);
        tools.put("batteringRam",batteringRam);
        batteringRam.setName("batteringRam");
    }

    public static void createSiegeTower() {
        int speed = Speed.FAST.getRate();
        int damage = AttackRating.NONE.getRate();
        Tool siegeTower = new Tool(4, speed,
                0, damage);
        tools.put("siegeTower",siegeTower);
        siegeTower.setName("siegeTower");
    }

    public static void createCatapult() {
        int speed = Speed.AVERAGE.getRate();
//        TODO: correct damage
        int damage = AttackRating.HIGH.getRate();
        Tool catapult = new Tool(2, speed,
                10, damage);
        tools.put("catapult",catapult);
        catapult.setName("catapult");
    }

    public static void createTrebuchet() {
        int speed = Speed.STATIONARY.getRate();
//        TODO: correct damage
        int damage = AttackRating.VERY_HIGH.getRate();
        Tool trebuchet = new Tool(3, speed,
                15, damage);
        tools.put("trebuchet",trebuchet);
        trebuchet.setName("trebuchet");
    }

    public static void createFireBallista() {
        int speed = Speed.SLOW.getRate();
//        TODO: correct damage
        int damage = AttackRating.VERY_HIGH.getRate();
        Tool fireBallista = new Tool(2, speed,
                10, damage);
        tools.put("fireBallista",fireBallista);
        fireBallista.setName("fireBallista");
    }

    public static Tool getClone(Tool tool){
        if(tool != null){
            return new Tool(tool);
        }
        return null;
    }
}
