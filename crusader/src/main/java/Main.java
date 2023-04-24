import controller.*;
import controller.gamestructure.GameBuildings;
import enumeration.commands.UnitMenuCommands;
import view.PrimaryMenu;
import view.UnitMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PrimaryMenu.run(new Scanner(System.in));
    }
}
