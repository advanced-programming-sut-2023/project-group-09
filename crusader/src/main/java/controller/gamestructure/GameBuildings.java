package controller.gamestructure;

import model.building.Building;
import model.building.producerbuildings.Barrack;
import model.building.producerbuildings.ProducerBuilding;
import model.building.storagebuildings.StorageBuilding;

import java.util.HashMap;

public class GameBuildings {
    public static HashMap<String, Building> buildings = new HashMap<>();
    public static HashMap<String, Building> producerBuildings = new HashMap<>();
    public static HashMap<String,Building> storageBuildings = new HashMap<>();

    public static void createSmallStoneGatehouse() {
    }

    public static void createBigStoneGatehouse() {
    }

    public static void createDrawBridge() {
    }

    public static void createLookoutTower() {
    }

    public static void createPerimeterTower() {
    }

    public static void createTurret() {
    }

    public static void createRoundTower() {
    }

    public static void createSquareTower() {
    }

    //=====================
    public static void createArmoury() {
        StorageBuilding storageBuilding = new StorageBuilding(0,0,"armoury",
                40,4,4,"weapons",100);
        storageBuilding.setItems(GameGoods.getHashMapOfWeapons());
        storageBuilding.addCost("wood",5);
        storageBuildings.put("armoury",storageBuilding);
        buildings.put("armoury",storageBuilding);
    }

    public static void createBarrack() {
        Barrack barrack = new Barrack(0,0,
        "barrack",80,9,9);
        barrack.addUnit("archer");
        barrack.addUnit("crossbowman");
        barrack.addUnit("spearman");
        barrack.addUnit("pikeman");
        barrack.addUnit("maceman");
        barrack.addUnit("swordsman");
        barrack.addUnit("knight");

        barrack.addCost("stone",15);
        buildings.put("barrack",barrack);
    }

    public static void createMercenaryPost() {
        Barrack barrack = new Barrack(0,0,
                "mercenaryPost",80,9,9);
        barrack.addUnit("archerBow");
        barrack.addUnit("slave");
        barrack.addUnit("spearman");
        barrack.addUnit("slinger");
        barrack.addUnit("assassin");
        barrack.addUnit("horseArcher");
        barrack.addUnit("arabianSwordsman");
        barrack.addUnit("fireThrower");

        barrack.addCost("wood",10);
        buildings.put("mercenaryPost",barrack);
    }

    public static void createEngineerGuild() {
        Barrack barrack = new Barrack(0,0,
                "engineerGuild",80,9,9);
        barrack.addUnit("engineer");
        barrack.addUnit("ladderman");

        barrack.addCost("wood",10);
        barrack.setPrice(100);
        buildings.put("barrack",barrack);
    }

    public static void createKillingPit() {
    }

    public static void createInn() {
    }

    public static void createMill() {
    }

    public static void createIronMine() {
    }

    public static void createShop() {
    }

    public static void createOxTether() {
    }

    public static void createPitchRig() {
    }

    public static void createQuarry() {
    }

    public static void createStockPile() {
        StorageBuilding storageBuilding = new StorageBuilding(0,0,"stockPile",
                40,5,5,"resources",100);
        storageBuilding.setItems(GameGoods.getHashMapOfResources());
        storageBuildings.put("stockPile",storageBuilding);
        buildings.put("stockPile",storageBuilding);
    }

    public static void createWoodCutter() {

    }

    public static void createHovel() {
    }

    public static void createChurch() {
    }

    public static void createCathedral() {
    }

    //==============
    public static void createArmourer() {
    }

    public static void createBlackSmith() {
    }

    public static void createFletcher() {
    }

    public static void createPoleTurner() {
    }

    public static void createTunnelEntrance() {
    }

    public static void createOilSmelter() {
    }

    public static void createCagedWarDogs() {
    }

    public static void createSiegeTent() {
    }

    public static void createStable() {
    }

    public static void createTunnelersGuild() {
    }

    public static void createAppleGarden() {
    }

    public static void createDairyProducts() {
    }

    public static void createHopGarden() {
    }

    public static void createHuntingPost() {
    }

    public static void createWheatFarm() {
    }

    public static void createBakery() {
    }

    public static void createBrewery() {
    }

    public static void createGranary() {
        StorageBuilding storageBuilding = new StorageBuilding(0,0,"granary",
                40,4,4,"foods",100);
        storageBuilding.setItems(GameGoods.getHashMapOfFoods());
        storageBuilding.addCost("wood",5);
        storageBuildings.put("granary",storageBuilding);
        buildings.put("granary",storageBuilding);
    }

    public static void createShortWall() {
    }

    public static void createWall() {
    }

    public static void createLongWall() {
    }

    public static void createStairs() {
    }
}
