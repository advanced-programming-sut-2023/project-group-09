package view.menus.chat;

import client.Packet;
import com.google.gson.Gson;
import controller.network.UsersController;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.User;
import model.chat.Room;
import view.Main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatViewController {
    public ChatMenu chat;
    public User currentUser;
    private HashMap<String, StackPane> tabs = new HashMap<>();
    private String currentTabName;
    private TextField typeBox;
    private TextField searchBar;
    private VBox list;
    HashMap<String, String> users = new HashMap<>();
    ArrayList<Room> rooms = new ArrayList<>();

    public ChatViewController(ChatMenu chat) {
        this.chat = chat;
        Packet dataRequest = new Packet("chat data");
        try {
            dataRequest.setToken(Main.connection.getToken());
            dataRequest.sendPacket();
            Packet response = Packet.receivePacket();
            currentUser = new Gson().fromJson((String) response.getAttribute("currentUser"), User.class);
            users = new Gson().fromJson((String) response.getAttribute("otherUsers"), HashMap.class);
            rooms = new Gson().fromJson((String) response.getAttribute("currentUserRooms"), ArrayList.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        currentUser = new User("Amirhossein", "Amir.123", "amir", "amir@gmail.com", "");
//        users.put("Farzam", "files/img/avatars/1.png");
//        users.put("Mitra", "files/img/avatars/2.png");
//        users.put("Mahdi", "files/img/avatars/3.png");
//        rooms.add(new Room("APkadeh", currentUser));
//        rooms.add(new Room("Oji", currentUser));
//        rooms.add(new Room("Obash", currentUser));
//        rooms.add(new Room("CE SUT", currentUser));
    }

    public void setChatMenu(String mode) {
        chat.getChatPart().getChildren().clear();
        switch (mode) {
            case "public" -> setPublicChatMenu();
            case "room" -> setRoomChatMenu();
            case "private" -> setPrivateChatMenu();
        }
    }

    private void setPublicChatMenu() {
        addTabs();
        addTypeBox();
        currentTabName = "public";
        selectTab("public");
    }

    private void setRoomChatMenu() {
        addTabs();

        currentTabName = "room";
        selectTab("room");
    }

    private void setPrivateChatMenu() {
        addTabs();
        addSearchBar();
        currentTabName = "private";
        selectTab("private");
        addListBox();
        try {
            showListOfUsers(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addTabs() {
        tabs.put("public", createTab("Public", -400.0 / 3, -371));
        tabs.put("room", createTab("Room", 0, -371));
        tabs.put("private", createTab("Private", 400.0 / 3, -371));
        chat.getChatPart().getChildren().addAll(tabs.values());
    }

    private StackPane createTab(String title, double x, double y) {
        StackPane tab = new StackPane();
        tab.setMinWidth(400 / 3);
        tab.setMaxWidth(400 / 3);
        tab.setMinHeight(50);
        tab.setMaxHeight(50);
        tab.setTranslateX(x);
        tab.setTranslateY(y);
        tab.setStyle("-fx-background-radius: 20");
        Text tabText = new Text(title);
        tabText.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
        tab.getChildren().add(tabText);
        tab.setOnMouseReleased(mouseEvent -> {
            setChatMenu(title.toLowerCase());
        });
        return tab;
    }

    private void selectTab(String tabName) {
        for (String name : tabs.keySet()) {
            if (name.equals(tabName))
                tabs.get(name).setStyle("-fx-background-color: rgba(127, 181, 240, 0.4); -fx-background-radius: 20");
            else tabs.get(name).setStyle("-fx-background-color: transparent");
        }
    }

    private void addTypeBox() {
        typeBox = new TextField();
        typeBox.setStyle("-fx-background-radius: 20; -fx-background-color: white; -fx-font-size: 15;" +
                "-fx-border-radius: 20; -fx-border-color: black; -fx-border-width: 0.5");
        typeBox.setMinWidth(394);
        typeBox.setMaxWidth(394);
        typeBox.setMinHeight(50);
        typeBox.setMaxHeight(50);
        typeBox.setTranslateY(369);
        typeBox.setPromptText("Message");
        chat.getChatPart().getChildren().add(typeBox);
    }

    private void addSearchBar() {
        searchBar = new TextField();
        searchBar.setStyle("-fx-background-radius: 20; -fx-background-color: white; -fx-font-size: 15;" +
                "-fx-border-radius: 20; -fx-border-color: black; -fx-border-width: 0.5");
        searchBar.setMinWidth(380);
        searchBar.setMaxWidth(380);
        searchBar.setMinHeight(35);
        searchBar.setMaxHeight(35);
        searchBar.setTranslateY(-320);
        searchBar.setPromptText("Search");
        chat.getChatPart().getChildren().add(searchBar);
    }

    private void addListBox() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(650);
        scrollPane.setPrefViewportWidth(396);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent");
        list = new VBox();
        list.setMinWidth(396);
        list.setMaxWidth(396);
        list.setMinHeight(650);
        list.setMaxHeight(650);
        list.setTranslateY(50);
        scrollPane.setContent(list);
        chat.getChatPart().getChildren().add(scrollPane);
    }

    private void showListOfUsers(HashMap<String, String> users) throws IOException {
        int count = 0;
        for (String username : users.keySet()) {
            StackPane listItem = new StackPane();
            listItem.setMinWidth(400);
            listItem.setMaxWidth(400);
            listItem.setMinHeight(50);
            listItem.setMaxHeight(50);
//            listItem.setTranslateY(y);

            ImageView avatar = new ImageView(new Image(new ByteArrayInputStream(UsersController
                    .getImageFromServerByUsername(username).toByteArray())));
            System.out.println(avatar.getImage().getWidth());
            System.out.println(avatar.getImage().getHeight());
            avatar.setTranslateX(0);

            Text usernameText = new Text(username);
            usernameText.setTranslateX(0);
            usernameText.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 18));
            listItem.getChildren().addAll(avatar, usernameText);
        }
    }
}
