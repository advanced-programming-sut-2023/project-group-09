package controller.gamestructure;

import enumeration.Textures;
import model.building.Building;
import model.building.producerbuildings.Barrack;
import model.building.producerbuildings.ProducerBuilding;
import model.building.producerbuildings.Quarry;
import model.building.producerbuildings.WeaponProducer;
import model.building.storagebuildings.StorageBuilding;

import java.util.HashMap;

public class GameBuildings {
    public static HashMap<String, Building> buildings = new HashMap<>();
    public static HashMap<String, Building> producerBuildings = new HashMap<>();
    public static HashMap<String,Building> storageBuildings = new HashMap<>();

    public static void createBuildings(){
        createShop();
        createHovel();
        createChurch();
        createProducerBuildings();
        createStorageBuilding();
        createCastleBuildings();
    }

    public static void createProducerBuildings(){
        createBakery();
        createBlackSmith();
        createBrewery();
        createWheatFarm();
        createWoodCutter();
        createHopFarm();
        createArmourer();
        createAppleGarden();
        createInn();
        createIronMine();
        createDairyProducts();
        createPitchRig();
        createMill();
        createHuntingPost();
        createFletcher();
        createPoleTurner();
    }

    public static void createStorageBuilding(){
        createGranary();
        createStockPile();
        createArmoury();
    }

    public static void createCastleBuildings(){
        createMainCastle();
        createSmallStoneGatehouse();
        createBigStoneGatehouse();
        createDrawBridge();
        createLowWall();
        createStoneWall();
        createCrenulatedWall();
        createStairs();
        create
    }

    public static void createMainCastle() {

    }

