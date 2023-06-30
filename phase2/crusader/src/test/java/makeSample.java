import controllers.Application;
import controllers.GameController;
import controllers.MainController;
import controllers.MapController;
import controllers.gamestructure.GameHumans;
import controllers.gamestructure.GameMaps;
import controllers.human.HumanController;
import enumeration.Pair;
import enumeration.dictionary.Colors;
import model.Government;
import model.User;
import model.game.Game;
import model.game.Map;
import model.human.military.Military;
import viewphase1.UnitMenu;

import java.util.ArrayList;

public class makeSample {
    public static Game makeSampleGame() {
        MainController.loadGame();
        ArrayList<Map> maps = GameMaps.smallMaps;
        Map map = maps.get(0);
        MapController.map = map;
        ArrayList<Pair<Integer, Integer>> castles = map.getDefaultCastles();
        User user = Application.getUserByUsername("Farzammm");
        User user1 = Application.getUserByUsername("Amirhossein");
        Government government = new Government(user, castles.get(0).getFirst(), castles.get(0).getSecond(), Colors.RED);
        Government government1 = new Government(user1, castles.get(1).getFirst(), castles.get(1).getSecond(), Colors.BLUE);
        Game game = new Game(map);
        game.addGovernment(government);
        game.addGovernment(government1);
        GameController.setGame(game);
        MapController.dropBuilding(0, 0, "lowWall", government1);
        MapController.dropBuilding(1, 0, "lowWall", government1);
        MapController.dropBuilding(2, 0, "lowWall", government1);
        MapController.dropBuilding(3, 0, "lowWall", government1);
        MapController.dropBuilding(4, 0, "lowWall", government1);
        MapController.dropBuilding(5, 0, "lowWall", government1);
        MapController.dropBuilding(6, 0, "lowWall", government1);
        MapController.dropBuilding(6, 1, "lowWall", government1);
        MapController.dropBuilding(6, 2, "lowWall", government1);
        MapController.dropBuilding(6, 3, "lowWall", government1);
        MapController.dropBuilding(6, 4, "lowWall", government1);
        MapController.dropBuilding(6, 5, "lowWall", government1);
        MapController.dropBuilding(6, 6, "lowWall", government1);
        MapController.dropBuilding(5, 6, "lowWall", government1);
        MapController.dropBuilding(4, 6, "lowWall", government1);
        MapController.dropBuilding(3, 6, "lowWall", government1);
        MapController.dropBuilding(2, 6, "lowWall", government1);
        MapController.dropBuilding(1, 6, "lowWall", government1);
        MapController.dropBuilding(0, 6, "lowWall", government1);
        MapController.dropBuilding(0, 5, "lowWall", government1);
        MapController.dropBuilding(0, 4, "lowWall", government1);
        MapController.dropBuilding(0, 3, "lowWall", government1);
        MapController.dropBuilding(0, 2, "lowWall", government1);
        MapController.dropBuilding(0, 1, "lowWall", government1);
        return game;

    }

    public static String selectUnit(int x, int y, String type, Game game,Government government) {
        ArrayList<Military> militaries;
        if (type == null) {
            militaries = MapController.getMilitariesOfGovernment(x - 1, y - 1, government);
        } else if (GameHumans.getUnit(type) == null) {
            return "invalid type!";
        } else {
            militaries = MapController.getOneTypeOfMilitariesOfGovernment(x - 1, y - 1, type, government);
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
