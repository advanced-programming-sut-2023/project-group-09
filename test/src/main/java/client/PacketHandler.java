package client;

import controller.GameController;
import controller.MapController;
import controller.UserController;
import controller.gamestructure.GameMaps;
import enumeration.dictionary.Colors;
import model.FakeGame;
import model.Government;
import model.building.castlebuildings.MainCastle;
import model.game.Game;
import view.Main;
import view.menus.GameMenu;
import view.menus.LoginMenu;
import view.menus.MainMenu;
import view.menus.profile.Scoreboard;

public class PacketHandler {
    Packet packet;

    public PacketHandler(Packet packet) {
        this.packet = packet;
    }

    public void handle() throws Exception {
        switch (packet.command) {
            case "username doesn't exist" -> {
                UserController.loginUsernameExistsAct();
            }
            case "password isn't correct" -> {
                UserController.loginUserPasswordWrongAct();
            }
            case "successful login" -> {
                UserController.loginUserSuccessfulAct();
                Main.connection.setToken(packet.token);
            }
            case "users have change" -> {
                Scoreboard.usersChanged = true;
            }
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
                        game.getGovernments().add(0 , government);
                    } else {
                        game.addGovernment(government);
                    }
                }
                new GameMenu().start(MainMenu.stage);
            }

        }
    }
}
