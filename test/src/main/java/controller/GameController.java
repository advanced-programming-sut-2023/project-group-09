package controller;

import client.Packet;
import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameHumans;
import controller.human.HumanController;
import controller.human.MoveController;
import enumeration.MilitaryStates;
import enumeration.Pair;
import enumeration.Textures;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.FakeGame;
import model.Government;
import model.activity.Move;
import model.building.Building;
import model.building.castlebuildings.Gatehouse;
import model.building.castlebuildings.MainCastle;
import model.building.castlebuildings.Wall;
import model.building.producerbuildings.ProducerBuilding;
import model.game.Game;
import model.game.Map;
import model.game.Tile;
import model.game.Tuple;
import model.human.Human;
import model.human.civilian.Civilian;
import model.human.military.Engineer;
import model.human.military.Military;
import model.human.military.Tunneler;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import model.tools.Tool;
import view.Main;
import view.controllers.GameViewController;
import view.controllers.HumanViewController;
import view.controllers.ViewController;
import view.menus.GameMenu;
import viewphase1.UnitMenu;


import java.io.IOException;
import java.util.*;

public class GameController {
    private static Game game;
    private static FakeGame fakeGame;
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

    public static String selectUnit(int x, int y, String type) {
        ArrayList<Military> militaries;
        if (type == null) {
            militaries = MapController.getMilitariesOfGovernment(x, y, game.getCurrentGovernment());
        } else if (GameHumans.getUnit(type) == null) {
            return "invalid type!";
        } else {
            militaries = MapController.getOneTypeOfMilitariesOfGovernment(x, y, type, game.getCurrentGovernment());
        }

        if (militaries.size() == 0) {
            return "There is no troop in this place!";
        }
        HumanController.militaries = militaries;
        UnitMenu.x = x;
        UnitMenu.y = y;
        UnitMenu.type = type;
        return "";
    }

    public static String moveUnit(int x, int y) {
        Tuple destination = new Tuple(y, x);
        boolean check = HumanController.move(destination);
        if (!check) {
            return "can't move unit no path to destination!";
        }
        return "unit(s) moved successfully!";
    }

    public static boolean validateMoveUnit(int x, int y) {
        Tuple destination = new Tuple(y, x);
        boolean check = HumanController.validateMove(destination);
        if (!check) {
            return false;
        }
        return true;
    }

    public static String patrolUnit(int x2, int y2) {
        if (HumanController.militaries.size() > 0) {
            int x1 = HumanController.militaries.get(0).getX();
            int y1 = HumanController.militaries.get(0).getY();

            boolean check = HumanController.patrolUnit(x1, y1, x2, y2);
            if (!check) {
                return "can't start patrol, no path to destination!";
            }
            HumanViewController.setFlagOfPatrol(x1, y1, x2, y2);
            return "patrol started successfully!";
        }
        return "";
    }

    public static String deactivatePatrolUnit() {
        boolean check = HumanController.deactivatePatrol();
        if (!check) {
            return "no troop is patrolling!";
        }
        return "patrol deactivate successfully!";
    }

