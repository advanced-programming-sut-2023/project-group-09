import controller.GameController;
import controller.MapController;
import controller.human.HumanController;
import model.Government;
import model.game.Game;
import model.game.Map;
import model.human.military.Military;
import org.junit.Assert;
import org.junit.Test;


public class AttackAndMoveTest {
    @Test
    public void movePatrolOfUnit(){
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        MapController.dropMilitary(4, 3, "maceman", government);
        System.out.println(GameController.showMap(0,0));
        Military military2 = map.getTile(4, 3).getMilitaries().get(0);
        makeSample.selectUnit(5,4,null,game,government);
        Assert.assertTrue(HumanController.patrolUnit(4,3,8,1));
        Assert.assertEquals(military2.getMove().getMoveState(),"patrol");
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
    }

    public void attackAirAttackOfUnit(){
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        MapController.dropMilitary(4, 3, "archer", government);
        System.out.println(GameController.showMap(0,0));
        Military military2 = map.getTile(4, 3).getMilitaries().get(0);
        makeSample.selectUnit(5,4,null,game,government);
        Assert.assertTrue(HumanController.patrolUnit(4,3,8,1));
        Assert.assertEquals(military2.getMove().getMoveState(),"patrol");
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
    }
}
