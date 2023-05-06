import controller.*;
import controller.gamestructure.GameHumans;
import controller.gamestructure.GameMaps;
import controller.human.HumanController;
import enumeration.Pair;
import enumeration.dictionary.Colors;
import model.Government;
import model.User;
import model.game.Game;
import model.game.Map;
import model.human.military.Military;
import org.junit.Test;
import view.UnitMenu;

import java.util.ArrayList;

public class makeSample {
    @Test
    public void makeSampleGame(){
        MainController.loadGame();
        ArrayList<Map> maps = GameMaps.smallMaps;
        Map map = maps.get(0);
        MapController.map = map;
        ArrayList<Pair<Integer, Integer>> castles = map.getDefaultCastles();
        User user = Application.getUserByUsername("Farzam");
        User user1 = Application.getUserByUsername("Amirhossein");
        Government government = new Government(user,castles.get(0).getFirst(),castles.get(0).getSecond(), Colors.RED);
        Government government1 = new Government(user1,castles.get(1).getFirst(),castles.get(1).getSecond(), Colors.BLUE);

        Game game = new Game(map);
        game.addGovernment(government);
        game.addGovernment(government1);

        GameController.setGame(game);

        MapController.dropBuilding(0,0,"lowWall",government1);
        MapController.dropBuilding(1,0,"lowWall",government1);
        MapController.dropBuilding(2,0,"lowWall",government1);
        MapController.dropBuilding(3,0,"lowWall",government1);
        MapController.dropBuilding(3,1,"lowWall",government1);
        MapController.dropBuilding(3,2,"lowWall",government1);
        MapController.dropBuilding(3,3,"lowWall",government1);
        MapController.dropBuilding(2,3,"lowWall",government1);
        MapController.dropBuilding(1,3,"lowWall",government1);
        MapController.dropBuilding(0,3,"lowWall",government1);
        MapController.dropBuilding(0,2,"lowWall",government1);
        MapController.dropBuilding(0,1,"lowWall",government1);


        MapController.dropMilitary(5,1,"ladderman",government);
        selectUnit(6,2,null,game);
        System.out.println(GameController.attackBuilding(4,2));
        Military military = map.getTile(5,1).getMilitaries().get(0);
        military.getMove().moveOneTurn();
        military.getAttack().doAttack();

        MapController.dropMilitary(2,1,"ladderman",government);
        selectUnit(3,2,null,game);
        System.out.println(GameController.attackBuilding(4,3));
        Military military1 = map.getTile(2,1).getMilitaries().get(0);
        military1.getMove().moveOneTurn();
        military1.getAttack().doAttack();

        MapController.dropMilitary(5,1,"spearman",government);
        selectUnit(6,2,null,game);
        System.out.println(GameController.moveUnit(3,2));


        System.out.println(GameController.showMap(0,0));
        System.out.println(GameController.showDetailsOfTile(4,1));
        System.out.println(GameController.showDetailsOfTile(5,1));
    }

    public static String selectUnit(int x, int y, String type,Game game) {
        ArrayList<Military> militaries;
        if (type == null) {
            militaries = MapController.getMilitariesOfGovernment(x - 1, y - 1, game.getCurrentGovernment());
        } else if (GameHumans.getUnit(type) == null) {
            return "invalid type!";
        } else {
            militaries = MapController.getOneTypeOfMilitariesOfGovernment(x - 1, y - 1, type, game.getCurrentGovernment());
        }

        if (militaries.size() == 0) {
            return "There is no troop in this place!";
        }
        HumanController.militaries = militaries;
        UnitMenu.x = x - 1;
        UnitMenu.y = y - 1;
        return "";
    }
}
