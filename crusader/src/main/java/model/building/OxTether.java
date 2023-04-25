package model.building;

import model.activity.Move;

public class OxTether extends Building{

    private Move move;

    public OxTether(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

}
