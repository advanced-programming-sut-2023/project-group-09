package model.human.civilian;


import enumeration.DefenseRating;
import model.Government;
import model.human.Human;

public class Civilian extends Human {
    private boolean hasJob = false;

    public Civilian(int speed, int y, DefenseRating defenseRating, int health, int shootingRange) {
        super(speed, defenseRating, health, shootingRange);
    }

    public boolean isHasJob() {
        return hasJob;
    }

    public void setHasJob(boolean hasJob) {
        this.hasJob = hasJob;
    }
}
