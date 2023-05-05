package model;

import controller.TradeController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Trade {
    private String id;
    private String requestMessage;
    private String type;
    private String tradeType = "request";
    private int amount;
    private int price;
    private String acceptMessage;
    private Government sender;
    private Government receiver;
    private boolean isAccepted = false;


    public Trade(String requestMessage, String type, int amount, int price, Government sender, Government receiver) {
        this.requestMessage = requestMessage;
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.sender = sender;
        this.receiver = receiver;
        if (price == 0) {
            tradeType = "donate";
        }
        setId();
    }

    public String getId() {
        return id;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public String getTradeType() {
        return tradeType;
    }

    public Government getSender() {
        return sender;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public String getAcceptMessage() {
        return acceptMessage;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAcceptMessage(String acceptMessage) {
        this.acceptMessage = acceptMessage;
    }

    public void accept() {
        isAccepted = true;
    }

    public void setId() {
        String id = makeId();
        this.id = id;
    }

    public void setIsAccepted(boolean accepted) {
        this.isAccepted = accepted;
    }

    public Government getReceiver() {
        return receiver;
    }

    public String makeId() {
        char[] allCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+-*/~`".toCharArray();
        char[] id = new char[5];
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int selectedChar = random.nextInt(allCharacters.length);
            id[i] = allCharacters[selectedChar];
        }
        HashMap<String, Trade> tradeHashMap = TradeController.allTrades;
        if (tradeHashMap.get(Arrays.toString(id)) != null) {
            return makeId();
        }
        return Arrays.toString(id);
    }
}
