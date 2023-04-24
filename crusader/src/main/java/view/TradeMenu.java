package view;

import controller.GameController;
import controller.TradeController;
import controller.ViewController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.TradeMenuCommands;
import model.Government;
import model.Trade;
import model.User;
import model.game.Game;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public static void run(Scanner scanner) {
        String input, output;
        showNotifications();
        boolean checkTargetGovernment = selectUser(scanner);
        if (checkTargetGovernment){
            Government government = TradeController.getTargetGovernment();
            System.out.println("selected government is : " + government.getUser().getUsername() + "(" + government.getColor() + ")");
        }else {
            System.out.println("<< Game Menu >>");
            return;
        }


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
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    int amount;
                    int price;
                    try{
                        amount= Integer.parseInt(ViewController.resultMatcher.group("amount"));
                    }catch (NumberFormatException e){
                        amount = -1;
                    }
                    try{
                       price = Integer.parseInt(ViewController.resultMatcher.group("price"));
                    }catch (NumberFormatException e){
                        price = -1;
                    }

                    String type = ViewController.resultMatcher.group("type");
                    type = ViewController.editItem(type);

                    String message = ViewController.resultMatcher.group("message");
                    message = ViewController.editItem(message);

                    output = TradeController.tradeGoods(type, amount, price, message);
                    System.out.println(output);
                }

            } else if (tradeAcceptMatcher.matches()) {
                String items = tradeAcceptMatcher.group("items");
                ArrayList<String> itemsPattern = new ArrayList<>();
                itemsPattern.add(TradeMenuCommands.ID_ITEM.getRegex());
                itemsPattern.add(TradeMenuCommands.MESSAGE_ITEM.getRegex());
                if (ViewController.isItemMatch(items, itemsPattern)) {
                    String id = ViewController.resultMatcher.group("id");
                    id = ViewController.editItem(id);

                    String message = ViewController.resultMatcher.group("message");
                    message = ViewController.editItem(message);

                    output = TradeController.acceptTrade(id,message);
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

    public static void showNotifications() {
        Game game = GameController.getGame();
        Government government = game.getCurrentGovernment();
        HashMap<String, Trade> trades = government.getNewTrades();
        for (String key : trades.keySet()){
            Trade trade = trades.get(key);
            System.out.println("notification: id: "+ key + ", type: " + trade.getTradeType()  + ", sender: " +
                    trade.getSender().getUser().getUsername() +"("+ trade.getSender().getColor() + "), message: " + trade.getRequestMessage());
        }
        government.clearTradeCash();
    }

    public static boolean selectUser(Scanner scanner) {
        ArrayList<Government> governments = new ArrayList<>();
        Game game = GameController.getGame();
        for (Government government : game.getGovernments()) {
            if (!government.equals(game.getCurrentGovernment())) {
                governments.add(government);
            }
        }
        while (true) {
            System.out.println("To which government do you want to send a trade request?");
            System.out.println("please enter the number");
            for (int i = 1; i <= governments.size(); i++) {
                Government government = governments.get(i - 1);
                User user = government.getUser();
                String color = government.getColor();
                System.out.println(i + "." + user.getUsername() + "( " + color + " )");

            }
            String input = scanner.nextLine();
            int num;

            try {
                num = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                num = -1;
            }

            if (1 <= num && num <= governments.size()) {
                TradeController.setTargetGovernment(governments.get(num - 1));
                return true;
            }else if (input.equals(Commands.BACK.getRegex())){
                return false;
            }else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }
}
