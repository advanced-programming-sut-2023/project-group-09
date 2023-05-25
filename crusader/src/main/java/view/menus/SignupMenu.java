package view.menus;

import enumeration.dictionary.Slogans;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class SignupMenu extends Application {
    public static GridPane signupPane;
    public Text signupText;
    public Label usernameLabel;
    public TextField username;
    public Label nicknameLabel;
    public TextField nickname;
    public Label emailLabel;
    public TextField email;
    public Label sloganLabel;
    public ComboBox slogan;
    public Label passwordLabel;
    public PasswordField password;
    public CheckBox isSlogan;
    public Button randomSlogan;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane rootPane = new BorderPane();
        makeScene(stage, rootPane);
    }

    @FXML
    public void initialize() {
        signupText.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
        usernameLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        username.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        nicknameLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        nickname.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        emailLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        email.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        sloganLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        passwordLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        password.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    }

    private void makeScene(Stage stage, BorderPane rootPane) throws IOException {
        signupPane = makeSignupScreen(stage, rootPane, 1000, -1);
        Scene scene = new Scene(rootPane);
        this.makeSloganCombobox();
        this.makeRandomSloganButton();
        this.makeIsSloganCheckbox();
        stage.setTitle("Signup Menu");
        stage.setScene(scene);
        stage.show();
    }

    private GridPane makeSignupScreen(Stage stage, BorderPane pane, double width, double height) throws IOException {
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        pane.setStyle("-fx-background-color: #000");
        GridPane gridPane = FXMLLoader.load(SignupMenu.class.getResource("/FXML/signupMenu.fxml"));
        pane.setCenter(gridPane);
        gridPane.setMaxWidth(width);
        if (height != -1) {
            gridPane.setMaxHeight(height);
        }
        gridPane.setStyle("-fx-background-color: #fff");
        return gridPane;
    }

    private void makeSloganCombobox() {
        String[] slogans = new String[17];
        for (int i = 1; i <= 17; i++) {
            slogans[i - 1] = Slogans.getSloganByNumber(i);
        }
        slogan = new ComboBox<>(FXCollections.observableArrayList(slogans));
        GridPane.setRowIndex(slogan, 3);
        GridPane.setColumnIndex(slogan, 3);
        slogan.setEditable(true);
        slogan.setDisable(true);
        signupPane.getChildren().add(slogan);
    }

    private void makeIsSloganCheckbox() {
        isSlogan = new CheckBox("slogan");
        GridPane.setRowIndex(isSlogan, 5);
        GridPane.setColumnIndex(isSlogan, 3);
        isSlogan.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));

        isSlogan.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                slogan.setDisable(!newValue);
                randomSlogan.setDisable(!newValue);
                slogan.setValue("");
            }
        });

        signupPane.getChildren().add(isSlogan);
    }

    private void makeRandomSloganButton() {
        randomSlogan = new Button("random");
        GridPane.setRowIndex(randomSlogan, 5);
        GridPane.setColumnIndex(randomSlogan, 3);
        GridPane.setHalignment(randomSlogan, HPos.RIGHT);
        randomSlogan.setDisable(true);
        randomSlogan.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));

        randomSlogan.setOnAction(actionEvent -> {
            int sloganNumber = new Random().nextInt(17) + 1;
            this.slogan.setValue(Slogans.getSloganByNumber(sloganNumber));
        });

        signupPane.getChildren().add(randomSlogan);
    }
}
