package model.buildinghandler;

public class Storage {
    private int capacity = 0;
    private int amount = 0;

    public int getCapacity() {
        return capacity;
    }

    public void addCapacity(int capacity) {
        this.capacity += capacity;
    }

    public int getAmount() {
        return amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }
}
