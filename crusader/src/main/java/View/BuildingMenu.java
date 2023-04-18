package View;

import Controller.MapController;
import Enumeration.Answers.Answers;
import Enumeration.Answers.BuildingAnswers;
import Enumeration.Commands.BuildingCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private static boolean isInvalidInput(Matcher matcher) {
        return matcher.results().count() != 1;

    }
    public static void run(Scanner scanner){
        while (true) {
            String command = scanner.nextLine();
            Matcher dropBuildingMatcher = BuildingCommands.getMatcher(command , BuildingCommands.DROP_BUILDING_REGEX);
            Matcher selectBuildingMatcher = BuildingCommands.getMatcher(command , BuildingCommands.SELECT_BUILDING_REGEX);
            Matcher createUnitMatcher = BuildingCommands.getMatcher(command , BuildingCommands.CREATE_UNIT_REGEX);
            Matcher repairMatcher = BuildingCommands.getMatcher(command , BuildingCommands.REPAIR_REGEX);
            if (dropBuildingMatcher.matches()) {
                Matcher xMatcher = BuildingCommands.getMatcher(command , BuildingCommands.X_COORD_REGEX);
                Matcher yMatcher = BuildingCommands.getMatcher(command , BuildingCommands.Y_COORD_REGEX);
                Matcher typeMatcher = BuildingCommands.getMatcher(command , BuildingCommands.TYPE_REGEX);
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
                Matcher xMatcher = BuildingCommands.getMatcher(command , BuildingCommands.X_COORD_REGEX);
                Matcher yMatcher = BuildingCommands.getMatcher(command , BuildingCommands.Y_COORD_REGEX);
                if (isInvalidInput(xMatcher)) {
                    System.out.println(BuildingAnswers.getMessage(BuildingAnswers.INVALID_X_COORD_ERROR));
                    continue;
                }
                if (isInvalidInput(yMatcher)) {
                    System.out.println(BuildingAnswers.getMessage(BuildingAnswers.INVALID_Y_COORD_ERROR));
                    continue;
                }
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
