package controller;

import model.User;

import java.util.ArrayList;

public class Application {

    private static User currentUser;
    private static ArrayList<User> users;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getUserByUsername(String username) {
        return null;
    }

    public static User getUserByEmail() {
        return null;
    }

    public static boolean isUserExistsByName(String username) {
        return false;
    }

    public static boolean isUserExistsByEmail(String email) {
        return false;
    }

    public static void addUser(User user) {

    }

}
