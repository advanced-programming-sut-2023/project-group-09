package view.menus;

import controller.UserController;
import enumeration.dictionary.Slogans;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.menugui.*;
import model.menugui.MenuButton;
import view.controllers.ViewController;

import java.util.regex.Pattern;

public class SignupMenu extends Application {
    public static Pane signupPane;
    public boolean usernameLiveInvalid = false;
    public boolean passwordLiveInvalid = false;
    public boolean confirmPasswordLiveInvalid = false;

    public static MenuBox menuBox;
    public static MenuTextField usernameField;
    public static MenuTextField nicknameField;
    public static MenuTextField emailField;
    public static MenuComboBox sloganField;
    public static CheckBox isSlogan;
    public static MenuButton randomSlogan;
    public static MenuPasswordField passwordField;
    public static MenuTextField passwordTextField;
    public static CheckBox showPassword;
    public static MenuPasswordField confirmPasswordField;
    public static MenuTextField confirmPasswordTextField;
    public static CheckBox showConfirmPassword;
    public static MenuButton randomPassword;
    public static MenuButton signup;

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("/FXML/signupMenu.fxml"));
        signupPane = ViewController.makePaneScreen(stage, root, 1000, -1);
        Scene scene = new Scene(root);

        menuBox = new MenuBox("Signup", 400, 130, 700, 600);
        root.getChildren().add(menuBox);

        usernameField = new MenuTextField(menuBox, "username", "Username", 0, -210);
        validateUsername();
        menuBox.getChildren().add(usernameField);

        nicknameField = new MenuTextField(menuBox, "nickname", "Nickname", 0, -150);
        menuBox.getChildren().add(nicknameField);

        emailField = new MenuTextField(menuBox, "email", "Email", 0, -90);
        menuBox.getChildren().add(emailField);

        String[] slogans = new String[17];
        for (int i = 1; i <= 17; i++) {
            slogans[i - 1] = Slogans.getSloganByNumber(i);
        }
        sloganField = new MenuComboBox(menuBox, "Slogan", 0, -30,
                FXCollections.observableArrayList(slogans));
        sloganField.setDisable(true);
        menuBox.getChildren().add(sloganField);

        isSlogan = new CheckBox("slogan");
        isSlogan.setStyle("-fx-background-color: rgba(42, 42, 42, 0.7); -fx-text-fill: gray; -fx-font-size: 14; " +
                "-fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-padding: 3; -fx-background-radius: 3");
        isSlogan.setTranslateX(-115);
        isSlogan.setTranslateY(25);
        checkIsSlogan();
        menuBox.getChildren().add(isSlogan);

        randomSlogan = new MenuButton("random slogan", menuBox, 75, 25, false);
        randomSlogan.setScaleX(0.6);
        randomSlogan.setScaleY(0.6);
        randomSlogan.setDisable(true);
        menuBox.getChildren().add(randomSlogan);

        passwordField = new MenuPasswordField(menuBox, "password", "Password", 0, 85);
        menuBox.getChildren().add(passwordField);

        passwordTextField = new MenuTextField(menuBox, "password", "Password", 0, 85);
        passwordTextField.setVisible(false);
        menuBox.getChildren().add(passwordTextField);

        showPassword = new CheckBox("\uD83D\uDC41");
        showPassword.setStyle("-fx-background-color: rgba(42, 42, 42, 0.7); -fx-text-fill: gray; -fx-font-size: 16; " +
                "-fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-padding: 3; -fx-background-radius: 3");
        showPassword.setTranslateX(185);
        showPassword.setTranslateY(85);
        passwordLiveValidate();
        menuBox.getChildren().add(showPassword);

        confirmPasswordField = new MenuPasswordField(menuBox, "confirmation", "Confirmation", 0, 145);
        menuBox.getChildren().add(confirmPasswordField);

        confirmPasswordTextField = new MenuTextField(menuBox, "confirmaion", "Confirmation", 0, 145);
        confirmPasswordTextField.setVisible(false);
        menuBox.getChildren().add(confirmPasswordTextField);

        showConfirmPassword = new CheckBox("\uD83D\uDC41");
        showConfirmPassword.setStyle("-fx-background-color: rgba(42, 42, 42, 0.7); -fx-text-fill: gray; -fx-font-size: 16; " +
                "-fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-padding: 3; -fx-background-radius: 3");
        showConfirmPassword.setTranslateX(185);
        showConfirmPassword.setTranslateY(145);
        confirmPasswordLiveValidate();
        menuBox.getChildren().add(showConfirmPassword);

        randomPassword = new MenuButton("random password", menuBox, 75, 205, false);
        randomPassword.setScaleX(0.6);
        randomPassword.setScaleY(0.6);
        randomPassword.setOnAction(actionEvent -> {
            this.showPassword.setSelected(true);
            this.passwordTextField.setText(UserController.generateRandomPassword());
        });
        menuBox.getChildren().add(randomPassword);

        signup = new MenuButton("Signup", menuBox, 0, 255, true);
        signup.setOnAction(actionEvent -> signup());
        menuBox.getChildren().add(signup);


        stage.setTitle("Signup Menu");
        stage.setScene(scene);
        stage.show();
    }