    public static String setStateOfMilitary(int x, int y, String state) {

        ArrayList<Military> militaries = MapController.getMilitariesOfGovernment(x, y, game.getCurrentGovernment());
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


    //==================================================================================
    public static String attackEnemy(int x, int y) {
        if (HumanController.militaries.size() == 0) return "";
        Government government = HumanController.militaries.get(0).getGovernment();
        ArrayList<Military> militaries = MapController.getMilitariesOfOtherGovernment(x, y, government);
        if (militaries.size() == 0) {
            return "your input is not valid please try again later!";
        }
        Random random = new Random();
        Military enemy = militaries.get(random.nextInt(militaries.size()));
        boolean canAttack = HumanController.attack(enemy);
        if (!canAttack) {
            return "can't attack to enemy with this type of unit or position!";
        }
        return "attack order record successfully!";
    }

    public static String airAttack(int x, int y) {
        if (HumanController.militaries.size() == 0) return "";
        Government government = HumanController.militaries.get(0).getGovernment();
        List<Military> enemies = MapController.getMilitariesOfOtherGovernment(x, y, government);
        if (enemies.size() == 0) {
            return "there is no enemy in this position!";
        }

        boolean canAttack = HumanController.airAttack(x , y, enemies);
        if (!canAttack) {
            return "can't attack with this type of unit or position!";
        }
        return "attack order record successfully!";
    }

    public static String attackBuilding(int x, int y) {
        if (HumanController.militaries.size() == 0) return "";
        Government government = HumanController.militaries.get(0).getGovernment();
        Building building = game.getMap().getTile(x, y).getBuilding();
        if (building == null) {
            return "no building in this place!";
        }
        if (building instanceof MainCastle) {
            return "you can't attack to mainCastle";
        }
        if (building.getGovernment().equals(government)) {
            return "this building is yours!";
        }
        boolean canAttack = HumanController.attack(building);
        if (!canAttack) {
            return "can't attack to enemy with this type of unit or position!";
        }
        return "attack order record successfully!";
    }

    public static String airAttackBuilding(int x, int y) {
        if (HumanController.militaries.size() == 0) return "";
        Government government = HumanController.militaries.get(0).getGovernment();
        Building building = game.getMap().getTile(x, y).getBuilding();
        if (building == null) {
            return "no building in this place!";
        }
        if (building.getGovernment().equals(government)) {
            return "this building is yours!";
        }
        boolean canAttack = HumanController.airAttack(building);
        if (!canAttack) {
            return "can't attack with this type of unit or position!";
        }
        return "attack order record successfully!";
    }

    public static String attackTool(int x, int y) {
        Tool tool = game.getMap().getTile(x, y ).getTool();
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

    //==================================================================

    public static boolean validateAttackEnemy(int x, int y) {
        ArrayList<Military> militaries = MapController.getMilitariesOfOtherGovernment(x, y, GameController.getGame().getCurrentGovernment());
        if (militaries.size() == 0) {
            return false;
        }
        Random random = new Random();
        Military enemy = militaries.get(random.nextInt(militaries.size()));
        boolean canAttack = HumanController.validateAttack(enemy);
        if (!canAttack) {
            return false;
        }
        return true;
    }

    public static boolean validateAirAttack(int x, int y) {
        List<Military> enemies = MapController.getMilitariesOfOtherGovernment(x, y, GameController.getGame().getCurrentGovernment());
        if (enemies.size() == 0) {
            return false;
        }

        return HumanController.validateAirAttack(x, y, enemies);
    }

    public static boolean validateAttackBuilding(int x, int y) {
        Building building = game.getMap().getTile(x, y).getBuilding();

        if (building == null) {
            return false;
        }

        if (building instanceof MainCastle) {
            return false;
        }
        if (building.getGovernment().equals(game.getCurrentGovernment())) {
            return false;
        }
        return HumanController.validateAttack(building);
    }

    public static boolean validateAirAttackBuilding(int x, int y) {
        Building building = game.getMap().getTile(x, y).getBuilding();
        if (building == null) {
            return false;
        }
        if (building.getGovernment().equals(game.getCurrentGovernment())) {
            return false;
        }
        return HumanController.validateAirAttack(building);
    }

    public static boolean validateAttackTool(int x, int y) {
        Tool tool = game.getMap().getTile(x, y).getTool();
        if (tool == null) {
            return false;
        }
        if (tool.getGovernment().equals(game.getCurrentGovernment())) {
            return false;
        }
        return HumanController.validateAttack(tool);
    }

    public static boolean validateAirAttackTool(int x, int y) {
        Tool tool = game.getMap().getTile(x, y).getTool();
        if (tool == null) {
            return false;
        }
        if (tool.getGovernment().equals(game.getCurrentGovernment())) {
            return false;
        }
        return HumanController.airAttack(tool);
    }


    //===================================================================
    public static String useTool(int x, int y) {
        Tool tool = game.getMap().getTile(x - 1, y - 1).getTool();
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


    public static String pourOil(String direction, ArrayList<Engineer> engineers) {
        Random random = new Random();
        Engineer engineer = engineers.get(random.nextInt(engineers.size()));
        if (!engineer.isHasOil()) {
            if (!HumanController.pourOilDirection(engineer, direction, engineer.getMilitaryState()))
                return "can't pour oil!";
            return "one engineer go to oilSmelter!";
        }
        if (!HumanController.pourOilDirection(engineer, direction, engineer.getMilitaryState()))
            return "can't pour oil!";

        return "poured oil successfully!";
    }

    public static String digTunnel(int x, int y) {
        ArrayList<Military> militaries = MapController.getOneTypeOfMilitariesOfGovernment(UnitMenu.x, UnitMenu.y, "tunneler", game.getCurrentGovernment());
        if (militaries.size() == 0) {
            return "no tunneler between selected troops!";
        }
        Random random = new Random();
        Tunneler tunneler = (Tunneler) militaries.get(random.nextInt(militaries.size()));

        Tile tile = game.getMap().getTile(x - 1, y - 1);
        if (!tile.getCanPutBuilding()) {
            return "this position is not suitable!";
        }

        Building targetBuilding = aroundBuilding(x - 1, y - 1);
        if (targetBuilding == null) {
            return "no enemy's castle building is around here!";
        }
        boolean checkPath = HumanController.digTunnel(targetBuilding, x - 1, y - 1, tunneler);
        if (!checkPath) {
            return "no path to position!";
        }

        return "dig tunnel order recorded successfully!";
    }

    public static String digMoat(int x, int y) {
        Military digger = null;
        Tile tile = GameController.getGame().getMap().getTile(x - 1, y - 1);
        if (!tile.isPassable()) {
            return "here is not suitable position for moat!";
        }
        for (Military military : HumanController.militaries) {
            if (military instanceof Engineer engineer && engineer.isHasOil()) {
                continue;
            }
            if (military.isDigsMoat()) {
                digger = military;
                break;
            }
        }
        if (digger == null) {
            System.out.println("there is no unit to dig moat!");
        }
        boolean check = EngineerController.digMoat(x - 1, y - 1, digger);
        if (!check) {
            return "can't move to this position!";
        }
        return "dig moat order recorded successfully!";
    }

    public static String fillMoat(int x, int y) {
        Military digger = null;
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        if (!tile.isMoat()) {
            return "no moat in position!";
        }
        for (Military military : HumanController.militaries) {
            if (military instanceof Engineer engineer && engineer.isHasOil()) {
                continue;
            }
            if (military.isDigsMoat()) {
                digger = military;
                break;
            }
        }
        if (digger == null) {
            System.out.println("there is no unit to fill moat!");
        }
        tile.setMoat(false);
        boolean check = EngineerController.fillMoat(x, y, digger);
        tile.setMoat(true);
        if (!check) {
            return "can't move to this position!";
        }
        return "fill moat order recorded successfully!";
    }

    public static String goToOilSmelter(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        Building building = tile.getBuilding();
        if (building == null || !building.getName().equals("oilSmelter")) {
            return "you should select an oilSmelter building!";
        }
        ArrayList<Engineer> engineers = UnitMenu.getEngineersOfTile();
        if (engineers.size() == 0) {
            return "no engineer in this place!";
        }
        Engineer engineer;
        Random random = new Random();
        engineer = engineers.get(random.nextInt(engineers.size()));
        if (engineer.isHasOil()) {
            return "this engineer has oil can't work inn oilSmelter,try again later!";
        }
        building.addHuman(engineer);
        MapController.moveMilitary(building.getStartX(), building.getStartY(), engineer);
        engineer.setInvisible(true);
        return "engineer now works in this oilSmelter!";
    }

    public static Building aroundBuilding(int x, int y) {
        Building targetBuilding = null;
        boolean check = true;


        if (0 < x) {
            Tile neighbor = game.getMap().getTile(x - 1, y);
            Building neighborBuilding = neighbor.getBuilding();
            if (checkCanDigTunnel(neighborBuilding) && !neighborBuilding.getGovernment().equals(game.getCurrentGovernment())) {
                targetBuilding = neighborBuilding;
                check = false;
            }
        }
        if (0 < y && check) {
            Tile neighbor = game.getMap().getTile(x, y - 1);
            Building neighborBuilding = neighbor.getBuilding();
            if (checkCanDigTunnel(neighborBuilding) && !neighborBuilding.getGovernment().equals(game.getCurrentGovernment())) {
                targetBuilding = neighborBuilding;
                check = false;
            }
        }

        if (x < game.getMap().getWidth() - 1 && check) {
            Tile neighbor = game.getMap().getTile(x + 1, y);
            Building neighborBuilding = neighbor.getBuilding();
            if (checkCanDigTunnel(neighborBuilding) && !neighborBuilding.getGovernment().equals(game.getCurrentGovernment())) {
                targetBuilding = neighborBuilding;
                check = false;
            }
        }
        if (y < game.getMap().getLength() - 1 && check) {
            Tile neighbor = game.getMap().getTile(x, y + 1);
            Building neighborBuilding = neighbor.getBuilding();
            if (checkCanDigTunnel(neighborBuilding) && !neighborBuilding.getGovernment().equals(game.getCurrentGovernment())) {
                targetBuilding = neighborBuilding;
            }
        }
        return targetBuilding;
    }

    public static boolean checkCanDigTunnel(Building neighborBuilding) {
        if (neighborBuilding.getName().equals("perimeterTurret") || neighborBuilding.getName().equals("defenseTurret")) {
            return true;
        }
        return neighborBuilding instanceof Wall;
    }

    public static String buildEquipment(int equipmentNumber, ArrayList<Engineer> engineers, int x, int y) {
        Random random = new Random();
        if (engineers.size() == 0) return "no engineer in this place!";
        EngineerController.currentEngineer = engineers.get(random.nextInt(engineers.size()));
        if (equipmentNumber == 1) {
            return EngineerController.buildTool("catapult", x, y);
        } else if (equipmentNumber == 2) {
            return EngineerController.buildTool("trebuchet", x, y);
        } else if (equipmentNumber == 3) {
            return EngineerController.buildTool("siegeTower", x, y);
        } else if (equipmentNumber == 4) {
            return EngineerController.buildTool("batteringRam", x, y);
        } else if (equipmentNumber == 5) {
            return EngineerController.buildTool("portableShield", x, y);
        } else if (equipmentNumber == 6) {
            return EngineerController.buildTool("fireBallista", x, y);
        }
        return "";
    }

    public static String disbandUnit() {
        HumanController.disbandUnits(HumanController.militaries, game.getCurrentGovernment());
        return "unit disbanded successfully!";
    }

    public static String dropBuilding(int x, int y, String type, String side) {
        if (type.equals("killingPit")) {
            game.getMap().getTile(x, y).setPit(true);
            game.getMap().getTile(x, y).setPitGovernment(game.getCurrentGovernment());
            return "killing pit dropped successfully";
        }
        Building building = GameBuildings.getBuilding(type);
        if (building == null) {
            return "building type is invalid!";
        }
        if (!hasRequired(building.getCost())) {
            return "your resource is not enough!";
        }

        if (type.equals("mainCastle") && GameController.getGame().getCurrentGovernment().getBuildingData("mainCastle").getNumber() != 0) {
            return "a government just can have one main Castle!";
        }

        if (building instanceof Gatehouse && !building.getName().equals("drawBridge")) {
            if (side != null && (side.equals("right") || side.equals("left"))) {
                if (!MapController.checkCanPutBuilding(x, y, type, GameController.getGame().getCurrentGovernment())) {
                    return "this coordinate is not suitable!";
                }
                MapController.isRightSide = side.equals("right");
                MapController.dropBuilding(x, y, type, GameController.getGame().getCurrentGovernment());
                return "building dropped successfully!";

            } else {
                return "side field is required!";
            }
        } else if (side != null) {
            return "side field is not for this type of building!";
        }


        if (!MapController.checkCanPutBuilding(x, y, type, GameController.getGame().getCurrentGovernment())) {
            return "this coordinate is not suitable!";
        }
        consumeRequired(building.getCost());
        GovernmentController.getCurrentGovernment().setGold(GovernmentController.getCurrentGovernment().getGold() - building.getPrice());
        MapController.dropBuilding(x, y, type, GameController.getGame().getCurrentGovernment());
        if (!GameMenu.isSpectator) {
            int popularity = GovernmentController.getCurrentGovernment().getPopularity() + 37;
            GameViewController.popularityReporter.setText(String.format("%d", popularity));
            GameViewController.populationReporter.setText(
                    String.format("%d/%d", GovernmentController.getCurrentGovernment().getPopulation(),
                            GovernmentController.getCurrentGovernment().getMaxPopulation()));
            GameViewController.updateFaceOfReporter();
        }
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

    public static String selectTool(int x, int y) {
        Tool tool = game.getMap().getTile(x, y).getTool();
        if (tool == null || !tool.getGovernment().equals(game.getCurrentGovernment())) {
            return "there is no tool of your government here!";
        }
        String result = "tool " + tool.getName() + " selected";
        if (tool.getName().equals("trebuchet") || tool.getName().equals("catapult"))
            result += "\nnumber of stones: " + tool.getStoneNumber();
        ToolsController.tool = tool;
        return result;
    }


    public static int howManyGovernmentsRemainsInGame() {
        boolean governmentDead = false;
        String deadMessage = "Lords ";
        int numberOfGovernments = 0;
        for (Government government : game.getGovernments()) {
            if (government.isAlive()) {
                government.addTurnsSurvive();
                numberOfGovernments++;
            } else if (!government.isDead()){
                deadMessage += government.getColor() + " With Score " + government.getHowManyTurnsSurvive() * 100
            + ", ";
                government.beingDead();
                governmentDead = true;
            }
        }
        if (governmentDead) {
            deadMessage = deadMessage.substring(0 , deadMessage.length() - 2);
            deadMessage += " Are Dead!";
            if (GameMenu.hoveringBarStateText != null)
                GameMenu.hoveringBarStateText.setText(deadMessage);
            ViewController.playDeadSong();
        }
        return numberOfGovernments;
    }

    private static void moveOfHumansAndTools() {
        for (Government government : game.getGovernments()) {
            ArrayList<Human> humans = new ArrayList<>(government.getSociety());
            for (Human human : humans) {
                if (human.getMove() != null) {
                    human.getMove().moveOneTurn();
                }
            }
            ArrayList<Military> militaries = new ArrayList<>(government.getTroops());
            for (Military military : militaries) {
                if (military.getMove() != null) {
                    military.getMove().moveOneTurn();
                }
            }
            ArrayList<Tool> tools = new ArrayList<>(government.getTools());
            for (Tool tool : tools) {
                if (tool.getToolMove() != null) {
                    tool.getToolMove().moveOneTurn();
                }
            }
        }
    }

    private static void attackOfMilitariesAndTools() {
        for (Government government : game.getGovernments()) {
            for (Military military : government.getTroops()) {
                military.getAttack().doAttack();
            }
            for (Tool tool : government.getTools()) {
                tool.getToolAttack().doAttack();
            }
        }
    }

    private static void gateHouseAutomaticOrder() {
        for (Government government : game.getGovernments()) {
            for (Building gatehouse : government.getBuildingData("smallStoneGatehouse").getBuildings()) {
                ((Gatehouse) gatehouse).checkToCloseGateHouse();
            }
            for (Building gatehouse : government.getBuildingData("bigStoneGatehouse").getBuildings()) {
                ((Gatehouse) gatehouse).checkToCloseGateHouse();
            }
        }
    }


    public static String changeTurn() throws IOException {
        String nickname = game.getCurrentGovernment().getUser().getNickname();
        String result = "Lord " + nickname + " was playing!\n";
        int round = game.getRound();
        game.changeTurn();
        if (game.getRound() != round) {
            result += "now Lord " + game.getCurrentGovernment().getUser().getNickname() + " is playing\n";
            result += "new Turn started!\n";
            for (Government government : game.getGovernments()) {
                government.updateAfterTurn();
            }
            gateHouseAutomaticOrder();
            moveOfHumansAndTools();
            attackOfMilitariesAndTools();
            int numberOfRemainedGovernments = howManyGovernmentsRemainsInGame();
            if (numberOfRemainedGovernments == 1) {
                game.setEndGame(true);
                game.setWinner();
                game.setScores();
                result = "The Game Is Over!\nWinner : ****** Lord " +
                        game.getWinner().getUser().getNickname() + " ******\n";
            } else if (numberOfRemainedGovernments == 0) {
                game.setEndGame(true);
                game.setWinner();
                game.setScores();
                result = "The Game Is Over!\nThere's no winner in this war!\n";
            }
            return result.substring(0, result.length() - 1);
        } else {
            result += "now Lord " + game.getCurrentGovernment().getUser().getNickname() + " is playing\n";
            return result.substring(0, result.length() - 1);
        }
    }

    public static void beingSick(Government government) {
        ArrayList<Pair <Integer , Integer>> tiles = GameController.getNeighborPairs(government.getMainCastle().getEndSpecialX() ,
                government.getMainCastle().getEndSpecialY() , government.getMainCastle().getWidth() ,
                government.getMainCastle().getLength());
        Timeline timeline = new Timeline( new KeyFrame(Duration.millis(100) , e -> {
            for (Pair<Integer , Integer> tile : tiles) {
                GameMap.getGameTile(tile.getFirst() , tile.getSecond()).setSickness();
            }
        }));
        timeline.setCycleCount(150);
        timeline.play();

        timeline.setOnFinished(e -> {
            for (Pair<Integer , Integer> tile : tiles) {
                GameMap.getGameTile(tile.getFirst() , tile.getSecond()).clearSickness();
            }
        });
    }

    public static String nextTurn() throws IOException {
            for (Government government : game.getGovernments()) {
                government.updateAfterTurnGraphical();
            }
            int numberOfRemainedGovernments = howManyGovernmentsRemainsInGame();
            if (numberOfRemainedGovernments - 1 > 0) {
                int randomGovernment = new Random().nextInt(numberOfRemainedGovernments - 1);
                Government government = null;
                int index = 0;
                for (Government iterator : game.getGovernments()) {
                    if (iterator.isAlive() && !iterator.isDead()) {
                        if (index == randomGovernment) {
                            government = iterator;
                            break;
                        } else {
                            index++;
                        }
                    }
                }
                beingSick(government);
            }
        if (numberOfRemainedGovernments == 1) {
            if (!game.isEndGame()) {
                game.setWinner();
                if (!GameMenu.isSpectator)
                    game.setScores();
            }
            game.setEndGame(true);
        } else if (numberOfRemainedGovernments == 0) {
            if (!game.isEndGame()) {
                game.setWinner();
                if (!GameMenu.isSpectator)
                    game.setScores();
            }
            game.setEndGame(true);
        }
            return "";
    }


    public static String showMap(int x, int y) {
        Map map = game.getMap();
        if (y - 3 < 0) y = 3;
        else if (y + 3 >= map.getLength()) y = map.getLength() - 4;
        if (x - 9 < 0) x = 9;
        else if (x + 9 >= map.getWidth()) x = map.getWidth() - 10;
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
                            if (tile.getTool().getToolMove() != null && tile.getTool().getToolMove().isMoving()) {
                                sign = tile.getTool().getGovernment().getColorRgb() + "A";
                            } else {
                                sign = tile.getTool().getGovernment().getColorRgb() + "a";
                            }
                        } else if (filteredHumansList(tile.getHumans()).size() != 0) {
                            HashMap<Government, Integer> numberOfHumansOnTile = new HashMap<>();
                            for (int m = 0; m < game.getGovernments().size(); m++) {
                                numberOfHumansOnTile.put(game.getGovernments().get(m), 0);
                            }
                            for (int m = 0; m < filteredHumansList(tile.getHumans()).size(); m++) {
                                Human human = filteredHumansList(tile.getHumans()).get(m);
                                numberOfHumansOnTile.replace(human.getGovernment(), numberOfHumansOnTile.get(human.getGovernment()) + 1);
                            }
                            int max = Collections.max(numberOfHumansOnTile.values());
                            int numberOfMax = 0;
                            Government maxHumansGovernment = null;
                            for (Government government : numberOfHumansOnTile.keySet()) {
                                if (numberOfHumansOnTile.get(government) == max) {
                                    numberOfMax++;
                                    maxHumansGovernment = government;
                                }
                            }
                            // walking civilians C
                            // walking militaries M
                            // standing civilians c
                            // standing militaries m
                            int walking = 0, standing = 0, civilians = 0, militaries = 0;
                            for (Human human : filteredHumansList(tile.getHumans())) {
                                if (human.getMove() != null && human.getMove().isMoving())
                                    walking++;
                                else
                                    standing++;
                                if (human instanceof Civilian)
                                    civilians++;
                                else
                                    militaries++;
                            }
                            String humansChar = "";
                            if (walking >= standing) {
                                if (militaries >= civilians)
                                    humansChar = "M";
                                else
                                    humansChar = "C";
                            } else {
                                if (militaries >= civilians)
                                    humansChar = "m";
                                else
                                    humansChar = "c";
                            }
                            if (numberOfMax == 1) sign = maxHumansGovernment.getColorRgb() + humansChar;
                            else sign = humansChar;
                        } else if (tile.getBuilding() != null && !(tile.getBuilding() instanceof Wall))
                            sign = tile.getBuilding().getGovernment().getColorRgb() + "B";
                        else if (tile.getBuilding() != null && tile.getBuilding() instanceof Wall)
                            sign = tile.getBuilding().getGovernment().getColorRgb() + ((Wall) tile.getBuilding()).getHeight();
                        else if (tile.isPit() && tile.getPitGovernment().equals(game.getCurrentGovernment()))
                            sign = tile.getPitGovernment().getColorRgb() + "P";
                        else if (tile.getTree() != null) sign = "T";
                        else if (tile.getRockDirection() != null) sign = "R";

                        if (tile.isMoat())
                            result += "\u001B[48;2;47;108;173m" + sign + "\u001B[0m";
                        else result += tile.getTexture().getColor() + sign + "\u001B[0m";
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

        return result.substring(0, result.length() - 1);
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

    public static String showPlayer() {
        return "now Lord " + game.getCurrentGovernment().getUser().getNickname() + " is playing!";
    }

    public static String showRound() {
        return "round " + game.getRound();
    }


    public static String showDetailsOfTile(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);

        String details = "";
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
        }
        if (tile.getTool() != null) {
            details += "tool " + tile.getTool().getName() + " from Lord " +
                    tile.getTool().getGovernment().getUser().getNickname() +
                    " | HP: " + tile.getTool().getHp() + "/20\n";
        }

        HashMap<Government, Integer> civilians = new HashMap<>();
        if (tile.getCivilians().size() != 0) {
            for (int i = 0; i != tile.getCivilians().size(); i++) {
                Civilian civilian = tile.getCivilians().get(i);
                if (civilians.get(civilian.getGovernment()) == null)
                    civilians.put(civilian.getGovernment(), 1);
                else civilians.put(civilian.getGovernment(), civilians.get(civilian.getGovernment()) + 1);
            }
        }
        for (Government government : civilians.keySet())
            details += "Lord " + government.getUser().getNickname() + ": " + civilians.get(government)
                    + "civilians\n";

        HashMap<Government, Integer> militaries = new HashMap<>();
        if (filteredMilitariesList(tile.getMilitaries()).size() != 0) {
            for (int i = 0; i != filteredMilitariesList(tile.getMilitaries()).size(); i++) {
                Military human = filteredMilitariesList(tile.getMilitaries()).get(i);
                if (militaries.get(human.getGovernment()) == null)
                    militaries.put(human.getGovernment(), 1);
                else militaries.put(human.getGovernment(), militaries.get(human.getGovernment()) + 1);
            }
        }
        for (Government government : militaries.keySet())
            details += "Lord " + government.getUser().getNickname() + ": " + militaries.get(government)
                    + "militaries\n";

        if (tile.isPit()) details += "there is a killing pit here\n";
        if (tile.isMoat()) details += "there is a moat here\n";
        return details.substring(0, details.length() - 1);
    }

    public static String showDetailsOfTiles(ArrayList<GameTile> tiles) {
        String details = "";
        ArrayList<Military> militaries = new ArrayList<>();
        for (GameTile gameTile : tiles) {
            militaries.addAll(filteredMilitariesList(gameTile.getTile().getMilitaries()));
        }

        HashMap<Government, Integer> militariesHashmap = new HashMap<>();
        if (militariesHashmap.size() != 0) {
            for (int i = 0; i != militaries.size(); i++) {
                Military human = militaries.get(i);
                if (militariesHashmap.get(human.getGovernment()) == null)
                    militariesHashmap.put(human.getGovernment(), 1);
                else militariesHashmap.put(human.getGovernment(), militariesHashmap.get(human.getGovernment()) + 1);
            }
        }
        for (Government government : militariesHashmap.keySet())
            details += "Lord " + government.getUser().getNickname() + ": " + militariesHashmap.get(government)
                    + "militaries\n";

        HashMap<Building, Integer> produceRates = new HashMap<>();
        for (GameTile gameTile : tiles) {
            Building building = gameTile.getTile().getBuilding();
            if (building != null && building instanceof ProducerBuilding && produceRates.get(building) == null)
                produceRates.put(building, ((ProducerBuilding) building).getRate());
        }
        if (produceRates.size() != 0) {
            details += "minimum production rate: " + Collections.min(produceRates.values());
            details += "maximum production rate: " + Collections.max(produceRates.values());
            double sum = 0;
            for (Integer rate : produceRates.values()) sum += (double) rate;
            details += "average production rate: " + sum / produceRates.size();
        }

        return details;
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
        if (numberOfRequiredWorkers != 0) {
            for (Human human : government.getSociety()) {
                if (human instanceof Civilian) {
                    if (!((Civilian) human).isHasJob()) {
                        building.addHuman(human);
                        numberOfRequiredWorkers--;
                        Move move = new Move(human.getX(), human.getY(), building,
                                true, human);
                        LinkedList<Tuple> path = MoveController.getPathForBuilding(move.getStartPair(), building, human);
                        move.setPath(path);
                        human.setMove(move);
                        ((Civilian) human).setHasJob(true);
                    }
                }
                if (numberOfRequiredWorkers == 0) {
                    return;
                }
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
        return input == -1 || input == -2;
    }

    private static boolean checkToShowOnMap(Human human) {
        if (human instanceof Engineer) return true;
        if (!human.isInvisible())
            return true;
        if (human.getGovernment().equals(game.getCurrentGovernment()))
            return true;
        return false;
    }

    private static ArrayList<Human> filteredHumansList(ArrayList<Human> humans) {
        ArrayList<Human> filteredList = new ArrayList<>();
        for (Human human : humans) {
            if (checkToShowOnMap(human))
                filteredList.add(human);
        }
        return filteredList;
    }

    private static ArrayList<Military> filteredMilitariesList(ArrayList<Military> militaries) {
        ArrayList<Military> filteredList = new ArrayList<>();
        for (Military military : militaries) {
            if (checkToShowOnMap(military))
                filteredList.add(military);
        }
        return filteredList;
    }

    public static ArrayList<Pair<Integer, Integer>> getNeighborPairs(int endX, int endY, int width, int length) {
        ArrayList<Pair<Integer, Integer>> neighborTiles = new ArrayList<>();
        int headX = endX, headY = endY;
        int x = endX, y = endY;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                neighborTiles.add(new Pair<>(x, y));
                if (y % 2 != 0) x--;
                y--;
            }
            if (headY % 2 == 0) headX++;
            headY--;
            x = headX;
            y = headY;
        }
        return neighborTiles;
    }

    public static ArrayList<Tile> getNeighborTiles(int endX, int endY, int width, int length) {
        ArrayList<Tile> neighborTiles = new ArrayList<>();
        int headX = endX, headY = endY;
        int x = endX, y = endY;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                neighborTiles.add(getGame().getMap().getTile(x, y));
                if (y % 2 != 0) x--;
                y--;
            }
            if (headY % 2 == 0) headX++;
            headY--;
            x = headX;
            y = headY;
        }
        return neighborTiles;
    }

