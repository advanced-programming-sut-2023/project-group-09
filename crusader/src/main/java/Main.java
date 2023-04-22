import controller.*;

public class Main {
    public static void main(String[] args) {
        DeletedController.makeFoodsFile();
        DeletedController.makeResourcesFile();
        DeletedController.makeWeaponsFile();
        DeletedController.makeGoodsFile();

        MainController.run();
    }
}
