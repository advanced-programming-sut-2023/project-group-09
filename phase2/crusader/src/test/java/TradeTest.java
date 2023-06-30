import controllers.*;
import controllers.gamestructure.GameMaps;
import enumeration.Pair;
import enumeration.dictionary.Colors;
import model.Government;
import model.Trade;
import model.User;
import model.game.Game;
import model.game.Map;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

public class TradeTest {
    public static Game makeSampleGame() {
        MainController.loadGame();
        ArrayList<Map> maps = GameMaps.smallMaps;
        Map map = maps.get(0);
        MapController.map = map;
        ArrayList<Pair<Integer, Integer>> castles = map.getDefaultCastles();
        User user = Application.getUserByUsername("Farzam");
        User user1 = Application.getUserByUsername("Amirhossein");
        User user2 = Application.getUserByUsername("Moeein");
        Government government = new Government(user, castles.get(0).getFirst(), castles.get(0).getSecond(), Colors.RED);
        Government government1 = new Government(user1, castles.get(1).getFirst(), castles.get(1).getSecond(), Colors.BLUE);
        Government government2 = new Government(user2, castles.get(2).getFirst(), castles.get(2).getSecond(), Colors.ORANGE);
        Game game = new Game(map);
        game.addGovernment(government);
        game.addGovernment(government1);
        game.addGovernment(government2);
        GameController.setGame(game);
        government.addAmountToProperties("wood", "resource", 100);
        government.addAmountToProperties("stone", "resource", 50);
        government.addAmountToProperties("bread", "food", 60);
        government.setGold(4000);
        MapController.dropBuilding(0, 0, "granary", government);
        government1.addAmountToProperties("wood", "resource", 100);
        government1.addAmountToProperties("stone", "resource", 50);
        government1.addAmountToProperties("bread", "food", 60);
        MapController.dropBuilding(0, 0, "granary", government1);

        government2.addAmountToProperties("wood", "resource", 100);
        government2.addAmountToProperties("stone", "resource", 50);
        government2.addAmountToProperties("bread", "food", 60);

        government1.setGold(4000);
        return game;

    }

    @Test
    public void tradeGoodsTest() {
        Game game = makeSampleGame();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        Government government2 = game.getGovernments().get(2);
        TradeController.setTargetGovernment(government1);
        //================================= trade goods
        String output = TradeController.tradeGoods(null, 1, 500, "thanks");
        String output1 = TradeController.tradeGoods("hello", -1, 500, "thanks");
        String output2 = TradeController.tradeGoods("hello", 1, -1, "thanks");
        String output3 = TradeController.tradeGoods("hello", 1, 500, null);
        String output4 = TradeController.tradeGoods("hello", 1, 500, "thanks");
        String output5 = TradeController.tradeGoods("meat", 0, 500, "thanks");
        String output6 = TradeController.tradeGoods("meat", 1, 5000, "thanks");
        Assert.assertEquals(output, "resource type field is required!");
        Assert.assertEquals(output1, "resource amount field is required!");
        Assert.assertEquals(output2, "price field is required!");
        Assert.assertEquals(output3, "message field is required!");
        Assert.assertEquals(output4, "resource type is invalid!");
        Assert.assertEquals(output5, "amount value can not be 0!");
        Assert.assertEquals(output6, "your gold isn't enough!");
        String output7 = TradeController.tradeGoods("meat", 1, 50, "thanks");
        Assert.assertEquals("request sent successfully!", output7);
        Assert.assertEquals(TradeController.allTrades.size(), 1);
        Assert.assertEquals(government.getSentTrades().size(), 1);
        Assert.assertEquals(government1.getReceivedTrades().size(), 1);
        TradeController.tradeGoods("bread", 5, 50, "thanks");
        System.out.println(TradeController.showTradeList());
        System.out.println(TradeController.showTradeHistory());
        Set<String> IDs = TradeController.allTrades.keySet();
        String id = null;
        String id1 = null;
        int count = 1;
        for (String ID : IDs) {
            if (count == 1) {
                id = ID;
            }
            if (count == 2) {
                id1 = ID;
            }
            count++;
        }
        Trade trade = TradeController.allTrades.get(id);

        game.setCurrentGovernment(government1);
        //================================= accept trade===============================
        System.out.println(TradeController.showTradeList());
        output = TradeController.acceptTrade(null, "ok");
        output1 = TradeController.acceptTrade(id, null);
        output2 = TradeController.acceptTrade("ssdfs", "ok");
        government.setGold(0);
        output5 = TradeController.acceptTrade(id1, "ok");
        government.setGold(4000);
        trade.setIsAccepted(true);
        output3 = TradeController.acceptTrade(id, "ok");
        trade.setIsAccepted(false);
        output4 = TradeController.acceptTrade(id, "ok");
        Assert.assertEquals(output, "id field is required!");
        Assert.assertEquals(output1, "message field is required!");
        Assert.assertEquals(output2, "no trade with this id exist!");
        Assert.assertEquals(output3, "this trade was accepted before!");
        Assert.assertEquals(output4, "your resource is not enough!");
        Assert.assertEquals(output5, "sender doesn't have enough money!");
        output5 = TradeController.acceptTrade(id1, "ok");
        Assert.assertEquals(output5, "request accepted successfully!");
        System.out.println(TradeController.showTradeHistory());
        Assert.assertEquals(government.getPropertyAmount("bread"),65);
        //===============================
        Assert.assertEquals(TradeController.getTargetGovernment(),government1);


        TradeController.setTargetGovernment(government2);
        output7 = TradeController.tradeGoods("bread", 10, 0, "thanks");
        Assert.assertEquals(output7,"request sent successfully!");
        TradeController.setTargetGovernment(government);
        output7 = TradeController.tradeGoods("bread", 10, 0, "thanks");
        Assert.assertEquals(output7,"request sent successfully!");


        Set<String> keys = government1.getSentTrades().keySet();
        Assert.assertEquals(government1.getSentTrades().size(),2);
        id = null;
        id1 = null;
        count = 1;
        for (String key : keys){
            if (count == 1) {
                id = key;
            }
            if (count == 2) {
                id1 = key;
            }
            count++;
        }

        game.setCurrentGovernment(government2);
        output1 = TradeController.acceptTrade(id,"tnx");
        Assert.assertEquals("you don't have capacity to store!",output1);
        game.setCurrentGovernment(government);
        output1 = TradeController.acceptTrade(id1,"tnx");
        Assert.assertEquals("request accepted successfully!",output1);
        Assert.assertEquals(government.getPropertyAmount("bread"),75);

    }
}
