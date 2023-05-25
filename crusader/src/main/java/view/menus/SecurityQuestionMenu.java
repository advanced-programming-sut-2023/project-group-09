package view.menus;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SecurityQuestionMenu extends Application {
    public static GridPane pane;
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    @FXML
    public void initialize() {
        BorderPane rootPane = new BorderPane();

    }

    private void makeScene(Stage stage, BorderPane rootPane) throws IOException {
        pane = makeSignupScreen(stage, rootPane, 1000, -1);
        Scene scene = new Scene(rootPane);
        stage.setTitle("Security Question Menu");
        stage.setScene(scene);

        stage.show();
    }

    private GridPane makeSignupScreen(Stage stage, BorderPane pane, double width, double height) throws IOException {
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        pane.setStyle("-fx-background-color: #000");
        GridPane gridPane = FXMLLoader.load(SignupMenu.class.getResource("/FXML/securityQuestionMenu.fxml"));
        pane.setCenter(gridPane);
        gridPane.setMaxWidth(width);
        if (height != -1) {
            gridPane.setMaxHeight(height);
        }
        gridPane.setStyle("-fx-background-color: #fff");
        return gridPane;
    }
}
