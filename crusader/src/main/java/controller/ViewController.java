package controller;

public class ViewController {
    public static String editItem(String input){
        if(input.length() != 0 && input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"'){
            input = input.substring(1,input.length() - 1);
        }

        return input;
    }
}
