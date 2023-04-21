package Controller;

import controller.TradeController;
import controller.ViewController;
import enumeration.commands.TradeMenuCommands;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ViewControllerTest {
    @Test
    public void permuteTest(){
        String input = "trade request -p 100 -t apple -m hello -a 10";
        Matcher tradeMatcher = TradeMenuCommands.getMatcher(input, TradeMenuCommands.TRADE);

        System.out.println(tradeMatcher.matches());
        String items = tradeMatcher.group("items");
        ArrayList<String> itemsPattern = new ArrayList<>();
        itemsPattern.add(TradeMenuCommands.TYPE_ITEM.getRegex());
        itemsPattern.add(TradeMenuCommands.AMOUNT_ITEM.getRegex());
        itemsPattern.add(TradeMenuCommands.PRICE_ITEM.getRegex());
        itemsPattern.add(TradeMenuCommands.MESSAGE_ITEM.getRegex());
        boolean result = ViewController.isItemMatch(items,itemsPattern);
        Assert.assertTrue(result);
        int amount = Integer.parseInt(ViewController.resultMatcher.group("amount"));
        int price = Integer.parseInt(ViewController.resultMatcher.group("price"));
        String type = ViewController.resultMatcher.group("type");
        type = ViewController.editItem(type);

        String message = ViewController.resultMatcher.group("message");
        message = ViewController.editItem(message);
        Assert.assertEquals("apple",type);
        Assert.assertEquals("hello",message);
        Assert.assertEquals(10,amount);
        Assert.assertEquals(100,price);
        System.out.println("type: "+type+" amount: "+ amount + " message: " + message);
    }

}