    public static ArrayList<Tile> getNeighborTilesFromStart(int startX, int startY, int width, int length) {
        ArrayList<Tile> neighborTiles = new ArrayList<>();
        int headX = startX, headY = startY;
        int x = startX, y = startY;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                neighborTiles.add(getGame().getMap().getTile(x, y));
                if (y % 2 != 0) x--;
                y++;
            }
            if (headY % 2 == 0) headX++;
            headY++;
            x = headX;
            y = headY;
        }
        return neighborTiles;
    }

    public static ArrayList<Tile> getDirectNeighborTiles(Building building) {
        ArrayList<Tile> neighborTiles = new ArrayList<>();
        ArrayList<Tile> tiles = getNeighborTiles(building.getEndX(), building.getEndY(), building.getWidth(), building.getLength());
        for (Tile tile : tiles) {
            ArrayList<Tile> neighbors = HumanController.getNeighbor(tile.x, tile.y);
            for (Tile neighbor : neighbors) {
                if (!tiles.contains(neighbor)) {
                    neighborTiles.add(neighbor);
                }
            }
        }
        return neighborTiles;
    }

    public static Set<GameTile> getSelectedAreaTiles(GameTile start, GameTile end) {
        Set<GameTile> tiles = new HashSet<>();
        if (start != null && end != null) {
            int startX = Math.min(start.getTileX(), end.getTileX());
            int startY = Math.min(start.getTileY(), end.getTileY());
            int endX = Math.max(start.getTileX(), end.getTileX());
            int endY = Math.max(start.getTileY(), end.getTileY());
            int headX = startX, headY = startY, x = startX, y = startY;
            for (int i = 0; i < endY - startY + 1; i++) {
                int indent = ((endY - startY) % 2 == 0 && i % 2 == 0) ? 1 : 0;
                for (int j = 0; j < endX - startX + indent; j++) {
                    tiles.add(GameMap.getGameTile(x++, y));
                }
                if (i % 2 == 0) {
                    if (headY % 2 == 0) headX++;
                    headY++;
                } else {
                    if (headY % 2 != 0) headX--;
                    headY++;
                }
                x = headX;
                y = headY;
            }

            return tiles;
        }

        return null;
    }

    public static void sendDropBuilding(int tileX, int tileY, String droppedBuildingName, String side , Government government) throws IOException {
        Packet packet = new Packet("drop building" , "Game");
        packet.addAttribute("tileX" , tileX);
        packet.addAttribute("tileY" , tileY);
        packet.addAttribute("droppedBuildingName" , droppedBuildingName);
        packet.addAttribute("side" , side);
        packet.addAttribute("color" , government.getColor());
        packet.sendPacket();
        Main.connection.getObjectOutputStream().writeObject(GameController.getFakeGame());
    }

    public static String dropBuilding(int x, int y, String type, String side , Government government) {
        if (type.equals("killingPit")) {
            game.getMap().getTile(x, y).setPit(true);
            game.getMap().getTile(x, y).setPitGovernment(government);
            return "killing pit dropped successfully";
        }
        Building building = GameBuildings.getBuilding(type);
        if (building == null) {
            return "building type is invalid!";
        }
        if (!hasRequired(building.getCost())) {
            return "your resource is not enough!";
        }

        if (type.equals("mainCastle") && government.getBuildingData("mainCastle").getNumber() != 0) {
            return "a government just can have one main Castle!";
        }

        if (building instanceof Gatehouse && !building.getName().equals("drawBridge")) {
            if (side != null && (side.equals("right") || side.equals("left"))) {
                if (!MapController.checkCanPutBuilding(x, y, type, government)) {
                    return "this coordinate is not suitable!";
                }
                MapController.isRightSide = side.equals("right");
                MapController.dropBuilding(x, y, type, government);
                return "building dropped successfully!";

            } else {
                return "side field is required!";
            }
        } else if (side != null) {
            return "side field is not for this type of building!";
        }


        if (!MapController.checkCanPutBuilding(x, y, type, government)) {
            return "this coordinate is not suitable!";
        }
        consumeRequired(building.getCost());
        government.setGold(government.getGold() - building.getPrice());
        MapController.dropBuilding(x, y, type, government);
        int popularity = government.getPopularity() + 37;
        return "building dropped successfully!";
    }

    public static FakeGame getFakeGame() {
        return fakeGame;
    }

    public static void setFakeGame(FakeGame fakeGame) {
        GameController.fakeGame = fakeGame;
    }

    public static void sendSetTexture(int tileX, int tileY, Textures texture) throws IOException {
        Packet packet = new Packet("set texture" , "Game");
        packet.addAttribute("tileX" , tileX);
        packet.addAttribute("tileY" , tileY);
        packet.addAttribute("texture" , texture.getTextureName());
        packet.sendPacket();
        Main.connection.getObjectOutputStream().writeObject(GameController.getFakeGame());
    }

    public static void sendDropRock(int tileX, int tileY, RockDirections rockDirection) throws IOException {
        Packet packet = new Packet("drop rock" , "Game");
        packet.addAttribute("tileX" , tileX);
        packet.addAttribute("tileY" , tileY);
        packet.addAttribute("rock" , rockDirection.getDirection());
        packet.sendPacket();
        Main.connection.getObjectOutputStream().writeObject(GameController.getFakeGame());
    }

    public static void sendDropTree(int tileX, int tileY, Trees tree) throws IOException {
        Packet packet = new Packet("drop tree" , "Game");
        packet.addAttribute("tileX" , tileX);
        packet.addAttribute("tileY" , tileY);
        packet.addAttribute("tree" , tree.getTreeName());
        packet.sendPacket();
        Main.connection.getObjectOutputStream().writeObject(GameController.getFakeGame());
    }

    public static void sendDropUnit(int x, int y, String name, Government government,int id) throws IOException {
        Packet packet = new Packet("drop arabian mercenary" , "Game");
        packet.addAttribute("x" , x);
        packet.addAttribute("y" , y);
        packet.addAttribute("id" , Integer.toString(id));
        packet.addAttribute("name" , name);
        packet.addAttribute("color" , government.getColor());
        packet.sendPacket();
        Main.connection.getObjectOutputStream().writeObject(GameController.getFakeGame());
    }

    public static ArrayList<FakeGame> getAllRunningFakeGames() throws IOException, ClassNotFoundException {
        Packet packet = new Packet("get fake games" , "Game");
        packet.sendPacket();
        FakeGame fakeGame1 = null;
        ArrayList <FakeGame> result = new ArrayList<>();
        do {
            fakeGame1 = (FakeGame) Main.connection.getObjectInputStream().readObject();
            if (fakeGame1.getMapName().equals("Null Map 400*400")) break;
            result.add(fakeGame1);
        } while (true);
        return result;
    }
}
