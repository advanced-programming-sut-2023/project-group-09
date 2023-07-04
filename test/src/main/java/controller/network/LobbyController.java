package controller.network;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class LobbyController {

    public static ArrayList<Integer> getGames(){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(1);
        result.add(1);
        result.add(1);
        result.add(1);
        result.add(1);
        result.add(1);
        result.add(1);
        result.add(1);
        result.add(1);
        return result;
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
