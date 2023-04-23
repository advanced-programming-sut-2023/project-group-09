package view;

import controller.GameController;
import controller.ViewController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.UnitMenuCommands;

import java.util.ArrayList;
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
                String equipment = buildMatcher.group("equipment");
                output = GameController.buildEquipment(equipment);
                System.out.println(output);
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
}
