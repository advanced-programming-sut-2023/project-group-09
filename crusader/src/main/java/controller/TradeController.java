package controller;

import controller.gamestructure.GameGoods;
import model.Government;
import model.Trade;

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
        String validateMessage = validateTradeItems(resourceType, resourceAmount, price, message);
        if (validateMessage != null) {
            return validateMessage;
        }
        Government currentGovernment = GameController.getGame().getCurrentGovernment();
        Trade trade = new Trade(message, resourceType, resourceAmount, price, currentGovernment, targetGovernment);
        currentGovernment.addSentTrade(trade);
        targetGovernment.addReceivedTrade(trade);
        allTrades.put(trade.getId(), trade);
        return "request sent successfully!";
    }

    public static String acceptTrade(String id, String message) {
        Government currentGovernment = GameController.getGame().getCurrentGovernment();

        if (id == null) {
            return "id field is required!";
        }

        if (message == null) {
            return "message field is required!";
        }

        Trade trade = currentGovernment.getReceivedTrades().get(id);
        if (trade == null) {
            return "no trade with this id exist!";
        }

        if (trade.isAccepted()) {
            return "this trade was accepted before!";
        }

        boolean check;
        if (trade.getTradeType().equals("request"))
            check = GovernmentController.consumeProduct(currentGovernment, trade.getType(), trade.getAmount());
        else check = GovernmentController.consumeProduct(trade.getSender(), trade.getType(), trade.getAmount());
        if (!check) {
            String pronoun = "your";
            if (trade.getTradeType().equals("donate")) pronoun = "sender's";
            return pronoun + " resource is not enough!";
        }

        if (trade.getTradeType().equals("request"))
            check = GovernmentController.canAddProduct(trade.getSender(), trade.getType(), trade.getAmount());
        else check = GovernmentController.canAddProduct(currentGovernment, trade.getType(), trade.getAmount());
        if (!check) {
            String pronoun = "sender doesn't";
            if (trade.getTradeType().equals("donate")) pronoun = "you don't";
            return pronoun + " have capacity to store!";
        }

        if (trade.getTradeType().equals("request") && trade.getSender().getGold() < trade.getPrice()) {
            return "sender doesn't have enough money!";
        }
        trade.setAcceptMessage(message);
        trade.setIsAccepted(true);
        if (trade.getTradeType().equals("request")) {
            currentGovernment.addGold(trade.getPrice());
            trade.getSender().addGold(-trade.getPrice());
            GovernmentController.generateProduct(trade.getSender(), trade.getType(), trade.getAmount());
        } else GovernmentController.generateProduct(currentGovernment, trade.getType(), trade.getAmount());
        return "request accepted successfully";
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

    public static String validateTradeItems(String resourceType, int resourceAmount, int price, String message) {
        if (checkNullFields(resourceType)) {
            return "resource type field is required!";
        }
        if (checkNullFields(resourceAmount)) {
            return "resource amount field is required!";
        }
        if (checkNullFields(price)) {
            return "price field is required!";
        }
        if (checkNullFields(message)) {
            return "message field is required!";
        }

        if (GameGoods.getProduct(resourceType) == null) {
            return "resource type is invalid!";
        }

        if (resourceAmount == 0) {
            return "amount value can not be 0!";
        }
        return null;
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
