package view.menus.profile;

import controller.DBController;
import enumeration.Paths;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import model.menugui.MenuBox;
import model.menugui.MenuFingerBack;
import view.controllers.ViewController;
import view.menus.LoginMenu;
import view.menus.MainMenu;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ChangeAvatar extends Application {
    public static Stage stage;
    public static StackPane root;
    public static Rectangle profilePhoto;
    public static GridPane avatarsGrid;
    public static MenuBox menuBox;
    public static MenuFingerBack back;
    public static Rectangle fileChooser;
    User user;

    @Override
    public void start(Stage stage) throws Exception {
        ChangeAvatar.stage = stage;
        user = controller.Application.getCurrentUser();
        makeScene();
    }

    @FXML
    public void initialize() {
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/profile/changeProfile.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);

        root = ViewController.makeStackPaneScreen(stage, pane, 1000, -1);
        setBackground();
        menuBox = new MenuBox("change avatar", 0, 0, 500, 500);
        avatarsGrid = new GridPane();
        avatarsGrid.setHgap(20);
        avatarsGrid.setVgap(20);
        setEvents();
        menuBox.getChildren().add(avatarsGrid);
        avatarsGrid.setTranslateX(300);
        avatarsGrid.setTranslateY(300);
        profilePhoto = new Rectangle(0, -200, 100, 100);
        profilePhoto.setFill(new ImagePattern(new Image(String.valueOf(new File(user.getPath()).toURI()))));
        profilePhoto.setArcHeight(100);
        profilePhoto.setArcWidth(100);
        profilePhoto.setStroke(Color.DARKRED);
        profilePhoto.setStrokeWidth(5);
        profilePhoto.setTranslateY(-120);

        fileChooser = new Rectangle(0, 0, 100, 100);
        fileChooser.setFill(new ImagePattern(new Image(getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "upload.png")));
        fileChooser.setArcHeight(100);
        fileChooser.setArcWidth(100);
        fileChooser.setTranslateY(100);
        fileChooser.setOnMouseEntered(mouseEvent -> {
            fileChooser.setScaleX(1.1);
            fileChooser.setScaleY(1.1);
        });

        fileChooser.setOnMouseExited(mouseEvent -> {
            fileChooser.setScaleX(1);
            fileChooser.setScaleY(1);
        });

        fileChooser.setOnMouseClicked(mouseEvent -> {
            try {
                openFile(mouseEvent);
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        fileChooser.setOnDragOver(event -> {
            if (event.getGestureSource() != fileChooser && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        fileChooser.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0);
                if (file != null) {
                    String path = Paths.USER_AVATARS.getPath() + user.getUsername();
                    boolean check = new File(path).mkdirs();
                    try {
                        Files.copy(file.toPath(), (new File(path + "/" + file.getName())).toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    user.setPath(path + "/" + file.getName());
                    DBController.saveCurrentUser();
                    DBController.saveAllUsers();
                    profilePhoto.setFill(new ImagePattern(new Image(new File(path + "/" + file.getName()).toURI().toString())));

                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        menuBox.getChildren().addAll(profilePhoto, fileChooser);
        back = new MenuFingerBack(-400,300);
        back.setOnMouseClicked(mouseEvent -> {
            try {
                new ProfileMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        root.getChildren().add(menuBox);
        root.getChildren().add(back);
        stage.setScene(scene);
        stage.show();
    }

    public void openFile(MouseEvent mouseEvent) throws URISyntaxException, IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            String path = Paths.USER_AVATARS.getPath() + user.getUsername();
            boolean check = new File(path).mkdirs();
            Files.copy(file.toPath(), (new File(path + "/" + file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
            user.setPath(path + "/" + file.getName());
            DBController.saveCurrentUser();
            DBController.saveAllUsers();
            profilePhoto.setFill(new ImagePattern(new Image(new File(path + "/" + file.getName()).toURI().toString())));

        }
    }

    private static void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    public void setEvents() {
        String path;
        for (int i = 0; i < 4; i++) {
            Rectangle rectangle = new Rectangle(0, 0, 80, 80);
            path = String.valueOf(new File(Paths.USER_AVATARS.getPath() + (i + 1) + ".png").toURI());
            rectangle.setFill(new ImagePattern(new Image(path)));
            rectangle.setArcHeight(50);
            rectangle.setArcWidth(50);

            rectangle.setOnMouseEntered(mouseEvent -> {
                rectangle.setScaleX(1.1);
                rectangle.setScaleY(1.1);
            });

            rectangle.setOnMouseExited(mouseEvent -> {
                rectangle.setScaleX(1);
                rectangle.setScaleY(1);
            });

            int finalI = i;
            rectangle.setOnMouseClicked(mouseEvent -> {
                user.setPath(Paths.USER_AVATARS.getPath() + (finalI + 1) + ".png");
                DBController.saveCurrentUser();
                DBController.saveAllUsers();
                String path1 = String.valueOf(new File(user.getPath()).toURI());
                profilePhoto.setFill(new ImagePattern(new Image(path1)));
            });

            avatarsGrid.add(rectangle, i % 4, i / 4);
        }
    }

    public void setBackground() {
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(getClass().getResource
                        (Paths.BACKGROUND_IMAGES.getPath()).toExternalForm() + "01.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }
}