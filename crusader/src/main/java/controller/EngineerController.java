package controller;

import controller.human.HumanController;
import controller.human.MoveController;
import model.activity.Move;
import model.activity.ToolMove;
import model.building.Building;
import model.game.Tile;
import model.game.Tuple;
import model.human.military.Engineer;
import model.tools.Tool;
import model.tools.Moat;

import java.util.LinkedList;

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

    public static String buildPortableShield(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        else return "you can't put a tool here";

        Building tent = GameController.getGame().getMap().getTile(x, y).getBuilding();
        Move move = new Move(currentEngineer.getX(), currentEngineer.getY(), tent, true, currentEngineer);
        LinkedList<Tuple> path = MoveController.getPathForBuilding(new Tuple(currentEngineer.getY(), currentEngineer.getX()),
                tent, currentEngineer);
        if (path == null) return "engineer can't go there";
        move.setPath(path);
        currentEngineer.setMove(move);

        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "portableShield", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "portableShield", currentEngineer.getGovernment());
        GameController.getGame().getMap().getTile(x, y).getTool().addEngineer(currentEngineer);
        currentEngineer.setInTool(true);
        return "portable shield added successfully";
    }

    public static String buildBatteringRam(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        else return "you can't put a tool here";

        Building tent = GameController.getGame().getMap().getTile(x, y).getBuilding();
        Move move = new Move(currentEngineer.getX(), currentEngineer.getY(), tent, true, currentEngineer);
        LinkedList<Tuple> path = MoveController.getPathForBuilding(new Tuple(currentEngineer.getY(), currentEngineer.getX()),
                tent, currentEngineer);
        if (path == null) return "engineer can't go there";
        move.setPath(path);
        currentEngineer.setMove(move);

        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "batteringRam", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "batteringRam", currentEngineer.getGovernment());
        GameController.getGame().getMap().getTile(x, y).getTool().addEngineer(currentEngineer);
        currentEngineer.setInTool(true);
        return "battering ram added successfully";
    }

    public static String buildSiegeTower(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        else return "you can't put a tool here";

        Building tent = GameController.getGame().getMap().getTile(x, y).getBuilding();
        Move move = new Move(currentEngineer.getX(), currentEngineer.getY(), tent, true, currentEngineer);
        LinkedList<Tuple> path = MoveController.getPathForBuilding(new Tuple(currentEngineer.getY(), currentEngineer.getX()),
                tent, currentEngineer);
        if (path == null) return "engineer can't go there";
        move.setPath(path);
        currentEngineer.setMove(move);

        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "siegeTower", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTower", currentEngineer.getGovernment());
        GameController.getGame().getMap().getTile(x, y).getTool().addEngineer(currentEngineer);
        currentEngineer.setInTool(true);
        return "siege tower added successfully";
    }

    public static String buildCatapult(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        else return "you can't put a tool here";

        Building tent = GameController.getGame().getMap().getTile(x, y).getBuilding();
        Move move = new Move(currentEngineer.getX(), currentEngineer.getY(), tent, true, currentEngineer);
        LinkedList<Tuple> path = MoveController.getPathForBuilding(new Tuple(currentEngineer.getY(), currentEngineer.getX()),
                tent, currentEngineer);
        if (path == null) return "engineer can't go there";
        move.setPath(path);
        currentEngineer.setMove(move);

        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "catapult", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "catapult", currentEngineer.getGovernment());
        GameController.getGame().getMap().getTile(x, y).getTool().addEngineer(currentEngineer);
        currentEngineer.setInTool(true);
        return "catapult added successfully";
    }

    public static String buildTrebuchet(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        else return "you can't put a tool here";

        Building tent = GameController.getGame().getMap().getTile(x, y).getBuilding();
        Move move = new Move(currentEngineer.getX(), currentEngineer.getY(), tent, true, currentEngineer);
        LinkedList<Tuple> path = MoveController.getPathForBuilding(new Tuple(currentEngineer.getY(), currentEngineer.getX()),
                tent, currentEngineer);
        if (path == null) return "engineer can't go there";
        move.setPath(path);
        currentEngineer.setMove(move);

        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "trebuchet", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "trebuchet", currentEngineer.getGovernment());
        GameController.getGame().getMap().getTile(x, y).getTool().addEngineer(currentEngineer);
        currentEngineer.setInTool(true);
        return "trebuchet added successfully";
    }

    public static String buildFireBallista(int x, int y) {
        if (MapController.checkCanPutBuilding(x, y, "siegeTent", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "siegeTent", currentEngineer.getGovernment());
        else return "you can't put a tool here!";

        Building tent = GameController.getGame().getMap().getTile(x, y).getBuilding();
        Move move = new Move(currentEngineer.getX(), currentEngineer.getY(), tent, true, currentEngineer);
        LinkedList<Tuple> path = MoveController.getPathForBuilding(new Tuple(currentEngineer.getY(), currentEngineer.getX()),
                tent, currentEngineer);
        if (path == null) return "engineer can't go there!";
        move.setPath(path);
        currentEngineer.setMove(move);

        GameController.getGame().getMap().getTile(x, y).setBuilding(null);
        if (MapController.checkCanPutBuilding(x, y, "fireBallista", currentEngineer.getGovernment()))
            MapController.dropBuilding(x, y, "fireBallista", currentEngineer.getGovernment());
        GameController.getGame().getMap().getTile(x, y).getTool().addEngineer(currentEngineer);
        currentEngineer.setInTool(true);
        return "fire ballista added successfully!";
    }
}
