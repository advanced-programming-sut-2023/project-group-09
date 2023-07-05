package model.game;

import client.Packet;
import controller.GameController;
import controller.GovernmentController;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Government;
import model.human.Human;
import model.menugui.MenuBox;
import model.menugui.MenuButton;
import view.Main;
import view.controllers.GameViewController;
import view.menus.GameMenu;
import view.menus.MainMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Game {
    private transient Map map;
    private final ArrayList<Government> governments = new ArrayList<>();

    public HashMap<Integer, Human> humans = new HashMap<>();
    private Government currentGovernment;
    private Government winner;
    private int round;
    private int currentMapX;
    private int currentMapY;
    private boolean endGame = false;

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) throws IOException {
        this.endGame = endGame;
        if (endGame) {
            Packet packet = new Packet("end game" , "Game");
            packet.sendPacket();
            Main.connection.getObjectOutputStream().writeObject(GameController.getFakeGame());
        }
        GameViewController.gameTimeLine.stop();
    }

    public Government getWinner() {
        return winner;
    }

    public void setWinner() {
        this.winner = null;
        Iterator itr = getGovernments().iterator();
        while (itr.hasNext()) {
            Government government = (Government) itr.next();
            if (government.isAlive() && government.equals(GameController.getGame().getCurrentGovernment())) {
                this.winner = government;
                setWinPage(government);
            }
        }
        GameViewController.gameTimeLine.stop();
    }

    private synchronized static void setWinPage(Government winnerGov) {
        MenuBox menuBox = new MenuBox("Game Is Over", 0, 0, 600, 600);
        Text winner = new Text("You Win With Score " +
                winnerGov.getHowManyTurnsSurvive() * 100);
        if (GameMenu.isSpectator) {
            winner.setText("Game is done!");
        }
        winner.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
        menuBox.getChildren().add(winner);
        MenuButton endButton = new MenuButton("Exit!", menuBox, 0, 100, false);
        endButton.setOnMouseClicked(e -> {
            try {
                new MainMenu().start(GameMenu.stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        menuBox.getChildren().add(endButton);
        menuBox.setViewOrder(-10000);
        GameMenu.root.getChildren().add(menuBox);
    }

    public Game(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }

    public void addGovernment(Government government) {
        if (currentGovernment == null) {
            currentGovernment = government;
            GovernmentController.setCurrentGovernment(government);
        }
        this.governments.add(government);
    }

    public Government getCurrentGovernment() {
        return currentGovernment;
    }

    public Government getGovernmentByUsername(String username) {
        for (int i = 0; i < governments.size(); i++) {
            Government government = governments.get(i);
            if (government.getUser().getUsername().equals(username))
                return government;
        }
        return null;
    }

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void changeTurn() {
        int index = governments.indexOf(currentGovernment);
        if (index + 1 == governments.size()) {
            index = -1;
            this.round++;
        }
        currentGovernment = governments.get(index + 1);
        if (!currentGovernment.isAlive())
            changeTurn();
    }

    public int getCurrentMapX() {
        return currentMapX;
    }

    public void setCurrentMapX(int currentMapX) {
        this.currentMapX = currentMapX;
    }

    public int getCurrentMapY() {
        return currentMapY;
    }

    public void setCurrentMapY(int currentMapY) {
        this.currentMapY = currentMapY;
    }

    public void setScores() throws IOException {
        for (Government government : getGovernments()) {
            Packet packet = new Packet("add score" , "Game");
            packet.addAttribute("color" , government.getColor());
            packet.addAttribute("score" , government.getHowManyTurnsSurvive() * 100);
            packet.sendPacket();
            Main.connection.getObjectOutputStream().writeObject(GameController.getFakeGame());
        }
    }
}