    public static void createLowWall() {

    }
    public static void createStoneWall() {

    }
    public static void createCrenulatedWall() {

    }
    public static void createStairs() {
    }




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
                40,4,4,"weapon",100);
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
                "engineerGuild",80,4,11);
        barrack.addUnit("engineer");
        barrack.addUnit("ladderman");

        barrack.addCost("wood",10);
        barrack.setPrice(100);
        buildings.put("engineerGuild",barrack);
    }

    public static void createKillingPit() {
    }

    public static void createInn() {
        ProducerBuilding producerBuilding = new ProducerBuilding(1,0,"inn",
                40,5,5,5,"stockPile","resource","ale");
        producerBuilding.addCost("wood",20);
        producerBuilding.setPrice(100);
        producerBuilding.addRequired("ale",5);
        producerBuildings.put("inn",producerBuilding);
        buildings.put("inn",producerBuilding);
    }

    public static void createMill() {
        ProducerBuilding producerBuilding = new ProducerBuilding(3,0,"mill",
                40,3,3,3,"stockPile","resource","flour");
        producerBuilding.addCost("wood",20);
        producerBuilding.addRequired("wheat",3);
        producerBuildings.put("mill",producerBuilding);
        buildings.put("mill",producerBuilding);
    }

    public static void createIronMine() {
        ProducerBuilding producerBuilding = new ProducerBuilding(2,0,"ironMine",
                60,4,4,3,"stockPile","resource","iron");
        producerBuilding.addCost("wood",20);
        producerBuilding.enableHasSpecialTexture();
        producerBuilding.addTexture(Textures.IRON_TEXTURE);
        producerBuildings.put("ironMine",producerBuilding);
        buildings.put("ironMine",producerBuilding);
    }

    public static void createShop() {
        Building building = new Building(1,0,"shop",70,5,5);
        building.addCost("wood",5);
        buildings.put("shop",building);
    }

    public static void createOxTether() {

    }

    public static void createPitchRig() {
        ProducerBuilding producerBuilding = new ProducerBuilding(1,0,"pitchRig",
                40,3,3,5,"stockPile","resource","pitch");

        producerBuilding.addCost("wood",20);

        producerBuilding.enableHasSpecialTexture();
        producerBuilding.addTexture(Textures.OIL);


        producerBuildings.put("pitchRig",producerBuilding);
        buildings.put("pitchRig",producerBuilding);
    }

    public static void createQuarry() {
        Quarry producerBuilding = new Quarry(3,0,"quarry",
                60,6,6,12,"stockPile","resource","stone");
        producerBuilding.addCost("wood",20);
        producerBuilding.enableHasSpecialTexture();
        producerBuilding.addTexture(Textures.BOULDER);
        producerBuildings.put("quarry",producerBuilding);
        buildings.put("quarry",producerBuilding);
    }

    public static void createStockPile() {
        StorageBuilding storageBuilding = new StorageBuilding(0,0,"stockPile",
                40,5,5,"resource",100);
        storageBuilding.setItems(GameGoods.getHashMapOfResources());
        storageBuilding.addCost("wood",20);
        storageBuildings.put("stockPile",storageBuilding);
        buildings.put("stockPile",storageBuilding);
    }

    public static void createWoodCutter() {
        ProducerBuilding producerBuilding = new ProducerBuilding(1,0,"woodCutter",
                40,3,3,3,"stockPile","resource","wood");
        producerBuilding.addCost("wood",3);
        producerBuildings.put("woodCutter",producerBuilding);
        buildings.put("woodCutter",producerBuilding);
    }

    public static void createHovel() {
        Building building = new Building(0,0,"hovel",70,4,4);
        building.addCost("wood",6);
        producerBuildings.put("hovel",building);
        buildings.put("hovel",building);
    }

    public static void createChurch() {
        Building building = new Building(0,0,"church",70,15,15);
        building.setPrice(500);
        buildings.put("church",building);
    }

    public static void createCathedral() {
        Barrack barrack = new Barrack(0,0,
                "cathedral",100,20,20);

        barrack.addUnit("blackmonk");
        barrack.changeShouldBeOne();

        barrack.setPrice(1000);
        buildings.put("cathedral",barrack);
    }

    //==============
    public static void createArmourer() {
          WeaponProducer producerBuilding = new WeaponProducer(1,0,"armourer",
                40,4,4,1,"armoury","weapon","metalArmour");

        producerBuilding.addWeapon("metalArmour");


        producerBuilding.addCost("wood",20);
        producerBuilding.setPrice(100);


        producerBuilding.addRequired("iron",1);


        producerBuildings.put("armourer",producerBuilding);
        buildings.put("armourer",producerBuilding);
    }

    public static void createBlackSmith() {
        WeaponProducer producerBuilding = new WeaponProducer(1,0,"blackSmith",
                40,4,4,1,"armoury","weapon","sword");


        producerBuilding.addWeapon("sword");
        producerBuilding.addWeapon("sword");

        producerBuilding.addRequired("iron",1);


        producerBuilding.addCost("wood",20);
        producerBuilding.setPrice(100);


        producerBuildings.put("blackSmith",producerBuilding);
        buildings.put("blackSmith",producerBuilding);
    }

    public static void createFletcher() {
        WeaponProducer producerBuilding = new WeaponProducer(1,0,"fletcher",
                40,4,4,1,"armoury","weapon","bow");

        producerBuilding.addWeapon("bow");
        producerBuilding.addWeapon("crossBow");

        producerBuilding.addRequired("wood",2);


        producerBuilding.addCost("wood",20);
        producerBuilding.setPrice(100);


        producerBuildings.put("fletcher",producerBuilding);
        buildings.put("fletcher",producerBuilding);
    }

    public static void createPoleTurner() {
        WeaponProducer producerBuilding = new WeaponProducer(1,0,"poleTurner",
                40,4,4,1,"armoury","weapon","spear");

        producerBuilding.addWeapon("spear");
        producerBuilding.addWeapon("pike");

        producerBuilding.addRequired("wood",1);


        producerBuilding.addCost("wood",20);
        producerBuilding.setPrice(100);


        producerBuildings.put("poleTurner",producerBuilding);
        buildings.put("poleTurner",producerBuilding);
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
        Building building = new Building(0,0,"stable",70,5,5);
        building.addCost("wood",20);
        building.setPrice(400);
        producerBuildings.put("stable",building);
        buildings.put("stable",building);
    }

    public static void createTunnelersGuild() {
        Barrack barrack = new Barrack(0,0,
                "tunnelersGuild",80,4,11);
        barrack.addUnit("tunneler");
        barrack.addCost("wood",10);
        barrack.setPrice(100);
        buildings.put("tunneler",barrack);
    }

    public static void createAppleGarden() {
        ProducerBuilding producerBuilding = new ProducerBuilding(1,0,"appleGarden",
                40,9,9,10,"granary","food","apple");

        producerBuilding.addCost("wood",5);

        producerBuilding.enableHasSpecialTexture();
        producerBuilding.addTexture(Textures.OASIS_GRASS);
        producerBuilding.addTexture(Textures.THICK_GRASS);



        producerBuildings.put("appleGarden",producerBuilding);
        buildings.put("appleGarden",producerBuilding);
    }

    public static void createDairyProducts() {
        ProducerBuilding producerBuilding = new ProducerBuilding(1,0,"dairyProducts",
                40,9,9,3,"granary","food","cheese");

        producerBuilding.addCost("wood",10);

        producerBuilding.enableHasSpecialTexture();
        producerBuilding.addTexture(Textures.OASIS_GRASS);
        producerBuilding.addTexture(Textures.THICK_GRASS);



        producerBuildings.put("dairyProducts",producerBuilding);
        buildings.put("dairyProducts",producerBuilding);
    }

    public static void createHopFarm() {
        ProducerBuilding producerBuilding = new ProducerBuilding(1,0,"hopFarm",
                40,9,9,10,"stockPile","resource","hop");

        producerBuilding.addCost("wood",15);

        producerBuilding.enableHasSpecialTexture();
        producerBuilding.addTexture(Textures.OASIS_GRASS);
        producerBuilding.addTexture(Textures.THICK_GRASS);



        producerBuildings.put("hopFarm",producerBuilding);
        buildings.put("hopFarm",producerBuilding);
    }

    public static void createHuntingPost() {
        ProducerBuilding producerBuilding = new ProducerBuilding(1,0,"huntingPost",
                40,3,3,1,"granary","food","meat");

        producerBuilding.addCost("wood",5);

        producerBuilding.addRequired("cow",1);


        producerBuildings.put("huntingPost",producerBuilding);
        buildings.put("huntingPost",producerBuilding);
    }

    public static void createWheatFarm() {
        ProducerBuilding producerBuilding = new ProducerBuilding(1,0,"wheatFarm",
                40,9,9,10,"stockPile","resource","wheat");

        producerBuilding.addCost("wood",15);

        producerBuilding.enableHasSpecialTexture();
        producerBuilding.addTexture(Textures.OASIS_GRASS);
        producerBuilding.addTexture(Textures.THICK_GRASS);



        producerBuildings.put("wheatFarm",producerBuilding);
        buildings.put("wheatFarm",producerBuilding);
    }

    public static void createBakery() {
        ProducerBuilding producerBuilding = new ProducerBuilding(1,0,"bakery",
                40,4,4,5,"granary","food","bread");

        producerBuilding.addCost("wood",10);

        producerBuilding.addRequired("flour",1);

        producerBuildings.put("bakery",producerBuilding);
        buildings.put("bakery",producerBuilding);
    }

    public static void createBrewery() {
        ProducerBuilding producerBuilding = new ProducerBuilding(1,0,"brewery",
                40,4,4,10,"stockPile","resource","ale");

        producerBuilding.addCost("wood",10);

        producerBuilding.addRequired("hop",5);
        producerBuildings.put("wheatFarm",producerBuilding);
        buildings.put("wheatFarm",producerBuilding);
    }

    public static void createGranary() {
        StorageBuilding storageBuilding = new StorageBuilding(0,0,"granary",
                40,4,4,"food",100);
        storageBuilding.setItems(GameGoods.getHashMapOfFoods());
        storageBuilding.addCost("wood",5);
        storageBuildings.put("granary",storageBuilding);
        buildings.put("granary",storageBuilding);
    }

}
