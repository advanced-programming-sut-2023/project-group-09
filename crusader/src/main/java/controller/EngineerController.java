package controller;

import controller.human.HumanController;
import model.game.Tile;
import model.game.Tuple;
import model.human.military.Engineer;
import model.tools.Tool;
import model.tools.Moat;

public class EngineerController {
    public static Engineer currentEngineer;

    public static void digMoat() {
        for (int i = 0; i < Moat.getTilesToBeMoat().size(); i++) {
            int x = Moat.getTilesToBeMoat().get(i).getX();
            int y = Moat.getTilesToBeMoat().get(i).getY();
            if (!HumanController.move(new Tuple(y, x))) continue;
            GameController.getGame().getMap().getTile(x, y).setMoat(true);
        }
//        TODO: refresh graphics
    }

    public void buildPortableShield(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        if (!HumanController.move(new Tuple(y, x))) {
            System.out.println("engineer can't go there");
            return;
        }
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "portableShield", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "portableShield", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void buildBatteringRam(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        if (!HumanController.move(new Tuple(y, x))) {
            System.out.println("engineer can't go there");
            return;
        }
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "batteringRam", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "batteringRam", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void buildSiegeTower(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        if (!HumanController.move(new Tuple(y, x))) {
            System.out.println("engineer can't go there");
            return;
        }
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "siegeTower", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTower", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void buildCatapult(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            if (!HumanController.move(new Tuple(y, x))) {
                System.out.println("engineer can't go there");
                return;
            }
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "catapult", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "catapult", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void buildTrebuchet(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        if (!HumanController.move(new Tuple(y, x))) {
            System.out.println("engineer can't go there");
            return;
        }
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "trebuchet", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "trebuchet", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public void buildFireBallista(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        if (!HumanController.move(new Tuple(y, x))) {
            System.out.println("engineer can't go there");
            return;
        }
        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "fireBallista", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "fireBallista", currentEngineer.getGovernment());
//        TODO: add current engineer to the tool
    }

    public static String enterTool(Tool tool) {
        if (!HumanController.move(new Tuple(tool.getY(), tool.getX())))
            return "engineer can't go there";
        tool.addEngineer(currentEngineer);
        currentEngineer.setInTool(true);
        return "engineer entered the tool successfully";
    }
}
