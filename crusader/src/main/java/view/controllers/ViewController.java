package view.controllers;

import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewController {

    //give -1 if you want full height
    public static BorderPane makeScreen(Stage stage,BorderPane pane,double width,double height){
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        //stage.setFullScreenExitHint(null);

        pane.setStyle("-fx-background-color: #000");
        BorderPane borderPane = new BorderPane();
        pane.setCenter(borderPane);
        borderPane.setMaxWidth(width);
        if (height != -1){
            borderPane.setMaxHeight(height);
        }
        borderPane.setStyle("-fx-background-color: #fff");
        return borderPane;
    }
}
