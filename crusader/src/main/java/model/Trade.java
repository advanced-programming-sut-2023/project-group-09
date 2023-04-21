package model;

public class Trade {
    String id;
    String requestMessage;
    String type;
    int amount;
    int price;
    String acceptMessage;
    boolean isAccepted = false;

    public Trade(String requestMessage, String type, int amount, int price) {
        this.requestMessage = requestMessage;
        this.type = type;
        this.amount = amount;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getRequestMessage() {
        return requestMessage;
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
}
