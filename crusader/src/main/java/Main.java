import controller.*;
import controller.gamestructure.GameBuildings;
import enumeration.commands.UnitMenuCommands;
import view.UnitMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        DeletedController.makeFoodsFile();
//        DeletedController.makeResourcesFile();
//        DeletedController.makeWeaponsFile();
//        DeletedController.makeGoodsFile();
//        DeletedController.makeBuildingsFile();
//        MainController.loadGame();
//        System.out.println(GameBuildings.buildings.size());
        //MainController.run();
        UnitMenu.run(new Scanner(System.in));
    }
}
