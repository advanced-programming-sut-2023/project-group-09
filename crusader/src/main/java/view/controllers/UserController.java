package view.controllers;

import controller.Application;

public class UserController {
    public static String validateUsername(String username){
        if (controller.UserController.checkNullFields(username)) {
            return "username field is required!";
        }
        if (controller.UserController.checkUsernameChars(username)) {
            return "username is invalid!";
        }
        return "";
    }
    public static String validateEmail(String email) {
        if (controller.UserController.checkNullFields(email)) {
            return "email field is required!";
        }
        if (controller.UserController.checkEmailFormat(email)) {
            return "email is invalid!";
        }
        return "";
    }


    public static String validatePassword(String email) {
        if (controller.UserController.checkNullFields(email)) {
            return "email field is required!";
        }
        if (controller.UserController.checkEmailFormat(email)) {
            return "email is invalid!";
        }
        return "";
    }
}
