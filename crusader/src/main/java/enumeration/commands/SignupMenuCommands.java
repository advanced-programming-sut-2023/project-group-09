package enumeration.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignupMenuCommands {
    SIGNUP("signup user (?<content>.+)"),
    SIGNUP_USERNAME("-u (?<content>(\"(?<username1>[^\\-]*)\")|(?<username2>[^ \t\\-\"]*))"),
    SIGNUP_PASSWORD("-p (?<content>(?<randomPassword>random)|(((?<password1>[^ \t\\-\"]*)" +
            " (?<passwordConfirm1>[^ \t\\-\"]*))|((\"(?<password2>[^\\-]*)\") (\"(?<passwordConfirm2>[^\\-]*)\"))))?"),
    SIGNUP_EMAIL("-e (?<content>(\"(?<email1>[^\\-]*)\")|(?<email2>[^ \t\\-\"]*))"),
    SIGNUP_NICKNAME("-n (?<content>(\"(?<nickname1>[^\\-]*)\")|(?<nickname2>[^ \t\\-\"]*))"),
    SIGNUP_SLOGAN("-s (?<content>(?<randomSlogan>random)|((\"(?<slogan1>[^\\-]*)\")|(?<slogan2>[^ \t\\-\"]*)))"),
    PICK_QUESTION("pick question (?<content>.+)"),
    QUESTION_NUMBER("-q (?<number>[\\d]*)"),
    QUESTION_ANSWER("-a (?<content>(\"(?<answer1>[^\\-]*)\")|(?<answer2>[^ \t\\-\"]*))"),
    QUESTION_ANSWER_CONFIRM("-c (?<content>(\"(?<answerConfirm1>[^\\-]*)\")|(?<answerConfirm2>[^ \t\\-\"]*))"),
    USERNAME_VALIDATION("[a-zA-Z\\d_]+"),
    PASSWORD_NOT_CONTAINING_SPACE("[\\s]"),
    PASSWORD_CONTAINING_UPPERCASE_LETTER("[A-Z]"),
    PASSWORD_CONTAINING_LOWERCASE_LETTER("[a-z]"),
    PASSWORD_CONTAINING_DIGIT("[\\d]"),
    PASSWORD_CONTAINING_SPECIAL_LETTER("[^a-zA-Z\\d]"),
    EMAIL_VALIDATION("[a-zA-Z\\d_\\.]+@[a-zA-Z\\d_\\.]+\\.[a-zA-Z\\d_\\.]+"),
    BACK("back");
    private String regex;

    private SignupMenuCommands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
