package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.gamestructure.*;
import enumeration.Paths;
import javafx.scene.paint.Color;
import model.User;
import model.building.Building;
import model.building.castlebuildings.Gatehouse;
import model.building.castlebuildings.Tower;
import model.building.castlebuildings.Wall;
import model.building.producerbuildings.Barrack;
import model.building.producerbuildings.ProducerBuilding;
import model.building.producerbuildings.WeaponProducer;
import model.building.storagebuildings.StorageBuilding;
import model.chat.Message;
import model.chat.Room;
import model.game.Map;
import model.game.Tile;
import model.goods.Goods;
import model.gsonmodels.ColorDeserializer;
import model.gsonmodels.ColorSerializer;
import model.human.military.ArabianMercenary;
import model.human.military.EuropeanTroop;
import model.tools.Tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBController {


    // load & save users from files
    public static void loadAllUsers(){
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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

    public static void loadRooms() {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Path.of("files/chat/rooms.json")));
            ArrayList<Room> rooms = new GsonBuilder().create().fromJson(content, new TypeToken<List<Room>>(){}.getType());
            if (rooms == null) rooms = new ArrayList<>();
            Application.setRooms(rooms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveRooms() {
        try {
            String gson = new GsonBuilder().setPrettyPrinting().create().toJson(Application.getRooms());
            File file = new File("files/chat/rooms.json");
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadMessages() {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Path.of("files/chat/messages.json")));
            ArrayList<Message> messages = new GsonBuilder().create().fromJson(content, new TypeToken<List<Message>>(){}.getType());
            if (messages == null) messages = new ArrayList<>();
            Application.setMessages(messages);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveMessages() {
        try {
            String gson = new GsonBuilder().setPrettyPrinting().create().toJson(Application.getMessages());
            File file = new File("files/chat/messages.json");
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // load goods from file
    public static void loadGoods(){
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
        GameHumans.createEngineer();
        GameHumans.createTunneler();
    }

    public static HashMap<String, EuropeanTroop> loadEuropeanTroops(){
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();;
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();
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

    public static void saveMap(Map map, String fileAddress) {
        try {
            checkFileExist(fileAddress);
            File file = new File(fileAddress);
            FileWriter fileWriter = new FileWriter(file);
                Gson gson = new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.STATIC,
                                Modifier.TRANSIENT,
                                Modifier.VOLATILE)
                        .create();
                String json = gson.toJson(map);
                fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.[save current user]");
            e.printStackTrace();
        }
    }

    public static Map loadMap(String filePath) {
        Map map = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE).create()
                .fromJson(FileController.readFile(filePath), Map.class);
        for (int i = 0;i < map.getLength();i++){
            for (int j = 0;j < map.getWidth();j++){
                Tile tile = map.getTile(i,j);
                tile.setColor();
            }
        }
        return map;
    }

    public static void loadAllMaps() {
        File mapsFolder = new File("src/main/resources/savedmaps/");
        for (File file : mapsFolder.listFiles()) {
            if (file.getName().endsWith(".json")) {
                Map map = new GsonBuilder().setPrettyPrinting().create().fromJson(FileController.readFile(file) , Map.class);
                GameMaps.allMaps.put(map.getName() , map);
            }
        }
    }


}
