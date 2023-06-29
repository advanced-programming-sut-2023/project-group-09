package model.menugui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MenuPasswordField extends PasswordField {
    private Pane pane;
    private String promptText;
    private String labelText;
    public Label errorLabel;
    private MenuTextField passwordTextField;
    private CheckBox showPassword;

    public MenuPasswordField(Pane pane, String promptText, String labelText, double x, double y) {
        this.promptText = promptText;
        this.labelText = labelText;
        this.setStyle("-fx-background-color: rgba(42 , 42 , 42 , 0.7); -fx-text-inner-color: white;");
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setMaxWidth(300);
        this.setMinWidth(300);
        this.pane = pane;
        createLabel(labelText);
        this.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        this.setPromptText(promptText);
        this.createErrorOrMessage();
        this.createTextField();
        this.createShowPassword();
        updateFieldValues();
    }

    private void createTextField() {
        passwordTextField = new MenuTextField(pane, promptText, labelText, this.getTranslateX(), this.getTranslateY(), 300);
        passwordTextField.setVisible(false);
        pane.getChildren().add(passwordTextField);
    }

    private void createShowPassword() {
        showPassword = new CheckBox("\uD83D\uDC41");
        showPassword.setStyle("-fx-background-color: rgba(42, 42, 42, 0.7); -fx-text-fill: gray; -fx-font-size: 16; " +
                "-fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-padding: 3; -fx-background-radius: 3");
        showPassword.setTranslateX(this.getTranslateX() + 185);
        showPassword.setTranslateY(this.getTranslateY());
        showPassword.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    setVisible(false);
                    passwordTextField.setVisible(true);
                } else {
                    setVisible(true);
                    passwordTextField.setVisible(false);
                }
            }
        });
        pane.getChildren().add(showPassword);
    }

    public void createLabel(String text) {
        Label label = new Label(text);
        label.setTranslateX(this.getTranslateX() - 300);
        label.setTranslateY(this.getTranslateY());
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        label.setTextFill(Color.BLACK);
        label.setMaxWidth(270);
        label.setAlignment(Pos.BASELINE_RIGHT);
        pane.getChildren().add(label);
    }

    private void createErrorOrMessage() {
        errorLabel = new Label();
        errorLabel.setTranslateX(this.getTranslateX());
        errorLabel.setTranslateY(this.getTranslateY() + 25);
        errorLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
        pane.getChildren().add(errorLabel);
    }

    public void handlingError(String errorText) {
        errorLabel.setTextFill(new Color(0.6, 0, 0.1, 1));
        errorLabel.setText(errorText);
    }

    public void handlingCorrect(String correctMessage) {
        errorLabel.setTextFill(new Color(0.2, 0.9, 0.2, 1));
        errorLabel.setText(correctMessage);
    }

    private void updateFieldValues() {
        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                passwordTextField.setText(newValue);
            }
        });

        passwordTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                setText(newValue);
            }
        });
    }

    public void clearErrorOrMessage() {
        errorLabel.setText("");
    }

    public MenuTextField getPasswordTextField() {
        return passwordTextField;
    }

    public void setPasswordTextField(MenuTextField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    public CheckBox getShowPassword() {
        return showPassword;
    }

    public void setShowPassword(CheckBox showPassword) {
        this.showPassword = showPassword;
    }
}
