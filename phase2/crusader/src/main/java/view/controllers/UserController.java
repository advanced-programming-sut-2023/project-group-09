package view.controllers;

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


    public static String validateOldPassword(String oldPassword) {
        if (controller.UserController.checkNullFields(oldPassword)) {
            return "old password field is required!";
        }
        return "";
    }

    public static String validateNewPassword(String newPassword) {
        if (controller.UserController.checkNullFields(newPassword)) {
            return "new password field is required!";
        }
        if (controller.UserController.checkPasswordPower(newPassword)) {
            return "new password is weak!";
        }
        return "";
    }
}
