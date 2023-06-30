package view.menus;

import controller.DBController;
import controller.UserController;
import enumeration.dictionary.Slogans;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;
import model.menugui.*;
import view.controllers.ViewController;

import java.util.Random;
import java.util.regex.Pattern;

public class SignupMenu extends Application {
    private Stage stage;
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
    public static MenuPasswordField confirmPasswordField;
    public static MenuButton randomPassword;
    public static MenuButton signup;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        DBController.loadAllUsers();
        Pane root = FXMLLoader.load(getClass().getResource("/FXML/signupMenu.fxml"));
        signupPane = ViewController.makePaneScreen(stage, root, 1000, -1);
        Scene scene = new Scene(root);

        menuBox = new MenuBox("Signup", 400, 130, 700, 600);
        root.getChildren().add(menuBox);

        makeCredentials();
        makeSlogan();
        makePassword();

        signup = new MenuButton("Signup", menuBox, 0, 255, true);
        signup.setOnAction(actionEvent -> signup());
        menuBox.getChildren().add(signup);

        Hyperlink toLoginMenu = new Hyperlink("You already have an account? Login...");
        menuBox.getChildren().add(toLoginMenu);
        toLoginMenu.setOnAction(actionEvent -> {
            LoginMenu loginMenu = new LoginMenu();
            try {
                loginMenu.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        toLoginMenu.setTextFill(Color.DARKBLUE);
        toLoginMenu.setTranslateY(285);

        stage.setTitle("Signup Menu");
        stage.setScene(scene);
        stage.show();
    }

    private void makeCredentials() {
        usernameField = new MenuTextField(menuBox, "username", "Username", 0, -210, 300);
        validateUsername();
        menuBox.getChildren().add(usernameField);

        nicknameField = new MenuTextField(menuBox, "nickname", "Nickname", 0, -150, 300);
        menuBox.getChildren().add(nicknameField);

        emailField = new MenuTextField(menuBox, "email", "Email", 0, -90, 300);
        menuBox.getChildren().add(emailField);
    }

    private void makeSlogan() {
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
        randomSlogan.setOnAction(actionEvent -> randomSlogan());
        menuBox.getChildren().add(randomSlogan);
    }

    private void makePassword() {
        passwordField = new MenuPasswordField(menuBox, "password", "Password", 0, 85);
        menuBox.getChildren().add(passwordField);
        validatePassword();

        confirmPasswordField = new MenuPasswordField(menuBox, "confirmation", "Confirmation", 0, 145);
        menuBox.getChildren().add(confirmPasswordField);
        validateConfirmPassword();

        randomPassword = new MenuButton("random password", menuBox, 75, 205, false);
        randomPassword.setScaleX(0.6);
        randomPassword.setScaleY(0.6);
        randomPassword.setOnAction(actionEvent -> {
            passwordField.getShowPassword().setSelected(true);
            passwordField.getPasswordTextField().setText(UserController.generateRandomPassword());
        });
        menuBox.getChildren().add(randomPassword);
    }

    private void randomSlogan() {
        sloganField.clearErrorOrMessage();
        int sloganNumber = new Random().nextInt(Slogans.values().length) + 1;
        sloganField.setValue(Slogans.getSloganByNumber(sloganNumber));
    }

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
                }
                if (controller.Application.isUserExistsByName(newValue)) {
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
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                passwordField.clearErrorOrMessage();
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

        passwordField.getPasswordTextField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                passwordField.clearErrorOrMessage();
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

        confirmPasswordField.getPasswordTextField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                confirmPasswordField.clearErrorOrMessage();
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
        User newUser = new User(usernameField.getText(), UserController.convertPasswordToHash(passwordField.getText()),
                nicknameField.getText(), emailField.getText(), sloganValue);
        SecurityQuestionMenu.user = newUser;
        SecurityQuestionMenu securityQuestionMenu = new SecurityQuestionMenu();
        try {
            securityQuestionMenu.start(this.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
