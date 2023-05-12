package model.game;

import enumeration.Pair;

public class Tuple {
    int x;
    int y;
    boolean overhead = false;

    Tuple parentPair;
    public Tuple(int y,int x , boolean overhead,Tuple tuple) {
        this.x = x;
        this.y = y;
        this.overhead = overhead;
        parentPair = tuple;
    }
    public Tuple(int y,int x,Tuple tuple) {
        this.x = x;
        this.y = y;
        parentPair = tuple;
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

    public Tuple getParentPair() {
        return parentPair;
    }

    public void setParentPair(Tuple tuple) {
        this.parentPair = tuple;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof  Tuple tuple){
            if(tuple == this){
                return true;
            }

            return tuple.x == x && tuple.y == y;
        }
        return false;
    }
}
