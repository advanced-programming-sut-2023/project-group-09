package controllers;

import enumeration.answers.Answers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewController {

    public static Matcher resultMatcher;

    public static String editItem(String input) {
        if (input.length() != 0 && input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"') {
            input = input.substring(1, input.length() - 1);
        }
        return input;
    }

    public static boolean permute(String input, ArrayList<String> arr, int k) {

        for (int i = k; i < arr.size(); i++) {
            java.util.Collections.swap(arr, i, k);
            boolean result = permute(input, arr, k + 1);
            if (result) {
                return true;
            }
            java.util.Collections.swap(arr, k, i);
        }

        if (k == arr.size() - 1) {
            StringBuilder patternString = new StringBuilder();
            for (String str : arr) {
                patternString.append(str);
            }
            Pattern pattern = Pattern.compile(patternString.toString());
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                resultMatcher = matcher;
                return true;
            }

        }
        return false;
    }

    public static boolean isItemMatch(String input, ArrayList<String> arr) {
        if (!permute(input, arr, 0)) {
            System.out.println(Answers.INVALID_COMMAND.getValue());
            return false;
        }
        return true;
    }

    public static int getNumberOfRegex(String groupName) {
        int x;
        try {
            x = Integer.parseInt(ViewController.resultMatcher.group(groupName));
        } catch (Exception e) {
            x = -1;
        }
        return x;
    }

}