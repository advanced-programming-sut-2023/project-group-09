package controller.network;

import client.Packet;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.FakeGame;
import view.Main;

import java.io.IOException;
import java.util.ArrayList;

public class LobbyController {

    public static FakeGame getFakeGame(long id) throws IOException, ClassNotFoundException {
        Packet packet = new Packet("get fake game","Game");
        packet.addAttribute("id",id);
        packet.sendPacket();
        FakeGame fakeGame = (FakeGame) Main.connection.getObjectInputStream().readObject();
        return fakeGame;
    }

    public static void scaleUp(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();

        ScaleTransition st = new ScaleTransition(Duration.millis(100), node);
        st.setFromX(1);
        st.setFromY(1);
        st.setToX(1.1);
        st.setToY(1.1);
        st.setAutoReverse(true);
        st.play();

    }
    public static void scaleDown(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(100), node);
        st.setFromX(1.1);
        st.setFromY(1.1);
        st.setToX(1);
        st.setToY(1);
        st.setAutoReverse(true);
        st.play();
    }
}
