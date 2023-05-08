package controller;

import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameGoods;
import controller.gamestructure.GameHumans;
import controller.gamestructure.GameTools;
import enumeration.Paths;
import model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.building.Building;
import model.building.castlebuildings.Gatehouse;
import model.building.castlebuildings.Tower;
import model.building.castlebuildings.Wall;
import model.building.producerbuildings.Barrack;
import model.building.producerbuildings.ProducerBuilding;
import model.building.producerbuildings.WeaponProducer;
import model.building.storagebuildings.StorageBuilding;
import model.goods.Goods;
import model.human.military.ArabianMercenary;
import model.human.military.EuropeanTroop;
import model.tools.Tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBController {


    // load & save users from files
    public static void loadAllUsers(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.USERS_PATH.getPath());
            String text = new String(Files.readAllBytes(Path.of(Paths.USERS_PATH.getPath())));
            ArrayList<User> allUsers = gson.fromJson(text, new TypeToken<List<User>>(){}.getType());
            Application.setUsers(allUsers);
            if (allUsers == null) {
                Application.setUsers(new ArrayList<>());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.[load users]");
            e.printStackTrace();
        }
    }

    public static void saveAllUsers(){

        try {
            checkFileExist(Paths.USERS_PATH.getPath());
            File file = new File(Paths.USERS_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            if(Application.getUsers() != null){
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(Application.getUsers());
                fileWriter.write(json);
            }else {
                fileWriter.write("");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[save users]");
            e.printStackTrace();
        }
    }

    public static void loadCurrentUser(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.CURRENT_USER_PATH.getPath());
            String text = new String(Files.readAllBytes(Path.of(Paths.CURRENT_USER_PATH.getPath())));
            User user = gson.fromJson(text, User.class);
            if (user != null) {
                User currentUser = Application.getUserByUsername(user.getUsername());
                Application.setCurrentUser(currentUser);
            } else {
                Application.setCurrentUser(null);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.[load current user]");
            e.printStackTrace();
        }
    }

    public static void saveCurrentUser(){
        try {
            checkFileExist(Paths.CURRENT_USER_PATH.getPath());
            File file = new File(Paths.CURRENT_USER_PATH.getPath());
            FileWriter fileWriter = new FileWriter(file);
            if(Application.getCurrentUser() != null){
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(Application.getCurrentUser());
                fileWriter.write(json);
            }else {
                fileWriter.write("");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[save current user]");
            e.printStackTrace();
        }
    }


    // load goods from file
    public static void loadGoods(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.GOODS_PATH.getPath());
            String text = new String(Files.readAllBytes(Path.of(Paths.GOODS_PATH.getPath())));
            GameGoods.goods = gson.fromJson(text, new TypeToken<HashMap<String, Goods>>(){}.getType());
            loadFoods();
            loadResources();
            loadWeapons();
        } catch (IOException e) {
            System.out.println("An error occurred.[load goods]");
            e.printStackTrace();
        }
    }

    private static void loadFoods(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.FOODS_PATH.getPath());
            String text = new String(Files.readAllBytes(Path.of(Paths.FOODS_PATH.getPath())));
            GameGoods.foods = gson.fromJson(text, new TypeToken<HashMap<String, Goods>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load goods]");
            e.printStackTrace();
        }
    }
    private static void loadResources(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.RESOURCES_PATH.getPath());
            String text = new String(Files.readAllBytes(Path.of(Paths.RESOURCES_PATH.getPath())));
            GameGoods.resources = gson.fromJson(text, new TypeToken<HashMap<String, Goods>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load goods]");
            e.printStackTrace();
        }
    }
    private static void loadWeapons(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.WEAPONS_PATH.getPath());
            String text = new String(Files.readAllBytes(Path.of(Paths.WEAPONS_PATH.getPath())));
            GameGoods.weapons = gson.fromJson(text, new TypeToken<HashMap<String, Goods>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load goods]");
            e.printStackTrace();
        }
    }


    //load military from file
    public static void loadMilitary(){
        HashMap<String, EuropeanTroop> europeanTroops = loadEuropeanTroops();
        HashMap<String, ArabianMercenary> arabianMercenaries = loadArabianMercenaries();

        GameHumans.militaries.putAll(europeanTroops);
        GameHumans.militaries.putAll(arabianMercenaries);

    }

    public static HashMap<String, EuropeanTroop> loadEuropeanTroops(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.EUROPEAN_TROOP_PATH.getPath());
            String text = new String(Files.readAllBytes(Path.of(Paths.EUROPEAN_TROOP_PATH.getPath())));
            return gson.fromJson(text, new TypeToken<HashMap<String, EuropeanTroop>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load humans]");
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, ArabianMercenary> loadArabianMercenaries(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.ARABIAN_MERCENARY_PATH.getPath());
            String text = new String(Files.readAllBytes(Path.of(Paths.ARABIAN_MERCENARY_PATH.getPath())));
            return gson.fromJson(text, new TypeToken<HashMap<String, ArabianMercenary>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load humans]");
            e.printStackTrace();
        }
        return null;
    }

    public static void loadBuildings(){
        HashMap<String, Wall> wallHashMap = loadWalls();
        HashMap<String, Gatehouse> gatehouseHashMap = loadGatehouses();
        HashMap<String, Tower> towerHashMap = loadTowers();
        HashMap<String, Barrack> barrackHashMap = loadBarracks();
        HashMap<String, ProducerBuilding> producerBuildingHashMap = loadProducerBuilding();
        HashMap<String, WeaponProducer> weaponProducerHashMap = loadWeaponProducer();
        HashMap<String, StorageBuilding> storageBuildingHashMap = loadStorageBuilding();
        HashMap<String, Building> buildingHashMap = loadOther();

        GameBuildings.buildings.putAll(wallHashMap);
        GameBuildings.buildings.putAll(gatehouseHashMap);
        GameBuildings.buildings.putAll(towerHashMap);
        GameBuildings.buildings.putAll(barrackHashMap);
        GameBuildings.buildings.putAll(producerBuildingHashMap);
        GameBuildings.buildings.putAll(weaponProducerHashMap);
        GameBuildings.buildings.putAll(storageBuildingHashMap);
        GameBuildings.buildings.putAll(buildingHashMap);
        GameBuildings.createQuarry();
        GameBuildings.createOxTether();
        GameBuildings.createMainCastle();


        GameBuildings.storageBuildings.putAll(storageBuildingHashMap);


        GameBuildings.castleBuildings.putAll(wallHashMap);
        GameBuildings.castleBuildings.putAll(gatehouseHashMap);
        GameBuildings.castleBuildings.putAll(towerHashMap);


        GameBuildings.producerBuildings.putAll(producerBuildingHashMap);
        GameBuildings.producerBuildings.putAll(weaponProducerHashMap);

    }

    public static HashMap<String, Wall> loadWalls(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.BUILDINGS_PATH.getPath() + "walls.json");
            String text = new String(Files.readAllBytes(Path.of(Paths.BUILDINGS_PATH.getPath() + "walls.json")));
            return gson.fromJson(text, new TypeToken<HashMap<String, Wall>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load buildings]");
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, Gatehouse> loadGatehouses(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.BUILDINGS_PATH.getPath() + "gateHouses.json");
            String text = new String(Files.readAllBytes(Path.of(Paths.BUILDINGS_PATH.getPath() + "gateHouses.json")));
            return gson.fromJson(text, new TypeToken<HashMap<String, Gatehouse>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load buildings]");
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, Tower> loadTowers(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.BUILDINGS_PATH.getPath() + "towers.json");
            String text = new String(Files.readAllBytes(Path.of(Paths.BUILDINGS_PATH.getPath() + "towers.json")));
            return gson.fromJson(text, new TypeToken<HashMap<String, Tower>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load buildings]");
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, Barrack> loadBarracks(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.BUILDINGS_PATH.getPath() + "barracks.json");
            String text = new String(Files.readAllBytes(Path.of(Paths.BUILDINGS_PATH.getPath() + "barracks.json")));
            return gson.fromJson(text, new TypeToken<HashMap<String, Barrack>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load buildings]");
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, ProducerBuilding> loadProducerBuilding(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.BUILDINGS_PATH.getPath() + "producerBuildings.json");
            String text = new String(Files.readAllBytes(Path.of(Paths.BUILDINGS_PATH.getPath() + "producerBuildings.json")));
            return gson.fromJson(text, new TypeToken<HashMap<String, ProducerBuilding>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load buildings]");
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, WeaponProducer> loadWeaponProducer(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.BUILDINGS_PATH.getPath() + "weaponProducers.json");
            String text = new String(Files.readAllBytes(Path.of(Paths.BUILDINGS_PATH.getPath() + "weaponProducers.json")));
            return gson.fromJson(text, new TypeToken<HashMap<String, WeaponProducer>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load buildings]");
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, StorageBuilding> loadStorageBuilding(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.BUILDINGS_PATH.getPath() + "storageBuilding.json");
            String text = new String(Files.readAllBytes(Path.of(Paths.BUILDINGS_PATH.getPath() + "storageBuilding.json")));
            return gson.fromJson(text, new TypeToken<HashMap<String, StorageBuilding>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load buildings]");
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, Building> loadOther(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.BUILDINGS_PATH.getPath() + "other.json");
            String text = new String(Files.readAllBytes(Path.of(Paths.BUILDINGS_PATH.getPath() + "other.json")));
            return gson.fromJson(text, new TypeToken<HashMap<String, Building>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load buildings]");
            e.printStackTrace();
        }
        return null;
    }
    public static void loadTools(){
        try {
            Gson gson = new Gson();
            checkFileExist(Paths.TOOLS_PATH.getPath());
            String text = new String(Files.readAllBytes(Path.of(Paths.TOOLS_PATH.getPath())));
            GameTools.tools = gson.fromJson(text, new TypeToken<HashMap<String, Tool>>(){}.getType());
        } catch (IOException e) {
            System.out.println("An error occurred.[load tools]");
            e.printStackTrace();
        }
    }
    public static void checkFileExist(String fileAddress){
        try {
            File myObj = new File(fileAddress);
            boolean check = myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.[check file exist]");
            e.printStackTrace();
        }
    }
}
