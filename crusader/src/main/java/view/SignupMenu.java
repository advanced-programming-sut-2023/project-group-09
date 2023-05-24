package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SignupMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GridPane signupPane = FXMLLoader.load(SignupMenu.class.getResource("/FXML/signupMenu.fxml"));
        Scene scene = new Scene(signupPane);



        stage.setTitle("Signup Menu");
        stage.setScene(scene);
        stage.show();
    }
}
