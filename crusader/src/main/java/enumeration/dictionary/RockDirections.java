package enumeration.dictionary;

import java.util.Random;

public enum RockDirections {
    NORTH("north"),
    EAST("east"),
    WEST("west"),
    SOUTH("south");

    private RockDirections(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return this.direction;
    }

    public static RockDirections getRockByDirection(String direction) {
        for (RockDirections rock : RockDirections.values()) {
            if (rock.getDirection().equals(direction)) return rock;
        }
        return null;
    }

    public static RockDirections getRandomDirection(){
        Random rand = new Random();
        int random = rand.nextInt(1, 5);
        int i = 0;
        for (RockDirections rock : RockDirections.values()) {
            i++;
            if (i == random) return rock;
        }
        return null;
    }

    private String direction;
}
