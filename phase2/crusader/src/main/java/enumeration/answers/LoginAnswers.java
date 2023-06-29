package enumeration.answers;

public enum LoginAnswers {SUCCESSFUL_LOGIN_MESSAGE("logged in successfully!"),
    USER_DOESNT_EXIST_MESSAGE("user with this username doesn't exist!"),
    WRONG_PASSWORD_MESSAGE("password is wrong!"),
    INVALID_COMMAND_MESSAGE("invalid command!"),
    WRONG_ANSWER_MESSAGE("answer to security question is wrong!"),
    ENTER_YOUR_PASSWORD_MESSAGE("type your new password: "),
    INVALID_USERNAME_INPUT_MESSAGE("invalid format for username!"),
    INVALID_PASSWORD_INPUT_MESSAGE("invalif format for password!"),
    INVALID_LOGIN_INPUT_MESSAGE("invalid format for login fields!"),
    WEAK_PASSWORD_MESSAGE("Your password is weak!"),
    PASSWORD_CHANGE_SUCCESSFUL_MESSAGE("Your password changed successfully!"),
    PASSWORD_LENGTH_ERROR(" : password length must be greater or equals than 6!"),
    PASSWORD_LOWERCASE_ERROR(" : password must have a lowercase letter!"),
    PASSWORD_UPPERCASE_ERROR(" : password must have a uppercase letter!"),
    PASSWORD_NUMBER_ERROR(" : password must have a number digit!"),
    PASSWORD_OTHER_CHARACTERS_ERROR(" : password must have a character except letters and numbers!"),
    PASSWORD_CONFIRMATION_MESSAGE("type your new password again: "),
    PASSWORD_AND_CONFIRMATION_DOESNT_MATCH("Password and confirmation doesn't match!");


    private String message;

    private LoginAnswers(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
}
