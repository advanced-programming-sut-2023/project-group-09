package controller.gamestructure;

import enumeration.Textures;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import model.game.Map;

import java.util.ArrayList;

public class GameMaps {
    public static ArrayList<Map> smallMaps = new ArrayList<>();
    public static ArrayList<Map> largeMaps = new ArrayList<>();

    private static void changeTextureOfSomeTiles(int x1, int x2, int y1, int y2, Map map, Textures texture) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                map.getTile(i, j).setTexture(texture);
            }
        }
    }

    public static void createMap1() {
        Map map1 = new Map(400, 400);
        changeTextureOfSomeTiles(15, 60, 15, 40, map1, Textures.IRON_TEXTURE);
        for (int i = 5; i <= 45; i++) {
            for (int j = 40; j <= 40 + i; j++) {
                map1.getTile(i, j).setTexture(Textures.ROCK_TEXTURE);
            }
        }
        changeTextureOfSomeTiles(60, 100, 0, 60, map1, Textures.MARSH);
        for (int i = 70; i <= 100; i++) {
            map1.getTile(i, i).setRockDirection(RockDirections.getRandomDirection());
        }
        for (int i = 100; i >= 50; i--) {
            map1.getTile(i, 200 - i).setRockDirection(RockDirections.getRandomDirection());
        }
        changeTextureOfSomeTiles(105, 110, 0, 50, map1, Textures.RIVER);
        for (int i = 105; i <= 155; i++) {
            map1.getTile(i, 50 + (i - 105)).setTexture(Textures.RIVER);
        }
        for (int i = 105; i <= 155; i++) {
            map1.getTile(i+1, 50 + (i - 105)).setTexture(Textures.RIVER);
        }
        for (int i = 105; i <= 155; i++) {
            map1.getTile(i+2, 50 + (i - 105)).setTexture(Textures.RIVER);
        }
        changeTextureOfSomeTiles(10, 40, 138, 142, map1, Textures.GRASS);
        map1.getTile(95, 80).setTree(Trees.OLIVE_TREE);
        changeTextureOfSomeTiles(120, 200, 105, 110, map1, Textures.THICK_GRASS);
        changeTextureOfSomeTiles(150, 220, 150, 170, map1, Textures.MARSH);
        for (int i = 155; i <= 185; i++) {
            map1.getTile(i, 20 + i - 155).setRockDirection(RockDirections.getRandomDirection());
        }
        map1.getTile(220, 25).setTree(Trees.DATE_PALM);
        changeTextureOfSomeTiles(350, 390, 40, 70, map1, Textures.BOULDER);
        changeTextureOfSomeTiles(270, 305, 65, 95, map1, Textures.IRON_TEXTURE);
        changeTextureOfSomeTiles(330, 399, 80, 160, map1, Textures.GRASS);
        changeTextureOfSomeTiles(330, 360, 160, 220, map1, Textures.GRASS);
        changeTextureOfSomeTiles(270, 310, 140, 180, map1, Textures.BOULDER);
        changeTextureOfSomeTiles(0, 50, 50, 399, map1, Textures.THICK_GRASS);
        map1.getTile(90, 260).setTree(Trees.CHERRY_PALM);
        map1.getTile(90, 261).setTree(Trees.DATE_PALM);
        map1.getTile(90, 262).setTree(Trees.COCONUT_PALM);
        changeTextureOfSomeTiles(100, 150, 220, 250, map1, Textures.IRON_TEXTURE);
        changeTextureOfSomeTiles(140, 170, 300, 330, map1, Textures.BOULDER);
        changeTextureOfSomeTiles(205, 250, 285, 350, map1, Textures.OASIS_GRASS);
        changeTextureOfSomeTiles(205, 220, 350, 370, map1, Textures.OIL);
        changeTextureOfSomeTiles(330, 399, 330, 399, map1, Textures.SEA);
        changeTextureOfSomeTiles(360, 399, 280, 310, map1, Textures.IRON_TEXTURE);
        map1.addDefaultCastle(85, 100);
        map1.addDefaultCastle(180, 30);
        map1.addDefaultCastle(330, 25);
        map1.addDefaultCastle(365, 200);
        map1.addDefaultCastle(290, 340);
        map1.addDefaultCastle(80, 380);
        map1.addDefaultCastle(200, 250);
        map1.addDefaultCastle(30, 280);
//        Map map1 = DBController.loadMap("src/main/resources/savedmaps/map1.json");
        largeMaps.add(map1);
//        DBController.saveMap(map1, "src/main/resources/savedmaps/map1.json");
    }

    public static void createMap2() {
        Map map2 = new Map(200, 200);
        changeTextureOfSomeTiles(50, 85, 75, 140, map2, Textures.SEA);
        changeTextureOfSomeTiles(25, 50, 115, 140, map2, Textures.ROCK_TEXTURE);
        for (int i = 25; i <= 50; i++) {
            map2.getTile(i, 114).setTree(Trees.DESERT_SHRUB);
        }
        changeTextureOfSomeTiles(35, 45, 100, 114, map2, Textures.BOULDER);
        changeTextureOfSomeTiles(45, 55, 100, 110, map2, Textures.IRON_TEXTURE);
        changeTextureOfSomeTiles(30, 50, 140, 150, map2, Textures.BOULDER);
        changeTextureOfSomeTiles(0, 40, 0, 50, map2, Textures.THICK_GRASS);
        for (int i = 20; i <= 30; i++) {
            for (int j = 20; j <= 30; j++) {
                map2.getTile(i, j).setTree(Trees.OLIVE_TREE);
            }
        }
        changeTextureOfSomeTiles(45, 60, 35, 55, map2, Textures.OIL);
        changeTextureOfSomeTiles(15, 30, 80, 95, map2, Textures.BOULDER);
        changeTextureOfSomeTiles(0, 15, 130, 150, map2, Textures.IRON_TEXTURE);
        changeTextureOfSomeTiles(0, 20, 160, 185, map2, Textures.OASIS_GRASS);
        changeTextureOfSomeTiles(120, 140, 165, 175, map2, Textures.BOULDER);
        changeTextureOfSomeTiles(180, 195, 150, 165, map2, Textures.IRON_TEXTURE);
        changeTextureOfSomeTiles(150, 170, 130, 150, map2, Textures.GRASS);
        changeTextureOfSomeTiles(130, 180, 0, 60, map2, Textures.OASIS_GRASS);
        changeTextureOfSomeTiles(190, 199, 40, 50, map2, Textures.ROCK_TEXTURE);
        changeTextureOfSomeTiles(100, 115, 5, 15, map2, Textures.IRON_TEXTURE);
        map2.getTile(100, 130).setTree(Trees.COCONUT_PALM);
        map2.getTile(100, 131).setTree(Trees.CHERRY_PALM);
        map2.getTile(99, 129).setTree(Trees.DATE_PALM);
        map2.getTile(100, 132).setTree(Trees.DESERT_SHRUB);
        map2.getTile(99, 128).setTree(Trees.DESERT_SHRUB);
        map2.getTile(1, 1).setRockDirection(RockDirections.getRandomDirection());
        map2.getTile(1, 4).setRockDirection(RockDirections.getRandomDirection());
        map2.addDefaultCastle(25, 60);
        map2.addDefaultCastle(85, 35);
        map2.addDefaultCastle(170, 40);
        map2.addDefaultCastle(120, 90);
        map2.addDefaultCastle(35, 180);
        map2.addDefaultCastle(110, 180);
        map2.addDefaultCastle(180, 180);
        map2.addDefaultCastle(190, 120);
        smallMaps.add(map2);
    }
    public static void createMap3() {
        Map map3 = new Map(200, 200);
        map3.addDefaultCastle(25, 60);
        map3.addDefaultCastle(85, 35);
        map3.addDefaultCastle(170, 40);
        map3.addDefaultCastle(120, 90);
        map3.addDefaultCastle(35, 180);
        map3.addDefaultCastle(110, 180);
        map3.addDefaultCastle(180, 180);
        map3.addDefaultCastle(190, 120);
        smallMaps.add(map3);
    }
    public static void createMaps() {
        createMap1();
        createMap2();
        createMap3();
    }
}
