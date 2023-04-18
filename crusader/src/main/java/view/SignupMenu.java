package view;

import controller.UserController;
import enumeration.commands.SignupMenuCommands;
import model.User;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu {
    //    0: register - 1: random slogan - 2: random password - 3: 1 and 2 - 4: security question - 5: end
    public static int signupState = 0;
    public static User currentUser = null;
    public static HashMap<String, String> user = new HashMap<>();

    public static void run(Scanner scanner) {
        while (true) {
            if (signupState == 0) runRegisterPart(scanner);
            else if (signupState == 2 || signupState == 3) System.out.println(UserController.createUser(user, scanner.nextLine()));
            else if (signupState == 4) runSecurityQuestionPart(scanner);
        }
    }

    private static void runRegisterPart(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher signupM = SignupMenuCommands.SIGNUP.getMatcher(input);
        if (!signupM.matches()) {
            System.out.println("invalid command");
            return;
        }
        String content = signupM.group("content");

        HashMap<String, Matcher> matchers = new HashMap<>();
        matchers.put("username", SignupMenuCommands.SIGNUP_USERNAME.getMatcher(content));
        matchers.put("password", SignupMenuCommands.SIGNUP_PASSWORD.getMatcher(content));
        matchers.put("email", SignupMenuCommands.SIGNUP_EMAIL.getMatcher(content));
        matchers.put("nickname", SignupMenuCommands.SIGNUP_NICKNAME.getMatcher(content));
        matchers.put("slogan", SignupMenuCommands.SIGNUP_SLOGAN.getMatcher(content));
        boolean isSlogan = matchers.get("slogan").find();

        String result = UserController.signup(matchers, isSlogan);
        System.out.println(result);
    }

    private static void runSecurityQuestionPart(Scanner scanner) {
        System.out.println(UserController.showSecurityQuestions());
        String input = scanner.nextLine();
        Matcher securityQuestionM = SignupMenuCommands.PICK_QUESTION.getMatcher(input);
        if (!securityQuestionM.matches()) {
            System.out.println("invalid command");
            return;
        }
        String content = securityQuestionM.group("content");

        HashMap<String, Matcher> matchers = new HashMap<>();
        matchers.put("questionNumber", SignupMenuCommands.QUESTION_NUMBER.getMatcher(content));
        matchers.put("answer", SignupMenuCommands.QUESTION_ANSWER.getMatcher(content));
        matchers.put("answerConfirm", SignupMenuCommands.QUESTION_ANSWER_CONFIRM.getMatcher(content));

        System.out.println(UserController.pickSecurityQuestion(currentUser, matchers));
    }
}
