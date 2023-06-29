package view.menus.chat;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ChatController {
    
    public static void setChatMenu(String mode, ChatMenu chat) {
        chat.getChatPart().getChildren().clear();
        setMainMenu();
        switch (mode) {

        }
    }

    private static void setMainMenu() {
        StackPane publicChatIcon = new StackPane();
        Text publicChat = new Text("Public");
        publicChat.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
        publicChatIcon.getChildren().add(publicChat);

        StackPane roomIcon = new StackPane();
        Text room = new Text("Room");
        publicChat.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
        roomIcon.getChildren().add(publicChat);

        StackPane privateChatIcon = new StackPane();
        Text privateChat = new Text("Private");
        publicChat.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
        privateChatIcon.getChildren().add(publicChat);

//        chat
    }
}
