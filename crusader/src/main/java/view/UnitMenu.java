package view;

import controller.GameController;
import controller.MapController;
import controller.ViewController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.UnitMenuCommands;
import model.human.military.Engineer;
import model.human.military.Military;

import java.util.ArrayList;
import java.util.Objects;
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
            Matcher fillMoatMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.FILL_MOAT);
            Matcher oilSmelterMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.GO_TO_OIL_SMELTER);
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
                String items = enterToolMatcher.group("items");
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
            } else if (oilSmelterMatcher.matches()) {
                String items = oilSmelterMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.goToOilSmelter(x, y);
                    System.out.println(output);
                    if (output.equals("engineer now works in this oilSmelter!")) {
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
                if (!Objects.equals(direction, "w") && !Objects.equals(direction, "e") && !Objects.equals(direction, "n") && !Objects.equals(direction, "s")) {
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
                boolean check = runBuild(scanner);
                if(check){
                    return;
                }
            } else if (digMoatMatcher.matches()) {
                String items = digMoatMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = Integer.parseInt(ViewController.resultMatcher.group("x"));
                    int y = Integer.parseInt(ViewController.resultMatcher.group("y"));
                    output = GameController.digMoat(x, y);
                    System.out.println(output);
                    if (output.equals("dig moat order recorded successfully!")) {
                        return;
                    }
                }
            } else if (fillMoatMatcher.matches()) {
                String items = fillMoatMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = Integer.parseInt(ViewController.resultMatcher.group("x"));
                    int y = Integer.parseInt(ViewController.resultMatcher.group("y"));
                    output = GameController.fillMoat(x, y);
                    System.out.println(output);
                    if (output.equals("fill moat order recorded successfully!")) {
                        return;
                    }
                }
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

    private static boolean runBuild(Scanner scanner) {
        ArrayList<Engineer> engineers = getEngineersOfTile();
        if (engineers.size() == 0) {
            System.out.println("invalid command");
            return false;
        }
        for (int i = 0; i < engineers.size(); i++) {
            if (engineers.get(i).isInTool()) {
                System.out.println("some of the selected engineers are in a tool");
                return false;
            }
        }
        if (!GameController.getGame().getMap().getTile(x, y).getCanPutBuilding()) {
            System.out.println("you can't build a tool here!");
            return false;
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
        int number = 0;
        while (true) {
            try {
                number = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("enter a number:");
                continue;
            }
            if (number < 1 || number > 7) {
                System.out.println("invalid number!\nreenter a number:");
                continue;
            }
            if (number == 7) back = true;
            break;
        }

        if (back) return false;
        String output = GameController.buildEquipment(number, engineers, x, y);
        System.out.println(output);
        return output.equals("siegeTent put for this goal successfully!");
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
        ArrayList<Military> militaries = MapController.getOneTypeOfMilitariesOfGovernment(x, y, "engineer", GameController.getGame().getCurrentGovernment());
        for (Military military : militaries) {
            engineers.add((Engineer) military);
        }
        return engineers;
    }
}
