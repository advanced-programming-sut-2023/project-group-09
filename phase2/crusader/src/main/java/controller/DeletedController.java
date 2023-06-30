package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.gamestructure.GameBuildings;
import controllers.gamestructure.GameGoods;
import controllers.gamestructure.GameHumans;
import controllers.gamestructure.GameTools;
import enumeration.Paths;
import model.building.Building;
import model.building.castlebuildings.CastleBuilding;
import model.building.castlebuildings.Gatehouse;
import model.building.castlebuildings.Tower;
import model.building.castlebuildings.Wall;
import model.building.producerbuildings.Barrack;
import model.building.producerbuildings.ProducerBuilding;
import model.building.producerbuildings.WeaponProducer;
import model.building.storagebuildings.StorageBuilding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DeletedController {
    public static void makeEuropeanTroops(){
        try {
            DBController.checkFileExist(Paths.EUROPEAN_TROOP_PATH.getPath());
            File file = new File(Paths.EUROPEAN_TROOP_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            GameHumans.addEuropeanTroops();
            String json = gson.toJson(GameHumans.militaries);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add goods]");
            e.printStackTrace();
        }
    }
    public static void makeArabianMercenaries(){
        try {
            DBController.checkFileExist(Paths.ARABIAN_MERCENARY_PATH.getPath());
            File file = new File(Paths.ARABIAN_MERCENARY_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            GameHumans.militaries.clear();
            GameHumans.addArabianMercenaries();
            String json = gson.toJson(GameHumans.militaries);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add goods]");
            e.printStackTrace();
        }
    }
    public static void makeGoodsFile(){
        try {
            DBController.checkFileExist(Paths.GOODS_PATH.getPath());
            File file = new File(Paths.GOODS_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            GameGoods.addGoods();
            String json = gson.toJson(GameGoods.goods);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add goods]");
            e.printStackTrace();
        }
    }

    public static void makeFoodsFile(){
        try {
            DBController.checkFileExist(Paths.FOODS_PATH.getPath());
            File file = new File(Paths.FOODS_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            GameGoods.addGoods();
            String json = gson.toJson(GameGoods.foods);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add goods]");
            e.printStackTrace();
        }
    }

    public static void makeResourcesFile(){
        try {
            DBController.checkFileExist(Paths.RESOURCES_PATH.getPath());
            File file = new File(Paths.RESOURCES_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            GameGoods.addGoods();
            String json = gson.toJson(GameGoods.resources);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add goods]");
            e.printStackTrace();
        }
    }

    public static void makeWeaponsFile(){
        try {
            DBController.checkFileExist(Paths.WEAPONS_PATH.getPath());
            File file = new File(Paths.WEAPONS_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            GameGoods.addGoods();
            String json = gson.toJson(GameGoods.weapons);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add goods]");
            e.printStackTrace();
        }
    }

    public static void makeBuildingsFile(){

        GameBuildings.addBuildings();
        HashMap<String, Wall> wallHashMap = new HashMap<>();
        HashMap<String, Gatehouse> gatehouseHashMap = new HashMap<>();
        HashMap<String, Tower>towerHashMap = new HashMap<>();
        HashMap<String, Barrack> barrackHashMap = new HashMap<>();
        HashMap<String, ProducerBuilding> producerBuildingHashMap = new HashMap<>();
        HashMap<String, WeaponProducer> weaponProducerHashMap = new HashMap<>();
        HashMap<String, StorageBuilding> storageBuildingHashMap = new HashMap<>();
        HashMap<String, Building> buildingHashMap = new HashMap<>();
        for (String name: GameBuildings.buildings.keySet()){
            Building building = GameBuildings.buildings.get(name);
            if(building instanceof CastleBuilding){
                if(building instanceof Wall){
                    wallHashMap.put(name, (Wall) building);
                } else if (building instanceof Gatehouse) {
                    gatehouseHashMap.put(name, (Gatehouse) building);
                } else if (building instanceof  Tower) {
                    towerHashMap.put(name, (Tower) building);
                }
            } else if (building instanceof  Barrack) {
                barrackHashMap.put(name, (Barrack) building);
            } else if (building instanceof  StorageBuilding) {
                storageBuildingHashMap.put(name, (StorageBuilding) building);
            }else if (building instanceof  ProducerBuilding){
                if(building instanceof WeaponProducer){
                    weaponProducerHashMap.put(name, (WeaponProducer) building);
                }else {
                    producerBuildingHashMap.put(name, (ProducerBuilding) building);
                }
            }else{
                buildingHashMap.put(name,building);
            }
        }
        makeWallsFile(wallHashMap);
        makeBarrackFile(barrackHashMap);
        makeStorageBuildingFile(storageBuildingHashMap);
        makeTowerFile(towerHashMap);
        makeGateHouseFile(gatehouseHashMap);
        makeProducerBuildingFile(producerBuildingHashMap);
        makeWeaponBuildingFile(weaponProducerHashMap);
        makeOtherBuildingFile(buildingHashMap);
    }
    public static void makeToolsFile(){
        try {
            DBController.checkFileExist(Paths.TOOLS_PATH.getPath());
            File file = new File(Paths.TOOLS_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            GameTools.createTools();
            String json = gson.toJson(GameTools.tools);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add tools]");
            e.printStackTrace();
        }
    }
    public static void makeWallsFile(HashMap<String,Wall> wallHashMap){
        try {
            DBController.checkFileExist(Paths.BUILDINGS_PATH.getPath() + "walls.json");
            File file = new File(Paths.BUILDINGS_PATH.getPath() + "walls.json");
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(wallHashMap);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add buildings]");
            e.printStackTrace();
        }
    }

    public static void makeGateHouseFile(HashMap<String,Gatehouse> gatehouseHashMap){
        try {
            DBController.checkFileExist(Paths.BUILDINGS_PATH.getPath() + "gateHouses.json");
            File file = new File(Paths.BUILDINGS_PATH.getPath() + "gateHouses.json");
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(gatehouseHashMap);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add buildings]");
            e.printStackTrace();
        }
    }

    public static void makeTowerFile(HashMap<String,Tower> towerHashMap){
        try {
            DBController.checkFileExist(Paths.BUILDINGS_PATH.getPath() + "towers.json");
            File file = new File(Paths.BUILDINGS_PATH.getPath() + "towers.json");
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(towerHashMap);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add buildings]");
            e.printStackTrace();
        }
    }

    public static void makeBarrackFile(HashMap<String,Barrack> barrackHashMap){
        try {
            DBController.checkFileExist(Paths.BUILDINGS_PATH.getPath() + "barracks.json");
            File file = new File(Paths.BUILDINGS_PATH.getPath() + "barracks.json");
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(barrackHashMap);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add buildings]");
            e.printStackTrace();
        }
    }

    public static void makeProducerBuildingFile(HashMap<String,ProducerBuilding> producerBuildingHashMap){
        try {
            DBController.checkFileExist(Paths.BUILDINGS_PATH.getPath() + "producerBuildings.json");
            File file = new File(Paths.BUILDINGS_PATH.getPath() + "producerBuildings.json");
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(producerBuildingHashMap);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add buildings]");
            e.printStackTrace();
        }
    }

    public static void makeWeaponBuildingFile(HashMap<String,WeaponProducer> weaponProducerHashMap){
        try {
            DBController.checkFileExist(Paths.BUILDINGS_PATH.getPath() + "weaponProducers.json");
            File file = new File(Paths.BUILDINGS_PATH.getPath() + "weaponProducers.json");
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(weaponProducerHashMap);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add buildings]");
            e.printStackTrace();
        }
    }
    public static void makeOtherBuildingFile(HashMap<String,Building> other){
        try {
            DBController.checkFileExist(Paths.BUILDINGS_PATH.getPath() + "other.json");
            File file = new File(Paths.BUILDINGS_PATH.getPath() + "other.json");
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(other);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add buildings]");
            e.printStackTrace();
        }
    }

    public static void makeStorageBuildingFile(HashMap<String,StorageBuilding> storageBuildingHashMap){
        try {
            DBController.checkFileExist(Paths.BUILDINGS_PATH.getPath() + "storageBuilding.json");
            File file = new File(Paths.BUILDINGS_PATH.getPath() + "storageBuilding.json");
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(storageBuildingHashMap);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[add buildings]");
            e.printStackTrace();
        }
    }

}
