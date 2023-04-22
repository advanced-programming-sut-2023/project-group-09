package controller;

import model.game.Tile;
import model.human.military.Engineer;
import model.tools.AttackingAndDefendingTool;
import model.tools.Moat;

public class EngineerController {
    public static Engineer currentEngineer;

    public void digMoat() {
        for (int i = 0; i < Moat.getTilesToBeMoat().size(); i++) {
            Tile tile = Moat.getTilesToBeMoat().get(i);
//            TODO: move to (x, y)
            tile.setMoat(true);
        }
//        TODO: refresh graphics
    }

    public void buildPortableShield(int x, int y) {
//        TODO: create a siege tent in (x, y)
//        TODO: move engineer to (x, y)
//        TODO: create the tool
//        TODO: add current engineer to the tool
    }

    public void buildBatteringRam(int x, int y) {
//        TODO: create a siege tent in (x, y)
//        TODO: move engineer to (x, y)
//        TODO: create the tool
//        TODO: add current engineer to the tool
    }

    public void buildSiegeTower(int x, int y) {
//        TODO: create a siege tent in (x, y)
//        TODO: move engineer to (x, y)
//        TODO: create the tool
//        TODO: add current engineer to the tool
    }

    public void buildCatapult(int x, int y) {
//        TODO: create a siege tent in (x, y)
//        TODO: move engineer to (x, y)
//        TODO: create the tool
//        TODO: add current engineer to the tool
    }

    public void buildTrebuchet(int x, int y) {
//        TODO: create a siege tent in (x, y)
//        TODO: move engineer to (x, y)
//        TODO: create the tool
//        TODO: add current engineer to the tool
    }

    public void buildFireBallista(int x, int y) {
//        TODO: create a siege tent in (x, y)
//        TODO: move engineer to (x, y)
//        TODO: create the tool
//        TODO: add current engineer to the tool
    }

    public void enterTool(AttackingAndDefendingTool tool) {
//        TODO: move to the position of the tool
        tool.addEngineer(currentEngineer);
    }
}
