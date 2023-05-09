package view;

import controller.EngineerController;
import controller.GameController;
import controller.MapController;
import controller.ViewController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.UnitMenuCommands;
import model.human.military.Engineer;
import model.human.military.Military;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu {
    public static int x;
    public static int y;
    public static String type;


    public static void run(Scanner scanner) {

        String input, output;
        System.out.println("<< Unit Menu >>");
        while (true) {

            input = scanner.nextLine();
            Matcher moveUnitMenuMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.MOVE_UNIT);
            Matcher patrolUnitMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.PATROL_UNIT);
            Matcher deactivatePatrolMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.DEACTIVATE_PATROL);
            Matcher airAttackMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.AIR_ATTACK);
            Matcher attackEnemyMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.ATTACK_ENEMY);
            Matcher airAttackBuildingMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.AIR_ATTACK_BUILDING);
            Matcher attackBuildingMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.ATTACK_BUILDING);
            Matcher airAttackToolMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.AIR_ATTACK_TOOL);
            Matcher attackToolMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.ATTACK_TOOL);
            Matcher enterToolMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.ENTER_TOOL);
            Matcher pourOilMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.POUR_OIL);
            Matcher digTunnelMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.DIG_TUNNEL);
            Matcher buildMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.BUILD);
            Matcher digMoatMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.DIG_MOAT);
            Matcher disbandUnitMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.DISBAND_UNIT);
            Matcher backMatcher = Commands.getMatcher(input, Commands.BACK);


            if (moveUnitMenuMatcher.matches()) {

                String items = moveUnitMenuMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.moveUnit(x, y);
                    System.out.println(output);

                    if (output.equals("unit(s) moved successfully!")) {
                        return;
                    }
                }
            } else if (patrolUnitMatcher.matches()) {
                String items = patrolUnitMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X1_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y1_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.X2_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y2_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x1 = ViewController.getNumberOfRegex("x1");
                    int y1 = ViewController.getNumberOfRegex("y1");
                    int x2 = ViewController.getNumberOfRegex("x2");
                    int y2 = ViewController.getNumberOfRegex("y2");
                    output = GameController.patrolUnit(x1, y1, x2, y2);
                    System.out.println(output);
                    if (output.equals("patrol started successfully!")) {
                        return;
                    }
                }
            } else if (deactivatePatrolMatcher.matches()) {
                output = GameController.deactivatePatrolUnit();
                System.out.println(output);

            } else if (attackEnemyMatcher.matches()) {
                String items = attackEnemyMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.attackEnemy(x, y, scanner);
                    System.out.println(output);
                    if (output.equals("attack order has been recorded successfully!")) {
                        return;
                    }
                }
            } else if (airAttackMatcher.matches()) {
                String items = airAttackMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.airAttack(x, y);
                    System.out.println(output);
                    if (output.equals("attack order has been recorded successfully!")) {
                        return;
                    }
                }
            } else if (attackBuildingMatcher.matches()) {
                String items = attackBuildingMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.attackBuilding(x, y);
                    System.out.println(output);
                    if (output.equals("attack order has been recorded successfully!")) {
                        return;
                    }
                }

            } else if (airAttackBuildingMatcher.matches()) {
                String items = airAttackBuildingMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.airAttackBuilding(x, y);
                    System.out.println(output);
                    if (output.equals("attack order has been recorded successfully!")) {
                        return;
                    }
                }
            } else if (attackToolMatcher.matches()) {
                String items = attackToolMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.attackTool(x, y);
                    System.out.println(output);
                    if (output.equals("attack order has been recorded successfully!")) {
                        return;
                    }
                }

            } else if (airAttackToolMatcher.matches()) {
                String items = airAttackToolMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.airAttackTool(x, y);
                    System.out.println(output);
                    if (output.equals("attack order has been recorded successfully!")) {
                        return;
                    }
                }
            } else if (enterToolMatcher.matches()) {
                String items = airAttackToolMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.useTool(x, y);
                    System.out.println(output);
                    if (output.equals("order record successfully!")) {
                        return;
                    }
                }
            } else if (pourOilMatcher.matches()) {
                String direction = pourOilMatcher.group("direction");
                ArrayList<Engineer> engineers = getEngineersOfTile();
                if (engineers.size() == 0) {
                    System.out.println("invalid command");
                    return;
                }
                for (int i = 0; i < engineers.size(); i++) {
                    if (engineers.get(i).isInTool()) {
                        System.out.println("some of the selected engineers are in a tool");
                        return;
                    }
                }
                if (direction != "w" && direction != "e" && direction != "n" && direction != "s") {
                    System.out.println("invalid direction");
                    continue;
                }
                output = GameController.pourOil(direction, engineers);
                System.out.println(output);
            } else if (digTunnelMatcher.matches()) {
                String items = digTunnelMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = Integer.parseInt(ViewController.resultMatcher.group("x"));
                    int y = Integer.parseInt(ViewController.resultMatcher.group("y"));
                    output = GameController.digTunnel(x, y);
                    System.out.println(output);
                    if (output.equals("dig tunnel order recorded successfully!")) {
                        return;
                    }
                }
            } else if (buildMatcher.matches()) {
                runBuild(scanner);
            } else if (digMoatMatcher.matches()) {
                ArrayList<Engineer> engineers = getEngineersOfTile();
                if (engineers.size() == 0) {
                    System.out.println("invalid command");
                    return;
                }
                for (int i = 0; i < engineers.size(); i++) {
                    if (engineers.get(i).isInTool()) {
                        System.out.println("some of the selected engineers are in a tool");
                        return;
                    }
                }
                EngineerController.digMoat();
            } else if (disbandUnitMatcher.matches()) {
                output = GameController.disbandUnit();
                System.out.println(output);
                return;
            } else if (backMatcher.matches()) {
                System.out.println("<< Game Menu >>");
                return;
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }

    private static void runBuild(Scanner scanner) {
        ArrayList<Engineer> engineers = getEngineersOfTile();
        if (engineers.size() == 0) {
            System.out.println("invalid command");
            return;
        }
        for (int i = 0; i < engineers.size(); i++) {
            if (engineers.get(i).isInTool()) {
                System.out.println("some of the selected engineers are in a tool");
                return;
            }
        }

        String message = "select one of the following tools:\n";
        message += "1.Catapult\n";
        message += "2.Trebuchet\n";
        message += "3.Siege Tower\n";
        message += "4.Battering Ram\n";
        message += "5.Portable Shield\n";
        message += "6.Fire Ballista\n";
        message += "7.back";
        System.out.println(message);

        boolean back = false;
        int number = 0, x = 0, y = 0;
        while (true) {
            try {
                number = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("enter a number:");
                continue;
            }
            if (number < 1 || number > 7) {
                System.out.println("invalid number\nreenter a number:");
                continue;
            }
            if (number == 7) back = true;
            break;
        }

        if (back) return;

        while (true) {
            System.out.println("enter the x and y coordinates:");
            String coords = scanner.nextLine();
            Matcher xMatcher = UnitMenuCommands.getMatcher(coords, UnitMenuCommands.X_ITEM);
            Matcher yMatcher = UnitMenuCommands.getMatcher(coords, UnitMenuCommands.Y_ITEM);

            if (!xMatcher.find() || !yMatcher.find()) {
                System.out.println("invalid command");
                continue;
            }
            x = Integer.parseInt(xMatcher.group("x"));
            y = Integer.parseInt(yMatcher.group("y"));
            break;
        }

        System.out.println(GameController.buildEquipment(number, engineers, x, y));
    }

    public static Military getEnemy(int x, int y, Scanner scanner) {
        ArrayList<Military> militaries = MapController.getMilitariesOfOtherGovernment(x, y, GameController.getGame().getCurrentGovernment());
        if (militaries.size() == 0) {
            return null;
        }
        System.out.println("Choose one of the following troop:");
        for (int i = 0; i < militaries.size(); i++) {
            Military military = militaries.get(i);
            System.out.println(i + 1 + "." + military.getName() + " (" + military.getGovernment().getColor() + ") hp: " + military.getHealth());
        }
        String input = scanner.nextLine();
        int index = ViewController.getNumberOfRegex(input);
        if (index < 1 || index > militaries.size()) {
            return null;
        }
        return militaries.get(index - 1);
    }

    public static ArrayList<Engineer> getEngineersOfTile() {
        ArrayList<Engineer> engineers = new ArrayList<>();
        for (int i = 0; i < GameController.getGame().getMap().getTile(x, y).getMilitaries().size(); i++) {
            Military military = GameController.getGame().getMap().getTile(x, y).getMilitaries().get(i);
            if (military instanceof Engineer)
                engineers.add((Engineer) military);
        }
        return engineers;
    }
}
