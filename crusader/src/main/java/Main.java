import model.game.Tile;
import view.MapMenu;
import view.SignupMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SignupMenu.run(scanner);
    }
}
