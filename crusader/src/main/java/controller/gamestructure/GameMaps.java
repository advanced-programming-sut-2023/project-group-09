package controller.gamestructure;

import controller.GameController;
import controller.MapController;
import enumeration.Textures;
import enumeration.commands.MapCommands;
import enumeration.dictionary.RockDirections;
import model.game.Game;
import model.game.Map;

import java.util.HashMap;

public class GameMaps {
    HashMap<String , Map> maps = new HashMap<>();
    public static void createMap1() {
        Map map1 = new Map(400 , 400);
        for (int i = 15; i <= 60; i++) {
            for (int j = 15; j <= 40; j++) {
                map1.getTile(i , j).setTexture(Textures.IRON_TEXTURE);
            }
        }
        for (int i = 5; i <= 45; i++) {
            for (int j = 40; j <= 40 + i; j++) {
                map1.getTile(i , j).setTexture(Textures.ROCK_TEXTURE);
            }
        }
        for (int i = 60; i <= 100; i++) {
            for (int j = 0; j <= 60; j++) {
                map1.getTile(i , j).setTexture(Textures.MARSH);
            }
        }
        for (int i = 70; i <= 100 ;i++) {
            map1.getTile(i , i).setRockDirection(RockDirections.getRandomDirection());
        }
        for (int i = 100; i >= 50; i--) {
            map1.getTile(i , 200-i).setRockDirection(RockDirections.getRandomDirection());
        }
        for (int i = 105; i <= 110; i++) {
            for (int j = 0; j <= 50; j++) {
                map1.getTile(i, j).setTexture(Textures.RIVER);
            }
        }
        for (int i = 105; i <= 155; i++) {
            map1.getTile(i , 50 + (i - 105)).setTexture(Textures.RIVER);
        }

        GameController.setGame(new Game(map1));

        System.out.println(GameController.showMap(50, 50));
    }
}
