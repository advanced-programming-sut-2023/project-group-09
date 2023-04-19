package controller;

import enumeration.Paths;
import model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DBController {

    public static void loadAllUsers(){
        try {
            Gson gson = new Gson();
            String text = new String(Files.readAllBytes(Path.of(Paths.USERS_PATH.getPath())));
            ArrayList<User> allUsers = gson.fromJson(text, new TypeToken<List<User>>(){}.getType());
            Application.setUsers(allUsers);
        } catch (IOException e) {
            System.out.println("An error occurred.[load users]");
            e.printStackTrace();
        }
    }

    public static void saveAllUsers(){

        try {
            File file = new File(Paths.USERS_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(Application.getUsers());
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[save users]");
            e.printStackTrace();
        }
    }

    public static void loadCurrentUser(){
        try {
            Gson gson = new Gson();
            String text = new String(Files.readAllBytes(Path.of(Paths.CURRENT_USER_PATH.getPath())));
            User user = gson.fromJson(text, User.class);
            Application.setCurrentUser(user);
        } catch (IOException e) {
            System.out.println("An error occurred.[load current user]");
            e.printStackTrace();
        }
    }

    public static void saveCurrentUser(){
        try {
            File file = new File(Paths.CURRENT_USER_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(Application.getCurrentUser());
            fileWriter.write(json);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.[save current user]");
            e.printStackTrace();
        }
    }
}
