package controllers;

import model.Government;
import model.Trade;
import model.menugui.MenuPopUp;
import view.menus.GameMenu;

import java.util.LinkedHashMap;

public class TradeController {
    private static Government targetGovernment;
    public static LinkedHashMap<String, Trade> allTrades = new LinkedHashMap<>();

    public static Government getTargetGovernment() {
        return targetGovernment;
    }

    public static void setTargetGovernment(Government target) {
        targetGovernment = target;
    }

    public static String tradeGoods(String resourceType, int resourceAmount, int price, String message) {
        if (price > GameController.getGame().getCurrentGovernment().getGold()) {
            return "your gold isn't enough!";
        }
        Government currentGovernment = GameController.getGame().getCurrentGovernment();
        Trade trade = new Trade(message, resourceType, resourceAmount, price, currentGovernment, targetGovernment);
        currentGovernment.addSentTrade(trade);
        targetGovernment.addReceivedTrade(trade);
        allTrades.put(trade.getId(), trade);
        return "request sent successfully";
    }

    public static boolean acceptTrade(String id) {
        Government currentGovernment = GameController.getGame().getCurrentGovernment();

        Trade trade = currentGovernment.getReceivedTrades().get(id);

        if (trade.getTradeType().equals("request") && trade.getSender().getGold() < trade.getPrice()) {
            MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400, "error",
                    "sender doesn't have enough money!");
            GameMenu.root.getChildren().add(popUp);
            return false;
        }

        boolean check;
        if (trade.getTradeType().equals("request"))
            check = GovernmentController.consumeProduct(currentGovernment, trade.getType(), trade.getAmount());
        else check = GovernmentController.consumeProduct(trade.getSender(), trade.getType(), trade.getAmount());
        if (!check) {
            String pronoun = "your";
            if (trade.getTradeType().equals("donate")) pronoun = "sender's";
            MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400, "error",
                    pronoun + " resource is not enough!");
            GameMenu.root.getChildren().add(popUp);
            return false;
        }

        if (trade.getTradeType().equals("request"))
            check = GovernmentController.canAddProduct(trade.getSender(), trade.getType(), trade.getAmount());
        else check = GovernmentController.canAddProduct(currentGovernment, trade.getType(), trade.getAmount());
        if (!check) {
            String pronoun = "sender doesn't";
            if (trade.getTradeType().equals("donate")) pronoun = "you don't";
            MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400, "error",
                    pronoun + " have capacity to store!");
            GameMenu.root.getChildren().add(popUp);
            return false;
        }

        if (trade.getTradeType().equals("request")) {
            currentGovernment.addGold(trade.getPrice());
            trade.getSender().addGold(-trade.getPrice());
            GovernmentController.generateProduct(trade.getSender(), trade.getType(), trade.getAmount());
        } else GovernmentController.generateProduct(currentGovernment, trade.getType(), trade.getAmount());
        return true;
    }

    public static String showTradeList() {
        Government government = GameController.getGame().getCurrentGovernment();
        String output = "unaccepted received requests/donations:\n";
        int count = 0;
        for (String id : government.getReceivedTrades().keySet()) {
            Trade trade = government.getReceivedTrades().get(id);
            if (!trade.isAccepted()) {
                count++;
                if (count > 1) output += ".._.._.._.._.._.._.._.._.._.._.._.._.._.._.._\n";
                output += makeTradeInfo(trade);
                output += "request message: " + trade.getRequestMessage() + "\n";
            }
        }
        if (count == 0) {
            output += "you don't have any unaccepted received requests/donations!\n";
        }
        return output.substring(0, output.length() - 1);
    }

    public static String showTradeHistory() {
        Government government = GameController.getGame().getCurrentGovernment();
        String output = "sent requests/donations:\n";
        int count = 0;
        for (String id : government.getSentTrades().keySet()) {
            count++;
            Trade trade = government.getSentTrades().get(id);
            if (count > 1) output += ".._.._.._.._.._.._.._.._.._.._.._.._.._.._.._\n";
            output += makeTradeInfo(trade);
            output += "request message: " + trade.getRequestMessage() + "\n";
        }
        if (count == 0) output += "you don't have sent requests/donations!\n";
        output += "=============================================\n";
        output += "accepted received requests/donations:\n";
        count = 0;
        for (String id : government.getReceivedTrades().keySet()) {
            Trade trade = government.getReceivedTrades().get(id);
            if (trade.isAccepted()) {
                count++;
                if (count > 1) output += ".._.._.._.._.._.._.._.._.._.._.._.._.._.._.._\n";
                output += makeTradeInfo(trade);
                output += "accept message: " + trade.getAcceptMessage() + "\n";
            }
        }
        if (count == 0) {
            output += "you don't have any accepted received requests/donations!\n";
        }
        return output.substring(0, output.length() - 1);
    }

    private static String makeTradeInfo(Trade trade) {
        return "id: " + trade.getId() + "\n" +
                "type: " + trade.getTradeType() + "\n" +
                "resource type: " + trade.getType() + "\n" +
                "amount: " + trade.getAmount() + "\n" +
                "price: " + trade.getPrice() + "\n" +
                "sender: " + trade.getSender().getUser().getUsername() + " (" + trade.getSender().getColor() + ")\n";
    }

    private static boolean checkNullFields(String input) {
        return input == null || input.length() == 0;
    }

    private static boolean checkNullFields(int input) {
        return input == -1;
    }
}
