package view;

import controller.GameController;
import controller.MapController;
import controller.ToolsController;
import controller.human.HumanController;
import controller.human.MoveController;
import enumeration.commands.ToolMenuCommands;
import model.activity.ToolAttack;
import model.activity.ToolMove;
import model.building.Building;
import model.building.castlebuildings.Wall;
import model.game.Tile;
import model.game.Tuple;
import model.human.military.Engineer;
import model.human.military.Military;
import model.tools.SiegeTent;
import model.tools.Tool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ToolMenu {
    public static Tool tool;

    public static void run(Scanner scanner) {
        if (tool instanceof SiegeTent || !tool.isActive()) {
            System.out.println("tool is not active or you can't select it!");
            return;
        }

        System.out.println("<< Tool Menu >>");

        while (true) {
            String input = scanner.nextLine();

            Matcher moveM = ToolMenuCommands.MOVE.getMatcher(input);
            Matcher patrolM = ToolMenuCommands.PATROL.getMatcher(input);
            Matcher attackM = ToolMenuCommands.ATTACK.getMatcher(input);

            if (moveM.matches()) {
                String content = moveM.group("content");
                Matcher xM = ToolMenuCommands.X_COORDINATE.getMatcher(content);
                Matcher yM = ToolMenuCommands.Y_COORDINATE.getMatcher(content);

                String validation = validateCoordinates(xM, yM);
                if (!validation.isEmpty()) {
                    System.out.println(validation);
                    continue;
                }

                if (tool.getName().equals("trebuchet")) {
                    System.out.println("you can't move a trebuchet");
                    continue;
                }
                if (!tool.isCanMove()) {
                    System.out.println("you can't move");
                    continue;
                }

                int x = Integer.parseInt(xM.group("x"));
                int y = Integer.parseInt(yM.group("y"));
                LinkedList<Tuple> path = ToolsController.getPathTools(new Tuple(tool.getY(), tool.getX()), new Tuple(y, x));
                if (path == null) {
                    System.out.println("there is no available way to move tool");
                    continue;
                }
                ToolMove move = new ToolMove(tool.getX(), tool.getY(), new Tuple(y, x), true, tool);
                move.setPath(path);
                tool.setToolMove(move);
            } else if (ToolMenuCommands.DISBAND.getMatcher(input).matches()) {
                GameController.getGame().getCurrentGovernment().removeTool(tool);
                HumanController.disbandEngineers(tool.getEngineers(), tool.getGovernment());
//                TODO: update graphics
            } else if (ToolMenuCommands.STOP.getMatcher(input).matches()) {
                ToolsController.stop(tool);
            } else if (patrolM.matches()) {
                String content = patrolM.group("content");
                Matcher x1M = ToolMenuCommands.X1_COORDINATE.getMatcher(content);
                Matcher y1M = ToolMenuCommands.Y1_COORDINATE.getMatcher(content);
                Matcher x2M = ToolMenuCommands.X2_COORDINATE.getMatcher(content);
                Matcher y2M = ToolMenuCommands.Y2_COORDINATE.getMatcher(content);

                String validation = validateDoubleCoordinates(x1M, x2M, y1M, y2M);
                if (!validation.isEmpty()) {
                    System.out.println(validation);
                    continue;
                }

                if (tool.getName().equals("trebuchet")) {
                    System.out.println("you can't patrol a trebuchet");
                    continue;
                }

                if (ToolsController.patrolTool(Integer.parseInt(x1M.group("x1")), Integer.parseInt(y1M.group("y1")),
                        Integer.parseInt(x2M.group("x2")), Integer.parseInt(y2M.group("y2"))))
                    System.out.println("patrol started successfully");
                else System.out.println("invalid patrol");
            } else if (attackM.matches()) {
                String content = attackM.group("content");
                Matcher xM = ToolMenuCommands.X_COORDINATE.getMatcher(content);
                Matcher yM = ToolMenuCommands.Y_COORDINATE.getMatcher(content);

                String validation = validateCoordinates(xM, yM);
                if (!validation.isEmpty()) {
                    System.out.println(validation);
                    continue;
                }

                System.out.println(attack(Integer.parseInt(xM.group("x")), Integer.parseInt(yM.group("y"))));
            } else if (ToolMenuCommands.FREE.getMatcher(input).matches()) {
                for (int i = 0; i < tool.getEngineers().size(); i++) {
                    Engineer engineer = tool.getEngineers().get(i);
                    engineer.setInTool(false);
                    engineer.setInvisible(false);
                    MapController.moveMilitary(tool.getX(), tool.getY(), engineer);
                }
                tool.getEngineers().clear();
            } else if (ToolMenuCommands.ADD_STONE.getMatcher(input).matches()) {
                int rockNumber = tool.getGovernment().getPropertyAmount("rock");
                if (rockNumber < 10) {
                    System.out.println("you can't add stones to tool");
                    continue;
                }
                tool.getGovernment().addAmountToProperties("rock", "resource", -10);
                tool.addStone(20);
            } else if (ToolMenuCommands.BACK.getMatcher(input).matches()) {
                System.out.println("<< Game Menu >>");
                return;
            } else System.out.println("invalid command");
        }
    }

    private static String validateCoordinates(Matcher xM, Matcher yM) {
        if (!xM.find() || !yM.find()) return "invalid command";

        String result = "";
        if (xM.group("x").isEmpty()) result += "x coordinate field is empty\n";
        if (yM.group("y").isEmpty()) result += "y coordinate field is empty\n";
        if (!result.isEmpty()) return result.substring(0, result.length() - 1);

        int x = Integer.parseInt(xM.group("x"));
        int y = Integer.parseInt(yM.group("y"));
        if (x < 1 || y < 1 || x > GameController.getGame().getMap().getWidth() || y > GameController.getGame().getMap().getLength())
            return "invalid coordinates";

        return "";
    }

    private static String validateDoubleCoordinates(Matcher x1M, Matcher x2M, Matcher y1M, Matcher y2M) {
        if (!x1M.find() || !y1M.find() || !x2M.find() || !y2M.find()) return "invalid command";

        String result = "";
        if (x1M.group("x1").isEmpty()) result += "x1 coordinate field is empty\n";
        if (y1M.group("y1").isEmpty()) result += "y1 coordinate field is empty\n";
        if (x2M.group("x2").isEmpty()) result += "x2 coordinate field is empty\n";
        if (y2M.group("y2").isEmpty()) result += "y2 coordinate field is empty\n";
        if (!result.isEmpty()) return result.substring(0, result.length() - 1);

        int x1 = Integer.parseInt(x1M.group("x1"));
        int y1 = Integer.parseInt(y1M.group("y1"));
        int x2 = Integer.parseInt(x2M.group("x2"));
        int y2 = Integer.parseInt(y2M.group("y2"));
        int width = GameController.getGame().getMap().getWidth();
        int length = GameController.getGame().getMap().getLength();
        if (x1 < 1 || y1 < 1 || x2 < 1 || y2 < 1 || x1 > width || y1 > length || x2 > width || y2 > length)
            return "invalid coordinates";

        return "";
    }

    public static String attack(int x, int y) {
        if (tool.getName().equals("portableShield"))
            return "invalid command!";
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        Building building = tile.getBuilding();
        if (tool.getName().equals("batteringRam") || tool.getName().equals("siegeTower")) {
            if (building == null || building.getGovernment().equals(tool.getGovernment())){
                return "please select one building of enemy!";
            }
            LinkedList<Tuple> path = ToolsController.getPathForBuilding(new Tuple(tool.getY(), tool.getX()), building, tool);
            if (path == null) {
                return "there is no available way to move tool!";
            }
            ToolAttack attack = new ToolAttack(tool);
            tool.setToolAttack(attack);
            ToolMove move = new ToolMove(tool.getX(), tool.getY(), building, true, tool);
            move.setPath(path);
            attack.setTargetBuilding(building);
            tool.setToolMove(move);
            return "attack command applied successfully!";
        }

        if (MoveController.getDistance(x, y, tool.getX(), tool.getY()) > tool.getShootingRange())
            return "this point is out of range!";



        if (tool.getName().equals("fireBallista")){
            ArrayList<Military> militaries = MapController.getMilitariesOfOtherGovernment(x,y,tool.getGovernment());
            if (militaries.size() == 0){
                return "please select one point with enemy!";
            }
        }

        ToolAttack attack = new ToolAttack(tool);
        attack.setAttackPoint(new Tuple(y, x));
        tool.setToolAttack(attack);
        return "attack command applied successfully!";
    }
}
