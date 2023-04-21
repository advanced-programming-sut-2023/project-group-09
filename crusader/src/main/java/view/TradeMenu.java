package view;

import controller.GameController;
import controller.TradeController;
import controller.ViewController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.GameMenuCommands;
import enumeration.commands.TradeMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public static void run(Scanner scanner) {
        String input, output;
        while (true) {
            input = scanner.nextLine();
            Matcher tradeMatcher = TradeMenuCommands.getMatcher(input, TradeMenuCommands.TRADE);
            Matcher tradeListMatcher = TradeMenuCommands.getMatcher(input, TradeMenuCommands.TRADE_LIST);
            Matcher tradeHistoryMatcher = TradeMenuCommands.getMatcher(input, TradeMenuCommands.TRADE_HISTORY);
            Matcher tradeAcceptMatcher = TradeMenuCommands.getMatcher(input, TradeMenuCommands.TRADE_ACCEPT);
            Matcher backMatcher = Commands.getMatcher(input, Commands.BACK);

            if (tradeMatcher.matches()) {
                String items = tradeMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(TradeMenuCommands.TYPE_ITEM.getRegex());
                itemsPattern.add(TradeMenuCommands.AMOUNT_ITEM.getRegex());
                itemsPattern.add(TradeMenuCommands.PRICE_ITEM.getRegex());
                itemsPattern.add(TradeMenuCommands.MESSAGE_ITEM.getRegex());
                if(ViewController.isItemMatch(items,itemsPattern)){
                    int amount = Integer.parseInt(ViewController.resultMatcher.group("amount"));
                    int price = Integer.parseInt(ViewController.resultMatcher.group("price"));
                    String type = ViewController.resultMatcher.group("type");
                    type = ViewController.editItem(type);

                    String message = ViewController.resultMatcher.group("message");
                    message = ViewController.editItem(message);

                    output = TradeController.tradeGoods(type,amount,price,message);
                    System.out.println(output);
                }

            } else if (tradeAcceptMatcher.matches()) {
                String items = tradeAcceptMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(TradeMenuCommands.ID_ITEM.getRegex());
                itemsPattern.add(TradeMenuCommands.MESSAGE_ITEM.getRegex());
                if(ViewController.isItemMatch(items,itemsPattern)){
                    String id = ViewController.resultMatcher.group("id");
                    id = ViewController.editItem(id);
                    output = TradeController.acceptTrade(id);
                    System.out.println(output);
                }
            } else if (tradeHistoryMatcher.matches()) {
                output = TradeController.showTradeHistory();
                System.out.println(output);
            } else if (tradeListMatcher.matches()) {
                output = TradeController.showTradeList();
                System.out.println(output);
            } else if (backMatcher.matches()) {
                System.out.println("<< Game Menu >>");
                break;
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }

    public static void showNotifications(){
        //
    }

    public static void selectUser(Scanner scanner){
        //
    }
}
