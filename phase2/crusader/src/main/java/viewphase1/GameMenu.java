package viewphase1;

import controllers.GameController;
import controllers.MainController;
import controllers.ViewController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.GameMenuCommands;
import enumeration.commands.MapCommands;
import enumeration.commands.UnitMenuCommands;
import model.game.Game;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {

    public static void run(Scanner scanner, Game game) {
        String input, output;
        System.out.println("<< Game Menu >>");
        while (true) {
            input = scanner.nextLine();
            Matcher dropBuildingMatcher = GameMenuCommands.getMatcher(input, GameMenuCommands.DROP_BUILDING);
            Matcher selectBuildingMatcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_BUILDING);
            Matcher selectToolMatcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_TOOL);
            Matcher selectUnitMatcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_UNIT);
            Matcher governmentMenuMatcher = GameMenuCommands.getMatcher(input, GameMenuCommands.GOVERNMENT_MENU);
            Matcher tradeMenuMatcher = GameMenuCommands.getMatcher(input, GameMenuCommands.TRADE_MENU);
            Matcher setUnitStateMatcher = UnitMenuCommands.getMatcher(input, UnitMenuCommands.SET_UNIT_STATE);
            Matcher nextTurnMatcher = GameMenuCommands.getMatcher(input, GameMenuCommands.NEXT_TURN);
            Matcher showPlayerMatcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_PLAYER);
            Matcher showRoundMatcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_ROUND);
            Matcher backMatcher = Commands.getMatcher(input, Commands.BACK);
            Matcher exitMatcher = Commands.getMatcher(input, Commands.EXIT_CRUSADER);
            Matcher showMapM = MapCommands.SHOW_MAP.getMatcher(input);
            Matcher continueM = GameMenuCommands.getMatcher(input, GameMenuCommands.CONTINUE);

            if (dropBuildingMatcher.matches()) {
                String items = dropBuildingMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(GameMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(GameMenuCommands.Y_ITEM.getRegex());
                itemsPattern.add(GameMenuCommands.TYPE_ITEM.getRegex());
                itemsPattern.add(GameMenuCommands.SIDE_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    String type = ViewController.resultMatcher.group("type");
                    String side = ViewController.resultMatcher.group("side");
                    type = ViewController.editItem(type);
                    output = GameController.dropBuilding(x, y, type, side);
                    System.out.println(output);
                }
            } else if (selectBuildingMatcher.matches()) {
                String items = selectBuildingMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(GameMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(GameMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.selectBuilding(x, y);
                    System.out.println(output);
                    if (!output.equals("There is no building of your government here!")) {
                        BuildingMenu.run(scanner);
                    }
                }
            } else if (selectUnitMatcher.matches()) {
                String items = selectUnitMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(GameMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(GameMenuCommands.Y_ITEM.getRegex());
                itemsPattern.add(GameMenuCommands.UNIT_TYPE_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    String type = ViewController.resultMatcher.group("type");
                    output = GameController.selectUnit(x, y, type);
                    System.out.println(output);
                }
            } else if (selectToolMatcher.matches()) {
                String items = selectToolMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(GameMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(GameMenuCommands.Y_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    output = GameController.selectTool(x, y);
                    if (output.charAt(1) == 'h') {
                        System.out.println("no tool in this place!");
                        continue;
                    }
                    System.out.println(output);
                    ToolMenu.run(scanner);
                }
            } else if (setUnitStateMatcher.matches()) {
                String items = setUnitStateMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(UnitMenuCommands.X_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.Y_ITEM.getRegex());
                itemsPattern.add(UnitMenuCommands.S_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int x = ViewController.getNumberOfRegex("x");
                    int y = ViewController.getNumberOfRegex("y");
                    String state = ViewController.resultMatcher.group("type");
                    state = ViewController.editItem(state);
                    output = GameController.setStateOfMilitary(x, y, state);
                    System.out.println(output);
                }
            } else if (governmentMenuMatcher.matches()) {
                GovernmentMenu.run(scanner);
            } else if (tradeMenuMatcher.matches()) {
                TradeMenu.run(scanner);
            } else if (nextTurnMatcher.matches()) {
                System.out.println(GameController.changeTurn());
                if (game.isEndGame()) {
                    System.out.println("<< view.Main Menu >>");
                    break;
                }
            } else if (showPlayerMatcher.matches()) {
                System.out.println(GameController.showPlayer());
            } else if (showRoundMatcher.matches()) {
                System.out.println(GameController.showRound());
            } else if (showMapM.matches()) {
                MapMenu.runShowMapOrShowDetailsOrClearLand(showMapM, 0);
                MapMenu.run(scanner);
            } else if (backMatcher.matches()) {
                System.out.println("are you sure you want to quit?\n1.yes\n2.cancel");
                int number = 0;
                while (true) {
                    try {
                        number = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("enter a number!");
                        continue;
                    }
                    if (number != 1 && number != 2) {
                        System.out.println("invalid number!");
                        continue;
                    }
                    break;
                }
                if (number == 2) continue;
                else break;
            } else if (exitMatcher.matches()) {
                MainController.exitCrusader();
            } else if (input.equals("show gold")) {
                System.out.println(game.getCurrentGovernment().getGold());
            } else if (continueM.matches()) {
                System.out.println(GameController.changeTurn());
                if (game.isEndGame()) {
                    System.out.println("<< view.Main Menu >>");
                    break;
                }
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }
}
