package controller;


import model.User;

import java.util.ArrayList;
import java.util.Map;


public class Application {
    private static boolean stayLoggedIn;

    public static boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public static void setStayLoggedIn(boolean s) {
        stayLoggedIn = s;
    }

    private static User currentUser;
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Map> defaultSmallMaps = new ArrayList<>();
    private static ArrayList<Map> defaultLargeMaps = new ArrayList<>();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Application.currentUser = currentUser;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        Application.users = users;
    }


    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        for (User user : users) {
            email = email.toLowerCase();
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public static boolean isUserExistsByName(String username) {
        User user = getUserByUsername(username);
        return user != null;
    }

    public static boolean isUserExistsByEmail(String email) {
        User user = getUserByEmail(email);
        return user != null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static ArrayList<Map> getDefaultSmallMaps() {
        return defaultSmallMaps;
    }

    public static void setDefaultSmallMaps(ArrayList<Map> defaultSmallMaps) {
        Application.defaultSmallMaps = defaultSmallMaps;
    }

    public static ArrayList<Map> getDefaultLargeMaps() {
        return defaultLargeMaps;
    }

    public static void setDefaultLargeMaps(ArrayList<Map> defaultLargeMaps) {
        Application.defaultLargeMaps = defaultLargeMaps;
    }
}
