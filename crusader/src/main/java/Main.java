import controller.DeletedController;
import controller.MainController;


public class Main {
    public static void main(String[] args) {
        DeletedController.makeEuropeanTroops();
        DeletedController.makeArabianMercenaries();
        MainController.run();
    }
}
