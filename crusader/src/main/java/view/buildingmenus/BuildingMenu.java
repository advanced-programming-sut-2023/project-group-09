package view.buildingmenus;

import controller.BuildingController;
import enumeration.answers.BuildingAnswers;
import enumeration.commands.BuildingMenuCommands;
import enumeration.dictionary.Buildings;
import model.building.Building;

import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private static Building building = BuildingController.getBuilding();
    private static String nameOfBuilding = building.getName();
    private static boolean isThisBuildingSelected(Buildings building) {
        return Buildings.getName(building).equals(nameOfBuilding);
    }
    public static void run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            Matcher unselectBuildingMatcher = BuildingMenuCommands.getMatcher(command , BuildingMenuCommands.USELECT_BUILDING);
            if (unselectBuildingMatcher.matches()) {
                break;
            }
            if (isThisBuildingSelected(Buildings.MAIN_CASTLE)) {
                Matcher changeTaxRateMatcher = BuildingMenuCommands.getMatcher(command , BuildingMenuCommands.CHANGE_TAX_RATE);
                Matcher showTaxRateMatcher = BuildingMenuCommands.getMatcher(command , BuildingMenuCommands.SHOW_TAX_RATE);
                if (changeTaxRateMatcher.matches()) {
                    String rateNumberString = changeTaxRateMatcher.group("ratenumber");
                    System.out.println(BuildingController.changeTaxRate(rateNumberString));
                } else if (showTaxRateMatcher.matches()) {
                    System.out.println(BuildingController.showTaxRate());
                }
            } else if (isThisBuildingSelected(Buildings.SMALL_STONE_GATEHOUSE) ||
            isThisBuildingSelected(Buildings.BIG_STONE_GATEHOUSE)) {
                Matcher openOrCloseGatehouseMatcher = BuildingMenuCommands.getMatcher(command , BuildingMenuCommands.OPEN_OR_CLOSE_GATEHOUSE);
                Matcher repairMatcher = BuildingMenuCommands.getMatcher(command , BuildingMenuCommands.REPAIR_IT);
                if (openOrCloseGatehouseMatcher.matches()) {
                    String order = openOrCloseGatehouseMatcher.group("order");
                } else if (repairMatcher.matches()) {

                }
            }
        }
    }
}
