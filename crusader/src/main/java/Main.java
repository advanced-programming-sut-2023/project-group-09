import controller.*;

public class Main {
    public static void main(String[] args) {
        DeletedController.makeBuildingsFile();
        DeletedController.makeEuropeanTroops();
        DeletedController.makeArabianMercenaries();
        MainController.run();
    }
}
