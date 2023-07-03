package view.menus.chat;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.menus.GameMenu;

import java.io.IOException;

public class ChatMenu extends Pane {
    private StackPane chatPart;
    private StackPane optionsPart;

    public ChatMenu() {
        this.setWidth(1200);
        this.setHeight(800);
        this.setStyle("-fx-background-color: rgba(256, 256, 256, 0.7)");
        createChatPart();
        createOptionsPart();
        this.getChildren().add(this.chatPart);
        this.setViewOrder(-10000);
        new ChatViewController(this).setChatMenu("public");
    }

    private void createChatPart() {
        try {
            this.chatPart = FXMLLoader.load(GameMenu.class.getResource("/FXML/chatPart.fxml"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.chatPart.setMinWidth(400);
        this.chatPart.setMaxWidth(400);
        this.chatPart.setMinHeight(794);
        this.chatPart.setMaxHeight(794);
        this.chatPart.setTranslateX(797);
        this.chatPart.setTranslateY(3);
        this.chatPart.setStyle("-fx-background-color: rgb(198, 245, 239); -fx-border-width: 1.5;" +
                "-fx-border-color: black; -fx-border-radius: 20; -fx-background-radius: 20");
    }

    private void createOptionsPart() {
        this.optionsPart = new StackPane();
        this.optionsPart.setMinWidth(400);
        this.optionsPart.setMaxWidth(400);
        this.optionsPart.setMinHeight(40);
        this.optionsPart.setMaxHeight(40);
        this.optionsPart.setTranslateX(394);
        this.optionsPart.setTranslateY(757);
        this.optionsPart.setStyle("-fx-background-color: rgb(218, 247, 244); -fx-border-width: 1;" +
                "-fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10");
    }

    public StackPane getChatPart() {
        return chatPart;
    }

    public void setChatPart(StackPane chatPart) {
        this.chatPart = chatPart;
    }

    public StackPane getOptionsPart() {
        return optionsPart;
    }

    public void setOptionsPart(StackPane optionsPart) {
        this.optionsPart = optionsPart;
    }
}
