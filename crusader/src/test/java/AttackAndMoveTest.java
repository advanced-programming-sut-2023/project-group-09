import controller.GameController;
import controller.MapController;
import controller.human.HumanController;
import enumeration.MilitaryStates;
import model.Government;
import model.game.Game;
import model.game.Map;
import model.game.Tuple;
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

    @Test
    public void attackAirAttackOfUnit(){
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(7, 6, "maceman", government1);
        System.out.println(GameController.showMap(0,0));


        Military military2 = map.getTile(7, 6).getMilitaries().get(0);
        makeSample.selectUnit(8,7,null,game,government1);
        System.out.println(HumanController.move(new Tuple(5,1)));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getMove().moveOneTurn();

        MapController.dropMilitary(6, 5, "archer", government);
        makeSample.selectUnit(7,6,null,game,government);
        System.out.println(GameController.airAttack(2,6));
        Military military3 = map.getTile(6, 5).getMilitaries().get(0);
        military2.setMilitaryState(MilitaryStates.AGGRESSIVE_STANCE.getState());
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0,0));
    }

    @Test
    public void digTunnelTest(){
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(12, 6, "maceman", government);
        System.out.println(GameController.showMap(0,0));


        Military military2 = map.getTile(12, 6).getMilitaries().get(0);
        makeSample.selectUnit(13,7,null,game,government);
        System.out.println(GameController.digMoat(8,1));
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
        System.out.println(GameController.showDetailsOfTile(7,0));
    }
}
