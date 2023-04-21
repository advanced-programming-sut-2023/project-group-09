package model.human.civilian;


import enumeration.DefenseRating;
import model.Government;
import model.building.Building;
import model.building.producerbuildings.ProducerBuilding;
import model.building.storagebuildings.StorageBuilding;
import model.human.Human;

public class Civilian extends Human {
    private boolean hasJob = false;
    private ProducerBuilding originBuilding;
    private int originX, originY;
    private Building destinationBuilding;
    private int destinationX, destinationY;
    private boolean isGoingToDestination;

    public Civilian(int speed, DefenseRating defenseRating, int health, int shootingRange) {
        super(speed, defenseRating.getRate(), shootingRange);
    }

    public boolean isHasJob() {
        return hasJob;
    }

    public void setHasJob(boolean hasJob) {
        this.hasJob = hasJob;
    }

    public void doBuildingJob() {
        originBuilding.addProduct();
    }
}
