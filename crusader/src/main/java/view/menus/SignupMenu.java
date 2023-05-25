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
import model.User;

import java.io.IOException;
import java.util.regex.Pattern;

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
        emailLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        sloganLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        passwordLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        confirmPasswordLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
    }

    private void makeScene(Stage stage, BorderPane rootPane) throws IOException {
        signupPane = makeSignupScreen(stage, rootPane, 1000, -1);
        Scene scene = new Scene(rootPane);
        stage.setTitle("Signup Menu");
        stage.setScene(scene);
        this.makeUsernameTextField();
        this.makeNicknameTextField();
        this.makeEmailTextField();
        this.makeSloganCombobox();
        this.makeRandomSloganButton();
        this.makeIsSloganCheckbox();
        this.makePasswordField();
        this.makeConfirmPasswordField();
        this.makeRandomPasswordButton();
        this.makeSignupButton();
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

    private void makeNicknameTextField() {
        nickname = new TextField();
        GridPane.setRowIndex(nickname, 1);
        GridPane.setColumnIndex(nickname, 4);
        nickname.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));

        nicknameError = new Text();
        GridPane.setRowIndex(nicknameError, 2);
        GridPane.setColumnIndex(nicknameError, 4);
        nicknameError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));

        signupPane.getChildren().addAll(nickname, nicknameError);
    }

    private void makeEmailTextField() {
        email = new TextField();
        GridPane.setRowIndex(email, 3);
        GridPane.setColumnIndex(email, 1);
        email.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));

        emailError = new Text();
        GridPane.setRowIndex(emailError, 4);
        GridPane.setColumnIndex(emailError, 1);
        emailError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));

        signupPane.getChildren().addAll(email, emailError);
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

        sloganError = new Text();
        GridPane.setRowIndex(sloganError, 4);
        GridPane.setColumnIndex(sloganError, 4);
        sloganError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));

        signupPane.getChildren().addAll(slogan, sloganError);
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

        passwordError = new Text();
        GridPane.setRowIndex(passwordError, 6);
        GridPane.setColumnIndex(passwordError, 1);
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

        validatePassword();
        password.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        passwordTextField.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        showPassword.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        passwordError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        signupPane.getChildren().addAll(password, passwordTextField, showPassword, passwordError);
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

        confirmPasswordError = new Text();
        GridPane.setRowIndex(confirmPasswordError, 8);
        GridPane.setColumnIndex(confirmPasswordError, 1);
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

        validateConfirmPassword();
        confirmPassword.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        confirmPasswordTextField.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        showConfirmPassword.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        confirmPasswordError.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        signupPane.getChildren().addAll(confirmPassword, confirmPasswordTextField, showConfirmPassword, confirmPasswordError);
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

    private void makeSignupButton() {
        signup = new Button("Signup");
        GridPane.setRowIndex(signup, 10);
        GridPane.setColumnIndex(signup, 0);
        GridPane.setColumnSpan(signup, 5);
        GridPane.setHalignment(signup, HPos.CENTER);
        signup.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        signup.setOnAction(actionEvent -> signup());
        signupPane.getChildren().add(signup);
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
                } else {
                    usernameError.setFill(Color.GREEN);
                    usernameError.setText("valid username");
                }
            }
        });
    }

    private void validatePassword() {
        password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                passwordError.setText("");
                passwordTextField.setText(newValue);
                if (newValue == "") return;
                boolean smallLetter = Pattern.compile("[a-z]").matcher(newValue).find();
                boolean capitalLetter = Pattern.compile("[A-Z]").matcher(newValue).find();
                boolean digit = Pattern.compile("[0-9]").matcher(newValue).find();
                boolean specialCharacter = Pattern.compile("[^a-zA-Z0-9]").matcher(newValue).find();
                if (newValue.contains(" ")) {
                    passwordError.setFill(Color.RED);
                    passwordError.setText("you can't user space!");
                } else if (newValue.length() < 6) {
                    passwordError.setFill(Color.RED);
                    passwordError.setText("password is too short!");
                } else if (!smallLetter || !capitalLetter || !digit || !specialCharacter) {
                    passwordError.setFill(Color.RED);
                    passwordError.setText("weak password!");
                } else {
                    passwordError.setFill(Color.GREEN);
                    passwordError.setText("strong password");
                }
            }
        });

        passwordTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                passwordError.setText("");
                password.setText(newValue);
                if (newValue == "") return;
                boolean smallLetter = Pattern.compile("[a-z]").matcher(newValue).find();
                boolean capitalLetter = Pattern.compile("[A-Z]").matcher(newValue).find();
                boolean digit = Pattern.compile("[0-9]").matcher(newValue).find();
                boolean specialCharacter = Pattern.compile("[^a-zA-Z0-9]").matcher(newValue).find();
                if (newValue.contains(" ")) {
                    passwordError.setFill(Color.RED);
                    passwordError.setText("you can't user space!");
                } else if (newValue.length() < 6) {
                    passwordError.setFill(Color.RED);
                    passwordError.setText("password is too short!");
                } else if (!smallLetter || !capitalLetter || !digit || !specialCharacter) {
                    passwordError.setFill(Color.RED);
                    passwordError.setText("weak password!");
                } else {
                    passwordError.setFill(Color.GREEN);
                    passwordError.setText("strong password");
                }
            }
        });
    }

    private void validateConfirmPassword() {
        confirmPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                confirmPasswordError.setText("");
                confirmPasswordTextField.setText(newValue);
                if (newValue == "") return;
                if (!newValue.equals(password.getText())) {
                    confirmPasswordError.setFill(Color.RED);
                    confirmPasswordError.setText("passwords don't match!");
                } else {
                    confirmPasswordError.setFill(Color.GREEN);
                    confirmPasswordError.setText("passwords match");
                }
            }
        });

        confirmPasswordTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                confirmPasswordError.setText("");
                confirmPassword.setText(newValue);
                if (newValue == "") return;
                if (!newValue.equals(password.getText())) {
                    confirmPasswordError.setFill(Color.RED);
                    confirmPasswordError.setText("passwords don't match!");
                } else {
                    confirmPasswordError.setFill(Color.GREEN);
                    confirmPasswordError.setText("passwords match");
                }
            }
        });
    }

    public void signup() {
        nicknameError.setText("");
        emailError.setText("");
        sloganError.setText("");
        if (!usernameError.getText().equals("") && usernameError.getFill().equals(Color.RED)) return;
        if (!passwordError.getText().equals("") && passwordError.getFill().equals(Color.RED)) return;
        if (!confirmPasswordError.getFill().equals("") && confirmPasswordError.getFill().equals(Color.RED)) return;
        if (validateEmptyFields()) return;

        boolean validEmail = Pattern.compile("[a-zA-Z\\d_\\.]+@[a-zA-Z\\d_\\.]+\\.[a-zA-Z\\d_\\.]+").matcher(email.getText()).matches();
        if (!validEmail) {
            emailError.setFill(Color.RED);
            emailError.setText("invalid email!");
            return;
        }
        if (controller.Application.isUserExistsByEmail(email.getText())) {
            emailError.setFill(Color.RED);
            emailError.setText("email already exists!");
            return;
        }

        String sloganValue = (isSlogan.isSelected()) ? (String) slogan.getValue() : null;
        User newUser = new User(username.getText(), password.getText(), nickname.getText(), email.getText(), sloganValue);
//        TODO: run security question menu
    }

    public boolean validateEmptyFields() {
        boolean emptyField = false;
        if (username.getText().isEmpty()) {
            emptyField = true;
            usernameError.setFill(Color.RED);
            usernameError.setText("username is required!");
        }
        if (nickname.getText().isEmpty()) {
            emptyField = true;
            nicknameError.setFill(Color.RED);
            nicknameError.setText("nickname is required!");
        }
        if (email.getText().isEmpty()) {
            emptyField = true;
            emailError.setFill(Color.RED);
            emailError.setText("email is required!");
        }
        if (isSlogan.isSelected() && slogan.getValue().equals("")) {
            emptyField = true;
            sloganError.setFill(Color.RED);
            sloganError.setText("slogan is required!");
        }
        if (password.getText().isEmpty()) {
            emptyField = true;
            passwordError.setFill(Color.RED);
            passwordError.setText("password is required!");
        }
        if (confirmPassword.getText().isEmpty()) {
            emptyField = true;
            confirmPasswordError.setFill(Color.RED);
            confirmPasswordError.setText("confirmation is required!");
        }
        return emptyField;
    }
}
