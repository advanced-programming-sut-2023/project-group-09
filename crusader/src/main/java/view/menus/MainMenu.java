package view.menus;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.controllers.ViewController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        root = ViewController.makeScreen(stage, pane, 1000, -1);


        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        runPy();

    }

    public void runPy() {
        try {
            Process p = Runtime.getRuntime().exec("python files/captcha/data/main.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int ret = new Integer(in.readLine());
            System.out.println(ret);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
