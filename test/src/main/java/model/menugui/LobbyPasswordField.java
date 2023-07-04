package model.menugui;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class LobbyPasswordField extends PasswordField {
    private Pane pane;
    private String promptText;
    private String labelText;
    public Label label;
    private TextField passwordTextField;
    private CheckBox showPassword;

    public LobbyPasswordField(Pane pane, String promptText, String labelText, double x, double y) {
        this.promptText = promptText;
        this.labelText = labelText;
        this.getStyleClass().add("game-name");
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.pane = pane;
        label = new Label(labelText);
        label.setTranslateX(x - 100);
        label.setTranslateY(y + 10);
        pane.getChildren().add(label);
        this.setPromptText(promptText);
        this.createTextField();
        this.createShowPassword();
        updateFieldValues();
    }

    private void createTextField() {
        passwordTextField = new TextField();
        passwordTextField.getStyleClass().add("game-name");
        passwordTextField.setTranslateX(this.getTranslateX());
        passwordTextField.setTranslateY(this.getTranslateY());
        passwordTextField.setPromptText(promptText);
        passwordTextField.setVisible(false);
        pane.getChildren().add(passwordTextField);
    }

    private void createShowPassword() {
        showPassword = new CheckBox("\uD83D\uDC41");
        showPassword.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-size: 16; " +
                "-fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-padding: 3; -fx-background-radius: 3");
        showPassword.setTranslateX(this.getTranslateX() + 185);
        showPassword.setTranslateY(this.getTranslateY() + 10);
        showPassword.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) {
                setVisible(false);
                passwordTextField.setVisible(true);
            } else {
                setVisible(true);
                passwordTextField.setVisible(false);
            }
        });
        pane.getChildren().add(showPassword);
    }




    private void updateFieldValues() {
        this.textProperty().addListener((observableValue, oldValue, newValue) -> passwordTextField.setText(newValue));

        passwordTextField.textProperty().addListener((observableValue, oldValue, newValue) -> setText(newValue));
    }


    public String getValue(){
        if (passwordTextField.isVisible()){
            return passwordTextField.getText();
        }else{
            return this.getText();
        }
    }

    public void removeFromPane(){
        pane.getChildren().remove(passwordTextField);
        pane.getChildren().remove(showPassword);
        pane.getChildren().remove(this);
        pane.getChildren().remove(label);
    }
}
