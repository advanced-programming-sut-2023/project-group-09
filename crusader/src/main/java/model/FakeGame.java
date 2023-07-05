package model;


import java.io.Serializable;
import java.util.ArrayList;

public class FakeGame implements Serializable{

    private boolean isPrivate = false;
    private String password;
    private String mapName;
    private long gameId;
    private String gameName;
    private String adminUsername;
    private ArrayList<String> allUsernames = new ArrayList<>();
    private ArrayList<String> colors = new ArrayList<>();
    private ArrayList<Integer> castleXs = new ArrayList<>();
    private ArrayList<Integer> castleYs = new ArrayList<>();
    private ArrayList<String> spectatorsUsernames = new ArrayList<>();
    private boolean isGameStarted = false;
    private int maxPlayer;
    public String getAdminUsername() {
        return adminUsername;
    }

    public FakeGame() {
    }

    public FakeGame(FakeGame fakeGame) {
        this.setAllUsernames(new ArrayList<>(fakeGame.allUsernames));
        this.setGameId(fakeGame.gameId);
        this.setGameName(fakeGame.gameName);
        this.setMaxPlayer(fakeGame.maxPlayer);
        this.setGameStarted(fakeGame.isGameStarted);
        this.setPassword(fakeGame.password);
        this.setColors(new ArrayList<>(fakeGame.colors));
        this.setCastleXs(new ArrayList<>(fakeGame.castleXs));
        this.setCastleYs(new ArrayList<>(fakeGame.castleYs));
        this.setPrivate(fakeGame.isPrivate);
        this.setMapName(fakeGame.mapName);
        this.setAdminUsername(fakeGame.adminUsername);
        this.setSpectatorsUsernames(new ArrayList<>(fakeGame.spectatorsUsernames));
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public void addPlayer(String username , String color, int castleX , int castleY) {
        allUsernames.add(username);
        colors.add(color);
        castleXs.add(castleX);
        castleYs.add(castleY);
    }

    public ArrayList<String> getAllUsernames() {
        return allUsernames;
    }

    public void setAllUsernames(ArrayList<String> allUsernames) {
        this.allUsernames = allUsernames;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public ArrayList<Integer> getCastleXs() {
        return castleXs;
    }

    public void setCastleXs(ArrayList<Integer> castleXs) {
        this.castleXs = castleXs;
    }

    public ArrayList<Integer> getCastleYs() {
        return castleYs;
    }

    public void setCastleYs(ArrayList<Integer> castleYs) {
        this.castleYs = castleYs;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId() {
        this.gameId = System.currentTimeMillis();
    }
    public void setGameId(long id) {
        this.gameId = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ArrayList<String> getSpectatorsUsernames() {
        return spectatorsUsernames;
    }

    public void setSpectatorsUsernames(ArrayList<String> spectatorsUsernames) {
        this.spectatorsUsernames = spectatorsUsernames;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

}
