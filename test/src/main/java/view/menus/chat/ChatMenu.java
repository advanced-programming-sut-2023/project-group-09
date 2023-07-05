package view.menus.chat;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private StackPane reactPart;
    private ImageView back;

    public ChatMenu() {
        this.setWidth(1200);
        this.setHeight(800);
        this.setStyle("-fx-background-color: rgba(256, 256, 256, 0.7)");
        createChatPart();
        createOptionsPart();
        createReactPart();
        creatBackButton();
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
        this.optionsPart.setMinWidth(800 / 3);
        this.optionsPart.setMaxWidth(800 / 3);
        this.optionsPart.setMinHeight(40);
        this.optionsPart.setMaxHeight(40);
        this.optionsPart.setTranslateX(527);
        this.optionsPart.setTranslateY(712);
        this.optionsPart.setStyle("-fx-background-color: rgb(218, 247, 244); -fx-border-width: 1;" +
                "-fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10");
    }

    private void createReactPart() {
        this.reactPart = new StackPane();
        this.reactPart.setMinWidth(800 / 3);
        this.reactPart.setMaxWidth(800 / 3);
        this.reactPart.setMinHeight(40);
        this.reactPart.setMaxHeight(40);
        this.reactPart.setTranslateX(527);
        this.reactPart.setTranslateY(757);
        this.reactPart.setStyle("-fx-background-color: rgb(218, 247, 244); -fx-border-width: 1;" +
                "-fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10");
    }

    private void creatBackButton() {
        back = new ImageView(new Image(ChatMenu.class.getResource("/images/icons/chatBack.png").toExternalForm()));
        back.setTranslateX(10);
        back.setTranslateY(10);
        back.setFitWidth(50);
        back.setFitHeight(50);
        back.setOnMouseEntered(mouseEvent -> {
            back.setImage(new Image(ChatMenu.class.getResource("/images/icons/chatBackHovered.png").toExternalForm()));
        });
        back.setOnMouseExited(mouseEvent -> {
            back.setImage(new Image(ChatMenu.class.getResource("/images/icons/chatBack.png").toExternalForm()));
        });
        back.setOnMouseClicked(mouseEvent -> {
            GameMenu.root.getChildren().remove(GameMenu.chatMenu);
            GameMenu.chatMenu = null;
        });
        this.getChildren().add(back);
    }

    public StackPane getChatPart() {
        return chatPart;
    }

    public StackPane getOptionsPart() {
        return optionsPart;
    }

    public StackPane getReactPart() {
        return reactPart;
    }
}
