package client;

import controller.MapController;
import controller.gamestructure.GameMaps;
import enumeration.dictionary.Colors;
import model.FakeGame;
import model.Government;
import model.game.Game;
import view.Main;
import view.menus.CreateGameMenu;
import view.menus.GameMenu;

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
                for (int i = 0; i != fakeGame.getAllUsernames().size(); i++) {
                    Government government = new Government(null, fakeGame.getCastleXs().get(i),
                            fakeGame.getCastleYs().get(i), Colors.getColor(fakeGame.getColors().get(i)));
                    if (fakeGame.getAllUsernames().get(i).equals(username)) {
                        game.getGovernments().add(0 , government);
                    } else {
                        game.addGovernment(government);
                    }
                }
                MapController.map = GameMaps.largeMaps.get(0);
                new GameMenu().start(CreateGameMenu.stage);
            }
        }
    }
}
