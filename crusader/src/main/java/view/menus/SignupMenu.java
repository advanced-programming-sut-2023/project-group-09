package view.menus;

import controller.UserController;
import enumeration.dictionary.Slogans;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupMenu extends Application {
    public static GridPane signupPane;
    public Text signupText;
    public Label usernameLabel;
    public TextField username;
    public Text usernameError;
    public Label nicknameLabel;
    public TextField nickname;
    public Text nicknameError;
    public Label emailLabel;
    public TextField email;
    public Text emailError;
    public Label sloganLabel;
    public ComboBox slogan;
    public Text sloganError;
    public Label passwordLabel;
    public PasswordField password;
    public TextField passwordTextField;
    public Text passwordError;
    public CheckBox showPassword;
    public Label confirmPasswordLabel;
    public PasswordField confirmPassword;
    public TextField confirmPasswordTextField;
    public Text confirmPasswordError;
    public CheckBox showConfirmPassword;
    public CheckBox isSlogan;
    public Button randomSlogan;
    public Button randomPassword;
    public Button signup;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane rootPane = new BorderPane();
        makeScene(stage, rootPane);
    }

    @FXML
    public void initialize() {
        signupText.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 24));
        usernameLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        nicknameLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        nickname.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        emailLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        email.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        sloganLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        passwordLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        confirmPasswordLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        signup.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
    }

    private void makeScene(Stage stage, BorderPane rootPane) throws IOException {
        signupPane = makeSignupScreen(stage, rootPane, 1000, -1);
        Scene scene = new Scene(rootPane);
        stage.setTitle("Signup Menu");
        stage.setScene(scene);
        this.makeUsernameTextField();
        this.makeSloganCombobox();
        this.makeRandomSloganButton();
        this.makeIsSloganCheckbox();
        this.makePasswordField();
        this.makeConfirmPasswordField();
        this.makeRandomPasswordButton();
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

    private void makeUsernameTextField() {
        username = new TextField();
        GridPane.setRowIndex(username, 1);
        GridPane.setColumnIndex(username, 1);
        username.prefWidth(200);
        username.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));

        usernameError = new Text();
        GridPane.setRowIndex(usernameError, 2);
        GridPane.setColumnIndex(usernameError, 1);
        usernameError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));

        validateUsername();
        signupPane.getChildren().addAll(username, usernameError);
    }

    private void makeSloganCombobox() {
        String[] slogans = new String[17];
        for (int i = 1; i <= 17; i++) {
            slogans[i - 1] = Slogans.getSloganByNumber(i);
        }
        slogan = new ComboBox<>(FXCollections.observableArrayList(slogans));
        GridPane.setRowIndex(slogan, 3);
        GridPane.setColumnIndex(slogan, 4);
        slogan.setEditable(true);
        slogan.setDisable(true);
        signupPane.getChildren().add(slogan);
    }

    private void makeIsSloganCheckbox() {
        isSlogan = new CheckBox("slogan");
        GridPane.setRowIndex(isSlogan, 5);
        GridPane.setColumnIndex(isSlogan, 4);
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
        randomSlogan = new Button("Random Slogan");
        GridPane.setRowIndex(randomSlogan, 5);
        GridPane.setColumnIndex(randomSlogan, 4);
        GridPane.setHalignment(randomSlogan, HPos.RIGHT);
        randomSlogan.setDisable(true);
        randomSlogan.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));

        randomSlogan.setOnAction(actionEvent -> {
            this.slogan.setValue(UserController.generateRandomSlogan());
        });

        signupPane.getChildren().add(randomSlogan);
    }

    private void makePasswordField() {
        password = new PasswordField();
        GridPane.setRowIndex(password, 5);
        GridPane.setColumnIndex(password, 1);

        passwordTextField = new TextField();
        GridPane.setRowIndex(passwordTextField, 5);
        GridPane.setColumnIndex(passwordTextField, 1);
        passwordTextField.setVisible(false);

        showPassword = new CheckBox("\uD83D\uDC41");
        GridPane.setRowIndex(showPassword, 5);
        GridPane.setColumnIndex(showPassword, 2);
        showPassword.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    password.setVisible(false);
                    passwordTextField.setVisible(true);
                    passwordTextField.setText(password.getText());
                } else {
                    password.setVisible(true);
                    passwordTextField.setVisible(false);
                    password.setText(passwordTextField.getText());
                }
            }
        });

        password.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        passwordTextField.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        showPassword.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        signupPane.getChildren().addAll(password, passwordTextField, showPassword);
    }

    private void makeConfirmPasswordField() {
        confirmPassword = new PasswordField();
        GridPane.setRowIndex(confirmPassword, 7);
        GridPane.setColumnIndex(confirmPassword, 1);

        confirmPasswordTextField = new TextField();
        GridPane.setRowIndex(confirmPasswordTextField, 7);
        GridPane.setColumnIndex(confirmPasswordTextField, 1);
        confirmPasswordTextField.setVisible(false);

        showConfirmPassword = new CheckBox("\uD83D\uDC41");
        GridPane.setRowIndex(showConfirmPassword, 7);
        GridPane.setColumnIndex(showConfirmPassword, 2);
        showConfirmPassword.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    confirmPassword.setVisible(false);
                    confirmPasswordTextField.setVisible(true);
                    confirmPasswordTextField.setText(confirmPassword.getText());
                } else {
                    confirmPassword.setVisible(true);
                    confirmPasswordTextField.setVisible(false);
                    confirmPassword.setText(confirmPasswordTextField.getText());
                }
            }
        });

        confirmPassword.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        confirmPasswordTextField.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        showConfirmPassword.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        signupPane.getChildren().addAll(confirmPassword, confirmPasswordTextField, showConfirmPassword);
    }

    private void makeRandomPasswordButton() {
        randomPassword = new Button("Random Password");
        GridPane.setRowIndex(randomPassword, 9);
        GridPane.setColumnIndex(randomPassword, 1);
        randomPassword.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));

        randomPassword.setOnAction(actionEvent -> {
            this.showPassword.setSelected(true);
            this.passwordTextField.setText(UserController.generateRandomPassword());
        });

        signupPane.getChildren().add(randomPassword);
    }

    private void validateUsername() {
        username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                usernameError.setText("");
                if (newValue == "") return;
                if (!newValue.matches("([a-z]|[A-Z]|[0-9]|_)*")) {
                    usernameError.setFill(Color.RED);
                    usernameError.setText("invalid username!");
                } else if (controller.Application.isUserExistsByName(newValue)) {
                    usernameError.setFill(Color.RED);
                    usernameError.setText("username already exists!");
                }
            }
        });
    }
}
