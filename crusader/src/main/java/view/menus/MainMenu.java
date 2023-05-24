package view.menus;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.controllers.ViewController;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainMenu extends Application {
    public static Stage stage;
    public static BorderPane root;


    @Override
    public void start(Stage stage) throws Exception {
        MainMenu.stage = stage;
        makeScene();
    }

    @FXML
    public void initialize() {
        //root.setMaxHeight(stage.getMaxHeight());
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/fxml/mainMenu.fxml")).toExternalForm()));

        root = ViewController.makeScreen(stage,pane,1000,-1);


        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
