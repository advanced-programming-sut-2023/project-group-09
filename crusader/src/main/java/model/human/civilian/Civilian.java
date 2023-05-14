package model.human.civilian;


import controller.human.MoveController;
import enumeration.DefenseRating;
import enumeration.Speed;
import model.Government;
import model.activity.Move;
import model.building.Building;
import model.building.castlebuildings.MainCastle;
import model.building.producerbuildings.ProducerBuilding;
import model.game.Tuple;
import model.human.Human;

import java.util.LinkedList;

public class Civilian extends Human {
    private boolean hasJob = false;
    private ProducerBuilding originBuilding;
    private int originX, originY;
    private Building destinationBuilding;
    private int destinationX, destinationY;
    private boolean isGoingToDestination;

    public Civilian(int x, int y, boolean hasJob, Government government) {
        super(Speed.FAST.getRate(), DefenseRating.VERY_LOW.getRate(), 0);
        this.setX(x);
        this.setY(y);
        this.hasJob = hasJob;
        this.setGovernment(government);
        shouldGoToCastle();
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

    public void shouldGoToCastle() {
        if (hasJob) {
            return;
        }
        MainCastle mainCastle = this.getGovernment().getMainCastle();
        int x = this.getX();
        int y = this.getY();

        boolean isInCastle = mainCastle.getStartX() <= x && mainCastle.getEndX() >= x && mainCastle.getStartY() <= y && mainCastle.getEndY() >= y;
        if (isInCastle) {
            return;
        }

        Tuple end = mainCastle.makePositionOfUnit();
        Tuple start = new Tuple(this.getY(), this.getX());

        LinkedList<Tuple> path = MoveController.getPath(start, end, null);
        if (path != null) {
            Move move = new Move(start.getX(), start.getY(), end, true, this);
            move.setPath(path);
            this.setMove(move);
        }
    }

    public int getSpeed() {
        if (this.getGovernment() != null && hasJob) {
            if (this.getGovernment().getFearRate() > 0){
                return super.getSpeed();
            }
            return this.getGovernment().getFearRate() * (-1) + super.getSpeed();
        }
        return super.getSpeed();
    }

    public ProducerBuilding getOriginBuilding() {
        return originBuilding;
    }

    public void setOriginBuilding(ProducerBuilding originBuilding) {
        this.originBuilding = originBuilding;
    }
}
