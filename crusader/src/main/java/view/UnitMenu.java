package view;

import controller.EngineerController;
import controller.GameController;
import controller.ViewController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.UnitMenuCommands;
import model.human.military.Engineer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu {
    public static void run(Scanner scanner) {

        String input, output;

        while (true) {
            input = scanner.nextLine();
            Matcher moveUnitMenuMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.MOVE_UNIT);
            Matcher patrolUnitMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.PATROL_UNIT);
            Matcher setUnitStateMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.SET_UNIT_STATE);
            Matcher attackMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.ATTACK);
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
                    int x = Integer.parseInt(ViewController.resultMatcher.group("x"));
                    int y = Integer.parseInt(ViewController.resultMatcher.group("y"));
                    output = GameController.moveUnit(x, y);
                    System.out.println(output);
                }
            } else if (patrolUnitMatcher.matches()) {
                String items = patrolUnitMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X1_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y1_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.X2_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y2_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x1 = Integer.parseInt(ViewController.resultMatcher.group("x1"));
                    int y1 = Integer.parseInt(ViewController.resultMatcher.group("y1"));
                    int x2 = Integer.parseInt(ViewController.resultMatcher.group("x2"));
                    int y2 = Integer.parseInt(ViewController.resultMatcher.group("y2"));
                    output = GameController.patrolUnit(x1, y1, x2, y2);
                    System.out.println(output);
                }
            } else if (setUnitStateMatcher.matches()) {
                String items = setUnitStateMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.S_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = Integer.parseInt(ViewController.resultMatcher.group("x"));
                    int y = Integer.parseInt(ViewController.resultMatcher.group("y"));
                    String state = ViewController.resultMatcher.group("state");
                    state = ViewController.editItem(state);
                    output = GameController.setStateOfMilitary(x, y, state);
                    System.out.println(output);
                }
            } else if (attackMatcher.matches()) {
                String items = attackMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = Integer.parseInt(ViewController.resultMatcher.group("x"));
                    int y = Integer.parseInt(ViewController.resultMatcher.group("y"));
                    output = GameController.attackEnemy(x, y);
                    System.out.println(output);
                }
            } else if (pourOilMatcher.matches()) {
                String direction = pourOilMatcher.group("direction");
                output = GameController.pourOil(direction);
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
                }
            } else if (buildMatcher.matches()) {
                runBuild(scanner);
            } else if (digMoatMatcher.matches()) {
//                TODO: return "invalid command" if the selected unit is not an engineer
//                TODO: call digMoat()
            } else if (disbandUnitMatcher.matches()) {
                output = GameController.disbandUnit();
                System.out.println(output);
            } else if (backMatcher.matches()) {
                System.out.println("<< Game Menu >>");
                return;
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }

    private static void runBuild(Scanner scanner) {
//        TODO: return "invalid command" if the selected unit is not an engineer
        String message = "select one of the following tools:\n";
        message += "1. Catapult\n";
        message += "2. Trebuchet\n";
        message += "3. Siege Tower\n";
        message += "4. Battering Ram\n";
        message += "5. Portable Shield\n";
        message += "6. Fire Ballista\n";
        message += "7. Back";
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

        GameController.buildEquipment(number, x, y);
    }
}
