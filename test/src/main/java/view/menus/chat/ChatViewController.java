package view.menus.chat;

import client.Packet;
import client.PacketOnlineReceiver;
import controller.GameController;
import controller.network.UsersController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.User;
import model.chat.Message;
import view.Main;
import view.menus.GameMenu;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ChatViewController {
    public static ChatMenu chat;
    public static User currentUser;
    public static HashMap<String, StackPane> tabs = new HashMap<>();
    public static String currentTabName;
    public static String currentRoomName;
    public static TextField typeBox;
    public static ImageView sendMessage;
    public static TextField searchBar;
    public static ImageView addRoom;
    public static ImageView newMember;
    public static ImageView confirmEdit;
    public static TextField newRoomBox;
    public static TextArea editingMessageBox;
    public static ScrollPane scrollPane;
    public static GridPane list;
    public static ArrayList<String> users = new ArrayList<>();
    public static LinkedHashMap<String, String> rooms = new LinkedHashMap<>();
    public static LinkedHashMap<String, String> privates = new LinkedHashMap<>();
    public static ArrayList<Message> messages = new ArrayList<>();
    public static ArrayList<String> usersForRoom = new ArrayList<>();

    public ChatViewController(ChatMenu chat) {
        ChatViewController.chat = chat;
        Packet dataRequest = new Packet("chat data");
        try {
            dataRequest.setToken(Main.connection.getToken());
            dataRequest.sendPacket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setChatMenu(String mode) {
        chat.getChatPart().getChildren().clear();
        switch (mode) {
            case "public" -> setPublicChatMenu();
            case "room" -> setRoomChatMenu();
            case "private" -> setPrivateChatMenu();
        }
    }

    public static void setPublicChatMenu() {
        addTabs();
        currentTabName = "public";
        selectTab("public");
        addTypeBox();
        try {
            Packet packet = new Packet("get messages - public");
            packet.setToken(Main.connection.getToken());
            packet.sendPacket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setRoomChatMenu() {
        addTabs();
        currentTabName = "room";
        selectTab("room");
        addNewRoomBox();
        addListBox(0);
        addSearchBar(true);
        try {
            showListOfRooms(rooms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPrivateChatMenu() {
        addTabs();
        currentTabName = "private";
        selectTab("private");
        addListBox(1);
        addSearchBar(false);
        try {
            showListOfUsers(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTabs() {
        tabs.put("public", createTab("Public", -400.0 / 3, -371));
        tabs.put("room", createTab("Room", 0, -371));
        tabs.put("private", createTab("Private", 400.0 / 3, -371));
        chat.getChatPart().getChildren().addAll(tabs.values());
    }

    public static StackPane createTab(String title, double x, double y) {
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

    public static void selectTab(String tabName) {
        for (String name : tabs.keySet()) {
            if (name.equals(tabName))
                tabs.get(name).setStyle("-fx-background-color: rgba(127, 181, 240, 0.4); -fx-background-radius: 20");
            else tabs.get(name).setStyle("-fx-background-color: transparent");
        }
    }

    public static void addTypeBox() {
        typeBox = new TextField();
        typeBox.setStyle("-fx-background-radius: 20; -fx-background-color: white; -fx-font-size: 15;" +
                "-fx-border-radius: 20; -fx-border-color: black; -fx-border-width: 0.5");
        typeBox.setMinWidth(334);
        typeBox.setMaxWidth(334);
        typeBox.setMinHeight(50);
        typeBox.setMaxHeight(50);
        typeBox.setTranslateX(-30);
        typeBox.setTranslateY(369);
        typeBox.setPromptText("Message");

        sendMessage = new ImageView(new Image(ChatViewController.class.getResource("/images/icons/send.png").toExternalForm()));
        sendMessage.setOnMouseEntered(mouseEvent -> {
            sendMessage.setImage(new Image(ChatViewController.class.getResource("/images/icons/sendHovered.png").toExternalForm()));
        });
        sendMessage.setOnMouseExited(mouseEvent -> {
            sendMessage.setImage(new Image(ChatViewController.class.getResource("/images/icons/send.png").toExternalForm()));
        });
        sendMessage.setOnMouseClicked(mouseEvent -> {
            if (typeBox.getText().isEmpty() || typeBox.getText() == null) return;
            setSendAction();
        });
        typeBox.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode().getName().equals("Enter")) {
                if (typeBox.getText().isEmpty() || typeBox.getText() == null) return;
                setSendAction();
            }
        });
        sendMessage.setFitWidth(50);
        sendMessage.setFitHeight(50);
        sendMessage.setTranslateX(167);
        sendMessage.setTranslateY(367);

        chat.getChatPart().getChildren().addAll(typeBox, sendMessage);
    }

    public static void addSearchBar(boolean isInRoom) {
        searchBar = new TextField();
        searchBar.setStyle("-fx-background-radius: 20; -fx-background-color: white; -fx-font-size: 15;" +
                "-fx-border-radius: 20; -fx-border-color: black; -fx-border-width: 0.5");
        searchBar.setMinWidth(380);
        searchBar.setMaxWidth(380);
        searchBar.setMinHeight(35);
        searchBar.setMaxHeight(35);
        searchBar.setTranslateY(-320);
        searchBar.setPromptText("Search");
        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                try {
                    if (isInRoom) {
                        if (chat.getChatPart().getChildren().contains(newRoomBox)) {
                            if (newValue.isEmpty() || newValue == null)
                                showListOfRooms(rooms);
                            else {
                                LinkedHashMap<String, String> filteredRooms = new LinkedHashMap<>();
                                for (String roomName : rooms.keySet())
                                    if (roomName.toLowerCase().contains(newValue.toLowerCase()))
                                        filteredRooms.put(roomName, rooms.get(roomName));
                                showListOfRooms(filteredRooms);
                            }
                        } else {
                            if (newValue.isEmpty() || newValue == null)
                                showListOfUsersForRoom(usersForRoom);
                            else {
                                ArrayList<String> filteredUsersForRoom = new ArrayList<>();
                                for (String username : usersForRoom)
                                    if (username.toLowerCase().contains(newValue.toLowerCase()))
                                        filteredUsersForRoom.add(username);
                                showListOfUsersForRoom(filteredUsersForRoom);
                            }
                        }
                    } else {
                        if (newValue.isEmpty() || newValue == null)
                            showListOfUsers(users);
                        else {
                            ArrayList<String> filteredUsers = new ArrayList<>();
                            for (String username : users)
                                if (username.toLowerCase().contains(newValue.toLowerCase()))
                                    filteredUsers.add(username);
                            showListOfUsers(filteredUsers);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        chat.getChatPart().getChildren().add(searchBar);
    }

    public static void addListBox(int state) {
//        state: 0=roomsList - 1=usersList - 2=chatMessages - 3=roomMessages
        scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(400);
        scrollPane.setPrefViewportWidth(396);
        if (state == 0) {
            scrollPane.setMaxHeight(630);
            scrollPane.setTranslateY(65);
        } else if (state == 1) {
            scrollPane.setMaxHeight(680);
            scrollPane.setTranslateY(40);
        } else if (state == 2) {
            scrollPane.setMaxHeight(680);
            scrollPane.setVvalue(1);
        } else if (state == 3) {
            scrollPane.setMaxHeight(630);
            scrollPane.setTranslateY(25);
            scrollPane.setVvalue(1);
        }
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent");
        list = new GridPane();
        list.setHgap(20);
        scrollPane.setContent(list);
    }

    public static void showListOfUsers(ArrayList<String> users) throws IOException {
        list.getChildren().clear();
        chat.getChatPart().getChildren().remove(scrollPane);
        for (int i = 0; i < users.size(); i++) {
            String username = users.get(i);
            StackPane listItem = new StackPane();
            listItem.setMinWidth(400);
            listItem.setMaxWidth(400);
            listItem.setMinHeight(60);
            listItem.setMaxHeight(60);
            listItem.setStyle("-fx-background-color: rgb(250, 250, 250)");
            listItem.setOnMouseEntered(mouseEvent -> {
                listItem.setStyle("-fx-background-color: rgb(230, 230, 230)");
            });
            listItem.setOnMouseExited(mouseEvent -> {
                listItem.setStyle("-fx-background-color: rgb(250, 250, 250)");
            });
            listItem.setOnMouseClicked(mouseEvent -> {
                addTypeBox();
                try {
                    Packet packet = new Packet("get messages - private");
                    packet.setToken(Main.connection.getToken());
                    packet.addAttribute("roomId", privates.get(username));
                    packet.addAttribute("receiver", username);
                    packet.sendPacket();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            Circle avatarCircle = new Circle(30);
            avatarCircle.setTranslateX(30);
            avatarCircle.setTranslateY(30);
            ImageView avatar = new ImageView(new Image(new ByteArrayInputStream(UsersController
                    .getImageFromServerByUsername(username, true).toByteArray())));
            GameMenu.packetOnlineReceiver.resumeThread();
            avatar.setClip(avatarCircle);
            avatar.setFitWidth(60);
            avatar.setFitHeight(60);
            avatar.setTranslateX(-170);

            Text usernameText = new Text(username);
            usernameText.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
            StackPane.setAlignment(usernameText, Pos.CENTER_LEFT);
            usernameText.setTranslateX(75);

            listItem.getChildren().addAll(avatar, usernameText);
            list.add(listItem, 0, i);
        }
        chat.getChatPart().getChildren().add(scrollPane);
    }

    public static void addNewRoomBox() {
        newRoomBox = new TextField();
        newRoomBox.setStyle("-fx-background-radius: 20; -fx-background-color: white; -fx-font-size: 15;" +
                "-fx-border-radius: 20; -fx-border-color: black; -fx-border-width: 0.5");
        newRoomBox.setMinWidth(330);
        newRoomBox.setMaxWidth(330);
        newRoomBox.setMinHeight(35);
        newRoomBox.setMaxHeight(35);
        newRoomBox.setTranslateX(-25);
        newRoomBox.setTranslateY(-270);
        newRoomBox.setPromptText("New Room");

        addRoom = new ImageView(new Image(ChatViewController.class.getResource("/images/icons/plus.png").toExternalForm()));
        addRoom.setOnMouseEntered(mouseEvent -> {
            addRoom.setImage(new Image(ChatViewController.class.getResource("/images/icons/plusHovered.png").toExternalForm()));
        });
        addRoom.setOnMouseExited(mouseEvent -> {
            addRoom.setImage(new Image(ChatViewController.class.getResource("/images/icons/plus.png").toExternalForm()));
        });
        addRoom.setOnMouseClicked(mouseEvent -> {
            if (newRoomBox.getText().isEmpty() || newRoomBox.getText() == null) return;
            try {
                Packet packet = new Packet("new room");
                packet.setToken(Main.connection.getToken());
                packet.addAttribute("roomName", newRoomBox.getText());
                packet.sendPacket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        addRoom.setFitWidth(35);
        addRoom.setFitHeight(35);
        addRoom.setTranslateX(167);
        addRoom.setTranslateY(-270);

        chat.getChatPart().getChildren().addAll(newRoomBox, addRoom);
    }

    public static void showListOfRooms(LinkedHashMap<String, String> rooms) throws IOException {
        list.getChildren().clear();
        chat.getChatPart().getChildren().remove(scrollPane);
        int count = 0;
        for (String roomName : rooms.keySet()) {
            StackPane listItem = new StackPane();
            listItem.setMinWidth(400);
            listItem.setMaxWidth(400);
            listItem.setMinHeight(60);
            listItem.setMaxHeight(60);
            listItem.setStyle("-fx-background-color: rgb(250, 250, 250)");
            listItem.setOnMouseEntered(mouseEvent -> {
                listItem.setStyle("-fx-background-color: rgb(230, 230, 230)");
            });
            listItem.setOnMouseExited(mouseEvent -> {
                listItem.setStyle("-fx-background-color: rgb(250, 250, 250)");
            });
            listItem.setOnMouseClicked(mouseEvent -> {
                addTypeBox();
                try {
                    Packet packet = new Packet("get messages - room");
                    packet.setToken(Main.connection.getToken());
                    packet.addAttribute("roomId", rooms.get(roomName));
                    packet.sendPacket();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            Text usernameText = new Text(roomName);
            usernameText.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
            StackPane.setAlignment(usernameText, Pos.CENTER_LEFT);
            usernameText.setTranslateX(15);

            listItem.getChildren().addAll(usernameText);
            list.add(listItem, 0, count++);
        }
        chat.getChatPart().getChildren().add(scrollPane);
    }

    public static void setSendAction() {
        if (currentTabName.equals("public")) {
            Packet sendMessageInPublic = new Packet("send message - public");
            sendMessageInPublic.setToken(Main.connection.getToken());
            sendMessageInPublic.addAttribute("message", typeBox.getText());
            try {
                sendMessageInPublic.sendPacket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (currentTabName.equals("private")) {
            Packet sendMessageInPrivate = new Packet("send message - private");
            sendMessageInPrivate.setToken(Main.connection.getToken());
            sendMessageInPrivate.addAttribute("message", typeBox.getText());
            sendMessageInPrivate.addAttribute("roomId", privates.get(currentRoomName));
            try {
                sendMessageInPrivate.sendPacket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (currentTabName.equals("room")) {
            Packet sendMessageInRoom = new Packet("send message - room");
            sendMessageInRoom.setToken(Main.connection.getToken());
            sendMessageInRoom.addAttribute("message", typeBox.getText());
            sendMessageInRoom.addAttribute("roomId", rooms.get(currentRoomName));
            try {
                sendMessageInRoom.sendPacket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void addLeftMessage(Message message, int count) throws IOException {
        StackPane messagePane = new StackPane();
        messagePane.setMinWidth(400);
        messagePane.setMaxWidth(400);

        Circle avatarCircle = new Circle(20);
        avatarCircle.setTranslateX(20);
        avatarCircle.setTranslateY(20);
        ImageView avatar = new ImageView(new Image(new ByteArrayInputStream(UsersController
                .getImageFromServerByUsername(message.getSender(), true).toByteArray())));
        GameMenu.packetOnlineReceiver.resumeThread();
        avatar.setClip(avatarCircle);
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);
        avatar.setTranslateX(-175);

        TextArea messageBox = new TextArea(message.getSender() + ":\n" + message.getData());
        messageBox.setMaxWidth(250);
        messageBox.setPrefColumnCount(1);
        if (message.getLikeCount() == 0 && message.getDislikeCount() == 0 &&
                message.getFireCount() == 0 && message.getShitCount() == 0) messageBox.setPrefRowCount(2);
        else messageBox.setPrefRowCount(3);
        messageBox.setTranslateX(-20);
        messageBox.setWrapText(true);
        messageBox.setEditable(false);
        messageBox.getStyleClass().add("other-side");

        Text sentTime = new Text(message.getSentTime());
        sentTime.setTranslateX(130);
        sentTime.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 12));

        messagePane.setOnMouseClicked(mouseEvent -> {
            ChatViewController.chat.getChildren().add(ChatViewController.chat.getReactPart());
            addEmojis(message);
        });

        addAllEmojis(message, messagePane, false);

        messagePane.getChildren().addAll(messageBox, sentTime, avatar);
        list.add(messagePane, 0, count);
    }

    public static void addRightMessage(Message message, int count) throws IOException {
        StackPane messagePane = new StackPane();
        messagePane.setMinWidth(400);
        messagePane.setMaxWidth(400);

        Circle avatarCircle = new Circle(20);
        avatarCircle.setTranslateX(20);
        avatarCircle.setTranslateY(20);
        ImageView avatar = new ImageView(new Image(new ByteArrayInputStream(UsersController
                .getImageFromServerByUsername(message.getSender(), true).toByteArray())));
        GameMenu.packetOnlineReceiver.resumeThread();
        avatar.setClip(avatarCircle);
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);
        avatar.setTranslateX(165);

        TextArea messageBox = new TextArea(message.getSender() + ":\n" + message.getData());
        messageBox.setMaxWidth(250);
        messageBox.setPrefColumnCount(1);
        boolean reacted = message.getLikeCount() != 0 || message.getDislikeCount() != 0 ||
                message.getFireCount() != 0 || message.getShitCount() != 0;
        if (!reacted) messageBox.setPrefRowCount(2);
        else messageBox.setPrefRowCount(3);
        messageBox.setTranslateX(15);
        messageBox.setWrapText(true);
        messageBox.setEditable(false);

        Text sentTime = new Text(message.getSentTime());
        sentTime.setTranslateX(-135);
        sentTime.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 12));

        ImageView state = new ImageView();
        state.setTranslateX(125);
        state.setFitWidth(14);
        if (!message.isSeen()) {
            state.setImage(new Image(ChatViewController.class.getResource("/images/icons/sent.png").toExternalForm()));
            state.setFitHeight(10);
        } else {
            state.setImage(new Image(ChatViewController.class.getResource("/images/icons/seen.png").toExternalForm()));
            state.setFitHeight(14);
        }
        if (!reacted) state.setTranslateY(20);
        else state.setTranslateY(30);

        messagePane.setOnMouseClicked(mouseEvent -> {
            ChatViewController.chat.getChildren().add(ChatViewController.chat.getOptionsPart());
            ChatViewController.chat.getChildren().add(ChatViewController.chat.getReactPart());
            addOptions(messageBox, message);
            addEmojis(message);
        });

        addAllEmojis(message, messagePane, true);

        messagePane.getChildren().addAll(messageBox, sentTime, state, avatar);
        list.add(messagePane, 0, count);
    }

    public static StackPane createEmoji(String emojiName, int count) {
        StackPane emojiPane = new StackPane();
        emojiPane.setMinWidth(50);
        emojiPane.setMaxWidth(50);
        emojiPane.setMinHeight(30);
        emojiPane.setMaxHeight(30);
        emojiPane.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-color: white;" +
                "-fx-border-color: black; -fx-border-width: 0.5");
        emojiPane.setViewOrder(-1000000000);

        ImageView emoji = new ImageView(new Image(ChatViewController.class.getResource("/images/icons/").toExternalForm() +
                emojiName + ".png"));
        emoji.setFitWidth(20);
        emoji.setFitHeight(20);
        emoji.setTranslateX(-7);

        Text countText = new Text(Integer.toString(count));
        countText.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        countText.setTranslateX(12);

        emojiPane.getChildren().addAll(emoji, countText);
        return emojiPane;
    }

    public static void addAllEmojis(Message message, StackPane messagePane, boolean isRight) {
        int indent = (isRight) ? -75 : -110;
        int count = 0;
        if (message.getLikeCount() != 0) {
            StackPane likePane = createEmoji("like", message.getLikeCount());
            likePane.setTranslateX(count * 55 + indent);
            likePane.setTranslateY(25);
            messagePane.getChildren().add(likePane);
            count++;
        }
        if (message.getDislikeCount() != 0) {
            StackPane dislikePane = createEmoji("dislike", message.getDislikeCount());
            dislikePane.setTranslateX(count * 55 + indent);
            dislikePane.setTranslateY(25);
            messagePane.getChildren().add(dislikePane);
            count++;
        }
        if (message.getFireCount() != 0) {
            StackPane firePane = createEmoji("fire", message.getFireCount());
            firePane.setTranslateX(count * 55 + indent);
            firePane.setTranslateY(25);
            messagePane.getChildren().add(firePane);
            count++;
        }
        if (message.getShitCount() != 0) {
            StackPane shitPane = createEmoji("shit", message.getShitCount());
            shitPane.setTranslateX(count * 55 + indent);
            shitPane.setTranslateY(25);
            messagePane.getChildren().add(shitPane);
            count++;
        }
    }

    public static void showMessages(ArrayList<Message> messages) {
        list.getChildren().clear();
        chat.getChatPart().getChildren().remove(scrollPane);
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message == null) continue;
            try {
                if (message.getSender().equals(currentUser.getUsername()))
                    addRightMessage(message, i);
                else addLeftMessage(message, i);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        chat.getChatPart().getChildren().add(scrollPane);
    }

    public static void addOptions(TextArea messageBox, Message message) {
        createOption("Delete for All", -200 / 3, messageBox, message);
        createOption("Edit", 200 / 3, messageBox, message);
    }

    public static void addEmojis(Message message) {
        createReactOption("like", -100, message);
        createReactOption("dislike", -100 / 3, message);
        createReactOption("fire", 100 / 3, message);
        createReactOption("shit", 100, message);
    }

    public static void createOption(String text, double x, TextArea messageBox, Message message) {
        StackPane option = new StackPane();
        option.setMinWidth(130);
        option.setMaxWidth(130);
        option.setMinHeight(38);
        option.setMaxHeight(38);
        option.setTranslateX(x);
        Text tabText = new Text(text);
        tabText.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        option.getChildren().add(tabText);
        option.setOnMouseEntered(mouseEvent -> {
            option.setStyle("-fx-background-color: rgb(3, 119, 252); -fx-background-radius: 10");
        });
        option.setOnMouseExited(mouseEvent -> {
            option.setStyle("-fx-background-color: transparent");
        });
        option.setOnMouseReleased(mouseEvent -> {
            if (text.equals("Edit")) enterEditMode(messageBox, message);
            else setOptionAction(text, message.getId(), null);
        });
        ChatViewController.chat.getOptionsPart().getChildren().add(option);
    }

    public static void createReactOption(String emojiName, double x, Message message) {
        StackPane reactOption = new StackPane();
        reactOption.setMinWidth(64);
        reactOption.setMaxWidth(64);
        reactOption.setMinHeight(38);
        reactOption.setMaxHeight(38);
        reactOption.setTranslateX(x);
        ImageView emoji = new ImageView(new Image(ChatViewController.class.getResource("/images/icons/").
                toExternalForm() + emojiName + ".png"));
        emoji.setFitWidth(30);
        emoji.setFitHeight(30);
        reactOption.getChildren().add(emoji);
        reactOption.setOnMouseEntered(mouseEvent -> {
            reactOption.setStyle("-fx-background-color: rgb(3, 119, 252); -fx-background-radius: 10");
        });
        reactOption.setOnMouseExited(mouseEvent -> {
            reactOption.setStyle("-fx-background-color: transparent");
        });
        reactOption.setOnMouseReleased(mouseEvent -> {
            if (message.getReactors() != null && message.getReactors().contains(currentUser.getUsername())) {
                ChatViewController.chat.getChildren().remove(ChatViewController.chat.getReactPart());
                ChatViewController.chat.getChildren().remove(ChatViewController.chat.getOptionsPart());
                return;
            }
            try {
                Packet react = new Packet("new react");
                react.setToken(Main.connection.getToken());
                react.addAttribute("messageId", message.getId());
                react.addAttribute("emoji", emojiName);
                react.sendPacket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            chat.getChildren().remove(chat.getOptionsPart());
            chat.getChildren().remove(chat.getReactPart());
        });
        ChatViewController.chat.getReactPart().getChildren().add(reactOption);
    }

    public static void setOptionAction(String action, String messageId, String newMessage) {
        switch (action) {
            case "Delete for All" -> {
                try {
                    Packet packet = new Packet("delete for all");
                    packet.setToken(Main.connection.getToken());
                    packet.addAttribute("messageId", messageId);
                    packet.sendPacket();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "Edit" -> {
                try {
                    Packet packet = new Packet("edit");
                    packet.setToken(Main.connection.getToken());
                    packet.addAttribute("messageId", messageId);
                    packet.addAttribute("newMessage", newMessage);
                    packet.sendPacket();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void enterEditMode(TextArea messageBox, Message message) {
        ChatViewController.chat.getChildren().remove(ChatViewController.chat.getOptionsPart());
        ChatViewController.chat.getChildren().remove(ChatViewController.chat.getReactPart());
        editingMessageBox = messageBox;
        editingMessageBox.setEditable(true);
        editingMessageBox.getStyleClass().add("edit-mode");
        confirmEdit = new ImageView(new Image(ChatViewController.class.getResource("/images/icons/check.png").toExternalForm()));
        confirmEdit.setFitWidth(50);
        confirmEdit.setFitHeight(50);
        confirmEdit.setTranslateX(740);
        confirmEdit.setTranslateY(747);
        confirmEdit.setOnMouseEntered(mouseEvent -> {
            confirmEdit.setImage(new Image(ChatViewController.class.getResource("/images/icons/checkHovered.png").toExternalForm()));
        });
        confirmEdit.setOnMouseExited(mouseEvent -> {
            confirmEdit.setImage(new Image(ChatViewController.class.getResource("/images/icons/check.png").toExternalForm()));
        });
        ChatViewController.chat.getChildren().add(confirmEdit);
        confirmEdit.setOnMouseClicked(mouseEvent -> {
            String newMessage = editingMessageBox.getText().substring(editingMessageBox.getText().indexOf("\n") + 1);
            setOptionAction("Edit", message.getId(), newMessage);
        });
    }

    public static void addNewMember() {
        newMember = new ImageView(new Image(ChatViewController.class.getResource("/images/icons/plus.png").toExternalForm()));
        newMember.setOnMouseEntered(mouseEvent -> {
            newMember.setImage(new Image(ChatViewController.class.getResource("/images/icons/plusHovered.png").toExternalForm()));
        });
        newMember.setOnMouseExited(mouseEvent -> {
            newMember.setImage(new Image(ChatViewController.class.getResource("/images/icons/plus.png").toExternalForm()));
        });
        newMember.setOnMouseClicked(mouseEvent -> {
            scrollPane.setContent(null);
            chat.getChatPart().getChildren().remove(scrollPane);
            chat.getChatPart().getChildren().remove(newMember);
            chat.getChatPart().getChildren().remove(typeBox);
            chat.getChatPart().getChildren().remove(sendMessage);
            addSearchBar(true);
            try {
                Packet packet = new Packet("users for room");
                packet.setToken(Main.connection.getToken());
                packet.addAttribute("roomId", rooms.get(currentRoomName));
                packet.sendPacket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        newMember.setFitWidth(35);
        newMember.setFitHeight(35);
        newMember.setTranslateX(167);
        newMember.setTranslateY(-320);

        chat.getChatPart().getChildren().add(newMember);
    }

    public static void showListOfUsersForRoom(ArrayList<String> usersForRoom) throws IOException {
        list.getChildren().clear();
        chat.getChatPart().getChildren().remove(scrollPane);
        for (int i = 0; i < usersForRoom.size(); i++) {
            String username = usersForRoom.get(i);
            StackPane listItem = new StackPane();
            listItem.setMinWidth(400);
            listItem.setMaxWidth(400);
            listItem.setMinHeight(60);
            listItem.setMaxHeight(60);
            listItem.setStyle("-fx-background-color: rgb(250, 250, 250)");
            listItem.setOnMouseEntered(mouseEvent -> {
                listItem.setStyle("-fx-background-color: rgb(230, 230, 230)");
            });
            listItem.setOnMouseExited(mouseEvent -> {
                listItem.setStyle("-fx-background-color: rgb(250, 250, 250)");
            });
            listItem.setOnMouseClicked(mouseEvent -> {
                try {
                    Packet packet = new Packet("new member");
                    packet.setToken(Main.connection.getToken());
                    packet.addAttribute("roomId", rooms.get(currentRoomName));
                    packet.addAttribute("username", username);
                    packet.sendPacket();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                chat.getChatPart().getChildren().remove(searchBar);
                addTypeBox();
                try {
                    Packet packet = new Packet("get messages - room");
                    packet.setToken(Main.connection.getToken());
                    packet.addAttribute("roomId", rooms.get(currentRoomName));
                    packet.sendPacket();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            Circle avatarCircle = new Circle(30);
            avatarCircle.setTranslateX(30);
            avatarCircle.setTranslateY(30);
            ImageView avatar = new ImageView(new Image(new ByteArrayInputStream(UsersController
                    .getImageFromServerByUsername(username, true).toByteArray())));
            GameMenu.packetOnlineReceiver.resumeThread();
            avatar.setClip(avatarCircle);
            avatar.setFitWidth(60);
            avatar.setFitHeight(60);
            avatar.setTranslateX(-170);

            Text usernameText = new Text(username);
            usernameText.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
            StackPane.setAlignment(usernameText, Pos.CENTER_LEFT);
            usernameText.setTranslateX(75);

            listItem.getChildren().addAll(avatar, usernameText);
            list.add(listItem, 0, i);
        }
        chat.getChatPart().getChildren().add(scrollPane);
    }
}
