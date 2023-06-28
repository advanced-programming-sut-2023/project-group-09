package controller;

import model.User;

import java.util.HashMap;

public class TokenController {
    public static HashMap<String, User> tokens = new HashMap<>();
    public static HashMap<String, Long> expires = new HashMap<>();

    public static String generateToken(User user) {
        System.out.println(user);
        long currentTime = System.currentTimeMillis();
        String helper = user.getUsername() + currentTime;
        String token = UserController.convertPasswordToHash(helper);
        expires.put(token, 1800000L + currentTime);
        tokens.put(token, user);
        return token;
    }

    public static boolean validateToken(String token) {
        long currentTime = System.currentTimeMillis();
        if (tokens.get(token) == null) return false;
        if (currentTime > expires.get(token)) {
            tokens.remove(token);
            return false;
        }
        return true;
    }

    public static User getUserByToken(String token) {
        return tokens.get(token);
    }
}
