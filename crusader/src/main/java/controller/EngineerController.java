package controller;

import controller.gamestructure.GameTools;
import controller.human.MoveController;
import model.activity.Move;
import model.game.Tile;
import model.game.Tuple;
import model.human.military.Engineer;
import model.human.military.Military;
import model.tools.SiegeTent;
import model.tools.Tool;

import java.util.LinkedList;

public class EngineerController {
    public static Engineer currentEngineer;

    public static boolean digMoat(int x, int y, Military military) {
        LinkedList<Tuple> path = MoveController.getPath(new Tuple(military.getY(), military.getX()), new Tuple(y, x), military);
        if (path == null) {
            return false;
        }
        Move move = new Move(military.getX(), military.getY(), new Tuple(y, x), true, military);
        move.setShouldDigMoat(true);
        move.setPath(path);
        military.setMove(move);
        return true;
    }

    public static boolean fillMoat(int x, int y, Military military) {
        LinkedList<Tuple> path = MoveController.getPath(new Tuple(military.getY(), military.getX()), new Tuple(y, x), military);
        if (path == null) {
            return false;
        }
        Move move = new Move(military.getX(), military.getY(), new Tuple(y, x), true, military);
        move.setShouldFillMoat(true);
        move.setPath(path);
        military.setMove(move);
        return true;
    }

    public static String buildTool(String type, int x, int y) {

        if (GameTools.getTool(type) == null) {
            return "tool type is invalid!";
        }
        if (!GameController.getGame().getMap().getTile(x, y).isPassable()) {
            return "you can't put a tool here!";
        }
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        if (tile.getTool() != null) {
            return "you can't put a tool here!";
        }


        Tool tool = GameTools.getTool(type, currentEngineer.getGovernment(), x, y);
        SiegeTent tent = new SiegeTent();
        tent.setGovernment(currentEngineer.getGovernment());
        tent.setX(x);
        tent.setY(y);
        assert tool != null;
        tent.setConvertTool(tool);
        tent.addEngineer(currentEngineer);
        currentEngineer.setInTool(true);
        currentEngineer.setInvisible(true);
        tile.setTool(tent);
        if (tool.getName().equals("trebuchet") || tool.getName().equals("catapult"))
            tool.setStoneNumber(20);

        return "siegeTent put for this goal successfully!";
    }

}
