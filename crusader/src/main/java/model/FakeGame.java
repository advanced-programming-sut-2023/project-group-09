package model;


import java.io.Serializable;
import java.util.ArrayList;

public class FakeGame implements Serializable {
    private String adminUsername;
    private ArrayList<String> allUsernames = new ArrayList<>();
    private ArrayList<String> colors = new ArrayList<>();
    private ArrayList<Integer> castleXs = new ArrayList<>();
    private ArrayList<Integer> castleYs = new ArrayList<>();


    public String getAdminUsername() {
        return adminUsername;
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
}
