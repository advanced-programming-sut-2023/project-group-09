package client;

import controller.GameController;
import controller.GovernmentController;
import controller.MapController;
import controller.gamestructure.GameMaps;
import enumeration.dictionary.Colors;
import model.FakeGame;
import model.Government;
import model.building.castlebuildings.MainCastle;
import model.game.Game;
import view.Main;
import view.menus.CreateGameMenu;
import view.menus.GameMenu;
import view.menus.MainMenu;

public class PacketOnlineHandler {
    private Packet packet;
    private String token;

    public PacketOnlineHandler(Packet packet) {
        this.packet = packet;
    }

    public PacketOnlineHandler(Packet packet, String token) {
        this.packet = packet;
        this.token = token;
    }

    public void handle() throws Exception {
        switch (packet.command) {
            case "create fake game" -> {
                String username = (String)packet.getAttribute("username");
                FakeGame fakeGame = (FakeGame) Main.connection.getObjectInputStream().readObject();
                GameMaps.createMap1();
                Game game = new Game(GameMaps.largeMaps.get(0));
                MapController.map = GameMaps.largeMaps.get(0);
                GameController.setGame(game);
                for (int i = 0; i != fakeGame.getAllUsernames().size(); i++) {
                    Government government = new Government(null, fakeGame.getCastleXs().get(i),
                            fakeGame.getCastleYs().get(i), Colors.getColor(fakeGame.getColors().get(i)));
                    MapController.dropBuilding(government.getCastleX(), government.getCastleY(), "mainCastle", government);
                    MainCastle mainCastle = (MainCastle) GameController.getGame().getMap()
                            .getTile(government.getCastleX(), government.getCastleY()).getBuilding();
                    mainCastle.setGovernment(government);
                    government.setMainCastle(mainCastle);
                    government.addAmountToProperties("wood", "resource", 1000);
                    government.addAmountToProperties("stone", "resource", 500);
                    government.addAmountToProperties("iron", "resource", 500);
                    government.addAmountToProperties("bread", "food", 60);
                    government.setGold(4000);
                    if (fakeGame.getAllUsernames().get(i).equals(username)) {
                        System.out.println("test");
                        game.getGovernments().add(0 , government);
                        GameController.getGame().setCurrentGovernment(government);
                        GovernmentController.setCurrentGovernment(government);
                    } else {
                        game.addGovernment(government);
                    }
                }
            }
        }
    }
}
