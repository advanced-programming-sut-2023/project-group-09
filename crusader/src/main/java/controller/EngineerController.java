package controller;

import model.game.Tile;
import model.human.military.Engineer;
import model.tools.Tool;
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
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
//        TODO: move engineer to (x, y)
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "portableShield", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "portableShield", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void buildBatteringRam(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
//        TODO: move engineer to (x, y)
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "batteringRam", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "batteringRam", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void buildSiegeTower(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
//        TODO: move engineer to (x, y)
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "siegeTower", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTower", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void buildCatapult(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
//        TODO: move engineer to (x, y)
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "catapult", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "catapult", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void buildTrebuchet(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
//        TODO: move engineer to (x, y)
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "trebuchet", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "trebuchet", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void buildFireBallista(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
//        TODO: move engineer to (x, y)
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "fireBallista", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "fireBallista", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void enterTool(Tool tool) {
//        TODO: move to the position of the tool
        tool.addEngineer(currentEngineer);
    }
}