//    private void makeScene(Stage stage, BorderPane rootPane) throws IOException {
//        signupPane = makeSignupScreen(stage, rootPane, 1000, -1);
//        Scene scene = new Scene(rootPane);
//        stage.setTitle("Signup Menu");
//        stage.setScene(scene);
//        this.makeUsernameTextField();
//        this.makeNicknameTextField();
//        this.makeEmailTextField();
//        this.makeSloganCombobox();
//        this.makeRandomSloganButton();
//        this.makeIsSloganCheckbox();
//        this.makePasswordField();
//        this.makeConfirmPasswordField();
//        this.makeRandomPasswordButton();
//        this.makeSignupButton();
//        stage.show();
//    }

//    private GridPane makeSignupScreen(Stage stage, BorderPane pane, double width, double height) throws IOException {
//        stage.setFullScreen(true);
//        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
//
//        pane.setStyle("-fx-background-color: #000");
//        GridPane gridPane = FXMLLoader.load(SignupMenu.class.getResource("/FXML/signupMenu.fxml"));
//        pane.setCenter(gridPane);
//        gridPane.setMaxWidth(width);
//        if (height != -1) {
//            gridPane.setMaxHeight(height);
//        }
//        gridPane.setStyle("-fx-background-color: #fff");
//        return gridPane;
//    }

//    private void makeUsernameTextField() {
//        username = new TextField();
//        GridPane.setRowIndex(username, 1);
//        GridPane.setColumnIndex(username, 1);
//        username.prefWidth(200);
//        username.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
//
//        usernameError = new Text();
//        GridPane.setRowIndex(usernameError, 2);
//        GridPane.setColumnIndex(usernameError, 1);
//        usernameError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));
//
//        validateUsername();
//        signupPane.getChildren().addAll(username, usernameError);
//    }

//    private void makeNicknameTextField() {
//        nickname = new TextField();
//        GridPane.setRowIndex(nickname, 1);
//        GridPane.setColumnIndex(nickname, 4);
//        nickname.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
//
//        nicknameError = new Text();
//        GridPane.setRowIndex(nicknameError, 2);
//        GridPane.setColumnIndex(nicknameError, 4);
//        nicknameError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));
//
//        signupPane.getChildren().addAll(nickname, nicknameError);
//    }

//    private void makeEmailTextField() {
//        email = new TextField();
//        GridPane.setRowIndex(email, 3);
//        GridPane.setColumnIndex(email, 1);
//        email.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
//
//        emailError = new Text();
//        GridPane.setRowIndex(emailError, 4);
//        GridPane.setColumnIndex(emailError, 1);
//        emailError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));
//
//        signupPane.getChildren().addAll(email, emailError);
//    }

