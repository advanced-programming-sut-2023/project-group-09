package view;

import controller.UserController;
import enumeration.commands.SignupMenuCommands;
import model.User;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu {
    //    -1: register state - 0: security question state - 1: end state
    public static int signupState = -1;
    public static boolean validResult = false;
    public static User currentUser = null;

    public static void run(Scanner scanner) {
        while (true) {
            if (signupState == -1) {
                runRegisterPart(scanner);
            } else if (signupState == 0) {
                String input = scanner.nextLine();
                Matcher securityQuestionM = SignupMenuCommands.PICK_QUESTION.getMatcher(input);
                if (!securityQuestionM.matches()) System.out.println("invalid command");
                String content = securityQuestionM.group("content");

                HashMap<String, Matcher> matchers = new HashMap<>();
                matchers.put("questionNumber", SignupMenuCommands.QUESTION_NUMBER.getMatcher(content));
                matchers.put("answer", SignupMenuCommands.QUESTION_ANSWER.getMatcher(content));
                matchers.put("answerConfirm", SignupMenuCommands.QUESTION_ANSWER_CONFIRM.getMatcher(content));


            }
        }
    }

    private static void runRegisterPart(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher signupM = SignupMenuCommands.SIGNUP.getMatcher(input);
        if (!signupM.matches()) System.out.println("invalid command");
        String content = signupM.group("content");

        HashMap<String, Matcher> matchers = new HashMap<>();
        matchers.put("username", SignupMenuCommands.SIGNUP_USERNAME.getMatcher(content));
        matchers.put("password", SignupMenuCommands.SIGNUP_PASSWORD.getMatcher(content));
        matchers.put("email", SignupMenuCommands.SIGNUP_EMAIL.getMatcher(content));
        matchers.put("nickname", SignupMenuCommands.SIGNUP_NICKNAME.getMatcher(content));
        matchers.put("slogan", SignupMenuCommands.SIGNUP_SLOGAN.getMatcher(content));
        boolean isSlogan = matchers.get("slogan").find();

        String result = UserController.createUser(matchers, isSlogan);
        if (validResult) signupState = 0;
        System.out.println(result);
    }
}
