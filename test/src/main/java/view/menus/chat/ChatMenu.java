package view.menus.chat;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ChatMenu extends Pane {
    private StackPane chatPart;

    public ChatMenu() {
        this.setWidth(1200);
        this.setHeight(800);
        this.setStyle("-fx-background-color: rgba(256, 256, 256, 0.5)");
        this.chatPart = new StackPane();
        this.chatPart.setMinWidth(400);
        this.chatPart.setMaxWidth(400);
        this.chatPart.setMinHeight(794);
        this.chatPart.setMaxHeight(794);
        this.chatPart.setTranslateX(797);
        this.chatPart.setTranslateY(3);
        this.chatPart.setStyle("-fx-background-color: rgba(256, 256, 256, 1); -fx-border-width: 1.5;" +
                "-fx-border-color: black; -fx-border-radius: 20; -fx-background-radius: 20");
        this.getChildren().add(this.chatPart);
        this.setViewOrder(-10000);
        ChatController.setChatMenu("", this);
    }

    public StackPane getChatPart() {
        return chatPart;
    }

    public void setChatPart(StackPane chatPart) {
        this.chatPart = chatPart;
    }
}
