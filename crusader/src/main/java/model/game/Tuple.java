package model.game;

public class Tuple {
    int x;
    int y;
    boolean overhead = false;

    public Tuple(int y,int x , boolean overhead) {
        this.x = x;
        this.y = y;
        this.overhead = overhead;
    }

    public Tuple(int y,int x) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOverhead() {
        return overhead;
    }

    public void setOverhead(boolean overhead) {
        this.overhead = overhead;
    }
}
