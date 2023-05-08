package view;

import controller.GameController;
import controller.ToolsController;
import controller.human.MoveController;
import enumeration.commands.ToolMenuCommands;
import model.activity.Move;
import model.activity.ToolMove;
import model.game.Tuple;
import model.human.military.Engineer;
import model.tools.Tool;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ToolMenu {
    public static Tool tool;

    public static void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            Matcher moveM = ToolMenuCommands.MOVE.getMatcher(input);
            Matcher patrolM = ToolMenuCommands.PATROL.getMatcher(input);

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
//                TODO: disband engineers
//                TODO: update graphics
            } else if (ToolMenuCommands.STOP.getMatcher(input).matches()) {
//                TODO: stop patrol or attack
            } else if (patrolM.matches()) {
                String content = patrolM.group("content");
                Matcher xM = ToolMenuCommands.X_COORDINATE.getMatcher(content);
                Matcher yM = ToolMenuCommands.Y_COORDINATE.getMatcher(content);

                String validation = validateCoordinates(xM, yM);
                if (!validation.isEmpty()) {
                    System.out.println(validation);
                    continue;
                }

//                TODO: patrol tool
            } else if (ToolMenuCommands.FREE.getMatcher(input).matches()) {
                for (int i = 0; i < tool.getEngineers().size(); i++) {
                    Engineer engineer = tool.getEngineers().get(i);
                    engineer.setInTool(false);
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

    public static String validateCoordinates(Matcher xM, Matcher yM) {
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
}
