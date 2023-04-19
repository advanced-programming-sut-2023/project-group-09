package model.human.civilian;


import enumeration.DefenseRating;
import model.Government;
import model.human.Human;

public class Civilian extends Human {
    private boolean hasJob = false;

    public Civilian(Government government, int speed, int x, int y, DefenseRating defenseRating, int health, int shootingRange) {
        super(government, speed, x, y, defenseRating, health, shootingRange);
    }

    public boolean isHasJob() {
        return hasJob;
    }

    public void setHasJob(boolean hasJob) {
        this.hasJob = hasJob;
    }
}
