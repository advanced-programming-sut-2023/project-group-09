package controller;

import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameHumans;
import controller.human.HumanController;
import controller.human.MoveController;
import enumeration.MilitaryStates;
import model.Government;
import model.activity.Move;
import model.building.Building;
import model.building.castlebuildings.CastleBuilding;
import model.building.castlebuildings.Wall;
import model.game.Game;
import model.game.Map;
import model.game.Tile;
import model.game.Tuple;
import model.human.Human;
import model.human.civilian.Civilian;
import model.human.military.Military;
import model.human.military.Tunneler;
import model.tools.Tool;
import view.UnitMenu;

import java.util.*;

public class GameController {
    private static Game game;
    private static boolean isBuildingSelected = false;

    public static boolean isIsBuildingSelected() {
        return isBuildingSelected;
    }

    public static void setIsBuildingSelected(boolean isBuildingSelected) {
        GameController.isBuildingSelected = isBuildingSelected;
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GameController.game = game;
    }

    public static String selectUnit(int x, int y, String type, Scanner scanner) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        ArrayList<Military> militaries;
        if (type == null) {
            militaries = MapController.getMilitariesOfGovernment(x - 1, y - 1, game.getCurrentGovernment());
        } else if (GameHumans.getUnit(type) == null) {
            return "invalid type!";
        } else {
            militaries = MapController.getOneTypeOfMilitariesOfGovernment(x - 1, y - 1, type, game.getCurrentGovernment());
        }

