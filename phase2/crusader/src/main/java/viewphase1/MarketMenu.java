package viewphase1;

import controller.MarketController;
import controller.ViewController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.MarketMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MarketMenu {
    public static void run(Scanner scanner) {
        String input, output;
        System.out.println("<< Market Menu >>");
        while (true) {
            input = scanner.nextLine();
            Matcher showPriceListMatcher = MarketMenuCommands.getMatcher(input, MarketMenuCommands.SHOW_PRICE_LIST);
            Matcher buyMatcher = MarketMenuCommands.getMatcher(input, MarketMenuCommands.BUY);
            Matcher sellMatcher = MarketMenuCommands.getMatcher(input, MarketMenuCommands.SELL);
            Matcher backMatcher = Commands.getMatcher(input, Commands.BACK);
            if (showPriceListMatcher.matches()) {
                output = MarketController.showPriceList();
                System.out.println(output);
            } else if (buyMatcher.matches()) {
                String items = buyMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(MarketMenuCommands.NAME_ITEM.getRegex());
                itemsPattern.add(MarketMenuCommands.AMOUNT_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int amount = Integer.parseInt(ViewController.resultMatcher.group("amount"));
                    String name = ViewController.resultMatcher.group("name");
                    name = ViewController.editItem(name);
//                    output = MarketController.buyItem(name, amount);
//                    System.out.println(output);
                }
            } else if (sellMatcher.matches()) {
                String items = sellMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(MarketMenuCommands.NAME_ITEM.getRegex());
                itemsPattern.add(MarketMenuCommands.AMOUNT_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int amount = Integer.parseInt(ViewController.resultMatcher.group("amount"));
                    String name = ViewController.resultMatcher.group("name");
                    name = ViewController.editItem(name);
//                    output = MarketController.sellItem(name, amount);
//                    System.out.println(output);
                }
            } else if (backMatcher.matches()) {
                System.out.println("<< Game Menu >>");
                break;
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }
}
