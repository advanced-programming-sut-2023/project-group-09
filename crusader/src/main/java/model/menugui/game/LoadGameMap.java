package model.menugui.game;

import controller.gamestructure.GameMaps;
import model.game.Map;

public class LoadGameMap extends Thread {
    public static GameMap gameMap1;
    public static GameMap gameMap2;
    private int mapNumber;
    private int chunkNumber;

    public LoadGameMap(int mapNumber) {
        this.mapNumber = mapNumber;
    }

    @Override
    public void run() {
        super.run();
        if (mapNumber == 1) {
            GameMaps.createMap1();
            Map map = GameMaps.largeMaps.get(0);

            gameMap1 = new GameMap(map, 0, 0, 50, 30);
        } // else if (mapNumber == 2) {
//            GameMaps.createMap2();
//            Map map = GameMaps.smallMaps.get(0);
//
//            gameMap2 = new GameMap(map, 0, 0, 50, 30);
//        }
    }
}