        if (militaries.size() == 0) {
            return "There is no troop in this place!";
        }
        HumanController.militaries = militaries;
        UnitMenu.x = x - 1;
        UnitMenu.y = y - 1;
        UnitMenu.type = type;
        UnitMenu.run(scanner);
        return "";
    }

    public static String moveUnit(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Tuple destination = new Tuple(y - 1, x - 1);
        boolean check = HumanController.move(destination);
        if (!check) {
            return "can't move unit no path to destination!";
        }
        return "unit(s) moved successfully!";
    }

    public static String patrolUnit(int x1, int y1, int x2, int y2) {
        String message = validatePatrol(x1, y1, x2, y2);
        if (message != null) {
            return message;
        }

        boolean check = HumanController.patrolUnit(x1 - 1, y1 - 1, x2 - 1, y2 - 1);
        if (!check) {
            return "can't start patrol, no path to destination!";
        }
        return "patrol started successfully!";
    }

    public static String deactivatePatrolUnit() {
        boolean check = HumanController.deactivatePatrol();
        if (!check) {
            return "no troop is patrolling!";
        }
        return "patrol deactivate successfully!";
    }

    public static String setStateOfMilitary(int x, int y, String state) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }

        ArrayList<Military> militaries = MapController.getMilitariesOfGovernment(x - 1, y - 1, game.getCurrentGovernment());
        if (militaries.size() == 0) {
            return "There is no troop in this place!";
        }


        if (state.equals(MilitaryStates.STAND_GROUND.getState())) {
            HumanController.setState(MilitaryStates.STAND_GROUND.getState(), militaries);
        }

        if (state.equals(MilitaryStates.DEFENSIVE_STANCE.getState())) {
            HumanController.setState(MilitaryStates.DEFENSIVE_STANCE.getState(), militaries);
        }

        if (state.equals(MilitaryStates.AGGRESSIVE_STANCE.getState())) {
            HumanController.setState(MilitaryStates.AGGRESSIVE_STANCE.getState(), militaries);
        }

        return "invalid state!";
    }

    public static String attackEnemy(int x, int y, Scanner scanner) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Military enemy = UnitMenu.getEnemy(x, y, scanner);
        if (enemy == null) {
            return "your input is not valid please try again later!";
        }
        boolean canAttack = HumanController.attack(enemy);
        if (!canAttack) {
            return "can't attack to enemy with this type of unit or position!";
        }
        return "attack order record successfully!";
    }

    public static String airAttack(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        List<Military> enemies = MapController.getMilitariesOfOtherGovernment(x, y, GameController.getGame().getCurrentGovernment());
        if (enemies.size() == 0) {
            return "there is no enemy in this position!";
        }

        boolean canAttack = HumanController.airAttack(x, y, enemies);
        if (!canAttack) {
            return "can't attack with this type of unit or position!";
        }
        return "attack order record successfully!";
    }

    public static String attackBuilding(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Building building = game.getMap().getTile(x - 1, y - 1).getBuilding();
        if (building == null) {
            return "no building in this place!";
        }
        if (building.getGovernment().equals(game.getCurrentGovernment())) {
            return "this building is yours!";
        }
        boolean canAttack = HumanController.attack(building);
        if (!canAttack) {
            return "can't attack to enemy with this type of unit or position!";
        }
        return "attack order record successfully!";
    }

    public static String airAttackBuilding(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Building building = game.getMap().getTile(x, y).getBuilding();
        if (building == null) {
            return "no building in this place!";
        }
        if (building.getGovernment().equals(game.getCurrentGovernment())) {
            return "this building is yours!";
        }
        boolean canAttack = HumanController.airAttack(building);
        if (!canAttack) {
            return "can't attack with this type of unit or position!";
        }
        return "attack order record successfully!";
    }

    public static String attackTool(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Tool tool = game.getMap().getTile(x, y).getTool();
        if (tool == null) {
            return "no tool in this place!";
        }
        if (tool.getGovernment().equals(game.getCurrentGovernment())) {
            return "this tool is yours!";
        }
        boolean canAttack = HumanController.attack(tool);
        if (!canAttack) {
            return "can't attack to enemy with this type of unit or position!";
        }
        return "attack order record successfully!";
    }

    public static String airAttackTool(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Tool tool = game.getMap().getTile(x, y).getTool();
        if (tool == null) {
            return "no tool in this place!";
        }
        if (tool.getGovernment().equals(game.getCurrentGovernment())) {
            return "this tool is yours!";
        }
        boolean canAttack = HumanController.airAttack(tool);
        if (!canAttack) {
            return "can't attack with this type of unit or position!";
        }
        return "attack order record successfully!";
    }

    public static String useTool(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Tool tool = game.getMap().getTile(x, y).getTool();
        if (tool == null) {
            return "no tool in this place!";
        }
        if (!tool.getGovernment().equals(game.getCurrentGovernment())) {
            return "this tool is not yours!";
        }

        if (tool.getEngineers().size() == tool.getNumberOfRequiredEngineers()) {
            return "tool already is active!";
        }

        boolean canMove = HumanController.useTool(tool);
        if (!canMove) {
            return "can't move, order is unsuitable!";
        }
        return "order record successfully!";
    }


    public static String pourOil(String direction) {
        return "";
    }

    public static String digTunnel(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        ArrayList<Military> militaries = MapController.getOneTypeOfMilitariesOfGovernment(UnitMenu.x,UnitMenu.y,"tunneler",game.getCurrentGovernment());
        if(militaries.size() == 0){
            return "no tunneler between selected troops!";
        }
        Random random= new Random();
        Tunneler tunneler = (Tunneler) militaries.get(random.nextInt(militaries.size()));

        Tile tile = game.getMap().getTile(x,y);
        if (!tile.getCanPutBuilding()){
            return "this position is not suitable !";
        }

        Building targetBuilding = aroundBuilding(x, y);
        if (targetBuilding == null) {
            return "no enemy's castle building is around here!";
        }
        boolean checkPath = HumanController.digTunnel(targetBuilding,tunneler);
        if (!checkPath) {
            return "no path to position!";
        }

        return "dig tunnel order recorded successfully!";
    }

    public static Building aroundBuilding(int x, int y) {
        Building targetBuilding = null;
        x--;
        y--;
        Tile tile = game.getMap().getTile(x, y);
        boolean check = true;


        if (0 < x && check) {
            Tile neighbor = game.getMap().getTile(x - 1, y);
            Building neighborBuilding = neighbor.getBuilding();
            if (neighborBuilding instanceof CastleBuilding && !neighborBuilding.getGovernment().equals(game.getCurrentGovernment())) {
                targetBuilding = neighborBuilding;
                check = false;
            }
        }
        if (0 < y && check) {
            Tile neighbor = game.getMap().getTile(x, y - 1);
            Building neighborBuilding = neighbor.getBuilding();
            if (neighborBuilding instanceof CastleBuilding  && !neighborBuilding.getGovernment().equals(game.getCurrentGovernment())) {
                targetBuilding = neighborBuilding;
                check = false;
            }
        }

        if (x < game.getMap().getWidth() - 1 && check) {
            Tile neighbor = game.getMap().getTile(x + 1, y);
            Building neighborBuilding = neighbor.getBuilding();
            if (neighborBuilding instanceof CastleBuilding && !neighborBuilding.getGovernment().equals(game.getCurrentGovernment())) {
                targetBuilding = neighborBuilding;
                check = false;
            }
        }
        if (y < game.getMap().getLength() - 1 && check) {
            Tile neighbor = game.getMap().getTile(x, y + 1);
            Building neighborBuilding = neighbor.getBuilding();
            if (neighborBuilding instanceof CastleBuilding && !neighborBuilding.getGovernment().equals(game.getCurrentGovernment())) {
                targetBuilding = neighborBuilding;
            }
        }
        return targetBuilding;
    }

    public static String buildEquipment(int equipmentNumber, int x, int y) {
        return "";
    }

    public static String disbandUnit() {
        HumanController.disbandUnits(HumanController.militaries);
        return "unit disbanded successfully!";
    }

    public static String dropBuilding(int x, int y, String type) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }

        Building building = GameBuildings.getBuilding(type);
        if (building == null) {
            return "building type is invalid!";
        }
        if (!hasRequired(building.getCost())) {
            return "your resource is not enough!";
        }
        if (!MapController.checkCanPutBuilding(x, y, type, GameController.getGame().getCurrentGovernment())) {
            return "this coordinate is not suitable!";
        }
        consumeRequired(building.getCost());

        MapController.dropBuilding(x, y, type, GameController.getGame().getCurrentGovernment());
        return "building dropped successfully!";
    }

    public static boolean hasRequired(HashMap<String, Integer> required) {
        Government government = GameController.getGame().getCurrentGovernment();
        for (String product : required.keySet()) {
            if (government.getPropertyAmount(product) < required.get(product)) {
                return false;
            }
        }
        return true;
    }

    public static void consumeRequired(HashMap<String, Integer> required) {
        Government government = GameController.getGame().getCurrentGovernment();
        for (String product : required.keySet()) {
            GovernmentController.consumeProduct(government, product, required.get(product));
        }
    }


    public static String selectBuilding(int x, int y) {
        Government nowGovernment = game.getCurrentGovernment();
        Building building = game.getMap().getTile(x, y).getBuilding();
        if (building == null || !building.getGovernment().equals(nowGovernment)) {
            return "There is no building of your government here!";
        }
        GameController.setIsBuildingSelected(true);
        BuildingController.setBuilding(building);
        return "Building " + building.getName() + " Selected!";
    }

    public static int howManyGovermentsRemainsInGame() {
        int numberOfGoverments = 0;
        for (Government government : game.getGovernments()) {
            if (government.isAlive()) {
                government.addTurnsSurvive();
                numberOfGoverments++;
            }
        }
        return numberOfGoverments;
    }


    public static String changeTurn() {
        game.changeTurn();
        String nickname = game.getCurrentGovernment().getUser().getNickname();
        String result = "Lord " + nickname + " was played!\n";
        int indexOfCurrentGovernment = game.getGovernments().indexOf(game.getCurrentGovernment());
        if (indexOfCurrentGovernment == 7) {
            Government nowGovernment = game.getGovernments().get(0);
            result += "now Lord " + nowGovernment.getUser().getNickname() + " is playing\n";
            result += "new Turn started!\n";
            game.setRound(game.getRound() + 1);
            for (Government government : game.getGovernments()) {
                government.updateAfterTurn();
            }
            // TODO : next turn rules
            /*
                attacking and defending damages to Buildings and Humans
                routine work of buildings (such as producing some thing)
             */
            game.setCurrentGovernment(nowGovernment);
            int numberOfRemainedGoverments = howManyGovermentsRemainsInGame();
            if (numberOfRemainedGoverments == 1) {
                game.setEndGame(true);
                game.setWinner();
                game.setScores();
                result = "The Game Is Over!\nWinner : ****** Lord " +
                        game.getWinner().getUser().getNickname() + " ******\n";
            } else if (numberOfRemainedGoverments == 0) {
                game.setEndGame(true);
                game.setWinner();
                game.setScores();
                result = "The Game Is Over!\nThere's no winner in this war!\n";
            }
            return result;
        } else {
            Government nowGovernment = game.getGovernments().get(indexOfCurrentGovernment + 1);
            result += "now Lord " + nowGovernment.getUser().getNickname() + " is playing\n";
            game.setCurrentGovernment(nowGovernment);
            return result;
        }
    }


    public static Building getInstanceOfBuilding(int x, int y, String typeOfBuilding) {
        return null;
        // TODO: this method must check (x, y) and typeOfBuilding and returned correct value.
    }

    public static String dropCastleBuildings(int x, int y, String type) {
        return ""; // *********************************
    }

    public static String dropUnit(int x, int y, String type, int count) {
        return ""; // *********************************
    }

    public static String showMap(int x, int y) {
        Map map = game.getMap();
        if (y - 3 < 0) y = 3;
        else if (y + 3 >= map.getLength()) y = map.getLength() - 1;
        if (x - 9 < 0) x = 9;
        else if (x + 9 >= map.getWidth()) x = map.getWidth() - 1;
        game.setCurrentMapX(x);
        game.setCurrentMapY(y);

        String result = "";
        result += "-";
        for (int i = 0; i < 19 * 6; i++) {
            result += "-";
        }
        result += "\n";
        for (int i = y - 3; i <= y + 3; i++) {
            for (int j = 0; j < 2; j++) {
                result += "|";
                for (int k = x - 9; k <= x + 9; k++) {
                    for (int l = 0; l < 5; l++) {
                        Tile tile = map.getTile(k, i);
                        String sign = " ";
                        if (tile.getTool() != null) {
                            sign = tile.getTool().getGovernment().getColorRgb() + "A";
                        } else if (tile.getMilitaries().size() != 0) {
                            HashMap<Government, Integer> numberOfMilitariesOnTile = new HashMap<>();
                            for (int m = 0; m < game.getGovernments().size(); m++) {
                                numberOfMilitariesOnTile.put(game.getGovernments().get(m), 0);
                            }
                            for (int m = 0; m < tile.getMilitaries().size(); m++) {
                                Military military = tile.getMilitaries().get(m);
                                numberOfMilitariesOnTile.replace(military.getGovernment(), numberOfMilitariesOnTile.get(military.getGovernment()) + 1);
                            }
                            int max = Collections.max(numberOfMilitariesOnTile.values());
                            int numberOfMax = 0;
                            Government maxMilitariesGovernment = null;
                            for (Government government : numberOfMilitariesOnTile.keySet()) {
                                if (numberOfMilitariesOnTile.get(government) == max) {
                                    numberOfMax++;
                                    maxMilitariesGovernment = government;
                                }
                            }
                            if (numberOfMax == 1) sign = maxMilitariesGovernment.getColorRgb() + "S";
                            else sign = "S";
                        } else if (tile.getBuilding() != null && !(tile.getBuilding() instanceof Wall))
                            sign = tile.getBuilding().getGovernment().getColorRgb() + "B";
                        else if (tile.getBuilding() != null && tile.getBuilding() instanceof Wall)
                            sign = tile.getBuilding().getGovernment().getColorRgb() + "W";
                        else if (tile.getTree() != null) sign = "T";
                        else if (tile.getRockDirection() != null) sign = "R";
                        result += tile.getTexture().getColor() + sign + "\u001B[0m";
                    }
                    result += "|";
                }
                result += "\n";
            }
            result += "-";
            for (int j = 0; j < 19 * 6; j++) {
                result += "-";
            }
            result += "\n";
        }

        return result;
    }

    public static String showPreviewOfMap(Map map) {
        String result = "";
        for (int k = 1; k < map.getLength(); k++) {
            for (int l = 1; l < map.getWidth(); l++) {
                Tile tile = map.getTile(k, l);
                String sign = " ";
                if (tile.isDefaultCastle()) {
                    sign = "\u001B[40m" + "C";
                } else if (tile.getMilitaries().size() != 0) sign = "\uE54E";
                else if (tile.getBuilding() != null && !(tile.getBuilding() instanceof Wall)) sign = "B";
                else if (tile.getBuilding() != null && tile.getBuilding() instanceof Wall) sign = "W";
                else if (tile.getTree() != null) sign = "T";
                else if (tile.getRockDirection() != null) sign = "R";
                result += tile.getTexture().getColor() + sign + "\u001B[0m";
            }
            result += "\n";
        }
        return result;
    }

    public static String moveMap(int up, int left, int down, int right) {
        int y = down - up;
        int x = right - left;
        return showMap(GameController.getGame().getCurrentMapX() + x, GameController.game.getCurrentMapY() + y);
    }

    public static String showDetailsOfTile(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);

        String details = "tile (" + (x + 1) + ", " + (y + 1) + ") details:\n";
        details += "texture type: " + tile.getTexture().getTextureName() + "\n";
        if (tile.getTree() != null) {
            details += "Tree : " + tile.getTree().getTreeName() + "\n";
        }
        if (tile.getRockDirection() != null) {
            details += "Rock : " + tile.getRockDirection().getDirection() + "\n";
        }
        if (tile.getBuilding() != null) {
            details += "building " + tile.getBuilding().getName() + " from Lord " +
                    tile.getBuilding().getGovernment().getUser().getNickname() +
                    " | HP: " + tile.getBuilding().getHp() + "/" +
                    tile.getBuilding().getMaxHp() + "\n";
        } else details += "there is no building on this tile\n";
        details += "Civilian number : " + tile.getCivilians().size() + "\n";
        if (tile.getCivilians().size() != 0) {
            details += "Civilian in details : \n";
            for (int i = 0; i != tile.getCivilians().size(); i++) {
                details += "Civilian " + (i + 1) + ": from Lord " +
                        tile.getCivilians().get(i).getGovernment().getUser().getNickname() + "\n";
            }
        }
        details += "Military number : " + tile.getMilitaries().size() + "\n";
        for (int i = 0; i != tile.getMilitaries().size(); i++) {
            details += "Military " + (i + 1) + ": type: " + tile.getMilitaries().get(i).getName() + " | from Lord " +
                    tile.getMilitaries().get(i).getGovernment().getUser().getNickname() + "\n";
        }
        return details.substring(0, details.length() - 1);
    }

    public static String validateXAndY(int x, int y) {
        Map map = game.getMap();
        if (checkNullFields(x)) {
            return "x is required!";
        }
        if (checkNullFields(y)) {
            return "y is required!";
        }
        if (x < 1 || x > map.getWidth()) {
            return "invalid x!";
        }
        if (y < 1 || y > map.getLength()) {
            return "invalid y!";
        }
        return null;
    }

    public static String validatePatrol(int x1, int y1, int x2, int y2) {
        Map map = game.getMap();
        if (checkNullFields(x1)) {
            return "x1 is required!";
        }
        if (checkNullFields(y1)) {
            return "y1 is required!";
        }
        if (x1 < 1 || x1 > map.getWidth()) {
            return "invalid x1!";
        }
        if (y1 < 1 || y1 > map.getLength()) {
            return "invalid y!";
        }

        if (checkNullFields(x2)) {
            return "x2 is required!";
        }
        if (checkNullFields(y2)) {
            return "y2 is required!";
        }
        if (x2 < 1 || x2 > map.getWidth()) {
            return "invalid x2!";
        }
        if (y2 < 1 || y2 > map.getLength()) {
            return "invalid y2!";
        }
        return null;
    }

    public static void workerDistribution(Building building) {
        Government government = building.getGovernment();
        int numberOfRequiredWorkers = building.getNumberOfRequiredWorkers() - building.howManyWorkersHave();
        for (Human human : government.getSociety()) {
            if (human instanceof Civilian) {
                if (!((Civilian) human).isHasJob()) {
                    building.addHuman(human);
                    numberOfRequiredWorkers--;
                    Move move = new Move(human.getX(), human.getY(), building,
                            true, human);
                    move.setPath(MoveController.getPathForBuilding(move.getStartPair(), building, human));
                    ((Civilian) human).setHasJob(true);
                }
            }
            if (numberOfRequiredWorkers == 0) {
                return;
            }
        }
    }

    public static String unleashWarDogs() {
        if (!BuildingController.unleashWarDogs()) {
            return "no enemy around here!";
        }
        return "war dogs unleashed successfully!";
    }

    private static boolean checkNullFields(String input) {
        return input == null || input.length() == 0;
    }

    private static boolean checkNullFields(int input) {
        return input == -1;
    }
}
