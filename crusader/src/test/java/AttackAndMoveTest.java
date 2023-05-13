import controller.GameController;
import controller.MapController;
import controller.ToolsController;
import controller.human.HumanController;
import enumeration.MilitaryStates;
import model.Government;
import model.activity.ToolMove;
import model.game.Game;
import model.game.Map;
import model.game.Tuple;
import model.human.military.Engineer;
import model.human.military.Military;
import model.tools.Tool;
import org.junit.Assert;
import org.junit.Test;
import view.ToolMenu;

import java.util.ArrayList;
import java.util.LinkedList;


public class AttackAndMoveTest {
    @Test
    public void movePatrolOfUnit() {
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        MapController.dropMilitary(7, 7, "maceman", government);
        System.out.println(GameController.showMap(7, 7));
        Military military2 = map.getTile(7, 7).getMilitaries().get(0);
        makeSample.selectUnit(8, 8, null, game, government);
        Assert.assertTrue(HumanController.patrolUnit(7, 7, 8, 1));
        Assert.assertEquals(military2.getMove().getMoveState(), "patrol");
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(7, 7));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(7, 7));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(7, 7));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(7, 7));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
    }

    @Test
    public void attackAirAttackOfUnit() {
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(7, 6, "maceman", government1);
        System.out.println(GameController.showMap(0, 0));


        Military military2 = map.getTile(7, 6).getMilitaries().get(0);
        makeSample.selectUnit(8, 7, null, game, government1);
        System.out.println(HumanController.move(new Tuple(5, 1)));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();

        MapController.dropMilitary(6, 5, "archer", government);
        makeSample.selectUnit(7, 6, null, game, government);
        System.out.println(GameController.airAttack(2, 6));
        Military military3 = map.getTile(6, 5).getMilitaries().get(0);
        military2.setMilitaryState(MilitaryStates.AGGRESSIVE_STANCE.getState());
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getAttack().doAttack();
        military3.getAttack().doAttack();
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
    }

    @Test
    public void digMoatTest() {
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(12, 6, "maceman", government);
        System.out.println(GameController.showMap(0, 0));


        Military military2 = map.getTile(12, 6).getMilitaries().get(0);
        makeSample.selectUnit(13, 7, null, game, government);
        System.out.println(GameController.digMoat(8, 1));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        System.out.println(GameController.showDetailsOfTile(7, 0));
    }

    @Test
    public void digTunnelTest() {
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(12, 6, "tunneler", government);
        System.out.println(GameController.showMap(0, 0));


        Military military2 = map.getTile(12, 6).getMilitaries().get(0);
        makeSample.selectUnit(13, 7, null, game, government);
        System.out.println(GameController.digTunnel(8, 6));


        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military2.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        System.out.println(GameController.showDetailsOfTile(7, 0));
    }

    @Test
    public void makeToolAndMove() {
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(12, 6, "engineer", government);


        Military military2 = map.getTile(12, 6).getMilitaries().get(0);
        ArrayList<Engineer> engineers = new ArrayList<>();
        engineers.add((Engineer) military2);

        makeSample.selectUnit(13, 7, null, game, government);
        System.out.println(GameController.buildEquipment(1, engineers, 13, 7));
        System.out.println(GameController.showMap(0, 0));


        MapController.dropMilitary(12, 5, "engineer", government);
        Military military3 = map.getTile(12, 5).getMilitaries().get(0);
        makeSample.selectUnit(13, 6, null, game, government);
        System.out.println(GameController.useTool(13, 7));
        military3.getMove().moveOneTurn();
        GameController.selectTool(12, 6);
        Tool tool = ToolMenu.tool;
        System.out.println(tool.getName());
        toolMove(7, 0);
        tool.getToolMove().moveOneTurn();
        System.out.println(tool.getToolMove().getMoveState());
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        System.out.println(tool.getX() + " " + tool.getY());
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        System.out.println(tool.getToolMove().getMoveState());
        System.out.println(GameController.showMap(0, 0));

    }

    @Test
    public void patrolTool() {
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(12, 6, "engineer", government);


        Military military2 = map.getTile(12, 6).getMilitaries().get(0);
        ArrayList<Engineer> engineers = new ArrayList<>();
        engineers.add((Engineer) military2);

        makeSample.selectUnit(13, 7, null, game, government);
        System.out.println(GameController.buildEquipment(1, engineers, 13, 7));

        MapController.dropMilitary(12, 5, "engineer", government);
        Military military3 = map.getTile(12, 5).getMilitaries().get(0);
        makeSample.selectUnit(13, 6, null, game, government);
        System.out.println(GameController.useTool(13, 7));
        military3.getMove().moveOneTurn();
        GameController.selectTool(12, 6);
        Tool tool = ToolMenu.tool;
        ToolsController.tool = tool;
        System.out.println(ToolsController.patrolTool(11, 0, 8, 8));
        tool.getToolMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        System.out.println(GameController.showMap(5, 5));
    }

    @Test
    public void attackUnitToToolTest() {
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(12, 6, "engineer", government);


        Military military2 = map.getTile(12, 6).getMilitaries().get(0);
        ArrayList<Engineer> engineers = new ArrayList<>();
        engineers.add((Engineer) military2);

        makeSample.selectUnit(13, 7, null, game, government);
        System.out.println(GameController.buildEquipment(1, engineers, 13, 7));

        MapController.dropMilitary(12, 5, "engineer", government);
        Military military3 = map.getTile(12, 5).getMilitaries().get(0);
        makeSample.selectUnit(13, 6, null, game, government);
        System.out.println(GameController.useTool(13, 7));
        military3.getMove().moveOneTurn();
        GameController.selectTool(12, 6);
        ToolsController.tool = ToolMenu.tool;
        Tool tool = ToolMenu.tool;
        MapController.dropMilitary(12, 0, "maceman", government1);

        Military military4 = map.getTile(12, 0).getMilitaries().get(0);
        military4.setMilitaryState(MilitaryStates.AGGRESSIVE_STANCE.getState());
        System.out.println(GameController.showMap(0, 0));
        military4.getAttack().doAttack();
        military4.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military4.getAttack().doAttack();
        military4.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military4.getAttack().doAttack();
        military4.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military4.getAttack().doAttack();
        military4.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
    }

    @Test
    public void attackToolToPoint() {
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(12, 6, "engineer", government);


        Military military2 = map.getTile(12, 6).getMilitaries().get(0);
        ArrayList<Engineer> engineers = new ArrayList<>();
        engineers.add((Engineer) military2);

        makeSample.selectUnit(13, 7, null, game, government);
        System.out.println(GameController.buildEquipment(1, engineers, 13, 7));

        MapController.dropMilitary(12, 5, "engineer", government);
        Military military3 = map.getTile(12, 5).getMilitaries().get(0);
        makeSample.selectUnit(13, 6, null, game, government);
        System.out.println(GameController.useTool(13, 7));
        military3.getMove().moveOneTurn();
        GameController.selectTool(12, 6);
        ToolsController.tool = ToolMenu.tool;
        Tool tool = ToolMenu.tool;
        tool.setStoneNumber(12);
        System.out.println(ToolMenu.attack(6,6));
        tool.getToolAttack().doAttack();
        tool.getToolAttack().doAttack();

        System.out.println(GameController.showMap(0, 0));
        System.out.println(GameController.showDetailsOfTile(6,6));
    }

    @Test
    public void attackToolToBuilding() {
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(12, 6, "engineer", government);


        Military military2 = map.getTile(12, 6).getMilitaries().get(0);
        ArrayList<Engineer> engineers = new ArrayList<>();
        engineers.add((Engineer) military2);

        makeSample.selectUnit(13, 7, null, game, government);
        System.out.println(GameController.buildEquipment(4, engineers, 13, 7));

        MapController.dropMilitary(12, 5, "engineer", government);
        Military military3 = map.getTile(12, 5).getMilitaries().get(0);
        makeSample.selectUnit(13, 6, null, game, government);
        System.out.println(GameController.useTool(13, 7));
        military3.getMove().moveOneTurn();
        MapController.dropMilitary(12, 5, "engineer", government);

        Military military4 = map.getTile(12, 5).getMilitaries().get(0);
        makeSample.selectUnit(13, 6, null, game, government);
        System.out.println(GameController.useTool(13, 7));
        military4.getMove().moveOneTurn();

        MapController.dropMilitary(12, 5, "engineer", government);
        Military military5 = map.getTile(12, 5).getMilitaries().get(0);
        makeSample.selectUnit(13, 6, null, game, government);
        System.out.println(GameController.useTool(13, 7));
        military5.getMove().moveOneTurn();





        GameController.selectTool(12, 6);
        ToolsController.tool = ToolMenu.tool;
        Tool tool = ToolMenu.tool;
        System.out.println(tool.getEngineers().size());
        System.out.println(ToolMenu.attack(6,0));
        tool.getToolMove().moveOneTurn();
        tool.getToolAttack().doAttack();
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        tool.getToolAttack().doAttack();
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        tool.getToolAttack().doAttack();
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        tool.getToolAttack().doAttack();
        System.out.println(GameController.showMap(0, 0));
        System.out.println(GameController.showDetailsOfTile(6,0));
    }
    @Test
    public void attackToolWithSiegeTower() {
        Game game = makeSample.makeSampleGame();
        Map map = game.getMap();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        MapController.dropBuilding(5, 1, "smallStoneGatehouse", government1);

        MapController.dropMilitary(12, 6, "engineer", government);


        Military military2 = map.getTile(12, 6).getMilitaries().get(0);
        ArrayList<Engineer> engineers = new ArrayList<>();
        engineers.add((Engineer) military2);

        makeSample.selectUnit(13, 7, null, game, government);
        System.out.println(GameController.buildEquipment(3, engineers, 13, 7));

        MapController.dropMilitary(12, 5, "engineer", government);
        Military military3 = map.getTile(12, 5).getMilitaries().get(0);
        makeSample.selectUnit(13, 6, null, game, government);
        System.out.println(GameController.useTool(13, 7));
        military3.getMove().moveOneTurn();
        MapController.dropMilitary(12, 5, "engineer", government);

        Military military4 = map.getTile(12, 5).getMilitaries().get(0);
        makeSample.selectUnit(13, 6, null, game, government);
        System.out.println(GameController.useTool(13, 7));
        military4.getMove().moveOneTurn();

        MapController.dropMilitary(12, 5, "engineer", government);
        Military military5 = map.getTile(12, 5).getMilitaries().get(0);
        makeSample.selectUnit(13, 6, null, game, government);
        System.out.println(GameController.useTool(13, 7));
        military5.getMove().moveOneTurn();





        GameController.selectTool(12, 6);
        ToolsController.tool = ToolMenu.tool;
        Tool tool = ToolMenu.tool;
        System.out.println(tool.getEngineers().size());
        System.out.println(ToolMenu.attack(6,0));
        tool.getToolMove().moveOneTurn();
        tool.getToolAttack().doAttack();
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        tool.getToolAttack().doAttack();
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        tool.getToolAttack().doAttack();
        System.out.println(GameController.showMap(0, 0));
        tool.getToolMove().moveOneTurn();
        tool.getToolAttack().doAttack();
        System.out.println(GameController.showMap(0, 0));
        System.out.println(GameController.showDetailsOfTile(6,0));


        MapController.dropMilitary(12, 0, "maceman", government);

        Military military6 = map.getTile(12, 0).getMilitaries().get(0);
        makeSample.selectUnit(13, 1, null, game, government);
        System.out.println(GameController.moveUnit(3,2));
        military6.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military6.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military6.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));
        military6.getMove().moveOneTurn();
        System.out.println(GameController.showMap(0, 0));

    }


    public void toolMove(int x, int y) {
        Tool tool = ToolMenu.tool;
        LinkedList<Tuple> path = ToolsController.getPathTools(new Tuple(tool.getY(), tool.getX()), new Tuple(y, x));
        if (path == null) {
            System.out.println("there is no available way to move tool");
        }
        ToolMove move = new ToolMove(tool.getX(), tool.getY(), new Tuple(y, x), true, tool);
        move.setPath(path);
        tool.setToolMove(move);
    }


}