//    private void makeSloganCombobox() {
//        String[] slogans = new String[17];
//        for (int i = 1; i <= 17; i++) {
//            slogans[i - 1] = Slogans.getSloganByNumber(i);
//        }
//        slogan = new ComboBox<>(FXCollections.observableArrayList(slogans));
//        GridPane.setRowIndex(slogan, 3);
//        GridPane.setColumnIndex(slogan, 4);
//        slogan.setEditable(true);
//        slogan.setDisable(true);
//
//        sloganError = new Text();
//        GridPane.setRowIndex(sloganError, 4);
//        GridPane.setColumnIndex(sloganError, 4);
//        sloganError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));
//
//        signupPane.getChildren().addAll(slogan, sloganError);
//    }

    private void checkIsSlogan() {
        isSlogan.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                sloganField.setDisable(!newValue);
                randomSlogan.setDisable(!newValue);
                sloganField.setValue("");
                if (!newValue) sloganField.clearErrorOrMessage();
            }
        });
    }

//    private void makeRandomSloganButton() {
//        randomSlogan2 = new Button("Random Slogan");
//        GridPane.setRowIndex(randomSlogan2, 5);
//        GridPane.setColumnIndex(randomSlogan2, 4);
//        GridPane.setHalignment(randomSlogan2, HPos.RIGHT);
//        randomSlogan2.setDisable(true);
//        randomSlogan2.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
//
//        randomSlogan2.setOnAction(actionEvent -> {
//            this.slogan.setValue(UserController.generateRandomSlogan());
//        });
//
//        signupPane.getChildren().add(randomSlogan2);
//    }

    private void passwordLiveValidate() {
        showPassword.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    passwordField.setVisible(false);
                    passwordTextField.setVisible(true);
                    passwordTextField.setText(passwordField.getText());
                } else {
                    passwordField.setVisible(true);
                    passwordTextField.setVisible(false);
                    passwordField.setText(passwordTextField.getText());
                }
            }
        });

        validatePassword();
    }

    private void confirmPasswordLiveValidate() {
        showConfirmPassword.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    confirmPasswordField.setVisible(false);
                    confirmPasswordTextField.setVisible(true);
                    confirmPasswordTextField.setText(confirmPasswordField.getText());
                } else {
                    confirmPasswordField.setVisible(true);
                    confirmPasswordTextField.setVisible(false);
                    confirmPasswordField.setText(confirmPasswordTextField.getText());
                }
            }
        });

        validateConfirmPassword();
    }

    private void validateUsername() {
        usernameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                usernameField.clearErrorOrMessage();
                if (newValue == "") {
                    usernameLiveInvalid = false;
                    return;
                }
                if (!newValue.matches("([a-z]|[A-Z]|[0-9]|_)*")) {
                    usernameField.handlingError("invalid username!");
                    usernameLiveInvalid = true;
                } else if (controller.Application.isUserExistsByName(newValue)) {
                    usernameField.handlingError("username already exists!");
                    usernameLiveInvalid = true;
                } else {
                    usernameField.handlingCorrect("valid username");
                    usernameLiveInvalid = false;
                }
            }
        });
    }

    private void validatePassword() {
        passwordTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                passwordField.clearErrorOrMessage();
                passwordTextField.setText(newValue);
                if (newValue == "") {
                    passwordLiveInvalid = false;
                    return;
                }
                boolean smallLetter = Pattern.compile("[a-z]").matcher(newValue).find();
                boolean capitalLetter = Pattern.compile("[A-Z]").matcher(newValue).find();
                boolean digit = Pattern.compile("[0-9]").matcher(newValue).find();
                boolean specialCharacter = Pattern.compile("[^a-zA-Z0-9]").matcher(newValue).find();
                if (newValue.contains(" ")) {
                    passwordField.handlingError("you can't user space!");
                    passwordLiveInvalid = true;
                } else if (newValue.length() < 6) {
                    passwordField.handlingError("password is too short!");
                    passwordLiveInvalid = true;
                } else if (!smallLetter || !capitalLetter || !digit || !specialCharacter) {
                    passwordField.handlingError("weak password!");
                    passwordLiveInvalid = true;
                } else {
                    passwordField.handlingCorrect("strong password");
                    passwordLiveInvalid = false;
                }
            }
        });

        passwordTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                passwordField.clearErrorOrMessage();
                passwordField.setText(newValue);
                if (newValue == "") {
                    passwordLiveInvalid = false;
                    return;
                }
                boolean smallLetter = Pattern.compile("[a-z]").matcher(newValue).find();
                boolean capitalLetter = Pattern.compile("[A-Z]").matcher(newValue).find();
                boolean digit = Pattern.compile("[0-9]").matcher(newValue).find();
                boolean specialCharacter = Pattern.compile("[^a-zA-Z0-9]").matcher(newValue).find();
                if (newValue.contains(" ")) {
                    passwordField.handlingError("you can't user space!");
                    passwordLiveInvalid = true;
                } else if (newValue.length() < 6) {
                    passwordField.handlingError("password is too short!");
                    passwordLiveInvalid = true;
                } else if (!smallLetter || !capitalLetter || !digit || !specialCharacter) {
                    passwordField.handlingError("weak password!");
                    passwordLiveInvalid = true;
                } else {
                    passwordField.handlingCorrect("strong password");
                    passwordLiveInvalid = false;
                }
            }
        });
    }

    private void validateConfirmPassword() {
        confirmPasswordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                confirmPasswordField.clearErrorOrMessage();
                confirmPasswordTextField.setText(newValue);
                if (newValue == "") {
                    confirmPasswordLiveInvalid = false;
                    return;
                }
                if (!newValue.equals(passwordField.getText())) {
                    confirmPasswordField.handlingError("passwords don't match!");
                    confirmPasswordLiveInvalid = true;
                } else {
                    confirmPasswordField.handlingCorrect("passwords match");
                    confirmPasswordLiveInvalid = false;
                }
            }
        });

        confirmPasswordTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                confirmPasswordField.clearErrorOrMessage();
                confirmPasswordField.setText(newValue);
                if (newValue == "") {
                    confirmPasswordLiveInvalid = false;
                    return;
                }
                if (!newValue.equals(passwordField.getText())) {
                    confirmPasswordField.handlingError("passwords don't match!");
                    confirmPasswordLiveInvalid = true;
                } else {
                    confirmPasswordField.handlingCorrect("passwords match");
                    confirmPasswordLiveInvalid = false;
                }
            }
        });
    }

    public void signup() {
        nicknameField.clearErrorOrMessage();
        emailField.clearErrorOrMessage();
        sloganField.clearErrorOrMessage();
        if (usernameLiveInvalid) return;
        if (passwordLiveInvalid) return;
        if (confirmPasswordLiveInvalid) return;
        if (validateEmptyFields()) return;

        boolean validEmail = Pattern.compile("[a-zA-Z\\d_\\.]+@[a-zA-Z\\d_\\.]+\\.[a-zA-Z\\d_\\.]+").matcher(emailField.getText()).matches();
        if (!validEmail) {
            emailField.handlingError("invalid email!");
            return;
        }
        if (controller.Application.isUserExistsByEmail(emailField.getText())) {
            emailField.handlingError("email already exists!");
            return;
        }

        String sloganValue = (isSlogan.isSelected()) ? (String) sloganField.getValue() : null;
        User newUser = new User(usernameField.getText(), passwordField.getText(), nicknameField.getText(),
                emailField.getText(), sloganValue);
//        TODO: run security question menu
    }

    public boolean validateEmptyFields() {
        boolean emptyField = false;
        if (usernameField.getText().isEmpty()) {
            emptyField = true;
            usernameField.handlingError("username is required!");
        }
        if (nicknameField.getText().isEmpty()) {
            emptyField = true;
            nicknameField.handlingError("nickname is required!");
        }
        if (emailField.getText().isEmpty()) {
            emptyField = true;
            emailField.handlingError("email is required!");
        }
        if (isSlogan.isSelected() && sloganField.getValue().equals("")) {
            emptyField = true;
            sloganField.handlingError("slogan is required!");
        }
        if (passwordField.getText().isEmpty()) {
            emptyField = true;
            passwordField.handlingError("password is required!");
        }
        if (confirmPasswordField.getText().isEmpty()) {
            emptyField = true;
            confirmPasswordField.handlingError("confirmation is required!");
        }
        return emptyField;
    }
}
