package view;

import controller.GovernmentController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.GovernmentMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GovernmentMenu {
    public static void run(Scanner scanner) {

        String input, output;
        System.out.println("<< Government Menu >>");

        while (true) {
            input = scanner.nextLine();
            Matcher showPopularityFactorsMatcher = GovernmentMenuCommands.getMatcher(input, GovernmentMenuCommands.SHOW_POPULARITY_FACTORS);
            Matcher showPopularityMatcher = GovernmentMenuCommands.getMatcher(input, GovernmentMenuCommands.SHOW_POPULARITY);
            Matcher showFoodListMatcher = GovernmentMenuCommands.getMatcher(input, GovernmentMenuCommands.SHOW_FOOD_LIST);
            Matcher showFoodRateMatcher = GovernmentMenuCommands.getMatcher(input, GovernmentMenuCommands.SHOW_FOOD_RATE);
            Matcher showFearRateMatcher = GovernmentMenuCommands.getMatcher(input, GovernmentMenuCommands.SHOW_FEAR_RATE);
            Matcher showTaxRateMatcher = GovernmentMenuCommands.getMatcher(input, GovernmentMenuCommands.SHOW_TAX_RATE);
            Matcher setFoodRateMatcher = GovernmentMenuCommands.getMatcher(input, GovernmentMenuCommands.FOOD_RATE);
            Matcher setFearRateMatcher = GovernmentMenuCommands.getMatcher(input, GovernmentMenuCommands.FEAR_RATE);
            Matcher setTaxRateMatcher = GovernmentMenuCommands.getMatcher(input, GovernmentMenuCommands.TAX_RATE);
            Matcher backMatcher = Commands.getMatcher(input, Commands.BACK);


            if (showPopularityFactorsMatcher.matches()) {
                output = GovernmentController.showPopularityFactors();
                System.out.println(output);
            } else if (showPopularityMatcher.matches()) {
                output = GovernmentController.showPopularity();
                System.out.println(output);
            } else if (showFoodListMatcher.matches()) {
                output = GovernmentController.showFoodList();
                System.out.println(output);
            } else if (showFoodRateMatcher.matches()) {
                output = GovernmentController.showFoodRate();
                System.out.println(output);
            } else if (showFearRateMatcher.matches()) {
                output = GovernmentController.showFearRate();
                System.out.println(output);
            }else if (showTaxRateMatcher.matches()) {
                output = GovernmentController.showTaxRate();
                System.out.println(output);
            } else if (setFoodRateMatcher.matches()) {
                int rate = Integer.parseInt(setFoodRateMatcher.group("rate"));
                output = GovernmentController.changeFoodRate(rate);
                System.out.println(output);
            } else if (setFearRateMatcher.matches()) {
                int rate = Integer.parseInt(setFearRateMatcher.group("rate"));
                output = GovernmentController.changeFearRate(rate);
                System.out.println(output);
            } else if (setTaxRateMatcher.matches()) {
                int rate = Integer.parseInt(setTaxRateMatcher.group("rate"));
                output = GovernmentController.changeTaxRate(rate);
                System.out.println(output);
            } else if (backMatcher.matches()) {
                System.out.println("<< Game Menu >>");
                return;
            }else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }


        }

    }
}
