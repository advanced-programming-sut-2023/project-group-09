package controller.gamestructure;

import controller.GameController;
import controller.MapController;
import enumeration.Textures;
import enumeration.commands.MapCommands;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
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
        for (int i = 10; i <= 40; i++) {
            for (int j = 138; j <= 142; j++) {
                map1.getTile(i , j).setTexture(Textures.GRASS);
            }
        }
        map1.getTile(95 , 80).setTree(Trees.OLIVE_TREE);
        GameController.setGame(new Game(map1));
        for (int i = 120; i <= 200; i++) {
            for (int j = 105; j <= 110; j++) {
                map1.getTile(i , j).setTexture(Textures.THICK_GRASS);
            }
        }
        for (int i = 150; i <= 220; i++) {
            for (int j = 150; j <= 170; j++) {
                map1.getTile(i , j).setTexture(Textures.MARSH);
            }
        }
        for (int i = 155; i <= 185; i++) {
            map1.getTile(i , 20+i-155).setRockDirection(RockDirections.getRandomDirection());
        }
        map1.getTile(220 , 25).setTree(Trees.DATE_PALM);
        for (int i = 350; i <= 390; i++) {
            for (int j = 40; j <= 70; j++) {
                map1.getTile(i , j).setTexture(Textures.BOULDER);
            }
        }
        for (int i = 275; i <= 305; i++) {
            for (int j = 65; j <= 95; j++) {
                map1.getTile(i , j).setTexture(Textures.IRON_TEXTURE);
            }
        }
        for (int i = 330; i <= 400; i++) {
            for (int j = 80; j <= 160; j++) {
                map1.getTile(i , j).setTexture(Textures.GRASS);
            }
        }
        for (int i = 330; i <= 360; i++) {
            for (int j = 160; j <= 220; j++) {
                map1.getTile(i , j).setTexture(Textures.GRASS);
            }
        }
        for (int i = 270; i <= 310; i++) {
            for (int j = 140; j <= 180; j++) {
                map1.getTile(i , j).setTexture(Textures.BOULDER);
            }
        }
        for (int i = 0; i <= 50; i++) {
            for (int j = 50; j <= 400; j++) {
                map1.getTile(i , j).setTexture(Textures.THICK_GRASS);
            }
        }
        map1.getTile(90 , 260).setTree(Trees.CHERRY_PALM);
        map1.getTile(90 , 261).setTree(Trees.DATE_PALM);
        map1.getTile(90 , 262).setTree(Trees.COCONUT_PALM);
        for (int i = 110; i <= 150; i++) {
            for (int j = 220; j <= 250; j++) {
                map1.getTile(i ,j).setTexture(Textures.IRON_TEXTURE);
            }
        }
        for (int i = 140; i <= 170; i++) {
            for (int j = 300; j <= 330; j++) {
                map1.getTile(i , j).setTexture(Textures.BOULDER);
            }
        }
        for (int i = 205; i <= 250; i++) {
            for (int j = 285; j <= 350; j++) {
                map1.getTile(i ,j).setTexture(Textures.OASIS_GRASS);
            }
        }
        for (int i = 205; i <= 220; i++) {
            for (int j = 350; j <= 370; j++) {
                map1.getTile(i , j).setTexture(Textures.OIL);
            }
        }
        for (int i = 330; i <= 400; i++) {
            for (int j = 330; j <= 400; j++) {
                map1.getTile(i , j).setTexture(Textures.SEA);
            }
        }
        for (int i = 360; i <= 400; i++) {
            for (int j = 280; j <= 310; j++) {
                map1.getTile(i , j).setTexture(Textures.IRON_TEXTURE);
            }
        }
        map1.addDefaultCastle(85 , 60);
        map1.addDefaultCastle(180 , 30);
        map1.addDefaultCastle(330 , 25);
        map1.addDefaultCastle(365 , 200);
        map1.addDefaultCastle(290 , 340);
        map1.addDefaultCastle(80 , 380);
        map1.addDefaultCastle(200 , 250);
        map1.addDefaultCastle(30 , 280);
        System.out.println(GameController.showMap(50, 50));
    }
}
