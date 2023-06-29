package client;

import controller.UserController;
import view.Main;
import view.menus.LoginMenu;
import view.menus.profile.Scoreboard;

public class PacketHandler {
    Packet packet;

    public PacketHandler(Packet packet) {
        this.packet = packet;
    }

    public void handle() {
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

        }
    }
}
