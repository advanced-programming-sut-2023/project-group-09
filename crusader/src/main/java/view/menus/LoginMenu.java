package view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginMenu extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        GridPane loginPane = FXMLLoader.load(LoginMenu.class.getResource("/FXML/loginMenu.fxml"));
        Scene scene = new Scene(loginPane);



        stage.setTitle("Signup Menu");
        stage.setScene(scene);
        stage.show();

        stage.show();
    }
}
