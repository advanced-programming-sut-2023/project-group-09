package view;

import controller.BuildingController;
import enumeration.answers.Answers;
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
        BuildingController.setGovernment();
        while (true) {
            String command = scanner.nextLine();
            Matcher unselectBuildingMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.USELECT_BUILDING);
            if (unselectBuildingMatcher.matches()) {
                break;
            }
            if (isThisBuildingSelected(Buildings.MAIN_CASTLE)) {
                Matcher changeTaxRateMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.CHANGE_TAX_RATE);
                Matcher showTaxRateMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.SHOW_TAX_RATE);
                if (changeTaxRateMatcher.matches()) {
                    String rateNumberString = changeTaxRateMatcher.group("ratenumber");
                    System.out.println(BuildingController.changeTaxRate(rateNumberString));
                } else if (showTaxRateMatcher.matches()) {
                    System.out.println(BuildingController.showTaxRate());
                } else {
                    System.out.println(Answers.INVALID_COMMAND);
                }
            } else if (isThisBuildingSelected(Buildings.SMALL_STONE_GATEHOUSE) ||
                    isThisBuildingSelected(Buildings.BIG_STONE_GATEHOUSE) || isThisBuildingSelected(Buildings.DRAW_BRIDGE)) {
                Matcher openOrCloseGatehouseMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.OPEN_OR_CLOSE_GATEHOUSE);
                Matcher repairMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.REPAIR_IT);
                Matcher showGateStateMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.SHOW_GATE_STATE);
                if (openOrCloseGatehouseMatcher.matches()) {
                    String order = openOrCloseGatehouseMatcher.group("order");
                    System.out.println(BuildingController.openOrCloseGatehouse(order));
                } else if (repairMatcher.matches()) {
                    System.out.println(BuildingController.repair());
                } else if (showGateStateMatcher.matches()) {
                    System.out.println(BuildingController.showStateOfGate());
                } else {
                    System.out.println(Answers.INVALID_COMMAND);
                }
            } else if (isThisBuildingSelected(Buildings.DEFENSE_TURRET) ||
                    isThisBuildingSelected(Buildings.PERIMETER_TURRET) ||
                    isThisBuildingSelected(Buildings.LOOKOUT_TOWER) ||
                    isThisBuildingSelected(Buildings.ROUND_TOWER) ||
                    isThisBuildingSelected(Buildings.SQUARE_TOWER)) {
                Matcher repairMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.REPAIR_IT);
                if (repairMatcher.matches()) {
                    System.out.println(BuildingController.repair());
                } else {
                    System.out.println(Answers.INVALID_COMMAND);
                }
            } else if (isThisBuildingSelected(Buildings.ARMOURY)) {
                Matcher showWeaponsMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.SHOW_WEAPONS);
                if (showWeaponsMatcher.matches()) {
                    System.out.print(BuildingController.showItems());
                } else {
                    System.out.println(Answers.INVALID_COMMAND);
                }
            } else if (isThisBuildingSelected(Buildings.BARRACK) ||
                    isThisBuildingSelected(Buildings.MERCENARY_POST) ||
                    isThisBuildingSelected(Buildings.ENGINEERS_GUILD) ||
                    isThisBuildingSelected(Buildings.CATHEDRAL) ||
                    isThisBuildingSelected(Buildings.TUNNELERS_GUILD)) {
                Matcher showUnitsListMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.SHOW_UNITS_LIST);
                Matcher buyUnitMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.BUY_UNIT);
                if (showUnitsListMatcher.matches()) {
                    System.out.print(BuildingController.showUnitsList());
                } else if (buyUnitMatcher.matches()) {
                    String unitType = buyUnitMatcher.group("unitname");
                    System.out.println(BuildingController.buyUnit(unitType));
                } else {
                    System.out.println(Answers.INVALID_COMMAND);
                }
            } else if (isThisBuildingSelected(Buildings.SHOP)) {
                MarketMenu.run(scanner);
            } else if (isThisBuildingSelected(Buildings.STOCK_PILE)) {
                Matcher showSavedGoodsMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.SHOW_GOODS_SAVED);
                if (showSavedGoodsMatcher.matches()) {
                    System.out.print(BuildingController.showSavedGoods());
                } else {
                    System.out.println(Answers.INVALID_COMMAND);
                }
            } else if (isThisBuildingSelected(Buildings.BLACK_SMITH) ||
                    isThisBuildingSelected(Buildings.FLETCHER) ||
                    isThisBuildingSelected(Buildings.POLE_TURNER)) {
                // TODO: ehtiaje ya na? age nist ke bikhial
            } else if (isThisBuildingSelected(Buildings.STABLE)) {
                Matcher howManyHorsesMatcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.HOW_MANY_HORSES);
                if (howManyHorsesMatcher.matches()) {

                } else {
                    System.out.println(Answers.INVALID_COMMAND);
                }
            } else if (isThisBuildingSelected(Buildings.GRANARY)) {
                // TODO: I don't know.
            }
        }
    }
}
