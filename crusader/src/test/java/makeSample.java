import controller.Application;
import controller.GameController;
import controller.MainController;
import controller.MapController;
import controller.gamestructure.GameHumans;
import controller.gamestructure.GameMaps;
import controller.human.HumanController;
import enumeration.Pair;
import enumeration.dictionary.Colors;
import model.Government;
import model.User;
import model.game.Game;
import model.game.Map;
import model.game.Tuple;
import model.human.military.Military;
import org.junit.Test;
import view.UnitMenu;

import java.util.ArrayList;

public class makeSample {
    @Test
    public void makeSampleGame() {
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
        MapController.dropBuilding(3, 1, "lowWall", government1);
        MapController.dropBuilding(3, 2, "lowWall", government1);
        MapController.dropBuilding(3, 3, "lowWall", government1);
        MapController.dropBuilding(2, 3, "lowWall", government1);
        MapController.dropBuilding(1, 3, "lowWall", government1);
        MapController.dropBuilding(0, 3, "lowWall", government1);
        MapController.dropBuilding(0, 2, "lowWall", government1);
        MapController.dropBuilding(0, 1, "lowWall", government1);


        MapController.dropBuilding(4, 1, "stairs", government1);
        MapController.dropBuilding(5, 1, "stairs", government1);
        MapController.dropBuilding(6, 1, "stairs", government1);
        System.out.println(GameController.showMap(0,0));



        MapController.dropMilitary(0, 1, "maceman", government);
        selectUnit(1, 2, null, game, government);
        Military military2 = map.getTile(0, 1).getMilitaries().get(0);
        System.out.println(HumanController.move(new Tuple(5,5)));


        System.out.println(military2.getMove().getMoveState());
        MapController.dropMilitary(3, 0, "archer", government1);
        Military military3 = map.getTile(3, 0).getMilitaries().get(0);

        military2.getMove().moveOneTurn();
        military3.getAttack().doAttack();
        military2.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        military3.getAttack().doAttack();
        military2.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        military3.getAttack().doAttack();
        military2.getAttack().doAttack();

        military2.getMove().moveOneTurn();
        military3.getAttack().doAttack();
        military2.getAttack().doAttack();

        military2.setMilitaryState("offensive");

        System.out.println(military2.getMove().getMoveState());
        MapController.dropMilitary(3, 3, "spearman", government1);
        military3 = map.getTile(3, 3).getMilitaries().get(0);
        military3.setMilitaryState("defensive");


        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        //military3.getMove().moveOneTurn();
        military3.getAttack().doAttack();
        military2.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        //military3.getMove().moveOneTurn();
        military3.getAttack().doAttack();
        military2.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        //military3.getMove().moveOneTurn();
        military3.getAttack().doAttack();
        military2.getAttack().doAttack();
        System.out.println(GameController.showMap(0,0));

//        military2.getMove().moveOneTurn();
//        System.out.println(GameController.showMap(0,0));
//        //military3.getMove().moveOneTurn();
//        //military3.getMove().moveOneTurn();
//        System.out.println(GameController.showMap(0,0));
//        military3.getAttack().doAttack();
//        military2.getAttack().doAttack();



        System.out.println(GameController.showMap(0,0));
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
