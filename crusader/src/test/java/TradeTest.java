import controller.*;
import controller.gamestructure.GameMaps;
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

        government1.addAmountToProperties("wood", "resource", 100);
        government1.addAmountToProperties("stone", "resource", 50);
        government1.addAmountToProperties("bread", "food", 60);

        government2.addAmountToProperties("wood", "resource", 100);
        government2.addAmountToProperties("stone", "resource", 50);
        government2.addAmountToProperties("bread", "food", 60);

        government1.setGold(4000);
        return game;

    }

    @Test
    public void tradeGoodsTest(){
        Game game = makeSampleGame();
        Government government = game.getGovernments().get(0);
        Government government1 = game.getGovernments().get(1);
        Government government2 = game.getGovernments().get(2);
        TradeController.setTargetGovernment(government1);
        //================================= trade goods
        String output = TradeController.tradeGoods(null,1,500,"thanks");
        String output1 = TradeController.tradeGoods("hello",-1,500,"thanks");
        String output2 = TradeController.tradeGoods("hello",1,-1,"thanks");
        String output3 = TradeController.tradeGoods("hello",1,500,null);
        String output4 = TradeController.tradeGoods("hello",1,500,"thanks");
        String output5 = TradeController.tradeGoods("meat",0,500,"thanks");
        String output6 = TradeController.tradeGoods("meat",1,5000,"thanks");
        Assert.assertEquals(output,"resource type field is required!");
        Assert.assertEquals(output1,"resource amount field is required!");
        Assert.assertEquals(output2,"price field is required!");
        Assert.assertEquals(output3,"message field is required!");
        Assert.assertEquals(output4,"resource type is invalid!");
        Assert.assertEquals(output5,"amount value can not be 0!");
        Assert.assertEquals(output6,"your gold isn't enough!");
        String output7 = TradeController.tradeGoods("meat",1,50,"thanks");
        Assert.assertEquals("request sent successfully!",output7);
        Assert.assertEquals(TradeController.allTrades.size() , 1);
        Assert.assertEquals(government.getSentTrades().size(),1);
        Assert.assertEquals(government1.getReceivedTrades().size(),1);
        Set<String> IDs = TradeController.allTrades.keySet();
        String id = null;
        String id1 = null;
        for (String ID:IDs){
            id = ID;
            break;
        }
        Trade trade = TradeController.allTrades.get(id);
        TradeController.tradeGoods("bread",1,50,"thanks");
        game.setCurrentGovernment(government1);
        //================================= accept trade
        output = TradeController.acceptTrade(null,"ok");
        output1 = TradeController.acceptTrade(id,null);
        output2 = TradeController.acceptTrade("ssdfs","ok");
        trade.setIsAccepted(true);
        output3 = TradeController.acceptTrade(id,"ok");
        trade.setIsAccepted(false);
        output4 = TradeController.acceptTrade(id,"ok");
        Assert.assertEquals(output,"id field is required!");
        Assert.assertEquals(output1,"message field is required!");
        Assert.assertEquals(output2,"no trade with this id exist!");
        Assert.assertEquals(output3,"this trade was accepted before!");
        Assert.assertEquals(output4,"your resource is not enough!");
    }
}
