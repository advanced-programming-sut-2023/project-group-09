package viewphase1;

import controllers.GameController;
import controllers.GovernmentController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;
import enumeration.commands.GovernmentMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GovernmentMenu {
    public static void run(Scanner scanner) {

        String input, output;
        System.out.println("<< Government Menu >>");
        GovernmentController.setCurrentGovernment(GameController.getGame().getCurrentGovernment());
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
            Matcher showPropertiesMatcher = GovernmentMenuCommands.getMatcher(input , GovernmentMenuCommands.SHOW_PROPERTIES);
            Matcher backMatcher = Commands.getMatcher(input, Commands.BACK);


            if (showPopularityFactorsMatcher.matches()) {
                output = GovernmentController.showPopularityFactors();
                System.out.print(output);
            } else if (showPopularityMatcher.matches()) {
                output = GovernmentController.showPopularity();
                System.out.println(output);
            } else if (showFoodListMatcher.matches()) {
                output = GovernmentController.showFoodList();
                System.out.print(output);
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
                int rate;
                try {
                  rate = Integer.parseInt(setFoodRateMatcher.group("rate"));
                } catch (NumberFormatException e){
                    rate = -100;
                }
                output = GovernmentController.changeFoodRate(rate);
                System.out.println(output);
            } else if (setFearRateMatcher.matches()) {
                int rate;
                try {
                    rate = Integer.parseInt(setFearRateMatcher.group("rate"));
                }catch (NumberFormatException e){
                    rate = -100;
                }
                output = GovernmentController.changeFearRate(rate);
                System.out.println(output);
            } else if (showPropertiesMatcher.matches()) {
                System.out.println(GovernmentController.showProperties());
            } else if (setTaxRateMatcher.matches()) {
                int rate;
                try {
                    rate = Integer.parseInt(setTaxRateMatcher.group("rate"));
                }catch (NumberFormatException e){
                    rate = -100;
                }
                output = GovernmentController.changeTaxRate(rate);
                System.out.println(output);
            } else if (backMatcher.matches()) {
                System.out.println("<< Game Menu >>");
                return;
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }


        }

    }
}
