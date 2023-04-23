package view;

import controller.MapController;
import enumeration.answers.Answers;
import enumeration.answers.BuildingAnswers;
import enumeration.commands.BuildingMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private static boolean isInvalidInput(Matcher matcher) {
        return matcher.results().count() != 1;

    }
    public static void run(Scanner scanner){
        while (true) {
            String command = scanner.nextLine();
            Matcher dropBuildingMatcher = BuildingMenuCommands.getMatcher(command , BuildingMenuCommands.DROP_BUILDING_REGEX);
            Matcher selectBuildingMatcher = BuildingMenuCommands.getMatcher(command , BuildingMenuCommands.SELECT_BUILDING_REGEX);
            Matcher createUnitMatcher = BuildingMenuCommands.getMatcher(command , BuildingMenuCommands.CREATE_UNIT_REGEX);
            Matcher repairMatcher = BuildingMenuCommands.getMatcher(command , BuildingMenuCommands.REPAIR_REGEX);
            if (dropBuildingMatcher.matches()) {
                String content = dropBuildingMatcher.group("content");
                Matcher xMatcher = BuildingMenuCommands.getMatcher(content , BuildingMenuCommands.X_COORD_REGEX);
                Matcher yMatcher = BuildingMenuCommands.getMatcher(content , BuildingMenuCommands.Y_COORD_REGEX);
                Matcher typeMatcher = BuildingMenuCommands.getMatcher(content , BuildingMenuCommands.TYPE_REGEX);
                if (isInvalidInput(xMatcher)) {
                    System.out.println(BuildingAnswers.getMessage(BuildingAnswers.INVALID_X_COORD_ERROR));
                    continue;
                }
                if (isInvalidInput(yMatcher)) {
                    System.out.println(BuildingAnswers.getMessage(BuildingAnswers.INVALID_Y_COORD_ERROR));
                    continue;
                }
                if (isInvalidInput(typeMatcher)) {
                    System.out.println(BuildingAnswers.getMessage(BuildingAnswers.INVALID_TYPE_ERROR));
                    continue;
                }
                String xStr , yStr , typeOfBuilding;
                xStr = xMatcher.group("x");
                yStr = yMatcher.group("y");
                typeOfBuilding= typeMatcher.group("type");
                System.out.println(MapController.dropBuilding(xStr , yStr , typeOfBuilding));
            } else if (selectBuildingMatcher.matches()) {
                String content = selectBuildingMatcher.group("content");
                Matcher xMatcher = BuildingMenuCommands.getMatcher(content , BuildingMenuCommands.X_COORD_REGEX);
                Matcher yMatcher = BuildingMenuCommands.getMatcher(content , BuildingMenuCommands.Y_COORD_REGEX);
                if (isInvalidInput(xMatcher)) {
                    System.out.println(BuildingAnswers.getMessage(BuildingAnswers.INVALID_X_COORD_ERROR));
                    continue;
                }
                if (isInvalidInput(yMatcher)) {
                    System.out.println(BuildingAnswers.getMessage(BuildingAnswers.INVALID_Y_COORD_ERROR));
                    continue;
                }
                String xStr =
                // ؟؟؟؟؟؟؟؟
            } else if (createUnitMatcher.matches()) {
                // ؟؟؟؟؟؟؟؟
            } else if (repairMatcher.matches()) {

            } else {
                System.out.println(Answers.INVALID_COMMAND);
            }
        }
    }
}
