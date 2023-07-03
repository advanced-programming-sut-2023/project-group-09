package view.menus.chat;

import client.Packet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ChatViewController {
    public ChatMenu chat;
    public User currentUser;
    private HashMap<String, StackPane> tabs = new HashMap<>();
    private String currentTabName;
    private String currentRoomName;
    private TextField typeBox;
    private ImageView sendMessage;
    private TextField searchBar;
    private ImageView addRoom;
    private TextField newRoomBox;
    private ScrollPane scrollPane;
    private GridPane list;
    LinkedHashMap<String, String> users = new LinkedHashMap<>();
    LinkedHashMap<String, String> rooms = new LinkedHashMap<>();
    LinkedHashMap<String, String> privates = new LinkedHashMap<>();
    ArrayList<Message> messages = new ArrayList<>();

    public ChatViewController(ChatMenu chat) {
        this.chat = chat;
        Packet dataRequest = new Packet("chat data");
        try {
            dataRequest.setToken(Main.connection.getToken());
            dataRequest.sendPacket();
            Packet response = Packet.receivePacket();
            currentUser = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).
                    create().fromJson((String) response.getAttribute("currentUser"), User.class);
            users = new Gson().fromJson((String) response.getAttribute("otherUsers"), LinkedHashMap.class);
            rooms = new Gson().fromJson((String) response.getAttribute("currentUserRooms"), LinkedHashMap.class);
            privates = new Gson().fromJson((String) response.getAttribute("currentUserPrivates"), LinkedHashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        currentTabName = "public";
        selectTab("public");
        addTypeBox();
        try {
            Packet packet = new Packet("get messages - public");
            packet.setToken(Main.connection.getToken());
            packet.sendPacket();
            Packet response = Packet.receivePacket();
            messages = new ArrayList<>();
            for (int i = 0; i < response.getAttributes().size(); i++) {
                String message = (String) response.getAttribute("message" + i);
                messages.add(new Gson().fromJson((String) message, Message.class));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addListBox(2);
        showMessages(messages);
    }

    private void setRoomChatMenu() {
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

    private void setPrivateChatMenu() {
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
            try {
                Packet packet = new Packet("get messages - " + currentTabName);
                packet.setToken(Main.connection.getToken());
                if (currentTabName.equals("private")) packet.addAttribute("roomId", privates.get(currentRoomName));
                else if (currentTabName.equals("room")) packet.addAttribute("roomId", rooms.get(currentRoomName));
                packet.sendPacket();
                Packet response = Packet.receivePacket();
                messages = new ArrayList<>();
                for (int i = 0; i < response.getAttributes().size(); i++) {
                    String message = (String) response.getAttribute("message" + i);
                    messages.add(new Gson().fromJson((String) message, Message.class));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            typeBox.setText("");
            list.getChildren().clear();
            chat.getChatPart().getChildren().remove(scrollPane);
            addListBox(2);
            showMessages(messages);
        });
        typeBox.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode().getName().equals("Enter")) {
                if (typeBox.getText().isEmpty() || typeBox.getText() == null) return;
                setSendAction();
                try {
                    Packet packet = new Packet("get messages - " + currentTabName);
                    packet.setToken(Main.connection.getToken());
                    if (currentTabName.equals("private")) packet.addAttribute("roomId", privates.get(currentRoomName));
                    else if (currentTabName.equals("room")) packet.addAttribute("roomId", rooms.get(currentRoomName));
                    packet.sendPacket();
                    Packet response = Packet.receivePacket();
                    messages = new ArrayList<>();
                    for (int i = 0; i < response.getAttributes().size(); i++) {
                        String message = (String) response.getAttribute("message" + i);
                        messages.add(new Gson().fromJson((String) message, Message.class));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                typeBox.setText("");
                list.getChildren().clear();
                chat.getChatPart().getChildren().remove(scrollPane);
                addListBox(2);
                showMessages(messages);
            }
        });
        sendMessage.setFitWidth(50);
        sendMessage.setFitHeight(50);
        sendMessage.setTranslateX(167);
        sendMessage.setTranslateY(367);

        chat.getChatPart().getChildren().addAll(typeBox, sendMessage);
    }

    private void addSearchBar(boolean isInRoom) {
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
                            showListOfUsers(users);
                        else {
                            LinkedHashMap<String, String> filteredUsers = new LinkedHashMap<>();
                            for (String username : users.keySet())
                                if (username.toLowerCase().contains(newValue.toLowerCase()))
                                    filteredUsers.put(username, users.get(username));
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

    private void addListBox(int state) {
//        state: 0=roomsList - 1=usersList - 2=chatMessages
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
        }
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent");
        list = new GridPane();
        list.setHgap(20);
        scrollPane.setContent(list);
    }

    private void showListOfUsers(LinkedHashMap<String, String> users) throws IOException {
        list.getChildren().clear();
        chat.getChatPart().getChildren().remove(scrollPane);
        int count = 0;
        for (String username : users.keySet()) {
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
                    Packet response = Packet.receivePacket();
                    if (privates.get(username) == null)
                        privates.put(username, (String) response.getAttribute("roomId"));
                    messages = new ArrayList<>();
                    for (int i = 0; i < response.getAttributes().size(); i++) {
                        String message = (String) response.getAttribute("message" + i);
                        messages.add(new Gson().fromJson((String) message, Message.class));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                currentRoomName = username;
                list.getChildren().clear();
                chat.getChatPart().getChildren().remove(searchBar);
                addListBox(2);
                showMessages(messages);
            });

            Circle avatarCircle = new Circle(30);
            avatarCircle.setTranslateX(30);
            avatarCircle.setTranslateY(30);
            ImageView avatar = new ImageView(new Image(new ByteArrayInputStream(UsersController
                    .getImageFromServerByUsername(username).toByteArray())));
            avatar.setClip(avatarCircle);
            avatar.setFitWidth(60);
            avatar.setFitHeight(60);
            avatar.setTranslateX(-170);

            Text usernameText = new Text(username);
            usernameText.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 18));
            StackPane.setAlignment(usernameText, Pos.CENTER_LEFT);
            usernameText.setTranslateX(75);

            listItem.getChildren().addAll(avatar, usernameText);
            list.add(listItem, 0, count++);
        }
        chat.getChatPart().getChildren().add(scrollPane);
    }

    private void addNewRoomBox() {
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
            try {
                rooms.put(newRoomBox.getText(), (String) Packet.receivePacket().getAttribute("roomId"));
                list.getChildren().clear();
                addListBox(0);
                showListOfRooms(rooms);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            newRoomBox.setText("");
        });
        addRoom.setFitWidth(35);
        addRoom.setFitHeight(35);
        addRoom.setTranslateX(167);
        addRoom.setTranslateY(-270);

        chat.getChatPart().getChildren().addAll(newRoomBox, addRoom);
    }

    private void showListOfRooms(LinkedHashMap<String, String> rooms) throws IOException {
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
                    Packet response = Packet.receivePacket();
                    messages = new ArrayList<>();
                    for (int i = 0; i < response.getAttributes().size(); i++) {
                        String message = (String) response.getAttribute("message" + i);
                        messages.add(new Gson().fromJson((String) message, Message.class));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                currentRoomName = roomName;
                list.getChildren().clear();
                chat.getChatPart().getChildren().remove(searchBar);
                chat.getChatPart().getChildren().remove(newRoomBox);
                chat.getChatPart().getChildren().remove(addRoom);
                addListBox(2);
                showMessages(messages);
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

    private void setSendAction() {
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

    private void addLeftMessage(Message message, int count) throws IOException {
        StackPane messagePane = new StackPane();
        messagePane.setMinWidth(400);
        messagePane.setMaxWidth(400);

        Circle avatarCircle = new Circle(20);
        avatarCircle.setTranslateX(20);
        avatarCircle.setTranslateY(20);
        ImageView avatar = new ImageView(new Image(new ByteArrayInputStream(UsersController
                .getImageFromServerByUsername(message.getSender()).toByteArray())));
        avatar.setClip(avatarCircle);
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);
        avatar.setTranslateX(-175);

        TextArea messageBox = new TextArea(message.getSender() + ":\n" + message.getData());
        messageBox.setMaxWidth(250);
        messageBox.setPrefColumnCount(1);
        messageBox.setPrefRowCount(2);
        messageBox.setTranslateX(-20);
        messageBox.setWrapText(true);
        messageBox.setEditable(false);
        messageBox.getStyleClass().add("other-side");

        Text sentTime = new Text(message.getSentTime());
        sentTime.setTranslateX(130);
        sentTime.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 12));

        messagePane.getChildren().addAll(messageBox, avatar, sentTime);
        list.add(messagePane, 0, count);
    }

    private void addRightMessage(Message message, int count) throws IOException {
        StackPane messagePane = new StackPane();
        messagePane.setMinWidth(400);
        messagePane.setMaxWidth(400);

        Circle avatarCircle = new Circle(20);
        avatarCircle.setTranslateX(20);
        avatarCircle.setTranslateY(20);
        ImageView avatar = new ImageView(new Image(new ByteArrayInputStream(UsersController
                .getImageFromServerByUsername(message.getSender()).toByteArray())));
        avatar.setClip(avatarCircle);
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);
        avatar.setTranslateX(165);

        TextArea messageBox = new TextArea(message.getSender() + ":\n" + message.getData());
        messageBox.setMaxWidth(250);
        messageBox.setPrefColumnCount(1);
        messageBox.setPrefRowCount(2);
        messageBox.setTranslateX(15);
        messageBox.setWrapText(true);
        messageBox.setEditable(false);

        Text sentTime = new Text(message.getSentTime());
        sentTime.setTranslateX(-135);
        sentTime.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 12));

        messagePane.setOnMouseClicked(mouseEvent -> {
            this.chat.getChildren().add(this.chat.getOptionsPart());
            addOptions(messageBox, message);
        });

        messagePane.getChildren().addAll(messageBox, avatar, sentTime);
        list.add(messagePane, 0, count);
    }

    private void showMessages(ArrayList<Message> messages) {
        list.getChildren().clear();
        chat.getChatPart().getChildren().remove(scrollPane);
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
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

    private void addOptions(TextArea messageBox, Message message) {
        createOption("Delete", -400 / 3, messageBox, message);
        createOption("Delete for All", 0, messageBox, message);
        createOption("Edit", 400 / 3, messageBox, message);
    }

    private void createOption(String text, double x, TextArea messageBox, Message message) {
        StackPane option = new StackPane();
        option.setMinWidth(400 / 3);
        option.setMaxWidth(400 / 3);
        option.setMinHeight(40);
        option.setMaxHeight(40);
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
            else {
                setOptionAction(text, message.getId(), null);
                try {
                    Packet packet = new Packet("get messages - " + currentTabName);
                    packet.setToken(Main.connection.getToken());
                    if (currentTabName.equals("private")) packet.addAttribute("roomId", privates.get(currentRoomName));
                    else if (currentTabName.equals("room")) packet.addAttribute("roomId", rooms.get(currentRoomName));
                    packet.sendPacket();
                    Packet response = Packet.receivePacket();
                    messages = new ArrayList<>();
                    for (int i = 0; i < response.getAttributes().size(); i++) {
                        String currentMessage = (String) response.getAttribute("message" + i);
                        messages.add(new Gson().fromJson((String) currentMessage, Message.class));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                list.getChildren().clear();
                chat.getChatPart().getChildren().remove(scrollPane);
                addListBox(2);
                showMessages(messages);
            }
            this.chat.getChildren().remove(this.chat.getOptionsPart());

        });
        this.chat.getOptionsPart().getChildren().add(option);
    }

    private void setOptionAction(String action, String messageId, String newMessage) {
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

    private void enterEditMode(TextArea messageBox, Message message) {
        messageBox.setEditable(true);
        messageBox.getStyleClass().add("edit-mode");
        ImageView confirmEdit = new ImageView(new Image(ChatViewController.class.getResource("/images/icons/check.png").toExternalForm()));
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
        this.chat.getChildren().add(confirmEdit);
        confirmEdit.setOnMouseClicked(mouseEvent -> {
            String newMessage = messageBox.getText().substring(messageBox.getText().indexOf("\n") + 1);
            setOptionAction("Edit", message.getId(), newMessage);
            try {
                Packet packet = new Packet("get messages - " + currentTabName);
                packet.setToken(Main.connection.getToken());
                if (currentTabName.equals("private")) packet.addAttribute("roomId", privates.get(currentRoomName));
                else if (currentTabName.equals("room")) packet.addAttribute("roomId", rooms.get(currentRoomName));
                packet.sendPacket();
                Packet response = Packet.receivePacket();
                messages = new ArrayList<>();
                for (int i = 0; i < response.getAttributes().size(); i++) {
                    String currentMessage = (String) response.getAttribute("message" + i);
                    messages.add(new Gson().fromJson((String) currentMessage, Message.class));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.chat.getChildren().remove(confirmEdit);
            list.getChildren().clear();
            chat.getChatPart().getChildren().remove(scrollPane);
            addListBox(2);
            showMessages(messages);
            messageBox.getStyleClass().remove("edit-mode");
        });
    }
}
