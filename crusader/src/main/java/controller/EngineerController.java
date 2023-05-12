package controller;

import controller.gamestructure.GameTools;
import controller.human.HumanController;
import controller.human.MoveController;
import model.activity.Move;
import model.activity.ToolMove;
import model.building.Building;
import model.game.Tile;
import model.game.Tuple;
import model.human.military.Engineer;
import model.tools.SiegeTent;
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
    }



    public static String buildTool(String type,int x, int y){

        if (GameTools.getTool(type) == null){
            return "tool type is invalid!";
        }
        if (!GameController.getGame().getMap().getTile(x, y).isPassable()){
            return "you can't put a tool here!";
        }
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        if (tile.getTool() != null){
            return "you can't put a tool here!";
        }


        Tool tool = GameTools.getTool(type,currentEngineer.getGovernment(),x,y);
        SiegeTent tent = new SiegeTent();
        assert tool != null;
        tent.setConvertTool(tool);
        tent.addEngineer(currentEngineer);
        tile.setTool(tent);

        return "siegeTent put for this goal successfully!";
    }

}
