import controller.*;
import controller.gamestructure.GameMaps;
import enumeration.Textures;
import enumeration.dictionary.Trees;
import model.building.Building;
import model.building.OxTether;
import model.building.castlebuildings.Wall;
import model.game.Game;
import model.game.Map;
import model.game.Tile;
import model.human.military.ArabianMercenary;
import view.PrimaryMenu;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        MainController.run();
        /*Map map = new Map(30, 20);
        Tile[][] mapTiles = new Tile[30][20];

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 20; j++) {
                Tile tile = new Tile();
                tile.setTexture(Textures.EARTH);
                mapTiles[i][j] = tile;
            }
        }
        mapTiles[2][1].addMilitary(new ArabianMercenary(0, 0, 0, 0, 0));
        mapTiles[3][5].setTree(Trees.OLIVE_TREE);
        mapTiles[5][4].setTexture(Textures.GRASS);
        mapTiles[5][5].setBuilding(new Wall(0, 0, "wall", 0, 0, 0, 0));
        map.setMapTiles(mapTiles);
        GameController.setGame(new Game(map));

        System.out.println(GameController.showMap(0, 0));*/
//        GameMaps.createMap1();
//        System.out.println("\u1F60");
        MainController.run();
    }

}
